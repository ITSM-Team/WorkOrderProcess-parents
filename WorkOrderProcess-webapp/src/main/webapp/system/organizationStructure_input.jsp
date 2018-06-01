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
			<li><a href="#">组织管理</a></li>
			<li><a href="#">组织结构</a></li>
			<li><a href="#">新增结构</a></li>
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
							<label>名称:<b>*</b></label>
							<input name="" type="text" class="dfinput" style="width: 345px;" />
						</li>
						<li>
							<label>类别 :<b>*</b></label>
							<div class="vocation">
								<select class="select1">
									<option>全部</option>
									<option value="" code="2">公司</option>
									<option value="" code="3">部门</option>
									<option value="" code="4">小组</option>
									<option value="" code="5">人员</option>
								</select>
							</div>
						</li>
						<li>
							<label>上一级组织 :<b>*</b></label>
							<div class="vocation">
								<select class="select1">
									<option>全部</option>
									<option value="" code="id">citsh公司</option>
									<option value="" code="id">行政部</option>
									<option value="" code="id">人事部</option>
									<option value="" code="id">IT小组</option>
									<option value="" code="id">OA小组</option>
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