<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title></title>
<%@include file="/common/s3.jsp"%>
<style type="text/css">
	.tipbtn{
		margin-top:25px; 
		margin-left:35%;
	}
</style>
<script type="text/javascript">
	$(function(){
		$(".btnSubmit").click(function(){
			window.location.href = "release.jsp";
		})
	})
</script>
</head>
<body>
<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">流程首页</a></li>
			<li><a href="#">草稿箱</a></li>
			<li><a href="#">修改</a></li>
		</ul>
	</div>

	<div class="formbody">
	
		<div class="formtitle">
			<span>编辑</span>
		</div>

		<div class="toolsli">
			表格
			
			<div class="tipbtn" >
				<input name="" type="button" class="sure btnSubmit" value="提交" />&nbsp; 
				<input name="" type="button" class="sure" value="保存到草稿" />
			</div>
		</div>



	</div>

</body>
</html>