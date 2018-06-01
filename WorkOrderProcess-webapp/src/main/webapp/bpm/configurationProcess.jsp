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
</style>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">流程首页</a></li>
			<li><a href="#">流程配置</a></li>
			<li><a href="#">配置</a></li>
		</ul>
	</div>

	<div class="formbody">
		<div class="toolsli">
			<input name="" type="button" onclick="history.back();" class="sure" value="返回" />
		</div>
		<div class="formtitle">
			<span>配置流程</span>
		</div>
		<div class="toolsli">
			<table class="tablelist">
				<thead>
					<tr>
						<th>编号</th>
						<th>类型</th>
						<th>节点</th>
						<th>人员</th>
						<th>事件</th>
						<th>规则</th>
						<th>表单</th>
						<th>操作</th>
						<th>提醒</th>
					</tr>
				</thead>
				<tr>
					<td>1</td>
					<td>userTask</td>
					<td>总经理审批</td>
					<td><a href="${ctx }/bpm/configurationProcess_staff.jsp" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a> <i class="badge">0</i></td>
					<td><a href="${ctx }/bpm/configurationProcess_event.jsp" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a></td>
					<td><a href="${ctx }/bpm/configurationProcess_rule.jsp" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a></td>
					<td><a href="${ctx }/bpm/configurationProcess_forms.jsp" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a> <i class="badge">1</i></td>
					<td><a href="${ctx }/bpm/configurationProcess_operate.jsp" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a> <i class="badge">1</i></td>
					<td><a href="${ctx }/bpm/configurationProcess_remind.jsp" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a></td>
				</tr>

				<tr>
					<td>2</td>
					<td>process</td>
					<td>审批权限</td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a> <i class="badge">0</i></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a> <i class="badge">1</i></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a> <i class="badge">1</i></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a></td>
				</tr>
				
				<tr>
					<td>3</td>
					<td>startEvent</td>
					<td></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a> <i class="badge">0</i></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a> <i class="badge">1</i></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a> <i class="badge">1</i></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a></td>
				</tr>
				
				<tr>
					<td>4</td>
					<td>userTask</td>
					<td>发起申请</td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a> <i class="badge">0</i></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a> <i class="badge">1</i></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a> <i class="badge">1</i></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a></td>
				</tr>
				
				<tr>
					<td>5</td>
					<td>userTask</td>
					<td>部门经理审批</td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a> <i class="badge">0</i></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a> <i class="badge">1</i></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a> <i class="badge">1</i></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a></td>
				</tr>
				
				<tr>
					<td>6</td>
					<td>endEvent</td>
					<td></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a> <i class="badge">0</i></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a> <i class="badge">1</i></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a> <i class="badge">1</i></td>
					<td><a href="#" class="ope"><img class="update" src="${cdnPrefix}/template/images/update.png" /></a></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>