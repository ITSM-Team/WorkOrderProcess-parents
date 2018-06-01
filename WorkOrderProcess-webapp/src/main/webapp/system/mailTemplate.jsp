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
			<li><a href="#">系统设置</a></li>
			<li><a href="#">系统配置</a></li>
			<li><a href="#">模版管理</a></li>
			<li><a href="#">邮件模版</a></li>
		</ul>
	</div>


	<div class="formbody">
		<div id="usual1" class="usual">
			<div class="itab">
				<ul>
					<li><a href="#tab1" class="selected" onclick="mail()">邮件模版</a></li>
					<li><a href="#tab2" onclick="sms()">短信模版</a></li>
				</ul>
			</div>

			<div class="tabson">
				<div class="tools">
					<ul class="toolbar">
						<li class="click">
							<a href="${ctx}/system/mailTemplate_input.jsp">
							<span><img src="${cdnPrefix}/template/images/t01.png" /></span>新增模版</a>
						</li>
						<li class="click update">
							<a href="${ctx}/system/mailTemplate_input.jsp">
							<span><img src="${cdnPrefix}/template/images/t02.png" /></span>修改模版</a>
						</li>
						<li class="click">
							<a href="#"><span><img src="${cdnPrefix}/template/images/t03.png" /></span>删除模版</a>
						</li>
						<li class="click">
							<a href="${ctx }/system/mailTemplate_setting.jsp"><span><img src="${cdnPrefix}/template/images/t05.png" /></span>邮件配置</a>
						</li>
					</ul>
				</div>
				
				<table class="tablelist">
					<thead>
						<tr>
							<th><input name="" id="selectAll" type="checkbox" value=""/></th>
							<th>编号</th>
							<th>名称</th>
							<th>主题</th>
							<th>内容</th>
							<th>SMTP服务器</th>
							<th>创建时间</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input name="checkbox" type="checkbox" value="" /></td>
							<td>1</td>
							<td>到达模版</td>
							<td>到达模版</td>
							<td>你的工单已经到达部门审批！</td>
							<td>mail.citsh.com</td>
							<td>2018-3-13 08:20:22</td>
						</tr>
			
						<tr>
							<td><input name="checkbox" type="checkbox" value="" /></td>
							<td>2</td>
							<td>到达模版</td>
							<td>到达模版</td>
							<td>你的工单已经到达部门审批！</td>
							<td>mail.citsh.com</td>
							<td>2018-3-13 08:20:22</td>
						</tr>
						
						<tr>
							<td><input name="checkbox" type="checkbox" value="" /></td>
							<td>3</td>
							<td>到达模版</td>
							<td>到达模版</td>
							<td>你的工单已经到达部门审批！</td>
							<td>mail.citsh.com</td>
							<td>2018-3-13 08:20:22</td>
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
		function mail(){
			window.location.href="${ctx }/system/mailTemplate.jsp"
			
		}
		function sms(){
			location.href="smsTemplate.do"
		}
	</script>
</body>
</html>