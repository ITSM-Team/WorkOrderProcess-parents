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
			<li><a href="#">工单委托</a></li>
		</ul>
	</div>

	<div class="rightinfo">

		<div class="tools">
			<ul class="toolbar">
				<li class="click">
					<a href="${ctx}/bpm/workOrderEntrust_input.jsp">
					<span><img src="${cdnPrefix}/template/images/t01.png" /></span>新增代理</a>
				</li>
				<li class="click">
					<a href="#"><span><img src="${cdnPrefix}/template/images/t03.png" /></span>取消代理</a>
				</li>
			</ul>
			
			<div style="float: right; margin-right: 40px;">
				<input name="" type="text" class="scinput" placeholder="查询代理流程和代理人" />&nbsp;
				<input id="btn" name="" type="button" class="scbtn" value="查询"/>
			</div>	
		</div>
		
		

		<table class="tablelist">
			<thead>
				<tr>
					<th><input name="" id="selectAll" type="checkbox" value=""/></th>
					<th>流程名称</th>
					<th>代理人</th>
					<th>代理开始时间</th>
					<th>代理结束时间</th>
					<th>状态</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><input name="checkbox" type="checkbox" value="" /></td>
					<td>普通流程</td>
					<td>金</td>
					<td>2018-3-13 10:20:11</td>
					<td>2018-9-1 10:20:11</td>
					<td>代理中</td>
				</tr>
	
				<tr>
					<td><input name="checkbox" type="checkbox" value="" /></td>
					<td>普通流程</td>
					<td>金</td>
					<td>2018-3-13 10:20:11</td>
					<td>2018-9-1 10:20:11</td>
					<td>代理中</td>
				</tr>
	
				<tr>
					<td><input name="checkbox" type="checkbox" value="" /></td>
					<td>普通流程</td>
					<td>金</td>
					<td>2018-3-13 10:20:11</td>
					<td>2018-9-1 10:20:11</td>
					<td>代理中</td>
				</tr>
	
				<tr>
					<td><input name="checkbox" type="checkbox" value="" /></td>
					<td>普通流程</td>
					<td>金</td>
					<td>2018-3-13 10:20:11</td>
					<td>2018-9-1 10:20:11</td>
					<td>代理中</td>
				</tr>
	
				<tr>
					<td><input name="checkbox" type="checkbox" value="" /></td>
					<td>普通流程</td>
					<td>金</td>
					<td>2018-3-13 10:20:11</td>
					<td>2018-9-1 10:20:11</td>
					<td>代理中</td>
				</tr>
	
				<tr>
					<td><input name="checkbox" type="checkbox" value="" /></td>
					<td>普通流程</td>
					<td>金</td>
					<td>2018-3-13 10:20:11</td>
					<td>2018-9-1 10:20:11</td>
					<td>代理中</td>
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