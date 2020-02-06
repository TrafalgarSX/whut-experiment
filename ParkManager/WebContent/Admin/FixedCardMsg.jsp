<%@page errorPage="_Error.jsp"%>
<%@page import="java.util.*"%>
<%@page import="model.FixedCard"%>
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
				location.href="<%=path %>/FixedCardServlet?type=getfixedcardlist&page="+($(".txtPage").val()==""?1:$(".txtPage").val())+"&condition="+$("[name=hcondition]").val()+"&search_value="+$("[name=hvalue]").val();
			}		
		});
	});
</script>
</head>
<body>
<div class="div_container">
	<div class="searchDiv">
		<form action="<%=path %>/FixedCardServlet" method="get">
			<span>查询条件：</span>
			<select name="condition">
			 	<option value='card_id'>固定IC卡ID</option>
				<option value='seat_id'>车位编号</option>
				<option value='name'>用户名称</option>
				<option value='address'>家庭住址</option>
				<option value='car_num'>车牌号码</option>

			</select>
			 	
			<span>查询值：</span>
			<input type="hidden" name="type" value="getfixedcardlist" />
			<input type="text" name="search_value" />
			<input type="submit" value="查询 "/>
		</form>
	</div>
	<!-- searchDiv End -->

	<table>
		<thead>
			<tr>
				<th>IC卡编号</th>
				<th>车位编号</th>
				<th>用户名称</th>
				<th>家庭住址</th>
				<th>车牌号码</th>

				<th>操作</th>
			</tr>
		</thead>

		<tbody>
			<% 
	            response.setCharacterEncoding("UTF-8");//设置输出数据的编码格式
				request.setCharacterEncoding("UTF-8");//设置获取数据的编码格式

				List<FixedCard> list=(List<FixedCard>)request.getAttribute("list");//获取servlet端转发的list数据列表
				out.write("<input type='hidden' name='hcondition' value='"+(request.getAttribute("condition")!=null?request.getAttribute("condition").toString():"")+"' />");
				out.write("<input type='hidden' name='hvalue' value='"+(request.getAttribute("Search_value")!=null?request.getAttribute("search_value").toString():"")+"' />");
                		if(list!=null) //循环数据列表，生成表格行
                		{
                			for(int i=0;i<list.size();i++)
	                		{
	               				FixedCard fixedcard;
	               				fixedcard=(FixedCard)list.get(i);
	               				out.print("<tr><td>"+fixedcard.getCard_id()+"</td><td>"+fixedcard.getSeat_id()+"</td><td>"+fixedcard.getName()+"</td><td>"+fixedcard.getAddress()+"</td><td>"+fixedcard.getCar_num()+"</td><td><a href='"+path+"/Admin/FixedCardEdit.jsp?card_id="+fixedcard.getCard_id()+"' class='a_edit'>编辑</a><a href='"+path+"/FixedCardServlet?type=delete&card_id="+fixedcard.getCard_id()+"' class='a_del' onclick='return confirm(\"是否确认删除？\")'>删除</a></td></tr>");
	                		}
                		}

	                %>
		</tbody>

		<tfoot>
            		<tr><td colspan="7">
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
