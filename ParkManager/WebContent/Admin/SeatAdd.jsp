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
	<form action="<%=path %>/SeatServlet?type=add" method="post" class="Form">
		<table style=" margin:50px auto;"> 
	            <tbody>
			<tr><td>车位ID：</td><td><input type="text" name="seat_id" required pattern="^[0-9]*$" placeholder="请填写数字" /></td></tr>
			<tr><td>所属区域：</td><td>
				<select name="seat_section"  >
					<option>A区</option>
					<option>B区</option>
				</select>
			</td></tr>
			<tr>
			<td>车位备注：</td>
			<td>
			<select name="seat_tag"  >
			<option>固定车主车位</option>
			<option>临时车主车位</option>
			</select>
			</td></tr>
	            </tbody>   
	            
	            <tfoot>
	                <tr><td><input type="submit" value="确定" id="btnSure"/></td><td><input type="reset" value="取消" id="btnCancel"/></td></tr>
	            </tfoot> 
	        </table>
       </form>
</body>
</html>
