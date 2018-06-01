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
			<li><a href="#">工单超时提醒</a></li>
			<li><a href="#">新增提醒</a></li>
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
							<label>规则名称:<b>*</b></label>
							<input name="" type="text" class="dfinput" style="width: 345px;" />
						</li>
						<li>
							<label>流程名称 :<b>*</b></label>
							<div class="vocation">
								<select class="select1">
									<option>全部</option>
									<option value="">请假流程</option>
									<option value="">申请流程</option>
								</select>
							</div>
						</li>
						
						<li>
							<label>时间类别 :<b>*</b></label>
							<div class="vocation">
								<select class="select1">
									<option>全部</option>
									<option value="">正常时间</option>
									<option value="">五一时间</option>
									<option value="">端午时间</option>
									<option value="">中秋时间</option>
								</select>
							</div>
						</li>
						<li></li>
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