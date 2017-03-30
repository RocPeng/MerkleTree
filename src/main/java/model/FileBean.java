package model;

import java.util.LinkedList;
import java.util.List;

/**
 * 文件的模型类,主要属性: fileSize:文件大小 fileHash:文件md5值
 * fileName:由于同一个文件可能名字不同但是md5相同,因此使用list集合保存相同md5的文件名
 * 
 * @author roc_peng
 *
 */
public class FileBean {

	private String fileSize;
	private String fileHash;
	private List<String> fileName;

	public FileBean() {
		this.fileName = new LinkedList<String>();
	}

	public FileBean(String fileSize, String fileHash, String fileName) {
		this.fileName = new LinkedList<String>();
		this.fileSize = fileSize;
		this.fileHash = fileHash;
		this.fileName.add(fileName);
	}

	public List<String> getFileName() {
		return fileName;
	}

	public void addFileName(String fileName) {
		this.fileName.add(fileName);
	}
	public void addAllFileName(List<String> fileList) {
		this.fileName.addAll(fileList);
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileHash() {
		return fileHash;
	}

	public void setFileHash(String fileHash) {
		this.fileHash = fileHash;
	}
}
