package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DbUtil;

/**
 * 
 * @author guo
 *基础dao封装基本操作
 */

public class BaseDao {
	private DbUtil dbUtil=new DbUtil();
	
	public  void closeCon() {//关闭数据库连接
		dbUtil.closeCon();
	}
	/**
	 *基础查询  多条查询
	 */
	public ResultSet query(String sql){
		try {
			//�����ݿ����Ӻ��һ������
			PreparedStatement prepareStatement = dbUtil.getConnection().prepareStatement(sql);
			return prepareStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 更新或插入操作
	 */
	public boolean update(String sql) {
		try {
			return dbUtil.getConnection().prepareStatement(sql).executeUpdate()>0?true:false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public Connection getConnection() {
		
		return dbUtil.getConnection();
	}
}