package client;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.alibaba.fastjson.JSON;

import util.FileUtil;
import util.GlobalConfig;
import util.MerkleUtil;

public class Client {
	private static String select;
	private static String clientPath;
	private static List<String> fileNames = new LinkedList<>();
	private static List<String> merkleNames = new LinkedList<>();

	public static void main(String[] args) {
		clientPath = GlobalConfig.clientRoot;
		println("当前客户端存放文件路径为:" + clientPath + "  是否需要更改(Y/N)");
		String input = getInput();
		if (input.equalsIgnoreCase("y")) {
			println("请输入新的路径:");
			clientPath = getInput();
		}
		fileNames = FileUtil.getFileNames(clientPath, fileNames);
		merkleNames=FileUtil.getFileNames(clientPath + "merkle/", merkleNames);
		println("当前路径下的所有文件:" + JSON.toJSONString(fileNames));
		while (true) {
			println("******请选择********");
			println("1.生成文件的hash索引");
			println("2.生成每个文件的merkle树");
			println("3.完整性验证");
			select = getInput();
			switch (select) {
			case "1":
				createHash();
				break;
			case "2":
				createMerkle();
				break;
			case "3":
				getBlockTest();
				break;
			default:
				break;
			}
		}
	}

	public static void createHash() {
		FileUtil.getFilesHash(clientPath);
		println("hash索引已生成,路径:" + clientPath + "md5/hash  是否查看文件(Y/N)");
		String input = getInput();
		if (input.equalsIgnoreCase("y")) {
			String content = FileUtil.readFileToStr(clientPath + "md5/hash");
			println(content);
		}
	}

	public static void createMerkle() {
		for (final String str : fileNames) {
			MerkleUtil.getMerkleTree(clientPath + str);
		}
		merkleNames=FileUtil.getFileNames(clientPath + "merkle/", merkleNames);
		println("merkle已生成,路径:" + clientPath + "merkle  是否查看文件(Y/N)");
		String input = getInput();
		if (input.equalsIgnoreCase("y")) {
			println(JSON.toJSONString(merkleNames));
			println("输入序号可查看文件内容(-1退出):");
			String index=getInput();
			int index1=Integer.valueOf(index);
			if(index1>0&&index1<=merkleNames.size()){
				String content=FileUtil.readFileToStr(clientPath + "merkle/"+merkleNames.get(index1-1));
				println(content);
			}
		}
	}
	
	public static void getBlockTest(){
		println("当前所有merkle文件:"+JSON.toJSONString(merkleNames));
		println("请选择需要验证完整性的文件:");
		String index1=getInput();
		int index2=Integer.valueOf(index1);
		String fileName=merkleNames.get(index2-1);
		String content=FileUtil.readFileToStr(clientPath + "merkle/"+merkleNames.get(index2-1));
		List<List<Map<String, String>>> allList = new LinkedList<>();
		allList =JSON.parseObject(content,List.class);
		List<Map<String, String>> dataBlockList=allList.get(0);
		println("所有数据块为:");
		for(Map<String, String> data:dataBlockList){
			print(data.get("name")+" ");
		}println("");
		int randomIndex=(int) ((Math.random()*100)%dataBlockList.size());
		Map<String, String> randomData=dataBlockList.get(randomIndex);
		String blockName=randomData.get("name");
		String hash=randomData.get("hash");
		println("随机数据块为:"+"["+blockName+","+hash+"]");
		println("是否随机修改hash值(Y/N)");
		String input = getInput();
		if (input.equalsIgnoreCase("n")) {
			String result=GetRootHash.getRoothash(fileName, blockName, hash);
			System.out.println(result);
			Map<String,String> params=JSON.parseObject(result,Map.class);
			String serverRoot=params.get("roothash");
			List<Map<String, String>> clientRootData=allList.get(allList.size()-1);
			Map<String, String> clientData=clientRootData.get(0);
			String clientRoot=clientData.get("hash");
			print("本地文件的merkle根:"+clientRoot);
			if(clientRoot.equals(serverRoot)){
				println("\t与服务端根节点相同");
			}else{
				println("\t与服务端根节点不同");
			}
		}
	}

	public static String getInput() {
		Scanner scanner = new Scanner(System.in);
		String str = scanner.nextLine();
		return str;
	}

	public static void print(Object object) {
		System.out.print(object);
	}

	public static void println(Object object) {
		System.out.println(object);
	}
}
