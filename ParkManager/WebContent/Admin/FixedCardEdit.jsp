<%@page  errorPage="_Error.jsp"%>
<%@page import="java.util.*"%>
<%@page import="model.FixedCard"%>
<%@page import="dao.FixedCardDao"%>
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
<link rel="stylesheet" type="text/css" href="../Style/EditStyle.css"/>
<script type="text/javascript" src="<%=path %>/Script/jquery-1.10.1.js"></script>
<script type="text/javascript">
	$(function()
	{		
		//取消按钮点击事件
		$("#btnCancel").click(function()
		{
			location.href="<%=path %>/FixedCardServlet?type=getfixedcardlist";//点击后跳转至FixedCardServlet页面
		});
	});
</script>

</head>
<body>
	<%
		response.setCharacterEncoding("UTF-8");//设置输出编码格式
		String card_id=request.getParameter("card_id").toString();//获取url传过来的card_id
		FixedCardDao fixedcardDao=new FixedCardDao();
	    FixedCard fixedcard=fixedcardDao.getFixedCard(card_id);
	    fixedcardDao.closeCon();
	 %>
	 <form action="<%=path %>/FixedCardServlet?type=edit" method="post">
		<table style=" margin:50px auto;">
	            <tbody>
			 <tr><td>固定IC卡ID：</td><td><input type="text" name="card_id" value="<%=fixedcard.getCard_id() %>" readonly pattern="^[0-9]*$"/></td></tr>
			 <tr><td>车位编号：</td><td>
				<select name="seat_id">
				<%
				     Seat seat=new Seat();
				     SeatDao seatDao=new SeatDao();
				     List<Seat> list=seatDao.getNoUseSeat();
				     seatDao.closeCon();
				     for(int i=0;i<list.size();i++)
				     {
				      seat=list.get(i);
				      if(i==0)
				      {
				       out.write("<option value='"+fixedcard.getSeat_id()+"' selected='selected'>"+fixedcard.getSeat_id()+"</option>");
				      }
				      else
				      {
				       out.write("<option value='"+seat.getSeat_id()+"'>"+seat.getSeat_id()+"</option>");
				      }
				     }
				 %>
				</select>
			 </td></tr>
			 <tr><td>用户名称：</td><td><input type="text" name="name" value="<%=fixedcard.getName() %>" pattern="^[\u0391-\uFFE5]+$" required/></td></tr>
			 <tr><td>用户地址：</td><td><input type="text" name="address" value="<%=fixedcard.getAddress() %>" required pattern="^(?=.*?[\u4E00-\u9FA5])[\dA-Za-z-\u4E00-\u9FA5]+$"  /></td></tr>
			 <tr><td>车牌号码：</td><td><input type="text" name="car_num" value="<%=fixedcard.getCar_num() %>" required pattern="^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$" /></td></tr>

		    </tbody>   
	            
	            <tfoot>
	                <tr><td><input type="submit" value="确定" id="btnSure"/></td><td><input type="button" value="取消" id="btnCancel"/></td></tr>
	            </tfoot> 
	        </table>
     </form>
</body>
</html>
