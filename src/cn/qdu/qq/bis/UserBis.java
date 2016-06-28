package cn.qdu.qq.bis;

import cn.qdu.qq.dao.UserDAO;
import cn.qdu.qq.dao.UserDAOImpl;
import cn.qdu.qq.vo.Users;

public class UserBis {
	private UserDAO udao;
	
	public UserBis(){
		udao=new UserDAOImpl();
	}
public Users isLogin(Users u){
	 u=udao.isLogin(u.getAccount(), u.getPassword());
	 if (u!=null) {
		 u.setFriends( udao.queryFriends(u.getAccount()));
	}
		return u;
}
}
