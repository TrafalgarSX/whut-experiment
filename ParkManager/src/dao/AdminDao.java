package dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Admin;
import model.Page;
import util.DbUtil;
import util.StringUtil;

/**
 * 
 * @author guo
 *����Ա���ݿ������װ
 */

public class AdminDao  {
	private DbUtil dbUtil=new DbUtil();
	ResultSet resultSet;
	PreparedStatement prepareStatement;
	public Admin login(String user_id, String password) {
		String sql="select * from admin where admin_id=? and password=?";
		try {
			prepareStatement = dbUtil.getConnection().prepareStatement(sql);
			prepareStatement.setString(1, user_id);
			prepareStatement.setString(2, password);
			resultSet =  prepareStatement.executeQuery();
		
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			if(resultSet.next()) {
				Admin admin =new Admin();
				admin.setUser_id(resultSet.getString("admin_id"));
				admin.setName(resultSet.getString("name"));
				admin.setRole_id(resultSet.getInt("role_id"));
				admin.setUser_pwd(resultSet.getString("password"));
				admin.setUser_phone(resultSet.getString("phonenum"));
				return admin;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	public boolean addAdmin(Admin admin) {
		String sql="insert into admin(admin_id,role_id,name,password,phonenum) values(?,?,?,?,?)";
		try {
		prepareStatement = dbUtil.getConnection().prepareStatement(sql);
		prepareStatement.setString(1, admin.getUser_id());
		prepareStatement.setInt(2, admin.getRole_id());
		prepareStatement.setString(3, admin.getName());
		prepareStatement.setString(4, admin.getUser_pwd());
		prepareStatement.setString(5, admin.getUser_phone());

		return prepareStatement.executeUpdate()>0?true:false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

//	public boolean deleteAdmin(Admin admin) {
//		
//	}
	public List<Admin> getAdminList(Admin admin,Page page){
		List<Admin> ret =new ArrayList<Admin>();
		String sql="select * from admin ";
		if(!StringUtil.isEmpty(admin.getName())) {
			sql+="and name like '%"+admin.getName()+"%'";
		}
		if(!StringUtil.isEmpty(admin.getUser_id())) {
			sql+="and admin_id like '"+admin.getUser_id()+"'";
		}
		sql+=" limit "+page.getStart()+","+page.getPageSize();
		sql=sql.replaceFirst("and","where");
		ResultSet resultSet=null;
		try {
			prepareStatement = dbUtil.getConnection().prepareStatement(sql);
			 resultSet=prepareStatement.executeQuery();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			while(resultSet.next()) {
				Admin adm=new Admin();
				adm.setName(resultSet.getString("name"));
				adm.setRole_id(resultSet.getInt("role_id"));
				adm.setUser_id(resultSet.getString("admin_id"));
				adm.setUser_phone(resultSet.getString("phonenum"));
				adm.setUser_pwd(resultSet.getString("password"));
				ret.add(adm);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	public Admin getAdmin(String admin_id) {
		String sql ="select * from admin where admin_id='"+admin_id+"'";
		Admin admin=null;
		ResultSet resultSet = null;
		try {
			prepareStatement = dbUtil.getConnection().prepareStatement(sql);
			resultSet = prepareStatement.executeQuery();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if(resultSet.next()){
				Admin adm= new Admin();
				adm.setName(resultSet.getString("name"));
				adm.setRole_id(resultSet.getInt("role_id"));
				adm.setUser_id(resultSet.getString("admin_id"));
				adm.setUser_phone(resultSet.getString("phonenum"));
				adm.setUser_pwd(resultSet.getString("password"));
				return adm;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return admin;
	}
	public boolean updateAdmin(Admin admin) {
		String sql="update admin set role_id=?,name=?,password=?,phonenum=? where admin_id=?";
		try {
			prepareStatement = dbUtil.getConnection().prepareStatement(sql);
			prepareStatement.setInt(1, admin.getRole_id());
			prepareStatement.setString(2, admin.getName());
			prepareStatement.setString(3, admin.getUser_pwd());
			prepareStatement.setString(4, admin.getUser_phone());
			prepareStatement.setString(5, admin.getUser_id());

			return prepareStatement.executeUpdate()>0?true:false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return false;
	}
	public  void closeCon() {//关闭数据库连接
		dbUtil.closeCon();
	}
	public int getAdminListTotal(Admin admin) {
		// TODO Auto-generated method stub
		int total=0;
		String sql="select count(*)as total from admin ";
		if(!StringUtil.isEmpty(admin.getName())) {
			sql+="and name like '%"+admin.getName()+"%'";
		}
		if(!StringUtil.isEmpty(admin.getUser_id())) {
			sql+="and admin_id like '"+admin.getUser_id()+"'";
			
		}
		sql=sql.replaceFirst("and","where");
		ResultSet resultSet=null;
		try {
			prepareStatement = dbUtil.getConnection().prepareStatement(sql);
			resultSet=prepareStatement.executeQuery();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
		while(resultSet.next()) {
				total=resultSet.getInt("total");
		}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return total;
	}
	public boolean deleteAdmin(String admin_id) {
		// TODO Auto-generated method stub
		String sql="delete from admin where admin_id='"+admin_id+"'";
		try {
			prepareStatement = dbUtil.getConnection().prepareStatement(sql);
			return prepareStatement.executeUpdate()>0?true:false;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}
	public boolean editPassword(Admin admin, String newpassword) {
		// TODO Auto-generated method stub
		String sql="update admin set password='"+newpassword+"' where admin_id='"+admin.getUser_id()+"'";
		try {
			prepareStatement = dbUtil.getConnection().prepareStatement(sql);
			return prepareStatement.executeUpdate()>0?true:false;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}
}