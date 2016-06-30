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
	 * ����������
	 * @throws IOException 
	 */
	public void StartService() throws IOException{
		maps=new HashMap<String,Socket>();
		String sport=PropertiesUtil.readPro("port");
		if (sport!=null) {
			//�����˶˿�
			port=Integer.parseInt(sport);
		}else {
			//δ���ö˿�
			port=6000;
		}
		ss=new ServerSocket(port);
		while(true){
			Socket s=ss.accept();//�ȴ��ͻ�������
			System.out.println("�ͻ���������...");
			//����һ�������̣߳�������û�������ҵ�� 
			new ClientThread(s).start();
		}
	}
	/**
	 * ֹͣ������
	 * @throws IOException 
	 */
	public void StopService() throws IOException{
		ss.close();
	}

}
