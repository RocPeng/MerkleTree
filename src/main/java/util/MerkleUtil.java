package util;

import java.io.BufferedWriter;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import model.MerkleNode;

public class MerkleUtil {
	public static void main(String[] args) {
		List<List<MerkleNode>> list=new LinkedList();
		getMerkleTree("/Users/roc_peng/Downloads/merkle/roc/3");
	}
	
	/**
	 * 读取一个文件,并将文件的每个字节作为merkle树的叶子,然后生成默克尔树
	 * 默克尔树文件默认存放在/merkle/filename
	 */
	public static void getMerkleTree(String filePath){
		LinkedList<LinkedList<MerkleNode>> list=new LinkedList();
		LinkedList<MerkleNode> leafList=new LinkedList();
		String content=FileUtil.readFileToStr(filePath);
		char[] arr =content.toCharArray();
		//首先获得叶子
		for(int i=0;i<arr.length;i++){
			MerkleNode leaf=new MerkleNode();
			leaf.setName("区块D"+i);
			leaf.setHash(EncryptUtil.getSHA(arr[i]+""));
			leafList.add(leaf);
		}
		//构造二叉树
		getMerkleNode(leafList,list);
		//二叉树信息保存到文件中
		StringBuilder sb=new StringBuilder(filePath);
		sb.replace(sb.lastIndexOf("/"), sb.lastIndexOf("/"), "/merkle/");
		FileUtil.writeStrToFile(sb.toString(), JSON.toJSONString(list));
	}
	
	private static void getMerkleNode(LinkedList<MerkleNode> list,LinkedList<LinkedList<MerkleNode>> allList){
		allList.add(list);
		if(list.size()==1)return;
		LinkedList<MerkleNode> temp=new LinkedList<>();
		for(int i=0;i+1<list.size();i+=2){
			MerkleNode node1=list.get(i);
			MerkleNode node2=list.get(i+1);
			MerkleNode tempNode=new MerkleNode(node1.getName()+node2.getName(),
					EncryptUtil.getSHA(node1.getHash()+node2.getHash()));
			temp.add(tempNode);
		}
		if(list.size()%2==1){
			MerkleNode tempNode=new MerkleNode(list.getLast().getName(), 
					EncryptUtil.getSHA(list.getLast().getHash()));
			temp.add(tempNode);
		}
		getMerkleNode(temp,allList);
	}
}
