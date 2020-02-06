package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AdminDao;
import dao.SeatDao;
import model.Admin;
import model.Page;

public class RoleServlet extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response )throws IOException {//������֤��
		doPost(request,response);
		}
	public void doPost(HttpServletRequest request,HttpServletResponse response )throws IOException {//������֤��
		String method=request.getParameter("type");
		if("add".equals(method)) {
			RoleAdd(request,response);
			return;
		}
		else if("getadminlist".equals(method)){
		    GetAdminList(request,response);
		}
		else if("edit".equals(method)) {
			RoleEdit(request,response);
		}
		else if("delete".equals(method)) {
			RoleDelete(request,response);
		}
		else if("change".equals(method)) {
			RoleChange(request,response);
		}
}
	private void RoleChange(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String password=request.getParameter("OldPwd");
		String newpassword=request.getParameter("NewPwd");
		PrintWriter outp=response.getWriter();
		Admin admin=(Admin)request.getSession().getAttribute("user_name");
		if(!admin.getUser_pwd().equals(password)) {
			outp.write("<script>alert('原密码错误！'); </script>");
			//response.getWriter().write("原密码错误");
			return;
		}
		AdminDao adminDao=new AdminDao();
		if(adminDao.editPassword(admin,newpassword)) {
			try {
				outp.write("<script>alert('修改密码成功！'); window.parent.location.href = '/ParkManager/login.jsp';</script>");
				//response.getWriter().write("success");
			}finally {
				adminDao.closeCon();
			}
		}else {
			try {
				outp.write("<script>alert('数据库修改错误！'); </script>");
				//response.getWriter().write("数据库修改错误！");
			}finally {
				adminDao.closeCon();
				}
			}
	}
	private void RoleDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String admin_id=request.getParameter("Admin_id");
		
		AdminDao adminDao=new AdminDao();
		
		PrintWriter out=response.getWriter();
		if(adminDao.deleteAdmin(admin_id)) {
			try {
				out.write("<script>alert('删除信息成功！'); location.href = '/ParkManager/RoleServlet?type=getadminlist';</script>");
				//response.getWriter().write("success");
			}finally {
				adminDao.closeCon();
			}
		}
		else {
			try {
				out.write("<script>alert('删除信息失败！'); location.href = '/ParkManager/RoleServlet?type=getadminlist';</script>");
				//response.getWriter().write("failed");
			}finally {
				adminDao.closeCon();
			}
		}
	}
	private void RoleEdit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		int role_id=Integer.parseInt(request.getParameter("role_id"));
		String admin_name=new String(request.getParameter("admin_name").getBytes("ISO-8859-1"),"UTF-8");
		String admin_id=request.getParameter("admin_id");
		String admin_pass=request.getParameter("admin_pass");
		String admin_phone=request.getParameter("admin_phone");
		
		Admin admin=new Admin();
		
		admin.setName(admin_name);
		admin.setRole_id(role_id);
		admin.setUser_id(admin_id);
		admin.setUser_phone(admin_phone);
		admin.setUser_pwd(admin_pass);
		
		AdminDao adminDao=new AdminDao();
		PrintWriter out=null;
		out=response.getWriter();
		if(adminDao.updateAdmin(admin)) {
			
			try {
				out.write("<script>alert('修改信息成功！'); location.href = '/ParkManager/RoleServlet?type=getadminlist';</script>");
			//	response.getWriter().write("success");
			}finally {
				adminDao.closeCon();
			}
		}
		else {
			try {
				out.write("<script>alert('修改信息失败！'); location.href = '/ParkManager/RoleServlet?type=getadminlist';</script>");

			//	response.getWriter().write("failed");
			}finally {
				adminDao.closeCon();
			}
		}
	}
	private void GetAdminList(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		//通过管理员姓名和ID来搜索学生
		String condition=request.getParameter("condition");//获取查询字段的名称

		String admin_id=null;
		String admin_name = null;
		if("admin_id".equals(condition)) {
			 admin_id=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");
			 request.setAttribute("search_value",admin_id);
		}
		else  if ("admin_name".equals(condition)) {
			// admin_name=new String(request.getParameter("search_value").getBytes("ISO-8859-1"),"UTF-8");			
			admin_name=request.getParameter("search_value");
			 request.setAttribute("search_value",admin_name);
		}
		request.setAttribute("condition",condition);
		
		Admin admin=new Admin();
		admin.setUser_id(admin_id);
		admin.setName(admin_name);

		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		//Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		Integer pageSize=15;
		
		
		AdminDao adminDao=new AdminDao();
		List<Admin> adminList=adminDao.getAdminList(admin, new Page(currentPage,pageSize));
		int total=adminDao.getAdminListTotal(admin);
	    adminDao.closeCon();
	    int totalPage=total/pageSize+1;
	    request.setAttribute("list", adminList);
	    request.setAttribute("totalPage",totalPage);
	    //这张方法前一个网页设置的属性后一个网页也能用
		try {
			request.getRequestDispatcher("/Admin/UserMsg.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//请求转发

		
		
	}
	private void RoleAdd(HttpServletRequest request, HttpServletResponse response)   {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		int role_id=Integer.parseInt(request.getParameter("role_id"));
		String admin_name = null;
		try {
			admin_name = new String(request.getParameter("admin_name").getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String admin_id=request.getParameter("admin_id");
		String admin_pass=request.getParameter("admin_pass");
		String admin_phone=request.getParameter("admin_phone");
		
		Admin admin=new Admin();
		admin.setName(admin_name);
		admin.setRole_id(role_id);
		admin.setUser_id(admin_id);
		admin.setUser_phone(admin_phone);
		admin.setUser_pwd(admin_pass);
		AdminDao adminDao=new AdminDao();
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(adminDao.getAdmin(admin_id)==null) {
		if(adminDao.addAdmin(admin)) {
			try {
				out.write("<script>alert('数据添加成功！'); location.href = '/ParkManager/RoleServlet?type=getadminlist';</script>");
				//response.getWriter().write("success");
			}finally {
				adminDao.closeCon();
			}
		}
		else {
			try {
				out.write("<script>alert('数据添加失败！'); location.href = '/ParkManager/RoleServlet?type=getadminlist';</script>");
				//response.getWriter().write("failed");
			}finally {
				adminDao.closeCon();
			}
		}
		}
		else {
			out.write("<script>alert('用户名重复！'); location.href = '/ParkManager/RoleServlet?type=getadminlist';</script>");
		}
	}
}