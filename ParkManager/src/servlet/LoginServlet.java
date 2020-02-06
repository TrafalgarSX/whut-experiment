package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AdminDao;
import model.Admin;
/**
 * 
 * @author guo
 *��½��֤servlet
 */

public class LoginServlet extends HttpServlet {
/*
 * 
 * 
 */
	public void doGet(HttpServletRequest request,HttpServletResponse response )throws IOException {//������֤��
		doPost(request,response);
		}
	public void doPost(HttpServletRequest request,HttpServletResponse response )throws IOException {//������֤��
		String method=request.getParameter("method");
		if("loginout".equals(method)) {
			logout(request,response);
			return;
		}
		String user_id=request.getParameter("account");
		String password=request.getParameter("password");
	
		
		//
		String loginStatus="loginFaild";
		
		AdminDao adminDao=new AdminDao();
		Admin admin = adminDao.login(user_id, password);
		adminDao.closeCon();
		if(admin==null) {
			response.getWriter().write("loginError");
			return;
				}
		HttpSession session =request.getSession();
		session.setAttribute("user_name",admin);
		session.setAttribute("role_id", admin.getRole_id());
		loginStatus="loginSuccess";
		
		response.getWriter().write(loginStatus);
		//response.sendRedirect("view/Index.jsp");
//		try {
//			request.getRequestDispatcher("/Index.jsp").forward(request, response);
//		} catch (ServletException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		}
	private void logout(HttpServletRequest request,HttpServletResponse response) throws IOException {
		request.getSession().removeAttribute("user_name");
		response.sendRedirect("login.jsp");//�ض���
		
	}
	/*
	 *   <filter>
  	<description>登录状态过滤</description>
  	<filter-name>LoginFilter</filter-name>
  	<filter-class>Filter.LoginFilter</filter-class>
  </filter>
  <filter-mapping>
  	<filter-name>LoginFilter</filter-name>
  	<url-pattern>/LoginServlet</url-pattern>
  	<url-pattern>/RoleServlet</url-pattern>
  	<url-pattern>/CardServlet</url-pattern>
  	<url-pattern>/FixedServlet</url-pattern>
  	<url-pattern>/TempServlet</url-pattern>
  		<url-pattern>/SeatServlet</url-pattern>
  </filter-mapping>
	 * 
	 */
}
