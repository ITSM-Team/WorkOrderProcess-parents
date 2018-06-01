<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title></title>
<%@include file="/common/s3.jsp"%>
<script type="text/javascript">
$(document).ready(function(e) {
    $(".select1").uedSelect({
		width : 345			  
	});
});
</script>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">系统设置</a></li>
			<li><a href="#">系统配置</a></li>
			<li><a href="#">模版管理</a></li>
			<li><a href="#">邮件模版</a></li>
			<li><a href="#">新增模版</a></li>
		</ul>
	</div>

	<div class="formbody">
		<div id="usual1" class="usual">
			<div class="formbody">
				<div class="formtitle">
					<span>编辑</span>
				</div>
				<div class="toolsli">
					<ul class="forminfo">
						<li>
							<label>名称:</label>
							<input name="" type="text" class="dfinput" style="width: 345px;" />
						</li>
						<li>
							<label>主题:</label>
							<input name="" type="text" class="dfinput" style="width: 345px;" />
						</li>
						<li>
							<label>SMTP服务器 :</label>
							<div class="vocation">
								<select class="select1">
									<option value="">mail.citsh.com</option>
									<option value="">smtp.163.com</option>
								</select>
							</div>
						</li>
						<li>
							<label>内容:</label>
							<textarea name="" cols="" rows="" class="textinput"></textarea>
						</li>
					</ul>
					
					<div class="tipbtn" style="margin-left: 110px;">
						<input name="" type="button" class="sure" value="保存" />&nbsp; 
						<input name="" type="button" class="cancel" onclick="history.back();" value="返回" />
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>