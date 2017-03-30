package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import util.FileUtil;
import util.MerkleUtil;

/**
 * 接收客户端传来的datablock 并返回根节点 进行完整性验证
 * 
 * @author roc_peng
 *
 */
public class DataBlockServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void println(Object object) {
		System.out.println(object);
	}

	public void print(Object object) {
		System.out.print(object);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 返回值
		Map<String, String> map = new HashMap<>();
		// 三个属性 文件名 区块名 hash值
		String fileName = req.getParameter("fileName");
		String dataBlockName = req.getParameter("dataBlockName");
		String hash = req.getParameter("hash");
		System.out.println("fileName:"+fileName);
		System.out.println("dataBlockName:"+dataBlockName);
		System.out.println("hash:"+hash);
		// 首先判断服务端是否有该文件:
		List<String> fileNames = new LinkedList<>();
		fileNames = FileUtil.getFileNames(FileUtil.filesPath + "merkle/", fileNames);
		System.out.println("path:" + FileUtil.filesPath + "merkle/");
		System.out.println("files:" + JSON.toJSONString(fileNames));
		if (!fileNames.contains(fileName)) {
			map.put("errcode", "-1");
			map.put("errmsg", "服务端不存在该文件");
			String json = JSON.toJSONString(map);
			resp.setContentType("text/plain");
			resp.setCharacterEncoding("utf-8");
			PrintWriter out = new PrintWriter(resp.getOutputStream());
			out.print(json);
			out.flush();
			return;
		}
		// 根据区块计算根hash
		String fileMerkleData = FileUtil.readFileToStr(FileUtil.filesPath + "merkle/" + fileName);
		List<List<Map<String, String>>> allList = new LinkedList<>();
		allList =JSON.parseObject(fileMerkleData,List.class);
		List<Map<String, String>> leaf = allList.get(0);
		System.out.println("之前:"+JSON.toJSONString(leaf));
		for (Map<String, String> node : leaf) {
			if (node.get("name").equals(dataBlockName))
				node.put("hash", hash);
		}
		System.out.println("之后:"+JSON.toJSONString(leaf));
		// 生成merkle tree
		allList.clear();
		MerkleUtil.getMerkleNode2(leaf, allList);
		List<Map<String, String>> root = allList.get(allList.size()-1);
		Map<String, String> node = root.get(0);
		map.put("errcode", "0");
		map.put("roothash", node.get("hash"));
		String json = JSON.toJSONString(map);
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = new PrintWriter(resp.getOutputStream());
		out.print(json);
		out.flush();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
