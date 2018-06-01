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
			<li><a href="#">流程首页</a></li>
			<li><a href="#">待处理</a></li>
		</ul>
	</div>

	<div class="rightinfo">

		<div class="tools">

			<ul class="toolbar">
				<li class="click update">
					<a href="${ctx }/bpm/pending_input.jsp">
					<span><img src="${cdnPrefix}/template/images/t02.png" /></span>修改流程</a>
				</li>
			</ul>
		</div>


		<table class="tablelist">
			<thead>
				<tr>
					<th><input name="" id="selectAll" type="checkbox" value="" /></th>
					<th>工单标题</th>
					<th>编号</th>
					<th>优先级</th>
					<th>流程状态</th>
					<th>流程名称</th>
					<th>流程类别</th>
					<th>当前环节</th>
					<th>到达时间</th>
					<th>最后处理人</th>
					<th>发起人</th>
					<th>最后操作时间</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><input name="checkbox" type="checkbox" value="" /></td>
					<td>普通流程</td>
					<td>20130908</td>
					<td>1</td>
					<td>结束</td>
					<td>请假流程</td>
					<td>普通流程</td>
					<td>2018-3-13 10:20:11</td>
					<td>2018-3-13 14:11:47</td>
					<td>总经理</td>
					<td>张三</td>
					<td>2018-3-13 10:20:11</td>
				</tr>

				<tr>
					<td><input name="checkbox" type="checkbox" value="" /></td>
					<td>普通流程</td>
					<td>20130908</td>
					<td>1</td>
					<td>结束</td>
					<td>请假流程</td>
					<td>普通流程</td>
					<td>2018-3-13 10:20:11</td>
					<td>2018-3-13 14:11:47</td>
					<td>总经理</td>
					<td>张三</td>
					<td>2018-3-13 10:20:11</td>
				</tr>

				<tr>
					<td><input name="checkbox" type="checkbox" value="" /></td>
					<td>普通流程</td>
					<td>20130908</td>
					<td>1</td>
					<td>结束</td>
					<td>请假流程</td>
					<td>普通流程</td>
					<td>2018-3-13 10:20:11</td>
					<td>2018-3-13 14:11:47</td>
					<td>总经理</td>
					<td>张三</td>
					<td>2018-3-13 10:20:11</td>
				</tr>

				<tr>
					<td><input name="checkbox" type="checkbox" value="" /></td>
					<td>普通流程</td>
					<td>20130908</td>
					<td>1</td>
					<td>结束</td>
					<td>请假流程</td>
					<td>普通流程</td>
					<td>2018-3-13 10:20:11</td>
					<td>2018-3-13 14:11:47</td>
					<td>总经理</td>
					<td>张三</td>
					<td>2018-3-13 10:20:11</td>
				</tr>

				<tr>
					<td><input name="checkbox" type="checkbox" value="" /></td>
					<td>普通流程</td>
					<td>20130908</td>
					<td>1</td>
					<td>结束</td>
					<td>请假流程</td>
					<td>普通流程</td>
					<td>2018-3-13 10:20:11</td>
					<td>2018-3-13 14:11:47</td>
					<td>总经理</td>
					<td>张三</td>
					<td>2018-3-13 10:20:11</td>
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