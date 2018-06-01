<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title></title>
<%@include file="/common/s3.jsp"%>
<style type="text/css">
.ope {
	color: #056C9E;
}
.ope:hover{
	text-decoration: underline;
}
</style>
</head>
<body>
<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">流程首页</a></li>
			<li><a href="#">流程配置</a></li>
			<li><a href="#">配置</a></li>
			<li><a href="#">提醒</a></li>
		</ul>
	</div>

	<div class="formbody">
		<div id="usual1" class="usual">
			<div class="formbody">
				<div class="formtitle">
					<span>返回</span>
				</div>
				<div class="toolsli">
					<input name="" type="button" class="sure" onclick="history.back();" value="返回" />
				</div>
			</div>
		
			<div class="formbody">
				<div class="formtitle">
					<span>添加</span>
				</div>
				<div class="toolsli">
					<div class="tools">
						<ul class="toolbar">
							<li class="click">
								<a href="${ctx }/bpm/configurationProcess_remind_input.jsp"><span><img src="${cdnPrefix}/template/images/t01.png" /></span>新增提醒</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
			
			<div class="formbody">
				<div class="formtitle">
					<span>列表</span>
				</div>
				<div class="toolsli">
					<table class="tablelist">
						<thead>
							<tr>
								<th>类型</th>
								<th>提醒人</th>
								<th>提醒时间</th>
								<th>提醒模版</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>到达</td>
								<td>金</td>
								<td>2018-2-16</td>
								<td>到达提醒</td>
								<td><a href="#" class="ope">删除</a></td>
							</tr>
							<tr>
								<td>到达</td>
								<td>李四</td>
								<td>2018-2-15</td>
								<td>超时提醒</td>
								<td><a href="#" class="ope">删除</a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>


</body>
</html>