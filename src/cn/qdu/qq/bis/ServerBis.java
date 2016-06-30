package cn.qdu.qq.bis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import cn.qdu.qq.thread.ClientThread;
import cn.qdu.qq.util.PropertiesUtil;

public class ServerBis {
	
	ServerSocket ss;
	int port;
	private static Map<String,Socket> maps; 
	public static Map<String, Socket> getMaps() {
		return maps;
	}
	/**
	 * 启动服务器
	 * @throws IOException 
	 */
	public void StartService() throws IOException{
		maps=new HashMap<String,Socket>();
		String sport=PropertiesUtil.readPro("port");
		if (sport!=null) {
			//配置了端口
			port=Integer.parseInt(sport);
		}else {
			//未配置端口
			port=6000;
		}
		ss=new ServerSocket(port);
		while(true){
			Socket s=ss.accept();//等待客户端连接
			System.out.println("客户端已连接...");
			//启动一个独立线程，处理该用户的所有业务 
			new ClientThread(s).start();
		}
	}
	/**
	 * 停止服务器
	 * @throws IOException 
	 */
	public void StopService() throws IOException{
		ss.close();
	}

}
