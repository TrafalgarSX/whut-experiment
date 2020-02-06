<%@page  errorPage="_Error.jsp"%>
<%@page import="java.util.*"%>
<%@page import="model.TempCard"%>
<%@page import="dao.TempCardDao"%>
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
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
	$(function()
	{		
		//取消按钮点击事件
		$("#btnCancel").click(function()
		{
			location.href="<%=path %>/TempCardServlet?type=gettempcardlist";//点击后跳转至CardHandle Servlet页面
		});
		//获取当前时间
		$("[name=out_date]").val(getNowFormatDate());
		$("[name=out_time]").val(getNowFormatTime());
		
		var dateEntry=$("[name=entry_date]").val()+" "+$("[name=entry_time]").val();
		var dateOut=$("[name=out_date]").val()+" "+$("[name=out_time]").val();
		
		//获得当前停留时间
		var stayTime=new Date(dateOut).getTime()-new Date(dateEntry).getTime();
		var hours=Math.ceil(parseFloat(stayTime)/(1000*60*60));
		$("[name=time]").val(hours);
		$("[name=temp_money]").val($("[name=price]").val()*hours);
		$("[name=price]").blur(function()
				{
					$("[name=temp_money]").val($("[name=price]").val()*hours);
				});
		
	});
	function getNowFormatDate() {
	    var date = new Date();
	    var seperator1 = "-";
	    var year = date.getFullYear();
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    var currentdate = year + seperator1 + month + seperator1 + strDate;
	    return currentdate;
	}
	
	function getNowFormatTime()
	{
		var date = new Date();
		var seperator2 = ":";
		var currentTime= date.getHours() + seperator2 + date.getMinutes()+ seperator2 + date.getSeconds();
		return currentTime;
	}
</script>

</head>
<body>
	<%
		response.setCharacterEncoding("UTF-8");//设置输出编码格式
		String card_id=request.getParameter("card_id").toString();//获取url传过来的card_id
		TempCardDao tempcardDao=new TempCardDao();
	    TempCard tempcard=tempcardDao.getTempCard(card_id);
	    tempcardDao.closeCon();
	 %>
	 <form action="<%=path %>/TempCardServlet?type=edit" method="post">
		<table style=" margin:50px auto;">
	            <tbody>
			 <tr><td>临时IC卡ID：</td><td><input type="text" name="card_id" value="<%=tempcard.getCard_id() %>" readonly /></td></tr>
			 <tr><td>车位编号：</td><td>
				<select name="seat_id">
				<%
				 Seat seat=new Seat();
			     SeatDao seatDao=new SeatDao();
			     List<Seat> list=seatDao.getNoUseSeat();
			     seatDao.closeCon();
			     if("1111-11-11".equals(tempcard.getOut_date())){
			     for(int i=0;i<list.size();i++)
			     {
			      seat=list.get(i);
			      if(i==0)
			      {
			       out.write("<option value='"+tempcard.getSeat_id()+"' selected='selected'>"+tempcard.getSeat_id()+"</option>");
			      }
			      else
			      {
			       out.write("<option value='"+seat.getSeat_id()+"'>"+seat.getSeat_id()+"</option>");
			       }
			      }
			     }
			     else{
			    	  out.write("<option value='"+tempcard.getSeat_id()+"' selected='selected' readonly='readonly'>"+tempcard.getSeat_id()+"</option>");
			     }
				 %>
				</select>
			 </td></tr>
			 <tr><td>车牌号码：</td><td><input type="text" name="car_num" value="<%=tempcard.getCar_num() %>" readonly="readonly" pattern="^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$" /></td></tr>
			 <tr><td>入场日期：</td><td><input type="text" name="entry_date" value="<%=tempcard.getEntry_date() %>" readonly="readonly" /></td></tr>
			 <tr><td>入场时间：</td><td><input type="text" name="entry_time" value="<%=tempcard.getEntry_time() %>" readonly="readonly" /></td></tr>
			 <tr><td>出场日期：</td><td><input type="text" name="out_date" value="" readonly="readonly" /></td></tr>
			 <tr><td>出场时间：</td><td><input type="text" name="out_time" value="" readonly="readonly" /></td></tr>
			 <tr><td>停车小时：</td><td><input type="text" name="time" value="" readonly="readonly" style="width:140px" />小时</td></tr>
			 <tr><td>停车单价：</td><td><input type="text" name="price" value="5" style="width:140px"  readonly="readonly"/>每小时</td></tr>
			 <tr><td>总费用：</td><td><input type="text" name="temp_money" style="width:140px" value="<%=tempcard.getPay() %>" readonly="readonly" />元</td></tr>
		  
		    </tbody>   
	            
	            <tfoot>
	                <tr><td><input type="submit" value="确定" id="btnSure"/></td><td><input type="button" value="取消" id="btnCancel"/></td></tr>
	            </tfoot> 
	        </table>
     </form>
</body>
</html>
