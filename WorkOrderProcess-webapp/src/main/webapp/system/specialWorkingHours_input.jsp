<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title></title>
<%@include file="/common/s3.jsp"%>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">系统设置</a></li>
			<li><a href="#">系统配置</a></li>
			<li><a href="#">工作时间管理</a></li>
			<li><a href="#">特殊时间</a></li>
			<li><a href="#">新增</a></li>
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
							<label>名称：</label> 
							<input name="" type="text" class="scinput" style="width: 345px;"/>
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