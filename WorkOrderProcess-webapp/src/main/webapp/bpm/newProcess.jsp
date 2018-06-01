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
			<li><a href="#">流程首页</a></li>
			<li><a href="#">新建工单</a></li>
		</ul>
	</div>

	<div class="formbody">

		<div class="formtitle">
			<span>通用流程</span>
		</div>

		<div class="toolsli">
			<ul class="toollist">
				<li><a href="${ctx }/bpm/leaveProcess.jsp"><img src="${cdnPrefix}/template/images/i07.png" /></a>
				<h2>请假流程</h2></li>
			</ul>
		</div>

		<div class="formtitle">
			<span>OA流程</span>
		</div>

		<div class="toolsli">
			<ul class="toollist">
				<li><a href="#"><img src="${cdnPrefix}/template/images/i06.png" /></a>
				<h2>申请流程</h2></li>
			</ul>
		</div>



	</div>
</body>
</html>