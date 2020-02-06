<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<script>
	function checkPwd()
	{
		if($("[name=NewPwd]").val()==$("[name=RepPwd]").val())
		{
			return true;
		}
		alert("两次输入密码不一~~~");
		return false;
	}
</script>
</head>
<body>
	<form action="<%=path %>/RoleServlet?type=change"  method="post"  onsubmit="return checkPwd()">
		<table style=" margin:50px auto;">
			<tbody>
				<tr><td>原始密码:</td><td><input type="password"  name="OldPwd" required /></td></tr>
				<tr><td>新  密  码：</td><td><input type="password"  name="NewPwd" required /></td></tr>
				<tr><td>重复密码:</td><td><input type="password"  name="RepPwd" required /></td></tr>
				<tr><td></td><td>
					<input type="submit" class="btnSure"  value="提交"/>
					<input type="reset"  value="重置"/>
				</td></tr>
			</tbody>
		</table>
	</form>
	
</body>
</html>