package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Page;
import model.FixedCard;
import util.DbUtil;
import util.StringUtil;

public class FixedCardDao {
	private DbUtil dbUtil=new DbUtil();
	ResultSet resultSet;
	PreparedStatement prepareStatement;
	
	public  void closeCon() {//关闭数据库连接
		dbUtil.closeCon();
	}
	public boolean addFixedCard(FixedCard fixedcard) {
		String sql="insert into fixedcard(card_id,seat_id,name,address,car_num) values(?,?,?,?,?)";
		try {
		prepareStatement = dbUtil.getConnection().prepareStatement(sql);
		prepareStatement.setString(1, fixedcard.getCard_id());
		prepareStatement.setString(2, fixedcard.getSeat_id());
		prepareStatement.setString(3, fixedcard.getName());
		prepareStatement.setString(4, fixedcard.getAddress());
		prepareStatement.setString(5, fixedcard.getCar_num());
		return prepareStatement.executeUpdate()>0?true:false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public List<FixedCard> getFixedCardList(FixedCard fixedcard,Page page){
		List<FixedCard> ret =new ArrayList<FixedCard>();
		String sql="select * from fixedcard ";
		if(!StringUtil.isEmpty(fixedcard.getCard_id())) {
			sql+="and card_id like '"+fixedcard.getCard_id()+"'";
		}
		if(!StringUtil.isEmpty(fixedcard.getName())) {
			sql+="and name like '%"+fixedcard.getName()+"%'";
		}
		if(!StringUtil.isEmpty(fixedcard.getCar_num())) {
			sql+="and car_num like '%"+fixedcard.getCar_num()+"%'";
		}
		if(!StringUtil.isEmpty(fixedcard.getAddress())) {
			sql+="and address like '%"+fixedcard.getAddress()+"%'";
		}
		if(!StringUtil.isEmpty(fixedcard.getSeat_id())) {
			sql+="and seat_id like '%"+fixedcard.getSeat_id()+"%'";
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
				FixedCard fixedcard1=new FixedCard();
				fixedcard1.setCard_id(resultSet.getString("card_id"));
				fixedcard1.setSeat_id(resultSet.getString("seat_id"));
				fixedcard1.setName(resultSet.getString("name"));
				fixedcard1.setAddress(resultSet.getString("address"));
				fixedcard1.setCar_num(resultSet.getString("car_num"));
				
				ret.add(fixedcard1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	public FixedCard getFixedCard(String fixedcard_id) {
		String sql ="select * from fixedcard where card_id="+fixedcard_id;
		FixedCard fixedcard=null;
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
				FixedCard fixedcard1= new FixedCard();
				fixedcard1.setCard_id(resultSet.getString("card_id"));
				fixedcard1.setSeat_id(resultSet.getString("seat_id"));
				fixedcard1.setName(resultSet.getString("name"));
				fixedcard1.setAddress(resultSet.getString("address"));
				fixedcard1.setCar_num(resultSet.getString("car_num"));
				return fixedcard1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fixedcard;
	}
	public boolean updateFixedCard(FixedCard fixedcard) {
		String sql="update fixedcard set seat_id=?,name=?,address=?,car_num=? where card_id=?";
		try {
			prepareStatement = dbUtil.getConnection().prepareStatement(sql);
			prepareStatement.setString(1, fixedcard.getSeat_id());
			prepareStatement.setString(2, fixedcard.getName());
			prepareStatement.setString(3, fixedcard.getAddress());
			prepareStatement.setString(4, fixedcard.getCar_num());
			prepareStatement.setString(5, fixedcard.getCard_id());

			return prepareStatement.executeUpdate()>0?true:false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return false;
	}
	public int getFixedCardListTotal(FixedCard fixedcard) {
		// TODO Auto-generated method stub
		int total=0;
		String sql="select count(*)as total from fixedcard ";
		if(!StringUtil.isEmpty(fixedcard.getCard_id())) {
			sql+="and card_id like '"+fixedcard.getCard_id()+"'";
		}
		if(!StringUtil.isEmpty(fixedcard.getName())) {
			sql+="and name like '%"+fixedcard.getName()+"%'";
		}
		if(!StringUtil.isEmpty(fixedcard.getCar_num())) {
			sql+="and car_num like '%"+fixedcard.getCar_num()+"%'";
		}
		if(!StringUtil.isEmpty(fixedcard.getAddress())) {
			sql+="and address like '%"+fixedcard.getAddress()+"%'";
		}
		if(!StringUtil.isEmpty(fixedcard.getSeat_id())) {
			sql+="and seat_id like '%"+fixedcard.getSeat_id()+"%'";
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
	public boolean deleteFixedCard(String fixedcard_id) {
		// TODO Auto-generated method stub
		String sql="delete from fixedcard where card_id="+fixedcard_id;
		try {
			prepareStatement = dbUtil.getConnection().prepareStatement(sql);
			return prepareStatement.executeUpdate()>0?true:false;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}
	public List<FixedCard> getOutFixedCardList(FixedCard fixedcard,Page page){
		List<FixedCard> ret =new ArrayList<FixedCard>();
		String sql="select * from voutfixed where seat_state=0";//转门获取固定车主中  不在停车场中的固定车主信息
		
		sql+=" limit "+page.getStart()+","+page.getPageSize();
		//sql=sql.replaceFirst("and","where");
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
				FixedCard fixedcard1=new FixedCard();
				fixedcard1.setCard_id(resultSet.getString("card_id"));
				fixedcard1.setSeat_id(resultSet.getString("seat_id"));
				fixedcard1.setName(resultSet.getString("name"));
				fixedcard1.setAddress(resultSet.getString("address"));
				fixedcard1.setCar_num(resultSet.getString("car_num"));
				
				ret.add(fixedcard1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	public int getOutFixedCardListTotal(FixedCard fixedcard) {
		// TODO Auto-generated method stub
		int total=0;
		String sql="select count(*)as total from voutfixed where seat_state=0 ";
		
		//sql=sql.replaceFirst("and","where");
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
//	public List<FixedCard> getNoUseFixedCard(){
//		List<FixedCard> ret =new ArrayList<FixedCard>();
//		String sql="select * from fixedcard where fixedcard_state=0 and card_id NOT IN(select fixedcard_id from fixedcard";
//		ResultSet resultSet=null;
//		try {
//			prepareStatement = dbUtil.getConnection().prepareStatement(sql);
//			resultSet=prepareStatement.executeQuery();
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		try {
//			while(resultSet.next()) {
//				FixedCard fixedcard1=new FixedCard();
//				fixedcard1.setCard_id(resultSet.getString("card_id"));
//				fixedcard1.setSeat_id(resultSet.getString("seat_id"));
//				fixedcard1.setName(resultSet.getString("name"));
//				fixedcard1.setAddress(resultSet.getString("address"));
//				fixedcard1.setCar_num(resultSet.getString("car_num"));
//				ret.add(fixedcard1);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return ret;
//	}
}
