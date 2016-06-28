package cn.qdu.qq.bis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import cn.qdu.qq.thread.ClientThread;
import cn.qdu.qq.util.ObjectUtil;
import cn.qdu.qq.util.PropertiesUtil;
import cn.qdu.qq.vo.Users;

public class ServerBis {
	
	ServerSocket ss;
	int port;
	/**
	 * ����������
	 * @throws IOException 
	 */
	public void StartService() throws IOException{
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
