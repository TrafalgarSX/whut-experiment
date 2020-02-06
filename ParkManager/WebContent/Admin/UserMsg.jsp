<%@page errorPage="_Error.jsp"%>
<%@page import="java.util.*"%>
<%@page import="model.Admin"%>
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
				
				location.href="<%=path %>/RoleServlet?type=getadminlist&page="+($(".txtPage").val()==""?1:$(".txtPage").val())+"&condition="+$("[name=hcondition]").val()+"&search_value="+$("[name=hvalue]").val();
			}	
		});
	
	});
</script>
</head>
<body>
<div class="div_container">
	<div class="searchDiv">

	
	
		<form action="<%=path %>/RoleServlet" method="get">
			<span>查询条件：</span>
			<select name="condition">
			 	<option value='admin_id'>管理员id</option>
			 	<option value='admin_name'>管理员姓名</option>
			</select>
			 	
			<span>查询值：</span>
			<input type="hidden" name="type" value="getadminlist" />
			<input type="text" name="search_value" />
			<input type="submit" value="查询 "/>
		</form>
	</div>
	<!-- searchDiv End -->

	<table>
		<thead>
			<tr>
				<th>管理员id</th>
				<th>角色</th>
				<th>管理员姓名</th>
				<th>管理员密码</th>
				<th>管理员联系方式</th>
				<th>操作</th>
			</tr>
		</thead>

		<tbody>
			<% 
				request.setCharacterEncoding("UTF-8");//设置获取数据的编码格式
				List<Admin> list=(List<Admin>)request.getAttribute("list");//获取servlet端转发的list数据列表
				out.write("<input type='hidden' name='hcondition' value='"+(request.getAttribute("condition")!=null?request.getAttribute("condition").toString():"")+"' />");
				out.write("<input type='hidden' name='hvalue' value='"+(request.getAttribute("search_value")!=null?request.getAttribute("search_value").toString():"")+"' />");
                		if(list!=null) //循环数据列表，生成表格行
					{
						for(int i=0;i<list.size();i++)
						{
							Admin admin;
							admin=(Admin)list.get(i);
							out.print("<tr><td>"+admin.getUser_id()+"</td><td>"+admin.getRole_id()+"</td><td>"+admin.getName()+"</td><td>"+admin.getUser_pwd()+"</td><td>"+admin.getUser_phone()+"</td><td><a href='"+path+"/Admin/UserEdit.jsp?Admin_id="+admin.getUser_id()+"' class='a_edit'>编辑</a><a href='"+path+"/RoleServlet?type=delete&Admin_id="+admin.getUser_id()+"' class='a_del' onclick='return confirm(\"是否确认删除？\")'>删除</a></td></tr>");
						}
					}

				%>
		</tbody>

		<tfoot>
			<tr><td colspan="6">
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
