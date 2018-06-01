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
			<li><a href="#">流程设计</a></li>
			<li><a href="#">流程部署</a></li>
			<li><a href="#">查看资源</a></li>
		</ul>
	</div>

	<div class="formbody">
		<div class="toolsli">
			<input name="" type="button" onclick="javascript:window.history.back(-1);" class="sure" value="返回" />
		</div>

		<div class="formtitle">
			<span>列表</span>
		</div>

		<div class="toolsli">
			<table class="tablelist">
				<thead>
					<tr>
						<th>资源名称</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>测试流程.bpmn20.xml</td>
					</tr>
					<tr>
						<td>测试流程.process.png</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>