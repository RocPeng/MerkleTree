package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import util.GlobalConfig;

/**
 * 根据文件的某一个data数据块,请求服务端 得到根hash
 * @author roc_peng
 *
 */
public class GetRootHash {

	public static void main(String[] args) {
		System.out.println(getRoothash("1","BlockD0","34f201bc507e33caaef7222041234d1132"));
	}
	public static String getRoothash(String fileName,String dataBlockName,String hash){
		String param="fileName="+fileName+"&dataBlockName="+dataBlockName+"&hash="+hash;
		String data=sendPost(GlobalConfig.getRootUrl,param);
		return data;
	}
	public static String sendPost(String url,String params)
	{
		PrintWriter out=null;
		BufferedReader br=null;
		String result="";
		try {
			URL realURL=new URL(url);
			URLConnection conn=realURL.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0(compatible;MSIE)");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			//获取URLConnection对象对应的输入流
			out=new PrintWriter(conn.getOutputStream());
			//发送请求参数
			out.print(params);
			out.flush();
			br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while((line=br.readLine())!=null)
			{
				result+="\n"+line;
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("程序出现异常"+e);
			e.printStackTrace();
		}
		finally{
			try {
				
			if(br!=null)
			{
				br.close();
			}
				if(out!=null)
				{
					out.close();
				}
				
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		return result;
		
	}
}
