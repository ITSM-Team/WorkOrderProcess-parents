<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title></title>
<%@include file="/common/s3.jsp"%>
<link rel="stylesheet" href="${cdnPrefix}/date_plugin/jedate.css" />
<script type="text/javascript" src="${cdnPrefix}/date_plugin/jedate.js"></script>
<style type="text/css">
.ope { color: #056C9E; }
.ope:hover { text-decoration: underline; }
</style>
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
			<li><a href="#">工作时间管理</a></li>
			<li><a href="#">特殊时间</a></li>
			<li><a href="#">设置</a></li>
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
							<label style="width: 145px;">状态 :</label>
							<div class="vocation">
								<select class="select1">
									<option value="">上班</option>
									<option value="">休假</option>
								</select>
							</div>
						</li>
						<li>
							<label style="width: 145px;">日期 ：</label> 
							<input name="" type="text" readonly="readonly" class="scinput" id="dateTime" style="width: 345px;"/>
						</li>
						<li>
							<label style="width: 145px;">开始/结束时间（AM）：</label> 
							<input name="" type="text" readonly="readonly" class="scinput" id="amStartTime" style="width: 170px;"/>
							
							<input name="" type="text" readonly="readonly" class="scinput" id="amEndTime" style="width: 170px;"/>
						</li>
						<li>
							<label style="width: 145px;">开始/结束时间（PM）：</label> 
							<input name="" type="text" readonly="readonly" class="scinput" id="pmStartTime" style="width: 170px;"/>
							<input name="" type="text" readonly="readonly" class="scinput" id="pmEndTime" style="width: 170px;"/>
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

	<script type="text/javascript">
		$(function(){
			jeDate("#dateTime",{
			 	 theme:{bgcolor:"#00A1CB",pnColor:"#00CCFF"},
	        	format: "YYYY-MM-DD"
			 });
			 jeDate("#amStartTime",{
			 	 theme:{bgcolor:"#00A1CB",pnColor:"#00CCFF"},
	        	format: "hh:mm"
			 });
			 jeDate("#amEndTime",{
			 	 theme:{bgcolor:"#00A1CB",pnColor:"#00CCFF"},
	        	format: "hh:mm"
			 });
			 jeDate("#pmStartTime",{
			 	 theme:{bgcolor:"#00A1CB",pnColor:"#00CCFF"},
	        	format: "hh:mm"
			 });
			 jeDate("#pmEndTime",{
			 	 theme:{bgcolor:"#00A1CB",pnColor:"#00CCFF"},
	        	format: "hh:mm"
			 });
		})
	</script>
</body>
</html>