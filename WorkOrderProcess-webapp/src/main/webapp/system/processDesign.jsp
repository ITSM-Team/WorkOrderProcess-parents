<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

.ope:hover {
	text-decoration: underline;
}
</style>
</head>
<div class="place">
	<span><spring:message code="position" />：</span>
	<ul class="placeul">
		<li><a href="#"><spring:message code="systemSettings" /></a></li>
		<li><a href="#"> <spring:message code="processDesign" /></a></li>
		<li><a href="#"> <spring:message code="processDesigner" /></a></li>
	</ul>
</div>

<div class="rightinfo">

	<div class="tools">
		<ul class="toolbar">
			<li class="click"><a href="${ctx }/modeler/modeler-open.do"
				target="_parent"><span><img
						src="${cdnPrefix}/template/images/t01.png" /></span> <spring:message
						code="create" /><spring:message code="process" /> </a></li>
			<li class="click update"><a
				href="${cdnPrefix}/modeler/modeler.html?modelId=2" target="_parent"><span><img
						src="${cdnPrefix}/template/images/t02.png" /></span> <spring:message
						code="update" /><spring:message code="process" /></a></li>
			<li class="click"><a href="#"><span><img
						src="${cdnPrefix}/template/images/t03.png" /></span> <spring:message
						code="delete" /><spring:message code="process" /></a></li>
		</ul>
	</div>

	<table class="tablelist">
		<thead>
			<tr>
				<th><input name="" id="selectAll" type="checkbox" value="" /></th>
				<th><spring:message code="id" /></th>
				<th><spring:message code="processnName" /></th>
				<th><spring:message code="version" /></th>
				<th><spring:message code="createTime" /></th>
				<th><spring:message code="lastUpdateTime" /></th>
				<th><spring:message code="operating" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="model" items="${models}">
				<tr>
					<td><input name="checkbox" type="checkbox" value="" /></td>
					<td>${model.id}</td>
					<td>${model.name}</td>
					<td>${model.version }</td>
					<td><fmt:formatDate value="${model.createTime}"
							pattern="yyyy-MM-dd hh:mm:ss" /></td>
					<td><fmt:formatDate value="${model.lastUpdateTime}"
							pattern="yyyy-MM-dd hh:mm:ss" /></td>
					<td><a href="#" class="ope"><spring:message code="release" /></a></td>
				</tr>

			</c:forEach>
		</tbody>
	</table>


	<div class="pagin">
		<div class="message">
			共<i class="blue">1256</i>条记录，当前显示第&nbsp;<i class="blue">2&nbsp;</i>页
		</div>
		<ul class="paginList">
			<li class="paginItem"><a href="javascript:;"><span
					class="pagepre"></span></a></li>
			<li class="paginItem"><a href="javascript:;">1</a></li>
			<li class="paginItem current"><a href="javascript:;">2</a></li>
			<li class="paginItem"><a href="javascript:;">3</a></li>
			<li class="paginItem"><a href="javascript:;">4</a></li>
			<li class="paginItem"><a href="javascript:;">5</a></li>
			<li class="paginItem more"><a href="javascript:;">...</a></li>
			<li class="paginItem"><a href="javascript:;">10</a></li>
			<li class="paginItem"><a href="javascript:;"><span
					class="pagenxt"></span></a></li>
		</ul>
	</div>
</div>
</body>
</html>