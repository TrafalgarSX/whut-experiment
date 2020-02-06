<%@page errorPage="_Error.jsp"%>
<%@page import="java.util.*"%>
<%@page import="model.Fixed"%>
<%@page import="dao.FixedDao"%>
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
			location.href="<%=path %>/FixedServlet?type=getfixedlist";//点击后跳转至FixedServlet页面
		});
	});
</script>

</head>
<body>
	<%
		response.setCharacterEncoding("UTF-8");//设置输出编码格式
		String fixed_id=request.getParameter("card_id").toString();//获取url传过来的card_id
		String entry_date=request.getParameter("entry_date");
		String entry_time=request.getParameter("entry_time");
		FixedDao  fixedDao=new FixedDao();
		Fixed fixed=new Fixed();
		fixed=fixedDao.getFixed(fixed_id,entry_date,entry_time);
		fixedDao.closeCon();	        
	        
	 %>
	 <form action="<%=path %>/FixedServlet?type=edit" method="post">
		<table style=" margin:50px auto;">
	            <tbody>
			 <tr><td>IC卡号：</td><td><input type="text" name="card_id" value="<%=fixed.getCard_id() %>" readonly="readonly"/></td></tr>
			 <tr><td>入场日期：</td><td><input type="text" name="entry_date" value="<%=fixed.getEntry_date() %>" readonly="readonly"/></td></tr>
			 <tr><td>入场时间：</td><td><input type="text" name="entry_time" value="<%=fixed.getEntry_time() %>" readonly="readonly"/></td></tr>
			 <tr><td>出场日期：</td><td><input type="text" name="out_date" value="<%=fixed.getOut_date() %>" /></td></tr>
			 <tr><td>出场时间：</td><td><input type="text" name="out_time" value="<%=fixed.getOut_time() %>" /></td></tr>

		    </tbody>   
	            
	            <tfoot>
	                <tr><td><input type="submit" value="确定" id="btnSure"/></td><td><input type="button" value="取消" id="btnCancel"/></td></tr>
	            </tfoot> 
	        </table>
     </form>
</body>
</html>
