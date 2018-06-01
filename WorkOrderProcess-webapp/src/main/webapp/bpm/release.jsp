<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title></title>
<%@include file="/common/s3.jsp"%>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">发布流程</a></li>
		</ul>
	</div>

	<div class="formbody">

		<div class="formtitle">
			<span>发布流程</span>
		</div>

		<div class="toolsli">
			<ul class="toollist">
				<li><a href="#"><img src="${cdnPrefix}/template/images/release.png" /></a>
				<h2>发布</h2></li>
				<li><a href="#" onclick="history.back();"><img src="${cdnPrefix}/template/images/return.png" /></a>
				<h2>返回</h2></li>
			</ul>
		</div>

	</div>
</body>
</html>