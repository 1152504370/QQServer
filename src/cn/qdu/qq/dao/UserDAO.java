package cn.qdu.qq.dao;

import java.util.List;

import cn.qdu.qq.vo.Users;

public interface UserDAO {
/**
 * 验证用户
 * @param account
 * @param password
 * @return
 */
	public Users isLogin(String account,String password);
	/**
	 * 注册用户
	 * @param u
	 * @return
	 */
	public boolean addUser(Users u);
	/**
	 * 获取好友
	 */
	public List<Users>queryFriends(String account);
}
