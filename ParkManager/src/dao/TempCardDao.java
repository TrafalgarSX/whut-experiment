package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.TempCard;
import model.Page;
import util.DbUtil;
import util.StringUtil;

public class TempCardDao {
	private DbUtil dbUtil=new DbUtil();
	ResultSet resultSet;
	PreparedStatement prepareStatement;
	
	public  void closeCon() {//关闭数据库连接
		dbUtil.closeCon();
	}
	public boolean addTempCard(TempCard tempcard) {
		String sql="insert into tempcard(card_id,seat_id,car_num,entry_date,entry_time,out_date,out_time,pay) values(?,?,?,?,?,?,?,?)";
		try {
		prepareStatement = dbUtil.getConnection().prepareStatement(sql);
		prepareStatement.setString(1, tempcard.getCard_id());
		prepareStatement.setString(2, tempcard.getSeat_id());
		prepareStatement.setString(3, tempcard.getCar_num());
		prepareStatement.setString(4, tempcard.getEntry_date());
		prepareStatement.setString(5, tempcard.getEntry_time());
		prepareStatement.setString(6, tempcard.getOut_date());
		prepareStatement.setString(7, tempcard.getOut_time());
		prepareStatement.setDouble(8, tempcard.getPay());
		
		return prepareStatement.executeUpdate()>0?true:false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public List<TempCard> getTempCardList(TempCard tempcard,Page page){
		List<TempCard> ret =new ArrayList<TempCard>();
		String sql="select * from tempcard ";
		if(!StringUtil.isEmpty(tempcard.getCard_id())) {
			sql+="and card_id like '"+tempcard.getCard_id()+"'";
		}
		if(!StringUtil.isEmpty(tempcard.getCar_num())) {
			sql+="and car_num like '%"+tempcard.getCar_num()+"%'";
		}
		if(!StringUtil.isEmpty(tempcard.getOut_date())) {
			sql+="and out_date='1111-11-11'";
		}
		if(!StringUtil.isEmpty(tempcard.getSeat_id())) {
			sql+="and seat_id like '"+tempcard.getSeat_id()+"'";
		}
		if(!StringUtil.isEmpty(tempcard.getEntry_time())) {
			sql+="and entry_date like '%"+tempcard.getEntry_time()+"%'";
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
				TempCard tempcard1=new TempCard();
				tempcard1.setCard_id(resultSet.getString("card_id"));
				tempcard1.setSeat_id(resultSet.getString("seat_id"));
				tempcard1.setCar_num(resultSet.getString("car_num"));
				tempcard1.setEntry_date(resultSet.getString("entry_date"));
				tempcard1.setEntry_time(resultSet.getString("entry_time"));
				tempcard1.setOut_date(resultSet.getString("out_date"));
				tempcard1.setOut_time(resultSet.getString("out_time"));
				tempcard1.setPay(resultSet.getDouble("pay"));
				ret.add(tempcard1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	public TempCard getTempCard(String tempcard_id) {
		String sql ="select * from tempcard where card_id="+tempcard_id;
		TempCard tempcard=null;
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
				TempCard tempcard1= new TempCard();
				tempcard1.setCard_id(resultSet.getString("card_id"));
				tempcard1.setSeat_id(resultSet.getString("seat_id"));
				tempcard1.setCar_num(resultSet.getString("car_num"));
				tempcard1.setEntry_date(resultSet.getString("entry_date"));
				tempcard1.setEntry_time(resultSet.getString("entry_time"));
				tempcard1.setOut_date(resultSet.getString("out_date"));
				tempcard1.setOut_time(resultSet.getString("out_time"));
				tempcard1.setPay(resultSet.getDouble("pay"));
				return tempcard1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempcard;
	}
	public boolean updateTempCard(TempCard tempcard) {//更新不需要进入时间   进入时间一旦确定就不改了
		String sql="update tempcard set seat_id=?,car_num=?,out_date=?,out_time=?,pay=? where card_id=?";
		try {
			prepareStatement = dbUtil.getConnection().prepareStatement(sql);
			prepareStatement.setString(1, tempcard.getSeat_id());
			prepareStatement.setString(2, tempcard.getCar_num());
//		prepareStatement.setString(3, tempcard.getEntry_date());
//			prepareStatement.setString(4, tempcard.getEntry_date());
			prepareStatement.setString(3, tempcard.getOut_date());
			prepareStatement.setString(4, tempcard.getOut_time());
			prepareStatement.setDouble(5, tempcard.getPay());
			prepareStatement.setString(6, tempcard.getCard_id());
			

			return prepareStatement.executeUpdate()>0?true:false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return false;
	}
	public int getTempCardListTotal(TempCard tempcard) {
		// TODO Auto-generated method stub
		int total=0;
		String sql="select count(*)as total from tempcard ";
		if(!StringUtil.isEmpty(tempcard.getCard_id())) {
			sql+="and card_id like '"+tempcard.getCard_id()+"'";
		}
		if(!StringUtil.isEmpty(tempcard.getCar_num())) {
			sql+="and car_num like '%"+tempcard.getCar_num()+"%'";
		}
		if(!StringUtil.isEmpty(tempcard.getOut_date())) {
			sql+="and out_date='1111-11-11'";
		}
		if(!StringUtil.isEmpty(tempcard.getSeat_id())) {
			sql+="and seat_id like '"+tempcard.getSeat_id()+"'";
		}
		if(!StringUtil.isEmpty(tempcard.getEntry_time())) {
			sql+="and entry_date like '%"+tempcard.getEntry_time()+"%'";
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
	public boolean deleteTempCard(String tempcard_id) {
		// TODO Auto-generated method stub
		String sql="delete from tempcard where card_id="+tempcard_id;
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
