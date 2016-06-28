package cn.qdu.qq.thread;

import java.io.IOException;
import java.net.Socket;

import cn.qdu.qq.bis.ServerBis;
import cn.qdu.qq.bis.UserBis;
import cn.qdu.qq.util.ObjectUtil;
import cn.qdu.qq.vo.Users;

public class ClientThread extends Thread {
	private Socket s;
	private UserBis uBis;
	
	public ClientThread(Socket s) {
		this.s = s;
		uBis=new UserBis();
	}
public void run(){
	while(true){
		//��ͣ����ȡ�ͻ��˵ĸ�������
			try {
				Object o =ObjectUtil.readObject(s);
				if (o instanceof Users) {
					Users u = (Users) o;
					u=uBis.isLogin(u);
					ObjectUtil.writerObject(s, u);//������֤������ͻ���
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
}
}
