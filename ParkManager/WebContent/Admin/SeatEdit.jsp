<%@page errorPage="_Error.jsp"%>
<%@page import="java.util.*"%>
<%@page import="model.Seat"%>
<%@page import="dao.SeatDao"%>
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
			location.href="<%=path %>/SeatServlet?type=getseatlist";//点击后跳转至SeatHandle Servlet页面
		});
	});
</script>

</head>
<body>
	<%
		response.setCharacterEncoding("UTF-8");//设置输出编码格式
		String seat_id=request.getParameter("Seat_id").toString();//获取url传过来的seat_id
		SeatDao seatDao=new SeatDao();
		Seat seat=seatDao.getSeat(seat_id);
		seatDao.closeCon();

	 %>
	 <form action="<%=path %>/SeatServlet?type=edit" method="post">
		<table style=" margin:50px auto;">
	            <tbody>
			 <tr><td>车位ID：</td><td><input type="text" name="seat_id" value="<%=seat.getSeat_id() %>" readonly /></td></tr>
			 <tr><td>车位区域：</td><td>
			 	<select name="seat_section">
				      <%
				       if(seat.getSeat_section().toString().equals("A区"))
				       {
				      %>
				       <option value="A区" selected="selected">A区</option>
				       <option value="B区">B区</option>
				      <%
				       }
				       else
				       {
				       %>
				     	<option value="A区">A区</option>
				        <option value="B区" selected="selected">B区</option>
				      <% } %>
				</select>
			 </td></tr>
			 <tr><td>车位状态：</td><td>
			 	<select name="seat_state">
				      <%
				       if(seat.getSeat_state()==0)
				       {
				      %>
				       <option value="0" selected="selected">闲置</option>
				       <option value="1">占用</option>
				      <%
				       }
				       else
				       {
				       %>
				     	<option value="0">闲置</option>
				        <option value="1" selected="selected">占用</option>
				      <% } %>
				</select>
			 </td></tr>
			 <tr><td>车位备注：</td><td><input type="text" name="seat_tag" value="<%=seat.getSeat_tag() %>" /></td></tr>
		    </tbody>   
	            
	            <tfoot>
	                <tr><td><input type="submit" value="确定" id="btnSure"/></td><td><input type="button" value="取消" id="btnCancel"/></td></tr>
	            </tfoot> 
	        </table>
     </form>
</body>
</html>
