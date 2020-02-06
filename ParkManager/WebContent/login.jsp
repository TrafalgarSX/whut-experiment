<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="shortcut icon" href="favicon.ico"/>
<link rel="bookmark" href="favicon.ico"/>
<link href="h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="h-ui/css/H-ui.login.css" rel="stylesheet" type="text/css" />
<link href="h-ui/lib/icheck/icheck.css" rel="stylesheet" type="text/css" />
<link href="h-ui/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">

<script type="text/javascript" src="easyui/jquery.min.js"></script> 
<script type="text/javascript" src="h-ui/js/H-ui.js"></script> 
<script type="text/javascript" src="h-ui/lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="easyui/js/validateExtends.js"></script>
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>

<script type="text/javascript">
	$(function(){
	
		var data = $("#form").serialize();
		//登录
		$("#submitBtn").click(function(){
			
			
			var data = $("#form").serialize();
			$.ajax({
				type: "post",
				url: "LoginServlet?method=Login",
				data: data, 
				dataType: "text", //返回数据类型
				success: function(msg){
				if("loginError" == msg){
						$.messager.alert("消息提醒", "用户名或密码错误!", "warning");
					} else if("loginSuccess" == msg){
						//var url=window.location.href;
					//	alert(msg);
					//window.location.href ="RoleServlet?method=fuckme";
					window.location.href="index.jsp";
					} else{
						alert(msg);
					} 
				}
			});
		
		});
		
	
		$.extend($.fn.validatebox.defaults.rules, {
// 		name : {// 验证姓名，可以是中文或英文  在这里面不知道为什么不能用
// 			validator : function(value) {
// 				var reg=/^[A-Za-z\u0391-\uFFE5]{2,15}$/;
//   	            return reg.test(value);
// 			},
// 			message :'请输入姓名,只包含中文和英文,最多15位'
// 		},
// 		//密码规则
// 	    password: {
// 	        validator: function (value) {
// 	        	var reg = /^[a-zA-Z0-9]{6,10}$/;
// 	        	return reg.test(value);
	            
// 	        }, message: '密码6-10位，且只能为英文、数字'
//	    },
		})
	})
	
</script> 
<title>登录|停车场管理系统</title>
<meta name="keywords" content="学生信息管理系统">
</head>
<body>

<div class="header" style="padding: 0;">
	<h2 style="color: white; width: 400px; height: 60px; line-height: 60px; margin: 0 0 0 30px; padding: 0;">停车场管理系统</h2>
</div>

<div class="loginWraper">
	<div class="login-logo" style="position:absolute;top: 115px;left: 590px;">
			<img src="h-ui/images/logo-w.png">
		</div>
  <div id="loginform" class="loginBox" style="background-color:#FFFFFF;opacity:0.7">
    <form id="form" class="form form-horizontal" method="post">
      <div class="row cl">
        <label class="form-label col-3"><i class="Hui-iconfont">&#xe60d;</i></label>
        <div class="formControls col-8">
          <input id="" name="account" style="width: 360px; height: 40px;"  placeholder="账户" type="text" class="easyui-textbox" validType="name" data-options="required:true, missingMessage:'请填写2-15位仅包含中文和英文的账号'"  >
        </div>
      </div>
      <div class="row cl">
        <label class="form-label col-3"><i class="Hui-iconfont">&#xe60e;</i></label>
        <div class="formControls col-8">
          <input id="" style="width: 360px; height: 40px;"  name="password" placeholder="密码" type="password" class="easyui-textbox" validType="password" data-options="required:true, missingMessage:'请填写6-10位只包含英文数字密码'" />
        </div>
      </div>
     
     
      
      <div class="row">
        <div class="formControls col-8 col-offset-3">
          <input id="submitBtn" type="button" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
        </div>
      </div>
    </form>
  </div>
</div>
<div class="footer">Copyright &nbsp;  郭亚文@  </div>


</body>
</html>