package cn.qdu.qq.bis;

import java.util.List;

import cn.qdu.qq.dao.UserDAO;
import cn.qdu.qq.dao.UserDAOImpl;
import cn.qdu.qq.vo.AddRSMsg;
import cn.qdu.qq.vo.RegeditRs;
import cn.qdu.qq.vo.User;

public class UserBis {
	private UserDAO udao;
	
	public UserBis(){
		udao=new UserDAOImpl();
	}
public User isLogin(User u){
	 u=udao.isLogin(u.getAccount(), u.getPassword());
	 if (u!=null) {
		 u.setFriends( udao.queryFriends(u.getAccount()));
	}
		return u;
}
public RegeditRs regedit(User u){
	RegeditRs rs=new RegeditRs();
	String account=udao.getNextAccount();
	u.setAccount(account);
	boolean b=udao.addUser(u);
	if (b) {
		rs.setMsg("ÄúµÄÕÊºÅÎª£º "+account);
		rs.setRs(true);
	}else {
		rs.setMsg("×¢²áÊ§°Ü£¡");
		rs.setRs(false);
	}
	return rs;
	
}

 public  User queryByAccount(String account,String from){
	 return udao.queryByAccount(account,from);
 }
 
 public List<User> queryAll(String from){
	 return udao.queryAll(from);
 }
 
 public List<User> queryByNickname(String nickname,String from){
	return udao.queryByNickname(nickname, from);
	 
 }
 public void addFriends(AddRSMsg msg){
	 udao.addFriends(msg.getFrom(), msg.getTo());
 }
}
