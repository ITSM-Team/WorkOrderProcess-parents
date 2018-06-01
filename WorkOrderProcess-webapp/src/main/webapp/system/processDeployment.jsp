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
		<span><spring:message code="position" text="位置" />：</span>
		<ul class="placeul">
			<li><a href="#"><spring:message code="systemSettings"
						text="系统设置" /></a></li>
			<li><a href="#"><spring:message code="processManage"
						text="流程设计" /></a></li>
			<li><a href="#"><spring:message code="processDeployment"
						text="流程部署" /></a></li>
		</ul>
	</div>

	<div class="rightinfo">
		<table class="tablelist">
			<thead>
				<tr>
					<th><spring:message code="id" text="编号" /></th>
					<th><spring:message code="name" text="名称" /></th>
					<th><spring:message code="createTime" text="名称" /></th>
					<th><spring:message code="category" text="分类" /></th>
					<th><spring:message code="operating" text="操作" /></th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="deployment" items="${page.result}">
				<tr>
					<td>${deployment.id}</td>
					<td>${deployment.name}</td>
					<td><fmt:formatDate value="${deployment.deploymentTime}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
					<td>${deployment.category}</td>
					<td>
						<a href="#" class="ope"><spring:message code="delete" text="删除" /></a>&nbsp;
						<a href="${ctx }/deploy/listDeploymentResourceNames?deploymentId=${deployment.id}" class="ope">
							<spring:message code="view" text="查看" />
						</a>
					</td>
				</tr>
				</c:forEach>
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