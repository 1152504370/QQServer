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
		//不停地听取客户端的各种需求
			try {
				Object o =ObjectUtil.readObject(s);
				if (o instanceof Users) {
					Users u = (Users) o;
					u=uBis.isLogin(u);
					ObjectUtil.writerObject(s, u);//返回验证结果给客户端
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
}
}
