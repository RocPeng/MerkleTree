package dao;

import java.util.List;
import java.util.Map;



// 用户操作接口
public interface IFileDao {

	public void addFile(String fileName);// 增
	public void deleteFile(String fileName);// 删
	public void updateFile(String fileName);// 改
	public List<String> getListFileName();// 加载当前目录所有文件
	public List<Map<String, String>> getListFileParams();// 加载当前目录所有文件信息(包括文件名,文件大小,hash值等)
	
}
