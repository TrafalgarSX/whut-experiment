package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SeatDao;
import dao.SeatDao;
import dao.SeatDao;
import dao.SeatDao;
import model.Seat;
import model.Seat;
import model.Page;
import model.Seat;

public class SeatServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7527344521437019753L;
	public void doGet(HttpServletRequest request,HttpServletResponse response )throws IOException {//������֤��
		doPost(request,response);
		}
	public void doPost(HttpServletRequest request,HttpServletResponse response )throws IOException {//������֤��
		String method=request.getParameter("type");
		if("add".equals(method)) {
			SeatAdd(request,response);
			return;
		}
		else if("getseatlist".equals(method)){
		    GetSeatList(request,response);
		}
		else if("edit".equals(method)) {
			SeatEdit(request,response);
		}
		else if("delete".equals(method)) {
			SeatDelete(request,response);
		}
}
	private void SeatDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String seat_id =request.getParameter("Seat_id");
		
		SeatDao seatDao=new SeatDao();
		PrintWriter out=response.getWriter();
		if(seatDao.deleteSeat(seat_id)) {
			try {
				out.write("<script>alert('删除信息成功！'); location.href = '/ParkManager/SeatServlet?type=getseatlist';</script>");
				//response.getWriter().write("success");
			}finally {
				seatDao.closeCon();
			}
		}
		else {
			try {
				out.write("<script>alert('删除信息失败！'); location.href = '/ParkManager/SeatServlet?type=getseatlist';</script>");
				//response.getWriter().write("failed");
			}finally {
				seatDao.closeCon();
			}
		}
	}
	private void SeatEdit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		int seat_state=Integer.parseInt(request.getParameter("seat_state"));
		String seat_tag=new String(request.getParameter("seat_tag").getBytes("ISO-8859-1"),"UTF-8");
		String seat_id=request.getParameter("seat_id");
		String seat_section=new String(request.getParameter("seat_section").getBytes("ISO-8859-1"),"UTF-8");
		
		Seat seat=new Seat();
		
		seat.setSeat_id(seat_id);
		seat.setSeat_section(seat_section);
		seat.setSeat_state(seat_state);
		seat.setSeat_tag(seat_tag);
		
		SeatDao seatDao=new SeatDao();
		
		PrintWriter out=response.getWriter();
		if(seatDao.updateSeat(seat)) {
			
			try {
				out.write("<script>alert('修改信息成功！'); location.href = '/ParkManager/SeatServlet?type=getseatlist';</script>");

				//response.getWriter().write("success");
			}finally {
				seatDao.closeCon();
			}
		}
		else {
			try {
				out.write("<script>alert('修改信息失败！'); location.href = '/ParkManager/SeatServlet?type=getseatlist';</script>");
				//response.getWriter().write("failed");
			}finally {
				seatDao.closeCon();
			}
		}
	}
	private void GetSeatList(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		//通过车位ID和车位区域来搜索学生
		String condition=request.getParameter("condition");//获取查询字段的名称

		String seat_id=null;
		String seat_section = null;
		String seat_tag=null;
		int seat_state = 3;
		if("seat_id".equals(condition)) {
			 seat_id=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");
			 request.setAttribute("search_value",seat_id);
		}
		else  if ("seat_section".equals(condition)) {
			// seat_name=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");			
			seat_section=request.getParameter("search_value");
			 request.setAttribute("search_value",seat_section);
		}
		else  if ("seat_state".equals(condition)) {
			// seat_name=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");			
			seat_state=Integer.parseInt(request.getParameter("search_value"));
			 request.setAttribute("search_value",seat_state);
		}
		else  if ("seat_tag".equals(condition)) {
			// seat_name=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");			
			seat_tag=request.getParameter("search_value");//这个不用   转换
			 request.setAttribute("search_value",seat_tag);
		}
		
		request.setAttribute("condition",condition);
		
		Seat seat=new Seat();
		seat.setSeat_id(seat_id);
		seat.setSeat_section(seat_section);
		seat.setSeat_state(seat_state);
		seat.setSeat_tag(seat_tag);
		
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		//Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		Integer pageSize=15;
		
		
		SeatDao seatDao=new SeatDao();
		List<Seat> seatList=seatDao.getSeatList(seat, new Page(currentPage,pageSize));
		int total=seatDao.getSeatListTotal(seat);
	    seatDao.closeCon();
	    int totalPage=total/pageSize+1;
	    request.setAttribute("list", seatList);
	    request.setAttribute("totalPage",totalPage);
	    //这张方法前一个网页设置的属性后一个网页也能用
		try {
			request.getRequestDispatcher("/Admin/SeatMsg.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//请求转发

	}
	private void SeatAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String seat_tag=new String(request.getParameter("seat_tag").getBytes("ISO-8859-1"),"UTF-8");
		String seat_id=request.getParameter("seat_id");
		String seat_section=new String(request.getParameter("seat_section").getBytes("ISO-8859-1"),"UTF-8");
		
		Seat seat=new Seat();
		
		seat.setSeat_id(seat_id);
		seat.setSeat_section(seat_section);
		seat.setSeat_tag(seat_tag);
		
		SeatDao seatDao=new SeatDao();
		PrintWriter out=response.getWriter();
		if(seatDao.getSeat(seat_id)==null) {
		if(seatDao.addSeat(seat)) {
			try {
				out.write("<script>alert('添加信息成功！'); location.href = '/ParkManager/SeatServlet?type=getseatlist';</script>");

				//response.getWriter().write("success");
			}finally {
				seatDao.closeCon();
			}
		}
		else {
			try {
			out.write("<script>alert('添加信息失败！'); location.href = '/ParkManager/SeatServlet?type=getseatlist';</script>");
				//response.getWriter().write("failed");
			}finally {
				seatDao.closeCon();
			}
		 }
		}
		else {
			out.write("<script>alert('车位ID已存在！'); location.href = '/ParkManager/SeatServlet?type=getseatlist';</script>");

		}
	}
}
