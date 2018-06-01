<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title></title>
<%@include file="/common/s3.jsp"%>
<style type="text/css">
.ope {
	color: #056C9E;
}
.ope:hover{
	text-decoration: underline;
}
</style>
<script type="text/javascript">
$(document).ready(function(e) {
	$(".select1").uedSelect({
		width : 200
	});
});
</script>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">流程首页</a></li>
			<li><a href="#">流程配置</a></li>
			<li><a href="#">配置</a></li>
			<li><a href="#">规则</a></li>
		</ul>
	</div>

	<div class="formbody">
		<div id="usual1" class="usual">
			<div class="formbody">
				<div class="formtitle">
					<span>返回</span>
				</div>
				<div class="toolsli">
					<input name="" type="button" class="sure" onclick="history.back();" value="返回" />
				</div>
			</div>
		
			<div class="formbody">
				<div class="formtitle">
					<span>添加</span>
				</div>
				<div class="toolsli">
					<ul class="forminfo">
						<!-- <li>
							<label>规则:</label>
							<input name="" id="agent" type="text" class="dfinput" style="width: 200px;" />
						</li> -->
						<li>
							<label>规则:</label>
							<div class="vocation">
								<select class="select1">
									<option>高级职位自动跳过</option>
									<option>相邻相同人员自动跳过</option>
								</select>
							</div>
						</li>
					</ul>
					&nbsp;&nbsp;
					<input name="" type="button" class="sure" value="提交" /> 
				</div>
			</div>
			
			<div class="formbody">
				<div class="formtitle">
					<span>列表</span>
				</div>
				<div class="toolsli">
					<table class="tablelist">
						<thead>
							<tr>
								<th>编号</th>
								<th>名称</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1161196718833664</td>
								<td>高级职位自动跳过</td>
								<td><a href="#" class="ope">删除</a></td>
							</tr>
							<tr>
								<td>1161196718833664</td>
								<td>高级职位自动跳过</td>
								<td><a href="#" class="ope">删除</a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>