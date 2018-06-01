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
<link rel="stylesheet" href="${cdnPrefix}/simpleTree/css/SimpleTree.css" />
<script type="text/javascript" src="${cdnPrefix}/simpleTree/js/SimpleTree.js"></script>
<script type="text/javascript">
$(document).ready(function(e) {
	$(".select3").uedSelect({
		width : 100
	});
});	

$(function(){
	$(".st_tree").SimpleTree({});
	$(".st_tree ul li a").click(function(){
		$("#sponsorName").val($(this).attr("title"));
	}); 
	
	$("#sponsor").click(function(){
		$(".tip").fadeIn(200);
	});
	$(".tiptop a").click(function(){
	  $(".tip").fadeOut(200);
	});

  	$(".sure").click(function(){
	  $(".tip").fadeOut(100);
	  $("#sponsor").val($("#sponsorName").val());
	});
  	
  	/* 清除发起人 */
  	$(".position").click(function(){
  		$("#sponsor").val("");
  	});
});
</script>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">流程首页</a></li>
			<li><a href="#">工单查询</a></li>
		</ul>
	</div>

	<div class="rightinfo">
		<div class="tools">
			<ul class="seachform">
				<li><label>标题：</label><input name="" type="text" class="scinput" /></li>
				<li><label style="margin-left: 11px;">发起人： </label><input name="" id="sponsor" type="text" class="scinput" readonly="readonly" /> <img class="position" src="${cdnPrefix}/template/images/empty.png" /></li>
				<li><label>流程名称：</label>
					<div class="vocation">
						<select class="select3">
							<option>全部</option>
							<option>请假流程</option>
							<option>申请流程</option>
							<option>发文流程</option>
						</select>
					</div></li>
			</ul>
		</div>
		<div class="tools">
			<ul class="seachform">
				<li><label>编号：</label><input name="" type="text" class="scinput" /></li>
				<li><label>发起时间：</label> <input name="" type="text" readonly="readonly" class="scinput" id="initiateTime" /></li>

				<li><label style="margin-left: 22px;">工单状态：</label>
					<div class="vocation">
						<select class="select3" style="width: 100px;">
							<option>全部</option>
							<option>处理</option>
							<option>待处理</option>
							<option>结束</option>
						</select>
					</div></li>

				<li><input id="btn" name="" type="button" class="scbtn" value="查询" /></li>
			</ul>
		</div>

		<table class="tablelist">
			<thead>
				<tr>
					<th>编号</th>
					<th>流程名称</th>
					<th>标题</th>
					<th>发起人</th>
					<th>当前任务</th>
					<th>发起时间</th>
					<th>状态</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>20130908</td>
					<td>请假流程</td>
					<td>请假</td>
					<td>张三</td>
					<td>申请审批</td>
					<td>2018-3-13 10:20:11</td>
					<td>未结束</td>
				</tr>

				<tr>
					<td>20130908</td>
					<td>请假流程</td>
					<td>请假</td>
					<td>张三</td>
					<td>申请审批</td>
					<td>2018-3-13 10:20:11</td>
					<td>未结束</td>
				</tr>

				<tr>
					<td>20130908</td>
					<td>请假流程</td>
					<td>请假</td>
					<td>张三</td>
					<td>申请审批</td>
					<td>2018-3-13 10:20:11</td>
					<td>未结束</td>
				</tr>

				<tr>
					<td>20130908</td>
					<td>请假流程</td>
					<td>请假</td>
					<td>张三</td>
					<td>申请审批</td>
					<td>2018-3-13 10:20:11</td>
					<td>未结束</td>
				</tr>

			</tbody>
		</table>

		<div class="pagin">
			<div class="message">
				共<i class="blue">1256</i>条记录，当前显示第&nbsp;<i class="blue">2&nbsp;</i>页
			</div>
			<ul class="paginList">
				<li class="paginItem"><a href="javascript:;"><span class="pagepre"></span></a></li>
				<li class="paginItem"><a href="javascript:;">1</a></li>
				<li class="paginItem current"><a href="javascript:;">2</a></li>
				<li class="paginItem"><a href="javascript:;">3</a></li>
				<li class="paginItem"><a href="javascript:;">4</a></li>
				<li class="paginItem"><a href="javascript:;">5</a></li>
				<li class="paginItem more"><a href="javascript:;">...</a></li>
				<li class="paginItem"><a href="javascript:;">10</a></li>
				<li class="paginItem"><a href="javascript:;"><span class="pagenxt"></span></a></li>
			</ul>
		</div>
	</div>

	<div class="tip">
		<div class="tiptop">
			<span>查询发起人信息</span><a></a>
		</div>
		<div class="tipbtn">
			<label>名称：</label><input name="" id="sponsorName" type="text" class="scinput" readonly="readonly" />&nbsp;&nbsp; <input name="" type="button" class="sure" value="确定" />
		</div>
		<div class="st_tree" style="width: 350px; margin: 0 auto;">
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
			 jeDate("#initiateTime",{
			 	 theme:{bgcolor:"#00A1CB",pnColor:"#00CCFF"},
	        	format: "YYYY-MM-DD"
			 });
		})
	</script>
</body>
</html>