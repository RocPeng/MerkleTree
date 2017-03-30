package dao;

import java.io.File;
import java.util.List;
import java.util.Map;


import util.FileUtil;

// User数据库操作具体实现类
public class FileDao implements IFileDao {
	
	public void println(Object object){
		System.out.println(object);
	}

	public void addFile(String fileName) {
		// TODO Auto-generated method stub
		
	}

	public void deleteFile(String fileName) {
		//删除文件同时删除merkle树文件
		String filePath=FileUtil.filesPath+fileName;
		StringBuilder sb=new StringBuilder(FileUtil.filesPath+"merkle/"+fileName);
		if(sb.lastIndexOf(".")>0){
			sb.delete(sb.lastIndexOf("."), sb.length());
		}
		String fileMerkle=sb.toString();
		
		System.out.println("filePath:"+filePath);
		System.out.println("fileMerkle:"+fileMerkle);
		
		try {
			File file1=new File(filePath);
			File file2=new File(fileMerkle);
			if(file1.exists()){
				file1.delete();
			}
			if(file2.exists()){
				file2.delete();
			}
			
		} catch (Exception e) {
		}
		
	}

	public void updateFile(String fileName) {
		// TODO Auto-generated method stub
		
	}

	public List<String> getListFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Map<String, String>> getListFileParams() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
