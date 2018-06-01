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
			<li><a href="#">菜单配置</a></li>
			<li><a href="#">新增菜单</a></li>
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
					<label>编码 :<b>*</b></label>
					<input name=""  type="text" class="dfinput" style="width: 345px;" />
				</li>
				<li>
					<label>URL :<b>*</b></label>
					<input name=""  type="text" class="dfinput" style="width: 345px;" />
				</li>
				<li>
					<label>类型:<b>*</b></label>
					<input name=""  type="text" class="dfinput" style="width: 345px;" />
				</li>
				<li>
					<label>状态 :<b>*</b></label>
					<div class="vocation">
						<select class="select1">
							<option>全部</option>
							<option value="true">显示</option>
							<option value="false">隐藏</option>
						</select>
					</div>
				</li>
				<li>
					<label>上级菜单 :</label>
					<div class="vocation">
						<select class="select1">
							<option>全部</option>
							<option>首页</option>
							<option>流程</option>
							<option>设置</option>
						</select>
					</div>
				</li>
				<li>
					<label>权限 :</label>
					<div class="vocation">
						<select class="select1">
							<option>全部</option>
							<option>所有权限</option>
							<option>用户</option>
							<option>管理员</option>
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