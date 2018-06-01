<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title></title>
<%@include file="/common/s3.jsp"%>
<style type="text/css">
.ope{ color: #056C9E; }
.ope:hover{ text-decoration: underline; }
</style>
</head>
<body>
<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">系统设置</a></li>
			<li><a href="#">工单管理</a></li>
			<li><a href="#">删除工单</a></li>
		</ul>
	</div>

	<div class="rightinfo">

		<div class="tools">
			<ul class="toolbar">
				<li class="click">
					<a href="#"><span><img src="${cdnPrefix}/template/images/t03.png" /></span>删除工单</a>
				</li>
			</ul>
		</div>
		
		

		<table class="tablelist">
			<thead>
				<tr>
					<th><input name="" type="checkbox" value="" checked="checked" /></th>
					<th>编号</th>
					<th>工单标题</th>
					<th>工单名称</th>
					<th>工单类别</th>
					<th>发起人</th>
					<th>发起时间</th>
					<th>结束时间</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th><input name="" type="checkbox" value="" /></th>
					<td>20130908</td>
					<td>请假流程</td>
					<td>请假流程</td>
					<td>通用流程</td>
					<td>金</td>
					<td>2018-3-13 08:20:22</td>
					<td>2018-3-13 16:20:22</td>
					<td>结束</td>
					<td><a href="${ctx }/system/deleteWorkOrder_view.jsp" class="ope">查看</a></td>
				</tr>
				
				<tr>
					<th><input name="" type="checkbox" value="" /></th>
					<td>20130908</td>
					<td>请假流程</td>
					<td>请假流程</td>
					<td>通用流程</td>
					<td>金</td>
					<td>2018-3-13 08:20:22</td>
					<td>2018-3-13 16:20:22</td>
					<td>结束</td>
					<td><a href="${ctx }/system/deleteWorkOrder_view.jsp" class="ope">查看</a></td>
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
</body>
</html>