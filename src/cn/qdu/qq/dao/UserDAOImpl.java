package cn.qdu.qq.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.qdu.qq.util.DBUtil;
import cn.qdu.qq.vo.User;

public class UserDAOImpl implements UserDAO {

	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	String sql;
	List<User> ulist;
	
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
	public User isLogin(String account, String password) {
		sql="select * from users where account=? and password =? ";
		try {
			con=DBUtil.getConn();
			pst=con.prepareStatement(sql);
			pst.setString(1, account);
			pst.setString(2, password);
			rs=pst.executeQuery();
			if (rs.next()) {
				User u =new User();
				u.setAccount(rs.getString("account"));
				u.setAge(rs.getShort("age"));
				u.setPassword(rs.getString("password"));
				u.setEmail(rs.getString("email"));
				u.setImg(rs.getString("img"));
				u.setNickname(rs.getString("nickname"));
				System.out.println("登陆成功！");
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
	public boolean addUser(User u) {
		sql="insert into users(account,password,nickname,age,email,img) values(?,?,?,?,?,?)";
		try {
			con=DBUtil.getConn();
			pst=con.prepareStatement(sql);
			pst.setString(1, u.getAccount());
			pst.setString(2, u.getPassword());
			pst.setString(3, u.getNickname());
			pst.setInt(4, u.getAge());
			pst.setString(5, u.getEmail());
			pst.setString(6, u.getImg());
			pst.executeUpdate();
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
	public List<User> queryFriends(String account) {
		List<User> ulist=new ArrayList<User>();
		sql="select * from users where account in (select frid from friends where usid=?) ";
		try {
			con=DBUtil.getConn();
			pst=con.prepareStatement(sql);
			pst.setString(1, account);
			rs=pst.executeQuery();
			while(rs.next()){
				User u =new User();
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

	@Override
	public String getNextAccount() {
		String account=null;
		sql="select account FROM account";
		try {
			con=DBUtil.getConn();
			pst=con.prepareStatement(sql);
			rs=pst.executeQuery();
			while (rs.next()) {
				account=rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}


	@Override
	public User queryByAccount(String account,String from) {
		List<User> ulist=new ArrayList<User>();
		User u =new User();
		sql="select * from users where account =? and account!=?";
		try {
			con=DBUtil.getConn();
			pst=con.prepareStatement(sql);
			pst.setString(1, account);
			pst.setString(2, from);
			rs=pst.executeQuery();
			if(rs.next()){
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
		return u;
	}

	@Override
	public List<User> queryAll(String from) {
		List<User> ulist=new ArrayList<User>();
		sql="select * from users where account!=? and account not in (select frid from friends where usid=?) order by account asc ";
		try {
			con=DBUtil.getConn();
			pst=con.prepareStatement(sql);
			pst.setString(1, from);
			pst.setString(2,from);
			rs=pst.executeQuery();
			while(rs.next()){
				User u =new User();
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

	@Override
	public List<User> queryByNickname(String nickname, String from) {
		PreparedStatement pst = null; 
		List<User> ulist=new ArrayList<User>();
		sql="select * from users where nickname like ? and account!= ? and account not in (select frid from friends where usid= ? ) order by account asc";
		try {
			con=DBUtil.getConn();
			pst=con.prepareStatement(sql);
			pst.setString(1, "%" + nickname + "%");
			pst.setString(2, from);
			pst.setString(3, from);
			rs=pst.executeQuery();
			while(rs.next()){
				User u =new User();
				u.setAccount(rs.getString("account"));
				u.setAge(rs.getShort("age"));
				u.setPassword(rs.getString("password"));
				u.setEmail(rs.getString("email"));
				u.setImg(rs.getString("img"));
				u.setNickname(rs.getString("nickname"));
				System.out.println(u.getAccount()+" "+u.getNickname()+" "+u.getAge());
				ulist.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			closeAll();
		}
		return ulist;
	}
	public static void main(String[] args) {
		UserDAOImpl im=new UserDAOImpl();
		im.queryByNickname("a", "10001");
	}

	@Override
	public void addFriends(User u, User f) {
		sql="insert into friends values(?,?)";
		try {
			con=DBUtil.getConn();
			con.setAutoCommit(false);
			pst=con.prepareStatement(sql);
			pst.setString(1, u.getAccount());
			pst.setString(2, f.getAccount());
			pst.executeUpdate();
			pst.clearParameters();
			pst.setString(1, f.getAccount());
			pst.setString(2, u.getAccount());
			pst.executeUpdate();
			con.commit();//手动提交
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

		
	
}
