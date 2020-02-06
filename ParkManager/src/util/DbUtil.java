package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.management.loading.PrivateClassLoader;
/**
 * ���ݿ�����util   ʵ�ù�����
 * 
 */

public class DbUtil {
	private String dbUrl="jdbc:mysql://localhost:3306/parking?useUnicode=true&characterEncoding=utf8";
	private String dbUser="root";
	private String dbPassword="12345";
	private String jdbcName="com.mysql.jdbc.Driver";
	private  Connection connection=null;
	public Connection getConnection()  {
	
	 try {
		Class.forName(jdbcName);
		connection=DriverManager.getConnection(dbUrl,dbUser,dbPassword);
		System.out.println("数据库连接成功");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		System.out.println("数据库连接失败");
		e.printStackTrace();
	}catch(SQLException e1) {
		System.out.println("数据库连接失败");

		e1.printStackTrace();
	}
	 
	 return connection;
	}
	
	//�ر����ӵķ���
	public void closeCon() {
		if(connection!=null) {
			try {
				connection.close();
				System.out.println("数据库关闭");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.out.println("数据库无连接");
		}
		
	}
	
//	public static void main(String[] args) {
//		DbUtil dbUtil=new DbUtil();
//		dbUtil.getConnection();
//		dbUtil.closeCon();
//	}
}
