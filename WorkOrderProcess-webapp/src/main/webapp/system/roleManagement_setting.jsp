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
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">系统设置</a></li>
			<li><a href="#">系统配置</a></li>
			<li><a href="#">用户管理</a></li>
			<li><a href="#">设置权限</a></li>
		</ul>
	</div>

	<div class="formbody">

		<div class="formtitle">
			<span>编辑</span>
		</div>

		<div class="toolsli">
			<ul class="forminfo" style="margin-left: 10px;">
				<li>
					<label>个人事务:</label>
					<div style="padding-top: 12px;">
						<input name="" type="checkbox" value=""/>&nbsp;流程首页&nbsp;
						<input name="" type="checkbox" value=""/>&nbsp;新建工单&nbsp;
						<input name="" type="checkbox" value=""/>&nbsp;草稿箱&nbsp;
						<input name="" type="checkbox" value=""/>&nbsp;待处理&nbsp;
						<input name="" type="checkbox" value=""/>&nbsp;待认领&nbsp;
						<input name="" type="checkbox" value=""/>&nbsp;待阅&nbsp;
						<input name="" type="checkbox" value=""/>&nbsp;我的工单&nbsp;
						<input name="" type="checkbox" value=""/>&nbsp;工单查询&nbsp;
						<input name="" type="checkbox" value=""/>&nbsp;工单委托&nbsp;
					</div>
				</li>
				<li>
					<label>流程管理:</label>
					<div style="padding-top: 12px;">
						<input name="" type="checkbox" value=""/>&nbsp;流程配置&nbsp;
						<input name="" type="checkbox" value=""/>&nbsp;流程设计&nbsp;
						<input name="" type="checkbox" value=""/>&nbsp;流程管理&nbsp;
						<input name="" type="checkbox" value=""/>&nbsp;工单管理&nbsp;
					</div>
				</li>
				<li>
					<label>系统管理:</label>
					<div style="padding-top: 12px;">
						<input name="" type="checkbox" value=""/>&nbsp;系统设置&nbsp;
						<input name="" type="checkbox" value=""/>&nbsp;系统配置&nbsp;
					</div>
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