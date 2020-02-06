<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>异常处理页面</title>
<style type="text/css">
	*{ font-family:微软雅黑;}
	.div_error{width:480px; height:150px; margin:0 auto; margin-top:120px; padding-left:150px; background:url(../Images/p_error.jpg) no-repeat left top;}
	.p_error{ font-size:24px; font-weight:bold; color:#000; padding-top:25px;}
	.p_error_info{ font-size:18px; color:#333; margin-top:10px;}
	.p_error_info span{ font-weight:bold;}
</style>
</head>
<body>
	<div class="div_error">
               	    <p class="p_error">!!!诶呀，您当前操作出现异常~~~</p>
                    <p class="p_error_info"><span>错误原因：</span><%= exception.getMessage() %>~~~</p>
        </div>
</body>
</html>