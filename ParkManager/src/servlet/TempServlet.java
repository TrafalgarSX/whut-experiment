package servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TempDao;
import model.Page;
import model.Temp;

public class TempServlet extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response )throws IOException {//������֤��
		doPost(request,response);
		}
	public void doPost(HttpServletRequest request,HttpServletResponse response )throws IOException {//������֤��
		String method=request.getParameter("type");
		if("add".equals(method)) {
			TempAdd(request,response);
			return;
		}
		else if("gettemplist".equals(method)){
		    GetTempList(request,response);
		}
		else if("edit".equals(method)) {
			TempEdit(request,response);
		}
		else if("delete".equals(method)) {
			TempDelete(request,response);
		}
}
	private void TempDelete(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String temp_id =request.getParameter("card_id");
		
		TempDao tempDao=new TempDao();
		if(tempDao.deleteTemp(temp_id)) {
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				tempDao.closeCon();
			}
		}
		else {
			try {
				response.getWriter().write("failed");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				tempDao.closeCon();
			}
		}
	}
	private void TempEdit(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String temp_id=request.getParameter("card_id");
		double pay=Double.parseDouble(request.getParameter("pay"));
		String entry_time=request.getParameter("entry_time");
		String entry_date=request.getParameter("entry_date");
		String out_time=request.getParameter("out_time");
		String out_date=request.getParameter("out_date");		
		
		Temp temp=new Temp();
		
		temp.setCard_id(temp_id);
		temp.setEntry_date(entry_date);
		temp.setEntry_time(entry_time);
		temp.setOut_date(out_date);
		temp.setOut_time(out_time);
		temp.setPay(pay);
		
		TempDao tempDao=new TempDao();
		if(tempDao.updateTemp(temp)) {
			
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				tempDao.closeCon();
			}
		}
		else {
			try {
				response.getWriter().write("failed");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				tempDao.closeCon();
			}
		}
	}
	private void GetTempList(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html);charset=UTF-8");
		//通过管理员姓名和ID来搜索学生
		String condition=request.getParameter("condition");//获取查询字段的名称

		String temp_id=null;
		String entry_time = null;
		if("card_id".equals(condition)) {
			 temp_id=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");
			 request.setAttribute("search_value",temp_id);
		}
		else  if ("entry_time".equals(condition)) {
			// temp_name=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");			
			entry_time=request.getParameter("search_value");
			 request.setAttribute("search_value",entry_time);
		}
		request.setAttribute("condition",condition);
		
		Temp temp=new Temp();
		temp.setCard_id(temp_id);
		temp.setEntry_time(entry_time);

		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		//Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		Integer pageSize=15;
		
		
		TempDao tempDao=new TempDao();
		List<Temp> tempList=tempDao.getTempList(temp, new Page(currentPage,pageSize));
		int total=tempDao.getTempListTotal(temp);
	    tempDao.closeCon();
	    int totalPage=total/pageSize+1;
	    request.setAttribute("list", tempList);
	    request.setAttribute("totalPage",totalPage);
	    //这张方法前一个网页设置的属性后一个网页也能用
		try {
			request.getRequestDispatcher("/Admin/TempMsg.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//请求转发

	}
	private void TempAdd(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html);charset=UTF-8");
		
		String temp_id=request.getParameter("card_id");
		double pay=Double.parseDouble(request.getParameter("pay"));
//		String entry_time=request.getParameter("entry_time");
//		String entry_date=request.getParameter("entry_date");
		
		String entry_time,entry_date;
		SimpleDateFormat dFormat=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat tFormat=new SimpleDateFormat("HH:mm:ss");
		entry_date=dFormat.format(new Date());
		entry_time=tFormat.format(new Date());
		
		String out_time="1111-11-11";
		String out_date="11:11:11";		
		
		Temp temp=new Temp();
		
		temp.setCard_id(temp_id);
		temp.setEntry_date(entry_date);
		temp.setEntry_time(entry_time);
		temp.setOut_date(out_date);
		temp.setOut_time(out_time);
		temp.setPay(pay);
		
		TempDao tempDao=new TempDao();
		if(tempDao.addTemp(temp)) {
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				tempDao.closeCon();
			}
		}
		else {
			try {
				response.getWriter().write("failed");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				tempDao.closeCon();
			}
		}
	}
}
