package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FixedCardDao;
import dao.FixedDao;
import dao.SeatDao;
import model.Fixed;
import model.FixedCard;
import model.Page;
import model.Seat;
import model.TempCard;

public class FixedServlet extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response )throws IOException {//������֤��
		doPost(request,response);
		}
	public void doPost(HttpServletRequest request,HttpServletResponse response )throws IOException {//������֤��
		String method=request.getParameter("type");
		if("add".equals(method)) {
			FixedAdd(request,response);
			return;
		}
		else if("getfixedlist".equals(method)){
		    GetFixedList(request,response);
		}
		else if("edit".equals(method)) {
			FixedEdit(request,response);
		}
		else if("delete".equals(method)) {
			FixedDelete(request,response);
		}
		else if("out".equals(method)) {
			FixedOut(request,response);
		}
		else if("stillin".equals(method)){
			FixedStillin(request,response);
		}
}
	private void FixedStillin(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String out_date="1111-11-11";
		
		Fixed fixed=new Fixed();
		fixed.setOut_date(out_date);
		
		String condition=request.getParameter("condition");//获取查询字段的名称

		String fixed_id=null;
		String entry_time = null;
		String name=null;
		String car_num=null;
		if("card_id".equals(condition)) {
			 fixed_id=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");
			 request.setAttribute("search_value",fixed_id);
		}
		else  if ("entry_date".equals(condition)) {
			// fixed_name=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");			
			entry_time=request.getParameter("search_value");
			 request.setAttribute("search_value",entry_time);
		}
		else  if ("name".equals(condition)) {
			// fixed_name=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");			
			name=request.getParameter("search_value");
			 request.setAttribute("search_value",name);
		}
		else  if ("car_num".equals(condition)) {
			// fixed_name=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");			
			car_num=request.getParameter("search_value");
			 request.setAttribute("search_value",car_num);
		}
		
		request.setAttribute("condition",condition);
		
	//	Fixed fixed=new Fixed();
		fixed.setCard_id(fixed_id);
		fixed.setEntry_date(entry_time);
		fixed.setCar_num(car_num);
		fixed.setName(name);


		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		//Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		Integer pageSize=15;
		FixedDao fixedDao=new FixedDao();
		List<Fixed> fixedList=fixedDao.getFixedList(fixed, new Page(currentPage,pageSize));
		int total=fixedDao.getFixedListTotal(fixed);
	    fixedDao.closeCon();
	    int totalPage=total/pageSize+1;
	    request.setAttribute("list", fixedList);
	    request.setAttribute("totalPage",totalPage);
	    //这张方法前一个网页设置的属性后一个网页也能用
		try {
			request.getRequestDispatcher("/Admin/FixedOut.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//请求转发

	}
	private void FixedOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter outp=response.getWriter();
		String fixed_id =request.getParameter("card_id");
		String entry_date=request.getParameter("entry_date");
		
		String entry_time=request.getParameter("entry_time");
		//出场  改变 相应card_id 中的seat_i对应的seat_state 即可
		FixedDao fixedDao=new FixedDao();
		Fixed fixed=new Fixed();
		fixed=fixedDao.getFixed(fixed_id,entry_date,entry_time);//    等下这里有问题
		Seat seat=new Seat();
		SeatDao  seatDao=new SeatDao();
		seat=seatDao.getSeat(fixed.getSeat_id());
		seat.setSeat_state(0);//出场   将  seat_state设置为0\
		if(!seatDao.updateSeat(seat)) {
		
			try {
				outp.write("<script>alert('修改车位失败！'); location.href = '/ParkManager/FixedServlet?type=stillin';</script>");
				//response.getWriter().write("failed");
			}finally {
				seatDao.closeCon();
			}
		}
		//保存出场时间
		SimpleDateFormat dateFormat =new    SimpleDateFormat("yyyy-MM-dd"); 
	    String out_date=dateFormat.format(new Date());
	    SimpleDateFormat timeFormat =new    SimpleDateFormat("HH:mm:ss"); 
	    String out_time=timeFormat.format(new Date());
	    fixed.setOut_date(out_date);
	    fixed.setOut_time(out_time);
	    
       if(fixedDao.updateFixed(fixed)) {
			
			try {
				outp.write("<script>alert('出场成功！'); location.href = '/ParkManager/FixedServlet?type=stillin';</script>");
				//response.getWriter().write("success");
			}finally {
				fixedDao.closeCon();
			}
		}
		else {
			try {
				outp.write("<script>alert('出场失败！'); location.href = '/ParkManager/FixedServlet?type=stillin';</script>");
				//response.getWriter().write("failed");
			}finally {
				fixedDao.closeCon();
			}
		}
		
	}
	
	private void FixedDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter outp=response.getWriter();
		String fixed_id =request.getParameter("card_id");
		String entry_date=request.getParameter("entry_date");
		String entry_time=request.getParameter("entry_time");
		//删除就是删除  另外加一个出场 删除进出停车场的信息
		
		FixedDao fixedDao=new FixedDao();
		if(fixedDao.deleteFixed(fixed_id,entry_date,entry_time)) {
			try {
				outp.write("<script>alert('删除信息成功！'); location.href = '/ParkManager/FixedServlet?type=getfixedlist';</script>");
				//response.getWriter().write("success");
			}finally {
				fixedDao.closeCon();
			}
		}
		else {
			try {
				outp.write("<script>alert('删除信息失败！'); location.href = '/ParkManager/FixedServlet?type=getfixedlist';</script>");
			//	response.getWriter().write("failed");
			}finally {
				fixedDao.closeCon();
			}
		}
	}
	private void FixedEdit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter outp=response.getWriter();
		String fixed_id=request.getParameter("card_id");
		String out_time=request.getParameter("out_time");
		String out_date=request.getParameter("out_date");		
		
		Fixed fixed=new Fixed();
		
		fixed.setCard_id(fixed_id);
		fixed.setOut_date(out_date);
		fixed.setOut_time(out_time);
		
		FixedDao fixedDao=new FixedDao();
		if(fixedDao.updateFixed(fixed)) {
			
			try {
				outp.write("<script>alert('添加信息成功！'); location.href = '/ParkManager/FixedServlet?type=getfixedlist';</script>");
				//response.getWriter().write("success");
			}finally {
				fixedDao.closeCon();
			}
		}
		else {
			try {
				outp.write("<script>alert('修改信息失败！'); location.href = '/ParkManager/FixedServlet?type=getfixedlist';</script>");
				//response.getWriter().write("failed");
			}finally {
				fixedDao.closeCon();
			}
		}
	}
	private void GetFixedList(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		//通过管理员姓名和ID来搜索学生
		String condition=request.getParameter("condition");//获取查询字段的名称

		String fixed_id=null;
		String entry_time = null;
		String name=null;
		String car_num=null;
		if("card_id".equals(condition)) {
			 fixed_id=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");
			 request.setAttribute("search_value",fixed_id);
		}
		else  if ("entry_date".equals(condition)) {
			// fixed_name=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");			
			entry_time=request.getParameter("search_value");
			 request.setAttribute("search_value",entry_time);
		}
		else  if ("name".equals(condition)) {
			// fixed_name=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");			
			name=request.getParameter("search_value");
			 request.setAttribute("search_value",name);
		}
		else  if ("car_num".equals(condition)) {
			// fixed_name=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");			
			car_num=request.getParameter("search_value");
			 request.setAttribute("search_value",car_num);
		}
		
		request.setAttribute("condition",condition);
		
		Fixed fixed=new Fixed();
		fixed.setCard_id(fixed_id);
		fixed.setEntry_date(entry_time);
		fixed.setCar_num(car_num);
		fixed.setName(name);

		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		//Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		Integer pageSize=15;
		
		
		FixedDao fixedDao=new FixedDao();
		List<Fixed> fixedList=fixedDao.getFixedList(fixed, new Page(currentPage,pageSize));
		int total=fixedDao.getFixedListTotal(fixed);
	    fixedDao.closeCon();
	    int totalPage=total/pageSize+1;
	    request.setAttribute("list", fixedList);
	    request.setAttribute("totalPage",totalPage);
	    //这张方法前一个网页设置的属性后一个网页也能用
		try {
			request.getRequestDispatcher("/Admin/FixedMsg.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//请求转发

	}
	private void FixedAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter outp=response.getWriter();
		FixedDao fixedDao=new FixedDao();
		
		String fixed_id=request.getParameter("card_id");
//		String entry_time=request.getParameter("entry_time");
//		String entry_date=request.getParameter("entry_date");
		
		String entry_time,entry_date;
		SimpleDateFormat dFormat=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat tFormat=new SimpleDateFormat("HH:mm:ss");
		entry_date=dFormat.format(new Date());
		entry_time=tFormat.format(new Date());
		
		String out_date="1111-11-11";
		String out_time="11:11:11";		
			
		//固定车主进场   固定车位状态改变
		String seat_id = null;
		FixedCardDao fixedcardDao=new FixedCardDao();
		FixedCard fixedcard =fixedcardDao.getFixedCard(fixed_id);
		seat_id=fixedcard.getSeat_id();
		Seat seat=new Seat();
		SeatDao seatDao=new SeatDao();
		seat=seatDao.getSeat(seat_id);
		seat.setSeat_state(1);
		seatDao.updateSeat(seat);
		seatDao.closeCon();
		
		Fixed fixed=new Fixed();
		
		fixed.setCard_id(fixed_id);
		fixed.setEntry_date(entry_date);
		fixed.setEntry_time(entry_time);
		fixed.setOut_date(out_date);
		fixed.setOut_time(out_time);
		
		if(fixedDao.addFixed(fixed)) {
			try {
				outp.write("<script>alert('入场成功！'); location.href = '/ParkManager/FixedServlet?type=stillin';</script>");
				//response.getWriter().write("success");
			}finally {
				fixedDao.closeCon();
			}
		}
		else {
			try {
				outp.write("<script>alert('入场失败！'); location.href = '/ParkManager/FixedServlet?type=stillin';</script>");
				//response.getWriter().write("failed");
			}finally {
				fixedDao.closeCon();
			}
		}
	}
}
