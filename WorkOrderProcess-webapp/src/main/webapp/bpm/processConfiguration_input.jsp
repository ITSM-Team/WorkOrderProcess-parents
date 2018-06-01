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
$(function(){
	$("#agent").click(function(){
		$(".tip").fadeIn(200);
	});
	$(".tiptop a").click(function(){
	  $(".tip").fadeOut(200);
	});

  	$(".sure").click(function(){
	  $(".tip").fadeOut(100);
	});

  	$(".cancel").click(function(){
	  $(".tip").fadeOut(100);
	});
});
</script>
</head>
<body>
<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">流程首页</a></li>
			<li><a href="#">流程配置</a></li>
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
					<input name="" type="text" class="dfinput" style="width: 345px;" />
				</li>
				<li>
					<label>流程分类 :<b>*</b></label>
					<div class="vocation">
						<select class="select1">
							<option>全部</option>
							<option>通用流程</option>
							<option>OA流程</option>
						</select>
					</div>
				</li>
				<li>
					<label>绑定流程 :<b>*</b></label>
					<div class="vocation">
						<select class="select1">
							<option>全部</option>
							<option>请假流程1</option>
							<option>请假流程2</option>
							<option>申请流程1</option>
						</select>
					</div>
				</li>
				<li>
					<label>描述 :<b>*</b></label>
					<input name="" type="text" class="dfinput" style="width: 345px;" />
				</li>
			</ul>

			<div class="tipbtn">
				<input name="" type="button" class="sure" value="确定" />&nbsp; 
				<input name="" type="button" class="cancel" onclick="history.back();" value="取消" />
			</div>
		</div>
	</div>
</body>
</html>