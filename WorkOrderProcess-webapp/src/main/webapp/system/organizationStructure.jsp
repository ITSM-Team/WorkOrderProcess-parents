<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title></title>
<%@include file="/common/s3.jsp"%>
<link rel="stylesheet" href="${cdnPrefix}/simpleTree/css/SimpleTree.css" />
<script type="text/javascript" src="${cdnPrefix}/simpleTree/js/SimpleTree.js"></script>
<style type="text/css">
.ope{ color: #056C9E; }
.ope:hover{ text-decoration: underline; }
</style>
<script type="text/javascript">
$(function(){
	$(".st_tree").SimpleTree({});
});
</script>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">系统设置</a></li>
			<li><a href="#">系统配置</a></li>
			<li><a href="#">组织管理</a></li>
			<li><a href="#">组织结构</a></li>
		</ul>
	</div>


	<div class="formbody">
		<div id="usual1" class="usual">
			<div class="itab">
				<ul>
					<li><a href="#tab1" class="selected" onclick="organization()">组织结构</a></li>
					<li><a href="#tab2" onclick="position()">职位管理</a></li>
				</ul>
			</div>
			
			
			<div class="formbody">
				<div id="usual1" class="usual">
					<div class="formbody">
						<div class="formtitle">
							<span>组织结构</span>
						</div>
						<div class="toolsli">
						
							<div class="st_tree" style="width: 350px;;">
								<ul>
									<li><a href="#" ref="xtgl" title="公司">公司</a></li>
									<li><a href="#" ref="xtgl">行政部</a></li>
										<ul show="true">
											<li><a href="#" ref="yhgl" title="IT小组">IT小组</a></li>
											<ul show="true">
												<li><a href="#" ref="yhgl" title="张三">张三</a></li>
												<li><a href="#" ref="rzck" title="李四">李四</a></li>
											</ul>
											<li><a href="#" ref="rzck" title="李四">李四</a></li>
										</ul>
									<li><a href="#" ref="ckgl">人事部</a></li>
										<ul>
											<li><a href="#" ref="kcgl" title="金">金</a></li>
											<li><a href="#" ref="shgl" title="亚当">亚当</a></li>
											<li><a href="#" ref="fhgl" title="麦克">麦克</a></li>
										</ul>
								</ul>
							</div>
						
						</div>
					</div>
				</div>
			</div>
			
			<div class="formbody">
				<div id="usual1" class="usual">
					<div class="formbody">
						<div class="formtitle" style="margin-bottom: 0px;">
							<span>列表操作</span>
						</div>
						<div class="toolsli">
						
							<div class="tabson">
							   <div class="tools">
									<ul class="toolbar">
										<li class="click">
											<a href="${ctx}/system/organizationStructure_input.jsp">
											<span><img src="${cdnPrefix}/template/images/t01.png" /></span>新增结构</a>
										</li>
										<li class="click">
											<a href="${ctx}/system/organizationStructure_input.jsp">
											<span><img src="${cdnPrefix}/template/images/t02.png" /></span>修改结构</a>
										</li>
										<li class="click">
											<a href="#"><span><img src="${cdnPrefix}/template/images/t03.png" /></span>删除结构</a>
										</li>
									</ul>
								</div>
								
								<table class="tablelist">
									<thead>
										<tr>
											<th><input name="" type="checkbox" value="" checked="checked" /></th>
											<th>编号</th>
											<th>名称</th>
											<th>类型</th>
											<th>职位</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><input name="" type="checkbox" value="" /></td>
											<td>1</td>
											<td>citsh公司</td>
											<td>公司</td>
											<td>--</td>
											<td>--</td>
										</tr>
							
										<tr>
											<td><input name="" type="checkbox" value="" /></td>
											<td>2</td>
											<td>行政部</td>
											<td>部门</td>
											<td>--</td>
											<td>--</td>
										</tr>
										
										<tr>
											<td><input name="" type="checkbox" value="" /></td>
											<td>3</td>
											<td>张三</td>
											<td>人员</td>
											<td>经理</td>
											<td><a href="${ctx }/system/organizationStructure_setting.jsp" class="ope">配置职位</a></td>
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
				</div>
			</div>
			
			
			
		</div>
	</div>
</body>
<script type="text/javascript">
		function organization(){
			window.location.href="${ctx }/system/organizationStructure.jsp"
			
		}
		function position(){
			window.location.href="${ctx }/system/jobTitle.jsp"
		}
	</script>
</html>