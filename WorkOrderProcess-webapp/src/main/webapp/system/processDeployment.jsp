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
			<li><a href="#">流程设计</a></li>
			<li><a href="#">流程部署</a></li>
		</ul>
	</div>

	<div class="rightinfo">
		<table class="tablelist">
			<thead>
				<tr>
					<th>编号</th>
					<th>名称</th>
					<th>部署时间</th>
					<th>分类</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>16</td>
					<td>测试流程</td>
					<td>2018-04-23 10:09:09</td>
					<td>通用流程</td>
					<td>
						<a href="#" class="ope">删除</a>&nbsp;
						<a href="${ctx }/system/processDeployment_view.jsp" class="ope">查看资源</a>
					</td>
				</tr>
				
				<tr>
					<td>16</td>
					<td>测试流程</td>
					<td>2018-04-23 10:09:09</td>
					<td>通用流程</td>
					<td>
						<a href="#" class="ope">删除</a>&nbsp;
						<a href="${ctx }/system/processDeployment_view.jsp" class="ope">查看资源</a>
					</td>
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