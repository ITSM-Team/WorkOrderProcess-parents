<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title></title>
<%@include file="/common/s3.jsp"%>
<link rel='stylesheet'	href='${cdnPrefix}/bootstrap/3.3.7/css/bootstrap.min.css'
	type='text/css' media='screen' />
<link href="${cdnPrefix}/xform3/styles/xform.css" rel="stylesheet">
<script type="text/javascript" src="${cdnPrefix}/xform3/xform-packed.js"></script>

<link href="${cdnPrefix}/xform3/styles/xform.css" rel="stylesheet">
<script type="text/javascript" src="${cdnPrefix}/xform3/xform-packed.js"></script>
<script type="text/javascript">

var xform;

$(function() {
	xform = new xf.Xform('xf-form-table');
	xform.render();
	if ($('#__gef_content__').val() != '') {
		xform.doImport($('#__gef_content__').val());
	}
	if ('${xform.jsonData}' != '') {
		xform.setValue(${xform.jsonData});
	}

});
</script>

</head>
<body>
	<div class="place">
		<span><spring:message code="position" />：</span>
		<ul class="placeul">
			<li><a href="#"><spring:message code="systemSettings" /></a></li>
			<li><a href="#"> <spring:message code="processDesign" /></a></li>
			<li><a href="#"> <spring:message code="formDesigner" /></a></li>
			<li><a href="#" /> <spring:message code="preview" /></li>
		</ul>
	</div>

	<div class="row-fluid">

		<!-- start of main -->
		<section id="m-main" class="col-md-12" style="padding-top: 65px;">

			<form id="xf-form" method="post"
				action="${tenantPrefix}/operation/form-operation-test.do"
				class="xf-form" enctype="multipart/form-data">
				<input id="ref" type="hidden" name="ref" value="${record.ref}">
				<div id="xf-form-table"></div>
				<br>
				<div style="text-align: center;"></div>
			</form>

		</section>
		<!-- end of main -->

		<form id="f" action="form-template-save.do" method="post"
			style="display: none;">
			<textarea id="__gef_content__" name="content">${xform.content}</textarea>
		</form>
</body>
</html>

