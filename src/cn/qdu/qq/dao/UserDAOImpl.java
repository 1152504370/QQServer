package cn.qdu.qq.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.qdu.qq.util.DBUtil;
import cn.qdu.qq.vo.Users;

public class UserDAOImpl implements UserDAO {

	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	String sql;
	List<Users> ulist;
	
	private void closeAll(){
		try {
			
			if (rs!=null) {
				rs.close();
			}
			if (pst!=null) {
				pst.close();
			}
			if (con!=null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Users isLogin(String account, String password) {
		sql="select * from users where account=? and password =? ";
		try {
			con=DBUtil.getConn();
			pst=con.prepareStatement(sql);
			pst.setString(1, account);
			pst.setString(2, password);
			rs=pst.executeQuery();
			if (rs.next()) {
				Users u =new Users();
				u.setAccount(rs.getString("account"));
				u.setAge(rs.getShort("age"));
				u.setPassword(rs.getString("password"));
				u.setEmail(rs.getString("email"));
				u.setImg(rs.getString("img"));
				u.setNickname(rs.getString("nickname"));
				System.out.println("µÇÂ½³É¹¦£¡");
				return u;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return null;
	}

	@Override
	public boolean addUser(Users u) {
		sql="insert into users(password,nickname,age,email,img) values(?,?,?,?,?)";
		try {
			con=DBUtil.getConn();
			pst=con.prepareStatement(sql);
			pst.setString(1, u.getPassword());
			pst.setString(2, u.getNickname());
			pst.setInt(3, u.getAge());
			pst.setString(4, u.getEmail());
			pst.setString(5, u.getImg());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeAll();
		}
		return false;
	}

	@Override
	public List<Users> queryFriends(String account) {
		List<Users> ulist=new ArrayList<Users>();
		sql="select * from users where account in (select frid from friends where usid=?) ";
		try {
			con=DBUtil.getConn();
			pst=con.prepareStatement(sql);
			pst.setString(1, account);
			rs=pst.executeQuery();
			while(rs.next()){
				Users u =new Users();
				u.setAccount(rs.getString("account"));
				u.setAge(rs.getShort("age"));
				u.setPassword(rs.getString("password"));
				u.setEmail(rs.getString("email"));
				u.setImg(rs.getString("img"));
				u.setNickname(rs.getString("nickname"));
				ulist.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return ulist;
	}
}
