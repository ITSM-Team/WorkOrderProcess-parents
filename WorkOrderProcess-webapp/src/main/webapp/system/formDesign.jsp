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

.ope:hover {
	text-decoration: underline;
}
</style>
</head>
<body>
	<div class="place">
		<span><spring:message code="position" />：</span>
		<ul class="placeul">
			<li><a href="#"><spring:message code="systemSettings" /></a></li>
			<li><a href="#"> <spring:message code="processDesign" /></a></li>
			<li><a href="#"> <spring:message code="formDesigner" /></a></li>
		</ul>
	</div>

	<div class="rightinfo">

		<div class="tools">
			<ul class="toolbar">
				<li class="click"><a href="${ctx}/system/formDesign_input.jsp"
					target="_parent"><span><img
							src="${cdnPrefix}/template/images/t01.png" /></span>
					<spring:message code="create" /><spring:message code="form" /></a></li>
				<li class="click update"><a
					href="${ctx}/system/formDesign_input.jsp" target="_parent"><span><img
							src="${cdnPrefix}/template/images/t02.png" /></span>
					<spring:message code="update" /><spring:message code="form" /></a></li>
				<li class="click"><a href="#"><span><img
							src="${cdnPrefix}/template/images/t03.png" /></span>
					<spring:message code="delete" /><spring:message code="form" /></a></li>
			</ul>
		</div>



		<table class="tablelist">
			<thead>
				<tr>
					<th><input name="" id="selectAll" type="checkbox" value="" /></th>
					<th><spring:message code="id" /></th>
					<th><spring:message code="name" /></th>
					<th><spring:message code="identification" /></th>
					<th><spring:message code="type" /></th>
					<th><spring:message code="operating" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="entity" items="${page.result}">
					<tr>
						<td><input name="checkbox" type="checkbox" value="" /></td>
						<td>${entity.id}</td>
						<td>${entity.name}</td>
						<td>${entity.code}</td>
						<td>${entity.type==0?'内部':'外部'}</td>
						<td><a href="${ctx}/form/form-preview?code=${entity.code}" class="ope"><spring:message code="preview" /> </a></td>
					</tr>
				</c:forEach>


				<tr>
					<td><input name="checkbox" type="checkbox" value="" /></td>
					<td>2</td>
					<td>上级审批</td>
					<td>vacation-department</td>
					<td>内部</td>
					<td><a href="#" class="ope">预览</a></td>
				</tr>

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