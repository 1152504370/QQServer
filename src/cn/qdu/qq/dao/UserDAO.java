package cn.qdu.qq.dao;

import java.util.List;

import cn.qdu.qq.vo.Users;

public interface UserDAO {
/**
 * ��֤�û�
 * @param account
 * @param password
 * @return
 */
	public Users isLogin(String account,String password);
	/**
	 * ע���û�
	 * @param u
	 * @return
	 */
	public boolean addUser(Users u);
	/**
	 * ��ȡ����
	 */
	public List<Users>queryFriends(String account);
}
