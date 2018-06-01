<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title></title>
<%@include file="/common/s3.jsp"%>
<style type="text/css">
.ope { color: #056C9E; }
.ope:hover{ text-decoration: underline; }
</style>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">系统设置</a></li>
			<li><a href="#">系统配置</a></li>
			<li><a href="#">流程分类配置</a></li>
		</ul>
	</div>

	<div class="rightinfo">

		<div class="tools">
			<ul class="toolbar">
				<li class="click">
					<a href="${ctx}/system/processSort_input.jsp">
					<span><img src="${cdnPrefix}/template/images/t01.png" /></span>新增分类</a>
				</li>
				<li class="click update">
					<a href="${ctx}/system/processSort_input.jsp">
					<span><img src="${cdnPrefix}/template/images/t02.png" /></span>修改分类</a>
				</li>
				<li class="click">
					<a href="#"><span><img src="${cdnPrefix}/template/images/t03.png" /></span>删除分类</a>
				</li>
			</ul>
		</div>

		<table class="tablelist">
			<thead>
				<tr>
					<th><input name="" id="selectAll" type="checkbox" value="" /></th>
					<th>编号</th>
					<th>分类名称</th>
					<th>排序</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><input name="checkbox" type="checkbox" value="" /></td>
					<td>1</td>
					<td>通用流程</td>
					<td>0</td>
					<td><a href="${ctx }/system/processSort_setting.jsp" class="ope">配置</a></td>
				</tr>
	
				<tr>
					<td><input name="checkbox" type="checkbox" value="" /></td>
					<td>2</td>
					<td>OA流程</td>
					<td>1</td>
					<td><a href="${ctx }/system/processSort_setting.jsp" class="ope">配置</a></td>
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