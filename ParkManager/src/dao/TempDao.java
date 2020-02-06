package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Page;
import model.Temp;
import util.DbUtil;
import util.StringUtil;

public class TempDao {
	private DbUtil dbUtil=new DbUtil();
	ResultSet resultSet;
	PreparedStatement prepareStatement;
	public boolean addTemp(Temp temp) {
		String sql="insert into temp(card_id,entry_date,entry_time,out_date,out_time,pay) values(?,?,?,?,?,?)";
		try {
		prepareStatement = dbUtil.getConnection().prepareStatement(sql);
		prepareStatement.setString(1, temp.getCard_id());
		prepareStatement.setString(2, temp.getEntry_date());
		prepareStatement.setString(3, temp.getEntry_time());
		prepareStatement.setString(4, temp.getOut_date());
		prepareStatement.setString(5, temp.getOut_time());
		prepareStatement.setDouble(6, 0.0);		

		return prepareStatement.executeUpdate()>0?true:false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Temp> getTempList(Temp temp,Page page){
		List<Temp> ret =new ArrayList<Temp>();
		String sql="select * from temp ";
		if(!StringUtil.isEmpty(temp.getCard_id())) {
			sql+="and card_id like '"+temp.getCard_id()+"'";
		}
		if(!StringUtil.isEmpty(temp.getEntry_date())) {
			sql+="and entry_time like '%"+temp.getEntry_date()+"%'";
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
				Temp temp1=new Temp();
				temp1.setCard_id(resultSet.getString("card_id"));
				temp1.setEntry_date(resultSet.getString("entry_date"));
				temp1.setEntry_time(resultSet.getString("entry_time"));
				temp1.setOut_date(resultSet.getString("out_date"));
				temp1.setOut_time(resultSet.getString("out_time"));
				temp1.setPay(resultSet.getDouble("pay"));
				ret.add(temp1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	public Temp getTemp(String temp_id) {
		String sql ="select * from temp where card_id="+temp_id;
		Temp temp=null;
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
				Temp temp1= new Temp();
				temp1.setCard_id(resultSet.getString("card_id"));
				temp1.setEntry_date(resultSet.getString("entry_date"));
				temp1.setEntry_time(resultSet.getString("entry_time"));
				temp1.setOut_date(resultSet.getString("out_date"));
				temp1.setOut_time(resultSet.getString("out_time"));
				temp1.setPay(resultSet.getDouble("pay"));
				return temp1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}
	public boolean updateTemp(Temp temp) {
		String sql="update temp set entry_date=?,entry_time=?,out_date=?,out_time=?,pay=? where card_id=?";
		try {
			prepareStatement = dbUtil.getConnection().prepareStatement(sql);
			prepareStatement.setString(1, temp.getEntry_date());
			prepareStatement.setString(2, temp.getEntry_time());
			prepareStatement.setString(3, temp.getOut_date());
			prepareStatement.setString(4, temp.getOut_time());
			prepareStatement.setDouble(5, temp.getPay());		
			prepareStatement.setString(6, temp.getCard_id());

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
	public int getTempListTotal(Temp temp) {
		// TODO Auto-generated method stub
		int total=0;
		String sql="select count(*)as total from temp ";
		if(!StringUtil.isEmpty(temp.getCard_id())) {
			sql+="and card_id like '"+temp.getCard_id()+"'";
		}
		if(!StringUtil.isEmpty(temp.getEntry_date())) {
			sql+="and entry_time like '%"+temp.getEntry_date()+"%'";
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
	public boolean deleteTemp(String temp_id) {
		// TODO Auto-generated method stub
		String sql="delete from temp where card_id="+temp_id;
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
