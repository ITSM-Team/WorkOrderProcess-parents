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
			<li><a href="#">模版管理</a></li>
			<li><a href="#">邮件模版</a></li>
			<li><a href="#">邮件配置</a></li>
			<li><a href="#">新增配置</a></li>
		</ul>
	</div>

	<div class="formbody">

		<div class="formtitle">
			<span>编辑</span>
		</div>

		<div class="toolsli">
			<ul class="forminfo">
				<li>
					<label>名称 :<b>*</b></label>
					<input name=""  type="text" class="dfinput" style="width: 345px;" />
				</li>
				
				<li>
					<label>服务器 :<b>*</b></label>
					<input name=""  type="text" class="dfinput" style="width: 345px;" />
				</li>
			
				<li>
					<label>账号 :<b>*</b></label>
					<input name=""  type="text" class="dfinput" style="width: 345px;" />
				</li>
				
				<li>
					<label>密码 :<b>*</b></label><cite>
					<input name=""  type="text" class="dfinput" style="width: 345px;" />
				</li>
				
				<li>
					<label>appId :<b>*</b></label><cite>
					<input name=""  type="text" class="dfinput" style="width: 345px;" />
				</li>
				
				<li>
					<label>mobile :<b>*</b></label><cite>
					<input name=""  type="text" class="dfinput" style="width: 345px;" />
				</li>
				
				<li>
					<label>message :<b>*</b></label>
					<input name=""  type="text" class="dfinput" style="width: 345px;" />
				</li>
			</ul>

			<div class="tipbtn" style="margin-left: 110px;">
				<input name="" type="button" class="sure" value="保存" />&nbsp; 
				<input name="" type="button" class="cancel" onclick="history.back();" value="取消" />
			</div>
		</div>
	</div>
</body>
</html>