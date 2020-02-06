package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FixedCardDao;
import dao.SeatDao;
import dao.TempCardDao;
import model.TempCard;
import model.FixedCard;
import model.Page;
import model.Seat;

public class TempCardServlet extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response )throws IOException {//������֤��
		doPost(request,response);
		}
	public void doPost(HttpServletRequest request,HttpServletResponse response )throws IOException {//������֤��
		String method=request.getParameter("type");
		if("add".equals(method)) {
			TempCardAdd(request,response);
			//return;
		}
		else if("gettempcardlist".equals(method)){
		    GetTempCardList(request,response);
		}
		else if("edit".equals(method)) {
			TempCardEdit(request,response);
		}
		else if("delete".equals(method)) {
			TempCardDelete(request,response);
		}
		else if("out".equals(method)) {
			TempCardOut(request,response);
		}
		else if("stillin".equals(method)) {
			TempCardStillin(request,response);
		}
}
	private void TempCardStillin(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String out_date="1111-11-11";
		
		TempCard tempcard=new TempCard();
		tempcard.setOut_date(out_date);
		
		String condition=request.getParameter("condition");//获取查询字段的名称

		String tempcard_id=null;
		String car_num = null;
		String seat_id=null;
		String entry_time=null;
		if("card_id".equals(condition)) {
			 tempcard_id=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");
			 request.setAttribute("search_value",tempcard_id);
		}
		else  if ("car_num".equals(condition)) {
			// tempcard_name=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");			
			car_num=request.getParameter("search_value");
			 request.setAttribute("search_value",car_num);
		}
		else  if ("seat_id".equals(condition)) {
			// tempcard_name=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");			
			seat_id=request.getParameter("search_value");
			 request.setAttribute("search_value",car_num);
		}
		else  if ("entry_time".equals(condition)) {
			// tempcard_name=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");			
			entry_time=request.getParameter("search_value");
			 request.setAttribute("search_value",car_num);
		}
		
		request.setAttribute("condition",condition);
		
		//TempCard tempcard=new TempCard();
		tempcard.setCard_id(tempcard_id);
		tempcard.setCar_num(car_num);
		tempcard.setEntry_time(entry_time);
		tempcard.setSeat_id(seat_id);
		
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		//Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		Integer pageSize=15;
		
		
		TempCardDao tempcardDao=new TempCardDao();
		List<TempCard> tempcardList=tempcardDao.getTempCardList(tempcard, new Page(currentPage,pageSize));
		int total=tempcardDao.getTempCardListTotal(tempcard);
	    tempcardDao.closeCon();
	    int totalPage=total/pageSize+1;
	    request.setAttribute("list", tempcardList);
	    request.setAttribute("totalPage",totalPage);
	    //这张方法前一个网页设置的属性后一个网页也能用
		try {
			request.getRequestDispatcher("/Admin/TempOut.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//请求转发

	}
	private void TempCardOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter outp=response.getWriter();
		
		String tempcard_id=request.getParameter("card_id");
		//出场 改变seat_id中的  seat_state
		TempCardDao tempcardDao=new TempCardDao();
		TempCard tempcard=new TempCard();
		tempcard=tempcardDao.getTempCard(tempcard_id);
		
		Seat seat=new Seat();
		SeatDao  seatDao=new SeatDao();//临时车主出场将所占用车位的状态改变
		seat=seatDao.getSeat(tempcard.getSeat_id());
		seat.setSeat_state(0);//出场   将  seat_state设置为0\
		if(!seatDao.updateSeat(seat)) {//更新车位信息表
			try {//跳转的出场界面
				outp.write("<script>alert('修改车位信息失败！'); location.href = '/ParkManager/TempCardServlet?type=stillin';</script>");
				//response.getWriter().write("failed");
			}finally {
				seatDao.closeCon();
			}
		}
		//得到临时车主出场时间，和停车的花费
		SimpleDateFormat dateFormat =new    SimpleDateFormat("yyyy-MM-dd"); //出场时间
	    String out_date=dateFormat.format(new Date());//保存
	    SimpleDateFormat timeFormat =new    SimpleDateFormat("HH:mm:ss"); 
	    String out_time=timeFormat.format(new Date());
	    //获得入场时间
	    String entry_date=tempcard.getEntry_date();
	    String entry_time=tempcard.getEntry_time();
	    //计算停车花费
	    double price=5.0,pay;//停车单价  和 总花费
	    double hours;
	    SimpleDateFormat inputFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    Date entry=null,out=null;
	    try {//按格式连接出入场时间
			 entry=inputFormat.parse(entry_date+" "+entry_time);
			 out=inputFormat.parse(out_date+" "+out_time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    long start=entry.getTime();
	    long end=out.getTime();
	    //计算在停车场多少个小时
	    hours=(double)(end-start)/(1000*60*60);
	    //计算停车场花费    
	    pay=Math.ceil(price*hours);//停车场花费
	    tempcard.setPay(pay);//设置临时车主出场信息
	    tempcard.setOut_date(out_date);
	    tempcard.setOut_time(out_time);
if(tempcardDao.updateTempCard(tempcard)) {//更新临时车主信息表	
			try {//出场成功过后跳转到 出场界面
				outp.write("<script>alert('出场成功！'); location.href = '/ParkManager/TempCardServlet?type=stillin';</script>");
			}finally {
				tempcardDao.closeCon();
			}
		}
		else {
			try {
				outp.write("<script>alert('出场失败！'); location.href = '/ParkManager/TempCardServlet?type=stillin';</script>");
			}finally {
				tempcardDao.closeCon();
			}
		}
	}
	private void TempCardDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String tempcard_id =request.getParameter("card_id");
		
		PrintWriter out=response.getWriter();
		TempCardDao tempcardDao=new TempCardDao();
		if(tempcardDao.deleteTempCard(tempcard_id)) {
			try {
				out.write("<script>alert('删除信息成功！'); location.href = '/ParkManager/TempCardServlet?type=gettempcardlist';</script>");
				//response.getWriter().write("success");
			}finally {
				tempcardDao.closeCon();
			}
		}
		else {
			try {
				out.write("<script>alert('删除信息失败！'); location.href = '/ParkManager/TempCardServlet?type=gettempcardlist';</script>");
				//response.getWriter().write("failed");
			}finally {
				tempcardDao.closeCon();
			}
		}
	}
	private void TempCardEdit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		String seat_id=request.getParameter("seat_id");
		String tempcard_id=request.getParameter("card_id");
		String car_num=new String(request.getParameter("car_num").getBytes("ISO-8859-1"),"UTF-8");
		//这里有问题   等下回来看    14：00   11.27
		//修改临时IC卡的信息  如果修改seat_id 就要改变将要被分配的车位的  seat_tag  将修改后的seat_id 对应 的车位的seat_tag变成  临时车主车位   
				//修改后seat_id车位的状态 seat_state 需要改变   默认为0   换了个车位停   就要将改变后的车位状态设为1  
				//原来车位的seat_tag就不改变了  之前就时临时车主车位  不需要改变    这就要得知原来的seat_id   原来车位的seat_state变为0
				//这一切都在修改了  seat_id的前提下   若没有修改seat_id 就不需要  改变   所以要先判断  是否改变了seat_id  
				
				//修改前后  card_id不变  可根据这个得到修改前的  seat_id
				TempCard oldtempCard=new TempCard();
				TempCard tempcard=new TempCard();
				
				TempCardDao tempcardDao=new TempCardDao();
				
				//修改原来 seat_id 的相关信息
				oldtempCard=tempcardDao.getTempCard(tempcard_id);
				String oldseat_id = oldtempCard.getSeat_id();
				//先判断是否需要修改相关信息    seat_id  是否改变
				if(!oldseat_id.equals(seat_id)) {
				Seat seat=new Seat();
				SeatDao seatDao=new SeatDao();
				seat=seatDao.getSeat(oldseat_id);//根据原来的seat_id  找到原来的所有  seat_id的相关信息
				seat.setSeat_state(0);
				if(!seatDao.updateSeat(seat)){
					try {
						out.write("<script>alert('修改车位信息失败！'); location.href = '/ParkManager/TempCardServlet?type=gettempcardlist';</script>");
						//response.getWriter().write("failed");
					}finally {
						seatDao.closeCon();
					}
				}
				
				//修改现在seat_id的相关信息
				seat=seatDao.getSeat(seat_id);  //现在的  seat_id
				seat.setSeat_tag("临时车主车位");
				seat.setSeat_state(1);
				if(!seatDao.updateSeat(seat)){
					try {
						out.write("<script>alert('修改车位信息失败！'); location.href = '/ParkManager/TempCardServlet?type=gettempcardlist';</script>");
						//response.getWriter().write("failed");
					}finally {
						seatDao.closeCon();
					}
				}
				seatDao.closeCon();
				}
		TempCard temp=new TempCard();
		temp=tempcardDao.getTempCard(tempcard_id);
		tempcard.setCard_id(tempcard_id);
		tempcard.setSeat_id(seat_id);
		tempcard.setCar_num(car_num);
		tempcard.setOut_date(temp.getOut_date());
		tempcard.setOut_time(temp.getOut_time());
		tempcard.setPay(temp.getPay());
		
		if(tempcardDao.updateTempCard(tempcard)) {
			
			try {
				out.write("<script>alert('修改信息成功！'); location.href = '/ParkManager/TempCardServlet?type=gettempcardlist';</script>");
				//response.getWriter().write("success");
			}finally {
				tempcardDao.closeCon();
			}
		}
		else {
			try {
				out.write("<script>alert('修改信息失败！'); location.href = '/ParkManager/TempCardServlet?type=gettempcardlist';</script>");
				//response.getWriter().write("failed");
			}finally {
				tempcardDao.closeCon();
			}
		}
	}
	private void GetTempCardList(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String condition=request.getParameter("condition");//获取查询字段的名称

		String tempcard_id=null;//ID查询字段  具体值
		String car_num = null;//车牌号查询字段  具体值
		String seat_id=null;//车位号查询字段  具体值
		String entry_time=null;//进场时间查询字段  具体值
		if("card_id".equals(condition)) {//从网页端获取查询具体值
			 tempcard_id=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");
			 request.setAttribute("search_value",tempcard_id);
		}
		else  if ("car_num".equals(condition)) {	
			car_num=request.getParameter("search_value");//获得要查询的值
			 request.setAttribute("search_value",car_num);//设置此值用来翻页刷新时，继续用此字段查询
		}
		else  if ("seat_id".equals(condition)) {		
			seat_id=request.getParameter("search_value");
			 request.setAttribute("search_value",car_num);
		}
		else  if ("entry_time".equals(condition)) {		
			entry_time=request.getParameter("search_value");
			 request.setAttribute("search_value",car_num);
		}
		
		request.setAttribute("condition",condition);
		
		TempCard tempcard=new TempCard();//存储查询信息的类，传送信息以实现在数据库中查询
		tempcard.setCard_id(tempcard_id); //设置查询信息
		tempcard.setCar_num(car_num);
		tempcard.setEntry_time(entry_time);
		tempcard.setSeat_id(seat_id);
		//获得当前页数
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		//Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		Integer pageSize=15;//每页展示信息条数
		
		TempCardDao tempcardDao=new TempCardDao();
		//从数据库中获取相关临时车主的信息（车牌号  ID   车位号   入场时间  出场时间  花费等）
		List<TempCard> tempcardList=tempcardDao.getTempCardList(tempcard, new Page(currentPage,pageSize));
		int total=tempcardDao.getTempCardListTotal(tempcard);//  获得信息的总条数
	    tempcardDao.closeCon();//关闭数据库连接
	    int totalPage=total/pageSize+1;
	    request.setAttribute("list", tempcardList);//设置  信息列表属性  以便让网页端接受
	    request.setAttribute("totalPage",totalPage);//设置信息展示总页数
	    //这张方法前一个网页设置的属性后一个网页也能用
		try {//跳转到  展示信息的界面
			request.getRequestDispatcher("/Admin/TempCardMsg.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//请求转发

	}
	private void TempCardAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		//这里也有停车位   状态转变问题
		String tempcard_id=request.getParameter("card_id");
		String car_num=new String(request.getParameter("car_num").getBytes("ISO-8859-1"),"UTF-8");
		String seat_id=request.getParameter("seat_id");
		//往停车位中添加临时IC卡就要改变分配的车位的  seat_tag  临时车主车位
		//车位的状态seat_state也要改变  临时卡  只有在进场时才会分配   进场即改变车位状态  为占有   1     
		Seat seat=new Seat();
		SeatDao seatDao=new SeatDao();//停车位逻辑层，对数据库 表进行操作
		seat=seatDao.getSeat(seat_id);
		seat.setSeat_state(1);//入场后 更新临时车主占用车位的状态 ，设置为占有状态
		seat.setSeat_tag("临时车主车位");//设置备注未临时车主车位
		seatDao.updateSeat(seat);//更新状态
		seatDao.closeCon();
		
		String entry_time,entry_date;
		SimpleDateFormat dFormat=new SimpleDateFormat("yyyy-MM-dd");//设置进场时间
		SimpleDateFormat tFormat=new SimpleDateFormat("HH:mm:ss");//进场具体时间
		entry_date=dFormat.format(new Date());
		entry_time=tFormat.format(new Date());
		
		String out_date="1111-11-11";//先将进场时间设置为1111-11-11  标识未出场
		String out_time="11:11:11";		
		
		TempCard tempcard=new TempCard();
		tempcard.setCard_id(tempcard_id);//设置临时车主的相关信息  ID
		tempcard.setSeat_id(seat_id);//分配的停车位
		tempcard.setCar_num(car_num);//车牌号
		tempcard.setEntry_date(entry_date);//进场时间
		tempcard.setEntry_time(entry_time);//具体时间
		tempcard.setOut_date(out_date);//
		tempcard.setOut_time(out_time);
		//缴费  添加卡时默认为0
		TempCardDao tempcardDao=new TempCardDao();
		if(tempcardDao.addTempCard(tempcard)) {//对数据库中临时车主信息表进行添加信息
			try {
				out.write("<script>alert('添加信息成功（进场）！'); location.href = '/ParkManager/TempCardServlet?type=gettempcardlist';</script>");
				//response.getWriter().write("success");
			}
			finally {
				tempcardDao.closeCon();
			}
		}
		else {
			try {
				out.write("<script>alert('添加信息失败！'); location.href = '/ParkManager/TempCardServlet?type=gettempcardlist';</script>");
				//response.getWriter().write("failed");
			}finally {
				tempcardDao.closeCon();
			}
		}
	}
}
