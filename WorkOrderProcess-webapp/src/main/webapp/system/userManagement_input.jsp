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
			<li><a href="#">用户管理</a></li>
			<li><a href="#">新增用户</a></li>
		</ul>
	</div>

	<div class="formbody">

		<div class="formtitle">
			<span>编辑</span>
		</div>

		<div class="toolsli">
			<ul class="forminfo">
				<li>
					<label>账号:<b>*</b></label>
					<input name=""  type="text" class="dfinput" style="width: 345px;" />
				</li>
				<li>
					<label>密码:<b>*</b></label>
					<input name=""  type="text" class="dfinput" style="width: 345px;" />
				</li>
				<li>
					<label>邮箱:<b>*</b></label>
					<input name=""  type="text" class="dfinput" style="width: 345px;" />
				</li>
				<li>
					<label>电话:<b>*</b></label>
					<input name=""  type="text" class="dfinput" style="width: 345px;" />
				</li>
				<li>
					<label>状态 :<b>*</b></label>
					<div class="vocation">
						<select class="select1">
							<option value="false">启用</option>
							<option value="false">停用</option>
						</select>
					</div>
				</li>
				<li></li>
			</ul>

			<div class="tipbtn">
				<input name="" type="button" class="sure" value="保存" />&nbsp; 
				<input name="" type="button" class="cancel" onclick="history.back();" value="取消" />
			</div>
		</div>
	</div>
</body>
</html>