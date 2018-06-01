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
			<li><a href="#">流程定义</a></li>
		</ul>
	</div>

	<div class="rightinfo">
		<table class="tablelist">
			<thead>
				<tr>
					<th>编号</th>
					<th>代码</th>
					<th>名称</th>
					<th>分类</th>
					<th>版本</th>
					<th>描述</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>permission:1:6</td>
					<td>permission</td>
					<td>审批权限</td>
					<td>通用流程</td>
					<td>1</td>
					<td>审批权限</td>
					<td>挂起（<a href="#" class="ope">激活</a>）</td>
					<td>
						<a href="#" class="ope">流程图	</a>&nbsp;
						<a href="#" class="ope">查看XML</a>&nbsp;&nbsp;
						<a href="#" class="ope">图标查看器</a>
					</td>
				</tr>
				
				<tr>
					<td>permission:1:6</td>
					<td>permission</td>
					<td>审批权限</td>
					<td>通用流程</td>
					<td>1</td>
					<td>审批权限</td>
					<td>挂起（<a href="#" class="ope">激活</a>）</td>
					<td>
						<a href="#" class="ope">流程图	</a>&nbsp;
						<a href="#" class="ope">查看XML</a>&nbsp;&nbsp;
						<a href="#" class="ope">图标查看器</a>
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