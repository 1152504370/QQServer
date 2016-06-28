package cn.qdu.qq.util;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
public static final String filename="server.properties";
private static Properties pro=new Properties();

static{
	try {
		pro.load(Thread.currentThread()
				.getContextClassLoader()
				.getResourceAsStream(filename));
		
	} catch (IOException e) {
		System.out.println("∂¡»°"+filename+"“Ï≥££°");
		e.printStackTrace();
	}
}

public static String readPro(String key){
	return pro.getProperty(key);
}
}
