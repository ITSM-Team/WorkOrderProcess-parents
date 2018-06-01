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
					<label>SMTP服务器:<b>*</b></label>
					<input name=""  type="text" class="dfinput" style="width: 345px;" />
				</li>
			
				<li>
					<label>SMTP端口 :<b>*</b></label>
					<input name=""  type="text" class="dfinput" style="width: 345px;" />
				</li>
				
				<li>
					<label>需要认证 :<b>*</b></label><cite>
					<input name="type" type="radio" value="" checked="checked" />是&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="type" type="radio" value="" />否</cite>
				</li>
				
				<li>
					<label>使用TLS :<b>*</b></label><cite>
					<input name="type" type="radio" value="" checked="checked" />是&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="type" type="radio" value="" />否</cite>
				</li>
				
				<li>
					<label>使用SSL :<b>*</b></label><cite>
					<input name="type" type="radio" value="" checked="checked" />是&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="type" type="radio" value="" />否</cite>
				</li>
				
				<li>
					<label>账号 :<b>*</b></label>
					<input name=""  type="text" class="dfinput" style="width: 345px;" />
				</li>
				<li>
					<label>密码 :<b>*</b></label>
					<input name=""  type="text" class="dfinput" style="width: 345px;" />
				</li>
				<li>
					<label>默认发件人 :<b>*</b></label>
					<input name=""  type="text" class="dfinput" style="width: 345px;" />
				</li>
				<li>
					<label>状态 :<b>*</b></label><cite>
					<input name="type" type="radio" value="" checked="checked" />正常&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="type" type="radio" value="" />测试</cite>
				</li>
				<li>
					<label>测试邮件 :<b>*</b></label>
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