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
			<li><a href="#">系统设置</a></li>
			<li><a href="#">系统配置</a></li>
			<li><a href="#">流程分类配置</a></li>
			<li><a href="#">分类配置</a></li>
		</ul>
	</div>

	<div class="formbody">

		<div class="formtitle">
			<span>编辑</span>
		</div>

		<div class="toolsli">
			<ul class="forminfo">
				<li>
					<input name="" type="checkbox" value="" checked="checked" />&nbsp;超级管理员
				</li>
				
				<li>
					<input name="" type="checkbox" value="" checked="checked" />&nbsp;系统管理员
				</li>
				
				<li>
					<input name="" type="checkbox" value="" checked="checked" />&nbsp;普通用户
				</li>
			</ul>

			<div class="tipbtn">
				<input name="" type="button" class="sure" value="保存" />&nbsp; 
				<input name="" type="button" class="cancel" onclick="history.back();" value="取消" />
			</div>
		</div>
	</div>
</body>
</html>