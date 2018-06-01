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
			<li><a href="#">流程管理</a></li>
			<li><a href="#">表单业务验证配置</a></li>
			<li><a href="#">新增验证</a></li>
		</ul>
	</div>

	<div class="formbody">

		<div class="formtitle">
			<span>编辑</span>
		</div>

		<div class="toolsli">
			<ul class="forminfo">
				<li>
					<label>流程名称 :<b>*</b></label>
					<div class="vocation">
						<select class="select1">
							<option>全部</option>
							<option value="true">请假流程</option>
							<option value="false">申请流程</option>
						</select>
					</div>
				</li>
				
				<li>
					<label>任务名称 :</label>
					<div class="vocation">
						<select class="select1">
							<option>全部</option>
							<option>计算年假剩余天数</option>
							<option>处理工单并记录结果</option>
						</select>
					</div>
				</li>
				
				<li>
					<label>扩展类:<b>*</b></label>
					<input name=""  type="text" class="dfinput" style="width: 345px;" />
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