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
		//不停地听取客户端的各种需求
				Object o =ObjectUtil.readObject(s);
				if (o instanceof User) {
					User u = (User) o;
					if (u.getAccount()!=null) {
						//登录
						u=uBis.isLogin(u);
						if (u!=null) {
							//登录成功! account +socket->maps
							ServerBis.getMaps().put(u.getAccount(), s);
						}
						ObjectUtil.writerObject(s, u);//返回验证结果给客户端
					}else {
						//注册
						RegeditRs rs=uBis.regedit(u);//注册
						ObjectUtil.writerObject(s, rs);//返回验证结果给客户端
					}
				}else if(o instanceof Find){
					//查找
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
					ObjectUtil.writerObject(s, ulist);//返回验证结果给客户端
				}else if (o instanceof AddFriendsMsg) {
					//添加好友
					AddFriendsMsg msg=(AddFriendsMsg) o;
					System.out.println(msg.getFrom().getNickname()+" 要添加 "+msg.getTo().getNickname()+" 为好友。");
					Socket fs=ServerBis.getMaps().get(msg.getTo().getAccount());
					if (fs!=null) {
						//用户在线
						ObjectUtil.writerObject(fs, msg);
					}else {
						//用户不在线->保存消息到数据库中！！离线消息（未实现）
					}
				}else if (o instanceof AddRSMsg) {
					AddRSMsg msg=(AddRSMsg) o;
					Socket fs=ServerBis.getMaps().get(msg.getTo().getAccount());
					if (msg.isAgree()) {
						//保存好友关系
						uBis.addFriends(msg);
					}
					if (fs!=null) {
						//用户在线
						ObjectUtil.writerObject(fs, msg);
					}else {
						//用户不在线->保存消息到数据库中！！离线消息（未实现）
					}
				}else if (o instanceof SendMsg) {
					SendMsg msg=(SendMsg) o;
					Socket fs=ServerBis.getMaps().get(msg.getTo().getAccount());
					if (fs!=null) {
						//用户在线
						ObjectUtil.writerObject(fs, msg);
					}else {
						//用户不在线->保存消息到数据库中！！离线消息（未实现）
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
