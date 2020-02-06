package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Page;
import model.Fixed;
import model.Fixed;
import util.DbUtil;
import util.StringUtil;

public class FixedDao {
	private DbUtil dbUtil=new DbUtil();
	ResultSet resultSet;
	PreparedStatement prepareStatement;
	public boolean addFixed(Fixed fixed) {
		String sql="insert into fixed(card_id,entry_date,entry_time,out_date,out_time) values(?,?,?,?,?)";//进场就添加相关信息
		try {
		prepareStatement = dbUtil.getConnection().prepareStatement(sql);
		prepareStatement.setString(1, fixed.getCard_id());
		prepareStatement.setString(2, fixed.getEntry_date());
		prepareStatement.setString(3, fixed.getEntry_time());
		prepareStatement.setString(4, fixed.getOut_date());
		prepareStatement.setString(5, fixed.getOut_time());

		return prepareStatement.executeUpdate()>0?true:false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Fixed> getFixedList(Fixed fixed,Page page){
		List<Fixed> ret =new ArrayList<Fixed>();//这个没问题  就是在视图vfixed上查找的
		String sql="select * from vfixed ";//查询直接用视图   可以修改进出时间   修改进出时间是在   表fixed上修改
		if(!StringUtil.isEmpty(fixed.getCard_id())) {
			sql+="and card_id like '"+fixed.getCard_id()+"'";
		}
		if(!StringUtil.isEmpty(fixed.getEntry_date())) {
			sql+="and entry_date like '%"+fixed.getEntry_date()+"%'";
		}
		if(!StringUtil.isEmpty(fixed.getOut_date())) {
			sql+="and out_date='1111-11-11'";
		}
		if(!StringUtil.isEmpty(fixed.getName())) {
			sql+="and name like '%"+fixed.getName()+"%'";
		}
		if(!StringUtil.isEmpty(fixed.getCar_num())) {
			sql+="and car_num like '%"+fixed.getCar_num()+"%'";
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
				Fixed fixed1=new Fixed();
				fixed1.setCard_id(resultSet.getString("card_id"));
				fixed1.setEntry_date(resultSet.getString("entry_date"));
				fixed1.setEntry_time(resultSet.getString("entry_time"));
				fixed1.setOut_date(resultSet.getString("out_date"));
				fixed1.setOut_time(resultSet.getString("out_time"));
				fixed1.setAddress(resultSet.getString("address"));
				fixed1.setCar_num(resultSet.getString("car_num"));
				fixed1.setSeat_id(resultSet.getString("seat_id"));
				fixed1.setName(resultSet.getString("name"));
				
				ret.add(fixed1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	public Fixed getFixed(String fixed_id,String entry_date,String entry_time) {
		String sql ="select * from vfixed where card_id='"+fixed_id+"' and entry_time='"+entry_time+"' and entry_date='"+entry_date+"'";//这个函数有用？？
		Fixed fixed=null;// fixededit  的时候有用  显示信息   
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
				Fixed fixed1= new Fixed();
				fixed1.setCard_id(resultSet.getString("card_id"));
				fixed1.setEntry_date(resultSet.getString("entry_date"));
				fixed1.setEntry_time(resultSet.getString("entry_time"));
				fixed1.setOut_date(resultSet.getString("out_date"));
				fixed1.setOut_time(resultSet.getString("out_time"));
				fixed1.setSeat_id(resultSet.getString("seat_id"));
//				fixed.setAddress(resultSet.getString("address"));
//				fixed.setCar_num(resultSet.getString("car_num"));
//				fixed.setSeat_id(resultSet.getString("seat_id"));
//				fixed.setName(resultSet.getString("name"));
				return fixed1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fixed;
	}
	public boolean updateFixed(Fixed fixed) {//进入时间不改
		String sql="update fixed set out_date=?,out_time=? where card_id=?";
		try {
			prepareStatement = dbUtil.getConnection().prepareStatement(sql);
			prepareStatement.setString(1, fixed.getOut_date());
			prepareStatement.setString(2, fixed.getOut_time());
			prepareStatement.setString(3, fixed.getCard_id());

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
	public int getFixedListTotal(Fixed fixed) {
		// TODO Auto-generated method stub
		int total=0;
		String sql="select count(*)as total from vfixed ";
		if(!StringUtil.isEmpty(fixed.getCard_id())) {
			sql+="and card_id like '"+fixed.getCard_id()+"'";
		}
		if(!StringUtil.isEmpty(fixed.getEntry_date())) {
			sql+="and entry_time like '%"+fixed.getEntry_date()+"%'";
		}
		if(!StringUtil.isEmpty(fixed.getOut_date())) {
			sql+="and out_date='1111-11-11'";
		}
		if(!StringUtil.isEmpty(fixed.getName())) {
			sql+="and name like '%"+fixed.getName()+"%'";
		}
		if(!StringUtil.isEmpty(fixed.getCar_num())) {
			sql+="and car_num like '%"+fixed.getCar_num()+"%'";
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
	public boolean deleteFixed(String fixed_id,String entry_date,String entry_time)  {
		// TODO Auto-generated method stub
		String sql="delete from fixed where card_id='"+fixed_id+"' and entry_time='"+entry_time+"' and entry_date='"+entry_date+"'";
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
