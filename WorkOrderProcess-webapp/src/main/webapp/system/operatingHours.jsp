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
.ope:hover { text-decoration: underline; }
</style>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">系统设置</a></li>
			<li><a href="#">系统配置</a></li>
			<li><a href="#">工作时间管理</a></li>
			<li><a href="#">正常时间</a></li>
		</ul>
	</div>


	<div class="formbody">
		<div id="usual1" class="usual">
			<div class="itab">
				<ul>
					<li><a href="#tab1" class="selected" onclick="normal()">正常时间</a></li>
					<li><a href="#tab2" onclick="special()">特殊时间</a></li>
				</ul>
			</div>

			<div class="tabson">
				<table class="tablelist">
					<thead>
						<tr>
							<th>星期</th>
							<th>状态</th>
							<th>工作时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>星期一</td>
							<td>上班</td>
							<td>09:00-12:00/13:00-18:00</td>
							<td><a href="${ctx }/system/operatingHours_setting.jsp" class="ope">设置</a></td>
						</tr>
						<tr>
							<td>星期二</td>
							<td>上班</td>
							<td>09:00-12:00/13:00-18:00</td>
							<td><a href="#" class="ope">设置</a></td>
						</tr>
						<tr>
							<td>星期三</td>
							<td>上班</td>
							<td>09:00-12:00/13:00-18:00</td>
							<td><a href="#" class="ope">设置</a></td>
						</tr>
						<tr>
							<td>星期四</td>
							<td>上班</td>
							<td>09:00-12:00/13:00-18:00</td>
							<td><a href="#" class="ope">设置</a></td>
						</tr>
						<tr>
							<td>星期五</td>
							<td>上班</td>
							<td>09:00-12:00/13:00-18:00</td>
							<td><a href="#" class="ope">设置</a></td>
						</tr>
						<tr>
							<td>星期六</td>
							<td>休假</td>
							<td>--</td>
							<td><a href="#" class="ope">设置</a></td>
						</tr>
						<tr>
							<td>星期日</td>
							<td>休假</td>
							<td>--</td>
							<td><a href="#" class="ope">设置</a></td>
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
		</div>
	</div>
	<script type="text/javascript">
		function normal(){
			window.location.href="${ctx }/system/operatingHours.jsp"
			
		}
		function special(){
			window.location.href="${ctx }/system/specialWorkingHours.jsp"
		}
	</script>
</body>
</html>