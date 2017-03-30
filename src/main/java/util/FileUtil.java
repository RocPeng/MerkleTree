package util;

import java.io.File;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;

import model.FileBean;

public class FileUtil {
	public static String filesPath = "";

	/**
	 * 用户设置完路径后,生成merkle树
	 */
	public static void setUserPath(String path) {
		filesPath = GlobalConfig.filesRoot + path + "/";
		File fileDir = new File(filesPath);
		if (!fileDir.exists()) {
			// 按照指定的路径创建文件夹
			fileDir.mkdirs();
		}
	}

	/**
	 * 读取文件转换成String
	 */
	public static String readFileToStr(String filePath) {
		String content = "";
		File file = new File(filePath);
		try {
			content = FileUtils.readFileToString(file, Charset.forName("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * String写入文件
	 */
	public static void writeStrToFile(String filePath, String content) {
		File file = new File(filePath);
		try {
			FileUtils.writeStringToFile(file, content, Charset.forName("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取某一目录下所有文件的md5 hash值,并存放在/md5/hash文件
	 */
	public static void getFilesHash(String filePath) {
		List<FileBean> list = new LinkedList<>();
		list = getFilesParams(filePath);
		writeStrToFile(filePath + "md5/hash", JSON.toJSONString(list));
	}

	/**
	 * hash相同也不归并
	 * 
	 * @return
	 */
	public static List<FileBean> getFiles(String filePath) {
		List<FileBean> filesParams = new LinkedList();
		List<String> filesPath = new ArrayList<String>();
		filesPath = getFiles(filePath, filesPath);
		for (String path : filesPath) {
			FileBean fileBean = new FileBean();
			fileBean.addFileName(path.substring(path.lastIndexOf('/') + 1));
			fileBean.setFileSize(GetFileSize(new File(path)));
			fileBean.setFileHash(EncryptUtil.getMD5(readFileToStr(path)));
			filesParams.add(fileBean);
		}
		return filesParams;
	}

	/**
	 * 获取某一路径下所有文件的详细信息(文件名,大小,hash值等,hash相同则归并)
	 */
	public static List<FileBean> getFilesParams(String filePath) {
		List<FileBean> filesParams = new LinkedList();
		List<String> filesPath = new ArrayList<String>();
		filesPath = getFiles(filePath, filesPath);
		for (String path : filesPath) {
			FileBean fileBean = new FileBean();
			fileBean.addFileName(path.substring(path.lastIndexOf('/') + 1));
			fileBean.setFileSize(GetFileSize(new File(path)));
			fileBean.setFileHash(EncryptUtil.getMD5(readFileToStr(path)));
			if (isContain(filesParams, fileBean)) {
				getFileBean(filesParams, fileBean).addAllFileName(fileBean.getFileName());
			} else {
				filesParams.add(fileBean);
			}
		}
		return filesParams;
	}

	/**
	 * 判断同一目录下是否有hash相同的文件
	 */
	public static boolean isContain(List<FileBean> list, FileBean file) {
		for (FileBean bean : list) {
			if (bean.getFileHash().equals(file.getFileHash()))
				return true;
		}
		return false;
	}

	public static FileBean getFileBean(List<FileBean> list, FileBean file) {
		for (FileBean bean : list) {
			if (bean.getFileHash().equals(file.getFileHash()))
				return bean;
		}
		return null;
	}
	
	/**
	 * 只获取文件名,不获取全路径
	 */
	public static List<String> getFileNames(String filePath, List<String> filelist) {
		filelist=getFiles(filePath,filelist);
		List<String> fileNames=new LinkedList<>();
		for(String path:filelist){
			fileNames.add(path.substring(path.lastIndexOf('/')+1));
		}
		Iterator<String> iterator=fileNames.iterator();
		while(iterator.hasNext()){
			String str=iterator.next();
			if(str.startsWith("."))
				iterator.remove();
		}
		return fileNames;
	}
	/**
	 * 通过递归得到某一路径下所有的文件的名称(不包括子文件夹),分装到list里面
	 *
	 * @param filePath
	 * @param filelist
	 * @return
	 */
	public static List<String> getFiles(String filePath, List<String> filelist) {

		File root = new File(filePath);
		if (!root.exists()) {
			root.mkdirs();
		} else {
			File[] files = root.listFiles();
			Arrays.sort(files, new CompratorByLastModified());
			for (File file : files) {
				if (!file.isDirectory()) {
					// logger.info("目录:" + filePath + "文件全路径:" +
					// file.getAbsolutePath());
					filelist.add(file.getAbsolutePath());
					// filelist.add(file.getName());
				}
			}
		}
		return filelist;
	}

	/**
	 * 通过递归得到某一路径下所有的文件的名称(包括子文件夹),分装到list里面
	 *
	 * @param filePath
	 * @param filelist
	 * @return
	 */
	public static List<String> getDeepFiles(String filePath, List<String> filelist) {

		File root = new File(filePath);
		if (!root.exists()) {
			root.mkdirs();
		} else {
			File[] files = root.listFiles();
			Arrays.sort(files, new CompratorByLastModified());
			for (File file : files) {
				if (file.isDirectory()) {
					getDeepFiles(file.getAbsolutePath(), filelist);
				} else {
					// logger.info("目录:" + filePath + "文件全路径:" +
					// file.getAbsolutePath());
					filelist.add(file.getAbsolutePath());
					// filelist.add(file.getName());
				}
			}
		}
		return filelist;
	}

	// 根据文件修改时间进行比较的内部类
	static class CompratorByLastModified implements Comparator<File> {

		public int compare(File f1, File f2) {
			long diff = f1.lastModified() - f2.lastModified();
			if (diff > 0) {
				return 1;
			} else if (diff == 0) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	public static String GetFileSize(File file) {
		String size = "";
		if (file.exists() && file.isFile()) {
			long fileS = file.length();
			DecimalFormat df = new DecimalFormat("#.00");
			if (fileS < 1024) {
				size = df.format((double) fileS) + "BT";
			} else if (fileS < 1048576) {
				size = df.format((double) fileS / 1024) + "KB";
			} else if (fileS < 1073741824) {
				size = df.format((double) fileS / 1048576) + "MB";
			} else {
				size = df.format((double) fileS / 1073741824) + "GB";
			}
		} else if (file.exists() && file.isDirectory()) {
			size = "";
		} else {
			size = "0BT";
		}
		return size;
	}

	public static void main(String[] args) {
		// List<FileBean> list=new LinkedList<FileBean>();
		// list=getFilesParams("/Users/roc_peng/Downloads/merkle/roc/");
		// String tets=JSON.toJSONString(list);
		// System.out.println(tets);
		// writeStrToFile("/Users/roc_peng/Downloads/merkle/roc/json", tets);
		getFilesHash("/Users/roc_peng/Downloads/merkle/roc/");
	}
}
