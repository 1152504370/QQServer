package cn.qdu.qq.dao;

import java.util.List;

import cn.qdu.qq.vo.User;

public interface UserDAO {
/**
 * 验证用户
 * @param account
 * @param password
 * @return
 */
	public User isLogin(String account,String password);

	/**
	 * 获取下一个账号
	 * @param u
	 * @return
	 */
	public String getNextAccount();
	/**
	 * 注册用户
	 * @param u
	 * @return
	 */
	public boolean addUser(User u);
	/**
	 * 获取好友
	 */
	public List<User>queryFriends(String account);
	/**
	 * 按照帐号查找用户
	 * @param account
	 * @return
	 */
	public User queryByAccount(String account,String from);
	
	/**
	 * 按照昵称查找用户
	 * @param from
	 * @return
	 */
	
	public List<User> queryByNickname(String nickname,String from);
	
	public  List<User> queryAll(String from);
	
	/**
	 * 添加好友
	 * @param u
	 * @param f
	 */
	public void addFriends(User u,User f);
}
