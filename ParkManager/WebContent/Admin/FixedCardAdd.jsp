<%@page import="java.util.*" errorPage="_Error.jsp"%>
<%@page import="dao.SeatDao"%>
<%@page import="model.Seat"%>
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
 <script type="text/javascript"src="<%=path %>/Script/generateDate.js"></script>
</head>
<body>
	<form action="<%=path %>/FixedCardServlet?type=add" method="post" class="Form">
		<table style=" margin:50px auto;"> 
	            <tbody>
	        <tr>
	        <td>固定卡ID：</td><td><input id="card_id" type="text" name="card_id" required pattern="^[0-9]*$" placeholder="请点击生成ID" /></td>
	         <td> <button id='generate' type="button">生成ID</button></td>
	        </tr>
			<tr><td>车位编号：</td><td>
				<select name="seat_id">
					<%
					     SeatDao seatDao=new SeatDao();
					     List<Seat> list= seatDao.getNoUseSeat();
					     for(int i=0;i<list.size();i++)
					     {
					      Seat seat=(Seat)list.get(i);
					      out.write("<option value='"+seat.getSeat_id()+"'>"+seat.getSeat_id()+"</option>");
					     }
					 %>
				</select>
			</td></tr>
			<tr><td>用户姓名：</td><td><input type="text" name="name"  required  pattern="^[\u0391-\uFFE5]+$" required placeholder="中文名"/></td></tr>
			<tr><td>家庭住址：</td><td><input type="text" name="address" required  pattern="^(?=.*?[\u4E00-\u9FA5])[\dA-Za-z-\u4E00-\u9FA5]+$" required placeholder="家庭住址" /></td></tr>
			<tr><td>车牌号码：</td><td><input type="text" name="car_num" required pattern="^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$" /></td></tr>
	            </tbody>   
	            
	            <tfoot>
	                <tr><td><input type="submit" value="确定" id="btnSure"/></td><td><input type="reset" value="取消" id="btnCancel"/></td></tr>
	            </tfoot> 
	        </table>
       </form>
</body>
</html>
