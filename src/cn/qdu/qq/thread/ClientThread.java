package cn.qdu.qq.thread;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import cn.qdu.qq.bis.ServerBis;
import cn.qdu.qq.bis.UserBis;
import cn.qdu.qq.util.ObjectUtil;
import cn.qdu.qq.vo.AddFriendsMsg;
import cn.qdu.qq.vo.AddRSMsg;
import cn.qdu.qq.vo.Find;
import cn.qdu.qq.vo.RegeditRs;
import cn.qdu.qq.vo.SendMsg;
import cn.qdu.qq.vo.User;

public class ClientThread extends Thread {
	private Socket s;
	private UserBis uBis;
	
	public ClientThread(Socket s) {
		this.s = s;
		uBis=new UserBis();
	}
public void run(){
	while(true){
		try {
		//��ͣ����ȡ�ͻ��˵ĸ�������
				Object o =ObjectUtil.readObject(s);
				if (o instanceof User) {
					User u = (User) o;
					if (u.getAccount()!=null) {
						//��¼
						u=uBis.isLogin(u);
						if (u!=null) {
							//��¼�ɹ�! account +socket->maps
							ServerBis.getMaps().put(u.getAccount(), s);
						}
						ObjectUtil.writerObject(s, u);//������֤������ͻ���
					}else {
						//ע��
						RegeditRs rs=uBis.regedit(u);//ע��
						ObjectUtil.writerObject(s, rs);//������֤������ͻ���
					}
				}else if(o instanceof Find){
					//����
					Find find=(Find)o;
					List<User> ulist = new ArrayList<User>();
					switch (find.getType()) {
					case Find.ONE:
						User u=uBis.queryByAccount(find.getFaccount(),find.getFrom());
						if (u.getAccount()!=null) {
							ulist.add(u);
						}
						break;
					case Find.ALL:
						ulist.addAll(uBis.queryAll(find.getFrom()));
						break;
					case Find.NAME:
						ulist.addAll(uBis.queryByNickname(find.getNickname(), find.getFrom()));
						break;
					}
					ObjectUtil.writerObject(s, ulist);//������֤������ͻ���
				}else if (o instanceof AddFriendsMsg) {
					//��Ӻ���
					AddFriendsMsg msg=(AddFriendsMsg) o;
					System.out.println(msg.getFrom().getNickname()+" Ҫ��� "+msg.getTo().getNickname()+" Ϊ���ѡ�");
					Socket fs=ServerBis.getMaps().get(msg.getTo().getAccount());
					if (fs!=null) {
						//�û�����
						ObjectUtil.writerObject(fs, msg);
					}else {
						//�û�������->������Ϣ�����ݿ��У���������Ϣ��δʵ�֣�
					}
				}else if (o instanceof AddRSMsg) {
					AddRSMsg msg=(AddRSMsg) o;
					Socket fs=ServerBis.getMaps().get(msg.getTo().getAccount());
					if (msg.isAgree()) {
						//������ѹ�ϵ
						uBis.addFriends(msg);
					}
					if (fs!=null) {
						//�û�����
						ObjectUtil.writerObject(fs, msg);
					}else {
						//�û�������->������Ϣ�����ݿ��У���������Ϣ��δʵ�֣�
					}
				}else if (o instanceof SendMsg) {
					SendMsg msg=(SendMsg) o;
					Socket fs=ServerBis.getMaps().get(msg.getTo().getAccount());
					if (fs!=null) {
						//�û�����
						ObjectUtil.writerObject(fs, msg);
					}else {
						//�û�������->������Ϣ�����ݿ��У���������Ϣ��δʵ�֣�
					}
				}
			} catch (ClassNotFoundException e) {
				//e.printStackTrace();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		
	}
}
}
