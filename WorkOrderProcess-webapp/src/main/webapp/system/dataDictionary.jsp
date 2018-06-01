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
			<li><a href="#">数据字典</a></li>
		</ul>
	</div>

	<div class="rightinfo">

		<div class="tools">
			<ul class="toolbar">
				<li class="click">
					<a href="${ctx}/system/dataDictionary_input.jsp">
					<span><img src="${cdnPrefix}/template/images/t01.png" /></span>新增数据类</a>
				</li>
				<li class="click update">
					<a href="${ctx}/system/dataDictionary_input.jsp">
					<span><img src="${cdnPrefix}/template/images/t02.png" /></span>修改数据类</a>
				</li>
				<li class="click">
					<a href="#"><span><img src="${cdnPrefix}/template/images/t03.png" /></span>删除数据类</a>
				</li>
			</ul>
			
			<div style="float: right; margin-right: 40px;">
				<input name="" type="text" class="scinput" placeholder="查询数据类名称" />&nbsp;
				<input id="btn" name="" type="button" class="scbtn" value="查询"/>
			</div>	
		</div>
		
		

		<table class="tablelist">
			<thead>
				<tr>
					<th><input name="" id="selectAll" type="checkbox" value=""/></th>
					<th>编号</th>
					<th>名称</th>
					<th>类型</th>
					<th>备注</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><input name="checkbox" type="checkbox" value="" /></td>
					<td>1</td>
					<td>省直辖市</td>
					<td>城市名</td>
					<td>系统全部城市名称</td>
					<td><a href="${ctx }/system/dataDictionary_data.jsp" class="ope">数据</a></td>
				</tr>
	
				<tr>
					<td><input name="checkbox" type="checkbox" value="" /></td>
					<td>2</td>
					<td>民族</td>
					<td>民族</td>
					<td>系统全部民族名称</td>
					<td><a href="#" class="ope">数据</a></td>
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