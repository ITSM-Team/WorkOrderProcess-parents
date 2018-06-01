<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title>工单管理系统</title>
<%@include file="/common/s3.jsp"%>
<script type="text/javascript">
$(function(){	
	//顶部导航切换
	$(".nav li a").click(function(){
		$(".nav li a.selected").removeClass("selected");
		$(this).addClass("selected");
	})	
})	
</script>
</head>
<body style="background:url(${cdnPrefix}/template/images/topbg.gif) repeat-x;">
	<div class="topleft">
		<a href="main.jsp" target="_parent"><img src="${cdnPrefix}/template/images/logo.png" title="系统首页" /></a>
	</div>

	<ul class="nav">
		<li>
			<a href="main.jsp" target="_parent"  class="selected">
			<img src="${cdnPrefix}/template/images/icon01.png" title="流程首页" />
			<h2>流程首页</h2></a>
		</li>
		<li>
			<a href="systemSettings.jsp" target="_parent" class="">
			<img src="${cdnPrefix}/template/images/icon06.png" title="系统设置" />
			<h2>系统设置</h2></a>
		</li>
		<%-- <li>
			<a href="${ctx}/bpm/newProcess.jsp" target="rightFrame">
			<img src="${cdnPrefix}/template/images/icon03.png" title="新建工单"/>
			<h2>新建工单</h2></a>
		</li>
		<li>
			<a href="${ctx }/bpm/draftBox.jsp" target="rightFrame">
			<img src="${cdnPrefix}/template/images/icon05.png" title="草稿箱"/>
			<h2>草稿箱</h2></a>
		</li> --%>
	</ul>
	
	
	<div class="topright">
		<ul>
			<li><span><img src="${cdnPrefix}/template/images/help.png" title="帮助" class="helpimg" /></span><a href="#">帮助</a></li>
			<li><a href="#">关于</a></li>
			<li><a href="login.jsp" target="_parent">退出</a></li>
		</ul>

		<div class="user">
			<span>admin</span> <i>消息</i> <b>5</b>
		</div>

	</div>
</body>
</html>