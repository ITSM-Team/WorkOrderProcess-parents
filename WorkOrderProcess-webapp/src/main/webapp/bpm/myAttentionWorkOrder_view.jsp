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
			<li><a href="#">我的工单</a></li>
			<li><a href="#">我关注的工单</a></li>
			<li><a href="#">查看</a></li>
		</ul>
	</div>

	<div class="formbody">
		<div class="toolsli">
			<input name="" type="button" onclick="javascript:window.history.back(-1);" class="sure" value="返回" />
		</div>
		
		<div class="formtitle">
			<span>流程图</span>
		</div>
		<div class="toolsli">流程图</div>

		<div class="formtitle">
			<span>列表</span>
		</div>

		<div class="toolsli">
			<table class="tablelist">
				<thead>
					<tr>
						<th>名称</th>
						<th>开始时间</th>
						<th>结束时间</th>
						<th>负责人</th>
						<th>状态</th>
						<th>意见</th>
					</tr>
				</thead>
				<tr>
					<td>填写请假单</td>
					<td>2018-04-26 09:55:09</td>
					<td>2018-04-26 09:55:09</td>
					<td>张三</td>
					<td>提交</td>
					<td></td>
				</tr>

				<tr>
					<td>部门领导审批</td>
					<td>2018-04-26 09:55:09</td>
					<td>2018-04-26 10:07:39</td>
					<td>金</td>
					<td>完成</td>
					<td></td>
				</tr>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>