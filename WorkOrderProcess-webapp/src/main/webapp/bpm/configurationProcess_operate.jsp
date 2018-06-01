<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title></title>
<%@include file="/common/s3.jsp"%>
<style type="text/css">
.ope {
	color: #056C9E;
}
.ope:hover{
	text-decoration: underline;
}
</style>
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
			<li><a href="#">流程首页</a></li>
			<li><a href="#">流程配置</a></li>
			<li><a href="#">配置</a></li>
			<li><a href="#">操作</a></li>
		</ul>
	</div>

	<div class="formbody">
		<div id="usual1" class="usual">
			<div class="formbody">
				<div class="formtitle">
					<span>操作配置</span>
				</div>
				<div class="toolsli">
					<ul class="forminfo">
						<li>
							<span style="color: gray;">顶部左边按钮</span>
							<div style="padding-top: 10px;">
								&nbsp;&nbsp;&nbsp;&nbsp;
								<input name="" type="checkbox" value=""/>&nbsp;改派&nbsp;&nbsp;
								<input name="" type="checkbox" value=""/>&nbsp;回退&nbsp;&nbsp;								
								<input name="" type="checkbox" value=""/>&nbsp;创建知识&nbsp;&nbsp;
								<input name="" type="checkbox" value=""/>&nbsp;追回&nbsp;&nbsp;
								<input name="" type="checkbox" value=""/>&nbsp;添加关联关系&nbsp;&nbsp;
								<input name="" type="checkbox" value=""/>&nbsp;强制关单&nbsp;&nbsp;
							</div>
						</li>
						<li>
							<div style="padding-top: 10px;">
								&nbsp;&nbsp;&nbsp;&nbsp;
								<input name="" type="checkbox" value=""/>&nbsp;催办&nbsp;&nbsp;
								<input name="" type="checkbox" value=""/>&nbsp;抄送&nbsp;&nbsp;
								<input name="" type="checkbox" value=""/>&nbsp;取消关注&nbsp;&nbsp;
								<input name="" type="checkbox" value=""/>&nbsp;关注人&nbsp;&nbsp;
								<input name="" type="checkbox" value=""/>&nbsp;快照&nbsp;&nbsp;
								<input name="" type="checkbox" value=""/>&nbsp;撤销&nbsp;&nbsp;
							</div>
						</li>
						<li>
							<span style="color: gray;">顶部右边按钮 ：</span>
							<div style="padding-top: 10px;">
								&nbsp;&nbsp;&nbsp;&nbsp;
								<input name="" type="checkbox" value=""/>&nbsp;打印&nbsp;&nbsp;
								<input name="" type="checkbox" value=""/>&nbsp;导出&nbsp;&nbsp;								
								<input name="" type="checkbox" value=""/>&nbsp;流程图&nbsp;&nbsp;
							</div>
						</li>
						<li>
							<span style="color: gray;">底部按钮 ：</span>
							<div style="padding-top: 10px;">
								&nbsp;&nbsp;&nbsp;&nbsp;
								<input name="" type="checkbox" value=""/>&nbsp;提交&nbsp;&nbsp;
								<input name="" type="checkbox" value=""/>&nbsp;暂存&nbsp;&nbsp;								
							</div>
						</li>
					</ul>
					&nbsp;&nbsp;
					<input name="" type="button" class="sure" value="提交" />&nbsp;
					<input name="" type="button" class="cancel" onclick="history.back();" value="返回" />
				</div>
			</div>
			
		</div>
	</div>
</body>
</html>