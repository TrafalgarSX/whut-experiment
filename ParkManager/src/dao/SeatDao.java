package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Seat;
import model.Seat;
import model.Seat;
import model.Seat;
import model.Page;
import model.Seat;
import util.DbUtil;
import util.StringUtil;

public class SeatDao {
	private DbUtil dbUtil=new DbUtil();
	ResultSet resultSet;
	PreparedStatement prepareStatement;
	public boolean addSeat(Seat seat) {
		String sql="insert into seat(seat_id,seat_section,seat_state,seat_tag) values(?,?,?,?)";
		try {
		prepareStatement = dbUtil.getConnection().prepareStatement(sql);
		prepareStatement.setString(1, seat.getSeat_id());
		prepareStatement.setString(2, seat.getSeat_section());
		prepareStatement.setInt(3, seat.getSeat_state());
		prepareStatement.setString(4, seat.getSeat_tag());

		return prepareStatement.executeUpdate()>0?true:false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Seat> getSeatList(Seat seat,Page page){
		List<Seat> ret =new ArrayList<Seat>();
		String sql="select * from seat ";
		if(!StringUtil.isEmpty(seat.getSeat_id())) {
			sql+="and seat_id like '"+seat.getSeat_id()+"'";
		}
		if(!StringUtil.isEmpty(seat.getSeat_section())) {
			sql+="and seat_section like '%"+seat.getSeat_section()+"%'";
		}
		if(!StringUtil.isEmpty(seat.getSeat_tag())) {
			sql+="and seat_tag like '%"+seat.getSeat_tag()+"%'";
		}
		if(seat.getSeat_state()!=3) {
			sql+="and seat_state="+seat.getSeat_state();
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
				Seat seat1=new Seat();
				seat1.setSeat_id(resultSet.getString("seat_id"));
				seat1.setSeat_section(resultSet.getString("seat_section"));
				seat1.setSeat_state(resultSet.getInt("seat_state"));
				seat1.setSeat_tag(resultSet.getString("seat_tag"));
				ret.add(seat1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	public Seat getSeat(String seat_id) {
		String sql ="select * from seat where seat_id='"+seat_id+"'";
		Seat seat=null;
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
				Seat seat1= new Seat();
				seat1.setSeat_id(resultSet.getString("seat_id"));
				seat1.setSeat_section(resultSet.getString("seat_section"));
				seat1.setSeat_state(resultSet.getInt("seat_state"));
				seat1.setSeat_tag(resultSet.getString("seat_tag"));
				return seat1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return seat;
	}
	public boolean updateSeat(Seat seat) {
		String sql="update seat set seat_section=?,seat_state=?,seat_tag=? where seat_id=?";
		try {
			prepareStatement = dbUtil.getConnection().prepareStatement(sql);
			prepareStatement.setString(1, seat.getSeat_section());
			prepareStatement.setInt(2, seat.getSeat_state());
			prepareStatement.setString(3, seat.getSeat_tag());
			prepareStatement.setString(4, seat.getSeat_id());

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
	public int getSeatListTotal(Seat seat) {
		// TODO Auto-generated method stub
		int total=0;
		String sql="select count(*)as total from seat ";
		if(!StringUtil.isEmpty(seat.getSeat_id())) {
			sql+="and seat_id like '"+seat.getSeat_id()+"'";
		}
		if(!StringUtil.isEmpty(seat.getSeat_section())) {
			sql+="and seat_section like '%"+seat.getSeat_section()+"%'";
		}
		if(!StringUtil.isEmpty(seat.getSeat_tag())) {
			sql+="and seat_tag like '%"+seat.getSeat_tag()+"%'";
		}
		if(seat.getSeat_state()!=3) {
			sql+="and seat_state="+seat.getSeat_state();
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
	public boolean deleteSeat(String seat_id) {
		// TODO Auto-generated method stub
		String sql="delete from seat where seat_id='"+seat_id+"'";
		try {
			prepareStatement = dbUtil.getConnection().prepareStatement(sql);
			return prepareStatement.executeUpdate()>0?true:false;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}
	public List<Seat> getNoUseSeat(){
		List<Seat> ret =new ArrayList<Seat>();
		String sql="select * from seat where seat_state=0 and seat_id NOT IN(select seat_id from fixedcard)";
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
				Seat seat1=new Seat();
				seat1.setSeat_id(resultSet.getString("seat_id"));
				seat1.setSeat_section(resultSet.getString("seat_section"));
				seat1.setSeat_state(resultSet.getInt("seat_state"));
				seat1.setSeat_tag(resultSet.getString("seat_tag"));
				ret.add(seat1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
}
