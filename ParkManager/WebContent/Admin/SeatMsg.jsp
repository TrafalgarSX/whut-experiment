<%@page errorPage="_Error.jsp"%>
<%@page import="java.util.*"%>
<%@page import="model.Seat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path=request.getContextPath();//获取项目名称
 %>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=path %>/Style/MsgStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path %>/Script/jquery-1.10.1.js"></script>
<title>Insert title here</title>
<script>
	$(function()
	{
		$(".btnGo").click(function()
		{
			if($("[name=htype]").val()=="turnPage")
			{
				location.href="<%=path %>/SeatServlet?type=getseatlist&page="+($(".txtPage").val()==""?1:$(".txtPage").val())+"&condition="+$("[name=hcondition]").val()+"&search_value="+$("[name=hvalue]").val();	
			}
		});
	});
</script>
</head>
<body>
<div class="div_container">
	<div class="searchDiv">
		<form action="<%=path %>/SeatServlet" method="get">
			<span>查询条件：</span>
			<select name="condition">
			 	<option value='seat_id'>车位ID</option>
				<option value='seat_section'>所属区域</option>
				<option value='seat_tag'>车位备注</option>
                <option value='seat_state'>车位状态</option>
			</select>
			 	
			<span>查询值：</span>
			<input type="hidden" name="type" value="getseatlist" />
			<input type="text" name="search_value" />
			<input type="submit" value="查询 "/>
		</form>
	</div>
	<!-- searchDiv End -->

	<table>
		<thead>
			<tr>
				<th>车位ID</th>
				<th>所属区域</th>
				<th>车位状态</th>
				<th>车位备注</th>
				<th>操作</th>
			</tr>
		</thead>

		<tbody>
			<% 
	            response.setCharacterEncoding("UTF-8");//设置输出数据的编码格式
				request.setCharacterEncoding("UTF-8");//设置获取数据的编码格式

				List<Seat> list=(List<Seat>)request.getAttribute("list");//获取servlet端转发的list数据列表
				out.write("<input type='hidden' name='hcondition' value='"+(request.getAttribute("condition")!=null?request.getAttribute("condition").toString():"")+"' />");
				out.write("<input type='hidden' name='hvalue' value='"+(request.getAttribute("search_value")!=null?request.getAttribute("search_value").toString():"")+"' />");
                		if(list!=null) //循环数据列表，生成表格行
					{
						for(int i=0;i<list.size();i++)
						{
							Seat seat;
							seat=(Seat)list.get(i);
							out.print("<tr><td>"+seat.getSeat_id()+"</td><td>"+seat.getSeat_section()+"</td><td>"+seat.getSeat_state()+"</td><td>"+seat.getSeat_tag()+"</td><td><a href='"+path+"/Admin/SeatEdit.jsp?Seat_id="+seat.getSeat_id()+"' class='a_edit'>编辑</a><a href='"+path+"/SeatServlet?type=delete&Seat_id="+seat.getSeat_id()+"' class='a_del' onclick='return confirm(\"是否确认删除？\")'>删除</a></td></tr>");
						}
					}

	                %>
		</tbody>

		<tfoot>
            		<tr><td colspan="5">
            				<div>
						<%
						int totalPage=0;
						if(request.getAttribute("totalPage")!=null){
							totalPage=Integer.parseInt(request.getAttribute("totalPage").toString());
							out.write("<input type='hidden' name='htype' value='turnPage' />");
						}
            					 %>
            					<span>共<strong><%=totalPage %></strong>页  </span>
            					<span>跳转至</span><input type="text" class="txtPage" /><input type="button" value="转" class="btnGo" />
            				</div>
            			</td>
			</tr>
            </tfoot>

	</table>
</div>
</body>
</html>
