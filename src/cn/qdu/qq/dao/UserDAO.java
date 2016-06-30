package cn.qdu.qq.dao;

import java.util.List;

import cn.qdu.qq.vo.User;

public interface UserDAO {
/**
 * ��֤�û�
 * @param account
 * @param password
 * @return
 */
	public User isLogin(String account,String password);

	/**
	 * ��ȡ��һ���˺�
	 * @param u
	 * @return
	 */
	public String getNextAccount();
	/**
	 * ע���û�
	 * @param u
	 * @return
	 */
	public boolean addUser(User u);
	/**
	 * ��ȡ����
	 */
	public List<User>queryFriends(String account);
	/**
	 * �����ʺŲ����û�
	 * @param account
	 * @return
	 */
	public User queryByAccount(String account,String from);
	
	/**
	 * �����ǳƲ����û�
	 * @param from
	 * @return
	 */
	
	public List<User> queryByNickname(String nickname,String from);
	
	public  List<User> queryAll(String from);
	
	/**
	 * ��Ӻ���
	 * @param u
	 * @param f
	 */
	public void addFriends(User u,User f);
}
