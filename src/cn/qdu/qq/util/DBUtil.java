package cn.qdu.qq.util;
import cn.qdu.qq.dao.UserDAOImpl;
import cn.qdu.qq.util.PropertiesUtil;
import cn.qdu.qq.vo.Users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	
private static String driverClass;//驱动类
private static String user;//驱动类
private static String password;//驱动类
private static String url;//驱动类
static{
	driverClass=PropertiesUtil.readPro("driverClass");
	user=PropertiesUtil.readPro("user");
	password=PropertiesUtil.readPro("password");
	url=PropertiesUtil.readPro("url");
	try {
		Class.forName(driverClass);
	} catch (ClassNotFoundException e) {
		System.out.println("数据库启动加载失败！"+driverClass);
		e.printStackTrace();
	}
}

public static Connection getConn() throws SQLException{
	return DriverManager.getConnection(url, user, password);
}
public static void main(String[] args) {
	UserDAOImpl user=new UserDAOImpl(); 
	try {System.out.println(user.addUser(new Users("12345","小智",20,"abcd@qq.com","3")));
	System.out.println(user.isLogin("10001", "123456"));
		//System.out.println(DBUtil.getConn());
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}
