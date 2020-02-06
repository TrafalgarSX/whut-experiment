<%@page  errorPage="_Error.jsp"%>
<%@page import="java.util.*"%>
<%@page import="model.Admin"%>
<%@page import="dao.AdminDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();//获取项目名称
%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../Style/EditStyle.css"/>
<script type="text/javascript" src="<%=path %>/Script/jquery-1.10.1.js"></script>
<script type="text/javascript">
	$(function()
	{		
		//取消按钮点击事件
		$("#btnCancel").click(function()
		{
			location.href="<%=path %>/RoleServlet?type=getadminlist";//点击后跳转至UserHandle Servlet页面
		});
	});
</script>

</head>
<body>
	<%
	response.setCharacterEncoding("UTF-8");//设置输出编码格式
	String Admin_id=request.getParameter("Admin_id").toString();//获取url传过来的user_id
		AdminDao adminDao=new AdminDao();
		Admin admin=adminDao.getAdmin(Admin_id);
		adminDao.closeCon();

	 %>
	 <form action="<%=path %>/RoleServlet?type=edit" method="post">
		<table style=" margin:50px auto;">
	            <tbody>
			 <tr><td>管理员id：</td><td><input type="text" name="admin_id" value="<%=admin.getUser_id() %>" readonly="readonly" pattern="^[a-zA-Z0-9]{6,10}$" required placeholder="6-10位包含英文和数字的账号"/></td></tr>
			 <tr><td>角色：</td><td>
			 	<select name="role_id">
						<option value=0>超级管理员</option>
					<option value=1>管理员</option>  
			</select>
			 </td></tr>
			 <tr><td>管理员姓名：</td><td><input type="text" name="admin_name" value="<%=admin.getName() %>" required pattern="^[\u0391-\uFFE5]+$" required placeholder="中文名"/></td></tr>
			 <tr><td>管理员密码：</td><td><input type="text" name="admin_pass" value="<%=admin.getUser_pwd() %>" required pattern="^[a-zA-Z0-9]{6,15}$"  placeholder="6-15位包含数字和英文的密码" /></td></tr>
			 <tr><td>管理员联系方式：</td><td><input type="text" name="admin_phone" value="<%=admin.getUser_phone() %>"  pattern="^1[3|4|5|8|9]\d{9}$" title="手机号码有误" /></td></tr>

		    </tbody>   
	            
	            <tfoot>
	                <tr><td><input type="submit" value="确定" id="btnSure"/></td><td><input type="button" value="取消" id="btnCancel"/></td></tr>
	            </tfoot> 
	        </table>
     </form>
</body>
</html>
