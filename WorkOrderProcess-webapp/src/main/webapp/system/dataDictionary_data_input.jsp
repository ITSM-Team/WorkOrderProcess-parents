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
		width : 200
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
			<li><a href="#">数据字典</a></li>
			<li><a href="#">数据</a></li>
			<li><a href="#">新增数据</a></li>
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
							<label>类型:</label>
							<label>省直辖市</label>
						</li>
						<li>
							<label>名称:</label>
							<input name="" type="text" class="dfinput" style="width: 200px;" />
						</li>
						<li>
							<label>数据:</label>
							<input name="" type="text" class="dfinput" style="width: 200px;" />
						</li>
					</ul>
					
					<div class="tipbtn">
						<input name="" type="button" class="sure" value="保存" />&nbsp; 
						<input name="" type="button" class="cancel" onclick="history.back();" value="返回" />
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>