<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
 <%@include file="/common/meta.jsp"%>
<title>欢迎登录后工单管理系统</title>
<%@include file="/common/s3.jsp"%>
<script language="javascript">
	$(function(){
    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
	$(window).resize(function(){  
    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
    })  
});  
</script>

</head>
<body style="background-color: #1c77ac; background-image: url(${cdnPrefix}/template/images/light.png); background-repeat: no-repeat; background-position: center top; overflow: hidden;">


	<div id="mainBody">
		<div id="cloud1" class="cloud"></div>
		<div id="cloud2" class="cloud"></div>
	</div>


	<div class="logintop">
		<span>欢迎登录工单管理界面平台</span>
		<ul>
			<li><a href="#"><img src="${cdnPrefix}/template/images/help.png" />&nbsp;帮助</a></li>
			<li><a href="#">关于</a></li>
		</ul>
	</div>

	<div class="loginbody">

		<span class="systemlogo"></span>

		<div class="loginbox">

			<ul>
				<li><input name="" type="text" class="loginuser" value="admin" onclick="JavaScript:this.value=''" /></li>
				<li><input name="" type="text" class="loginpwd" value="密码" onclick="JavaScript:this.value=''" /></li>
				<li><input name="" type="button" class="loginbtn" value="登录" onclick="javascript:window.location='main.jsp'" /><label><input name="" type="checkbox" value="" checked="checked" />记住密码</label><label><a href="#">忘记密码？</a></label></li>
			</ul>


		</div>

	</div>



	<div class="loginbm">版权所有 2018 citsh.com</div>
</body>
</html>