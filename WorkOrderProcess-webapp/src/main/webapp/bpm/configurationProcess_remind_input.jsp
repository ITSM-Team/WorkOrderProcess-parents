<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title></title>
<%@include file="/common/s3.jsp"%>
<link rel="stylesheet" href="${cdnPrefix}/date_plugin/jedate.css" />
<script type="text/javascript" src="${cdnPrefix}/date_plugin/jedate.js" ></script>
<link rel="stylesheet" href="${cdnPrefix}/simpleTree/css/SimpleTree.css"/>
<script type="text/javascript" src="${cdnPrefix}/simpleTree/js/SimpleTree.js" ></script>
<script type="text/javascript">
$(document).ready(function(e) {
	$(".select1").uedSelect({
		width : 345
	});
});
$(function(){
	$(".st_tree").SimpleTree({});
	$(".st_tree ul li a").click(function(){
		$("#sponsorName").val($(this).attr("title"));
	});
	
	$("#remind").click(function(){
		$(".tip").fadeIn(200);
	});
	$(".tiptop a").click(function(){
	  $(".tip").fadeOut(200);
	});

  	$(".sure").click(function(){
	  $(".tip").fadeOut(100);
	  $("#remind").val($("#sponsorName").val());
	});

  	/* 清除代理人 */
  	$(".position").click(function(){
  		$("#remind").val("");
  	});
});
</script>
<style type="text/css">
	#jedate{
		margin-top: 100px;
	}
</style>
</head>
<body>
<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">流程首页</a></li>
			<li><a href="#">流程配置</a></li>
			<li><a href="#">配置</a></li>
			<li><a href="#">提醒</a></li>
			<li><a href="#">新增提醒</a></li>
		</ul>
	</div>

	<div class="formbody">

		<div class="formtitle">
			<span>编辑</span>
		</div>

		<div class="toolsli">
			<ul class="forminfo">
				<li>
					<label>类型 :<b>*</b></label>
					<div class="vocation">
						<select class="select1">
							<option>全部</option>
							<option>到达</option>
							<option>完成</option>
							<option>超时</option>
						</select>
					</div>
				</li>
				<li>
					<label>模版 :<b>*</b></label>
					<div class="vocation">
						<select class="select1">
							<option>任务到达模版</option>
							<option>任务完成模版</option>
							<option>任务超时模版</option>
						</select>
					</div>
				</li>
				<li>
					<label>提醒人 :<b>*</b></label>
					<input name="" id="remind" type="text" class="dfinput" readonly="readonly" style="width: 345px;" />
					<img class="position" src="${cdnPrefix}/template/images/empty.png"/>
				</li>
				<li>
					<label>提醒时间 :<b>*</b></label>
					<input name="" id="proxyTime" type="text" class="dfinput" readonly="readonly" style="width: 345px;" />
				</li>
				<li>
					<label>提醒方式 :<b>*</b></label>
					<div style="padding-top: 10px;">
						<input name="" type="checkbox" value="" checked="checked" />&nbsp;短信&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input name="" type="checkbox" value=""/>&nbsp;邮件
					</div>
				</li>
			</ul>

			<div class="tipbtn">
				<input name="" type="button" class="sure" value="保存" />&nbsp; 
				<input name="" type="button" class="cancel" onclick="history.back();" value="返回" />
			</div>
		</div>
	</div>
	
	<div class="tip">
    	<div class="tiptop"><span>查询提醒人信息</span><a></a></div>
		<div class="tipbtn">
		 	<label>名称：</label><input name="" id="sponsorName" type="text" class="scinput" readonly="readonly"/>&nbsp;&nbsp;
	        <input name=""  type="button" class="sure" value="确定" />
        </div>
   		<div class="st_tree" style="width:350px;margin:0 auto;">
			<ul>
				<li><a href="#" ref="xtgl" title="公司">公司</a></li>
				<li><a href="#" ref="xtgl">行政部</a></li>
				<ul show="true">
					<li><a href="#" ref="yhgl" title="张三">张三</a></li>
					<li><a href="#" ref="rzck" title="李四">李四</a></li>
				</ul>
				<li><a href="#" ref="ckgl">人事</a></li>
				<ul>
					<li><a href="#" ref="kcgl" title="金">金</a></li>
					<li><a href="#" ref="shgl" title="亚当">亚当</a></li>
					<li><a href="#" ref="fhgl" title="麦克">麦克</a></li>
				</ul>
			</ul>
		</div>    
    </div>
	
	<script type="text/javascript">
		$(function(){
			 jeDate("#proxyTime",{
			 	 theme:{bgcolor:"#00A1CB",pnColor:"#00CCFF"},
	        	format: "YYYY-MM-DD"
			 });
		})
	</script>
</body>
</html>