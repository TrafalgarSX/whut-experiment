package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FixedCardDao;
import dao.SeatDao;
import model.FixedCard;
import model.Page;
import model.Seat;

public class FixedCardServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6697294379663447698L;
	public void doGet(HttpServletRequest request,HttpServletResponse response )throws IOException {//������֤��
		doPost(request,response);
		}
	public void doPost(HttpServletRequest request,HttpServletResponse response )throws IOException {//������֤��
		String method=request.getParameter("type");
		if("add".equals(method)) {
			FixedCardAdd(request,response);
			return;
		}
		else if("getfixedcardlist".equals(method)){
		    GetFixedCardList(request,response);
		}
		else if("edit".equals(method)) {
			FixedCardEdit(request,response);
		}
		else if("delete".equals(method)) {
			FixedCardDelete(request,response);
		}
}
	private void FixedCardDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String fixedcard_id =request.getParameter("card_id");
		
		FixedCardDao fixedcardDao=new FixedCardDao();
		PrintWriter out=response.getWriter();
		if(fixedcardDao.deleteFixedCard(fixedcard_id)) {
			try {
				out.write("<script>alert('删除信息成功！'); location.href = '/ParkManager/FixedCardServlet?type=getfixedcardlist';</script>");
				//response.getWriter().write("success");
			}finally {
				fixedcardDao.closeCon();
			}
		}
		else {
			try {
				out.write("<script>alert('删除信息失败！'); location.href = '/ParkManager/FixedCardServlet?type=getfixedcardlist';</script>");
				//response.getWriter().write("failed");
			}finally {
				fixedcardDao.closeCon();
			}
		}
	}
	private void FixedCardEdit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String seat_id=request.getParameter("seat_id");
		String fixedcard_id=request.getParameter("card_id");
		String car_num=new String(request.getParameter("car_num").getBytes("ISO-8859-1"),"UTF-8");
		String name=new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");
		String address=new String(request.getParameter("address").getBytes("ISO-8859-1"),"UTF-8");
		//这里有问题   等下回来看    14：00   11.27
		//修改固定IC卡的信息  如果修改seat_id 就要改变将要被分配的车位的  seat_tag  将修改后的seat_id 对应 的车位的seat_tag变成  固定车主车位   
		//修改后seat_id车位的状态 seat_state 不需改变   默认为0 当固定车主进停车场时再改变   
		//原来车位的seat_tag也要改变    这就要得知原来的seat_id  找到并改变seat_tag 修改为临时车主车位   原来车位的seat_state变为0
		//这一切都在修改了  seat_id的前提下   若没有修改seat_id 就不需要  改变   所以要先判断  是否改变了seat_id  
		
		//修改前后  card_id不变  可根据这个得到修改前的  seat_id
		FixedCard oldfixedCard=new FixedCard();
		FixedCard fixedcard=new FixedCard();
		
		FixedCardDao fixedcardDao=new FixedCardDao();
		
		PrintWriter out=response.getWriter();
		//修改原来 seat_id 的相关信息
		oldfixedCard=fixedcardDao.getFixedCard(fixedcard_id);
		String oldseat_id = oldfixedCard.getSeat_id();
		//先判断是否需要修改相关信息    seat_id  是否改变
		if(!oldseat_id.equals(seat_id)) {
		Seat seat=new Seat();
		SeatDao seatDao=new SeatDao();
		seat=seatDao.getSeat(oldseat_id);//根据原来的seat_id  找到原来的所有  seat_id的相关信息
		seat.setSeat_tag("临时车主车位");
		seat.setSeat_state(0);
		if(!seatDao.updateSeat(seat)){
			try {
				out.write("<script>alert('修改车位信息失败！'); location.href = '/ParkManager/FixedCardServlet?type=getfixedcardlist';</script>");
				//response.getWriter().write("failed");
			}finally {
				seatDao.closeCon();
			}
		}
		
		//修改现在seat_id的相关信息
		seat=seatDao.getSeat(seat_id);  //现在的  seat_id
		seat.setSeat_tag("固定车主车位");
		if(!seatDao.updateSeat(seat)){
			try {
				out.write("<script>alert('修改车位信息失败！'); location.href = '/ParkManager/FixedCardServlet?type=getfixedcardlist';</script>");
				//response.getWriter().write("failed");
			}finally {
				seatDao.closeCon();
			}
		}
		seatDao.closeCon();
		}
		
		
		fixedcard.setCard_id(fixedcard_id);
		fixedcard.setSeat_id(seat_id);
		fixedcard.setCar_num(car_num);
		fixedcard.setName(name);
		fixedcard.setAddress(address);
		
		
		if(fixedcardDao.updateFixedCard(fixedcard)) {
			try {
				out.write("<script>alert('修改信息成功！'); location.href = '/ParkManager/FixedCardServlet?type=getfixedcardlist';</script>");
				//response.getWriter().write("success");
			}finally {
				fixedcardDao.closeCon();
			}
		}
		else {
			try {
				out.write("<script>alert('修改信息失败！'); location.href = '/ParkManager/FixedCardServlet?type=getfixedcardlist';</script>");
				//response.getWriter().write("failed");
			}finally {
				fixedcardDao.closeCon();
			}
		}
	}
	private void GetFixedCardList(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String condition=request.getParameter("condition");//获取查询字段的名称

		String fixedcard_id=null;
		String car_num = null;
		String seat_id=null;
		String name=null;
		String address=null;
		if("card_id".equals(condition)) {
			 fixedcard_id=request.getParameter("search_value");
			 request.setAttribute("search_value",fixedcard_id);
		}
		else  if ("car_num".equals(condition)) {
			// fixedcard_name=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");			
			car_num=request.getParameter("search_value");
			 request.setAttribute("search_value",car_num);
		}
		else  if ("seat_id".equals(condition)) {
			// fixedcard_name=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");			
			seat_id=request.getParameter("search_value");
			 request.setAttribute("search_value",seat_id);
		}
		else  if ("name".equals(condition)) {
			// fixedcard_name=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");			
			name=request.getParameter("search_value");
			 request.setAttribute("search_value",name);
		}
		else  if ("address".equals(condition)) {
			// fixedcard_name=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");			
			address=request.getParameter("search_value");
			 request.setAttribute("search_value",address);
		}
		
		request.setAttribute("condition",condition);
		
		FixedCard fixedcard=new FixedCard();
		fixedcard.setCard_id(fixedcard_id);
		fixedcard.setCar_num(car_num);
		fixedcard.setAddress(address);
		fixedcard.setName(name);
		fixedcard.setSeat_id(seat_id);

		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		//Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		Integer pageSize=15;
		
		
		FixedCardDao fixedcardDao=new FixedCardDao();
		List<FixedCard> fixedcardList=fixedcardDao.getFixedCardList(fixedcard, new Page(currentPage,pageSize));
		int total=fixedcardDao.getFixedCardListTotal(fixedcard);
	    fixedcardDao.closeCon();
	    int totalPage=total/pageSize+1;
	    request.setAttribute("list", fixedcardList);
	    request.setAttribute("totalPage",totalPage);
	    //这张方法前一个网页设置的属性后一个网页也能用
		try {
			request.getRequestDispatcher("/Admin/FixedCardMsg.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//请求转发

	}
	private void FixedCardAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		//这里也有停车位   状态转变问题
		String address=new String(request.getParameter("address").getBytes("ISO-8859-1"),"UTF-8");
		String name=new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");
		String fixedcard_id=request.getParameter("card_id");
		String car_num=new String(request.getParameter("car_num").getBytes("ISO-8859-1"),"UTF-8");
		String seat_id=request.getParameter("seat_id");
		//这里有问题   等下回来看    14：00   11.27
		
		//往停车位中添加固定IC卡就要改变分配的车位的  seat_tag   
		//车位的状态seat_state在固定IC卡车主进入停车场再改变，现在不改变     
		Seat seat=new Seat();
		SeatDao seatDao=new SeatDao();
		seat=seatDao.getSeat(seat_id);
		seat.setSeat_tag("固定车主车位");
		seatDao.updateSeat(seat);
		seatDao.closeCon();
		
		FixedCard fixedcard=new FixedCard();
		
		fixedcard.setCard_id(fixedcard_id);
		fixedcard.setSeat_id(seat_id);
		fixedcard.setCar_num(car_num);
		fixedcard.setName(name);
		fixedcard.setAddress(address);
		
		FixedCardDao fixedcardDao=new FixedCardDao();
		PrintWriter out=response.getWriter();
		if(fixedcardDao.addFixedCard(fixedcard)) {
			try {
				out.write("<script>alert('添加信息成功！'); location.href = '/ParkManager/FixedCardServlet?type=getfixedcardlist';</script>");
				//response.getWriter().write("success");
				}finally {
				fixedcardDao.closeCon();
			}
		}
		else {
			try {
				out.write("<script>alert('添加信息失败！'); location.href = '/ParkManager/FixedCardServlet?type=getfixedcardlist';</script>");
//				response.getWriter().write("failed");
			}finally {
				fixedcardDao.closeCon();
			}
		}
	}
}
