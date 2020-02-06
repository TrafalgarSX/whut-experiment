<%@page import="java.util.*" errorPage="_Error.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();//获取项目名称
%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=path %>/Style/AddStyle.css"/>
<script type="text/javascript" src="<%=path %>/Script/jquery-1.10.1.js"></script>
</head>
<body>
	<form action="<%=path %>/RoleServlet?type=add" method="post" class="Form">
		<table style=" margin:50px auto;"> 
	            <tbody>
			<tr><td>管理员id：</td><td><input type="text" name="admin_id" pattern="^[a-zA-Z0-9]{6,10}$" required placeholder="6-10位包含英文和数字的账号" /></td></tr>
			<tr><td>角色名称：</td><td>
				<select name="role_id">
					<option value=0>超级管理员</option>
					<option value=1>管理员</option>
				</select>
			</td></tr>
			<tr><td>管理员姓名：</td><td><input type="text" name="admin_name" pattern="^[\u0391-\uFFE5]+$" required placeholder="中文名"/></td></tr>
			<tr><td>管理员密码：</td><td><input type="text" name="admin_pass" required pattern="^[a-zA-Z0-9]{6,15}$"  title="密码长度不能少于6位" placeholder="6-15位包含数字和英文的密码" /></td></tr>
			<tr><td>管理员联系电话：</td><td><input type="text" name="admin_phone" pattern="^1[3|4|5|8|9]\d{9}$" title="手机号码有误"  /></td></tr>
	            </tbody>   
	            
	            <tfoot>
	                <tr><td><input type="submit" value="确定" id="btnSure"/></td><td><input type="reset" value="取消" id="btnCancel"/></td></tr>
	            </tfoot> 
	        </table>
       </form>
</body>
</html>
