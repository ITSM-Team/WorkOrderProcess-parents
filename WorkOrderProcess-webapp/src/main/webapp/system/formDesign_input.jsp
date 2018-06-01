<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title></title>
<script type='text/javascript' src='${cdnPrefix}/template/js/jquery.min.js'></script>
<link rel='stylesheet' href='${cdnPrefix}/bootstrap/3.3.7/css/bootstrap.min.css' type='text/css' media='screen' />
<script type='text/javascript' src='${cdnPrefix}/bootstrap/bootstrap.min.js'></script>
<link href="${cdnPrefix}/xform3/styles/xform.css" rel="stylesheet">
<script type="text/javascript" src="${cdnPrefix}/xform3/xform-packed.js"></script>
<script type="text/javascript" src="${cdnPrefix}/xform3/adaptor.js"></script>
<script type="text/javascript">
$(function(){
	
	
})
</script>
<style type="text/css">
.xf-pallete {
	border: dotted 2px gray;
	width: 45%;
	margin: 5px;
	padding: 5px;
	padding-left: 10px;
	text-align: left;
	background-color: #F8F8F8;
	float: left;
}

.xf-pallete img {
	float: right;
}

.xf-table td {
	height: 45px;
}

.tab-pane {
	margin-right: 20px;
}
</style>
</head>
<body>
	<div class="row-fluid">

		<!-- start of main -->
		<section id="m-main" class="col-md-12" style="padding-top: 65px;">

			<div id="__gef_container__" style="padding-left: 5px;">
				<div id="__gef_palette__" style="float: left; width: 260px;">
					<ul class="nav nav-tabs" id="myTab">
						<li class="active"><a href="#operation" data-toggle="tab">控件</a></li>
						<li><a href="#form" data-toggle="tab">控件属性</a></li>
						<li><a href="#prop" data-toggle="tab">表单属性</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="operation">
							<div style="padding-top: 5px;">
								<div class="xf-pallete" title="label">
									<img src="${cdnPrefix}/xform/images/xform/new_label.png"> label
								</div>
								<div class="xf-pallete" title="textfield">
									<img src="${cdnPrefix}/xform/images/xform/new_input.png"> textfield
								</div>
								<div class="xf-pallete" title="password">
									<img src="${cdnPrefix}/xform/images/xform/new_secret.png"> password
								</div>
								<div class="xf-pallete" title="textarea">
									<img src="${cdnPrefix}/xform/images/xform/new_textarea.png"> textarea
								</div>
								<div class="xf-pallete" title="select">
									<img src="${cdnPrefix}/xform/images/xform/new_select.png"> select
								</div>
								<div class="xf-pallete" title="radio">
									<img src="${cdnPrefix}/xform/images/xform/new_item.png"> radio
								</div>
								<div class="xf-pallete" title="checkbox">
									<img src="${cdnPrefix}/xform/images/xform/new_itemset.png"> checkbox
								</div>
								<div class="xf-pallete" title="fileupload">
									<img src="${cdnPrefix}/xform/images/xform/new_upload.png"> fileupload
								</div>
								<div class="xf-pallete" title="datepicker">
									<img src="${cdnPrefix}/xform/images/xform/new_range.png"> datepicker
								</div>
								<div class="xf-pallete" title="userpicker">
									<img src="${cdnPrefix}/xform/images/xform/userpicker.png"> userpicker
								</div>
							</div>
						</div>
						<div class="tab-pane" id="form">
							<div class="panel panel-default" style="display: block; position: relative;">
								<div class="panel-heading">控件属性</div>
								<div class="panel-body">
									<div id="xf-form-attribute" class="controls"></div>
								</div>
							</div>
						</div>
						<div class="tab-pane" id="prop">
							<div class="panel panel-default" style="display: block; position: relative;">
								<div class="panel-heading">表单属性</div>
								<div class="panel-body">
									<div id="xf-form-attribute" class="controls">
										<label> 名称 <input id="xFormName" type="text" class="form-control">
										</label> <label> 标识 <input id="xFormCode" type="text" class="form-control">
										</label>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="__gef_center__">
					<div id="__gef_toolbar__">
						<div class="btn-group">
							<button class="btn btn-default" onclick="doSave()">保存</button>
							<button class="btn btn-default" onclick="javascript:window.history.back(-1);">返回</button>
							<button class="btn btn-default" id="delete">删除控件</button>
							<!-- <button class="btn btn-default" onclick="alert(xform.doExport())">export</button>
							<button class="btn btn-default" onclick="doImport()">import</button> -->
							<button class="btn btn-default" onclick="xform.addRow()">添加行</button>
							<button class="btn btn-default" onclick="xform.removeRow()">删除行</button>
							<button class="btn btn-default" onclick="doChangeMode(this)">切换为合并模式</button>
							<button class="btn btn-default" onclick="doMerge()" id="mergeCell" style="display: none;">合并单元格</button>
							<button class="btn btn-default" onclick="doSplit()" id="splitCell" style="display: none;">拆分单元格</button>
						</div>
					</div>


					<div id="__gef_canvas__" style="overflow: auto;">
						<div id="xf-center" class="xf-center" unselectable="on">
							<div id="xf-layer-form" class="xf-layer-form">
								<form id="xf-form" action="#" method="post" class="controls"></form>
							</div>
							<div id="xf-layer-mask" class="xf-layer-mask"></div>
						</div>
					</div>
				</div>

			</div>
	</div>
	</div>

	<form id="f" action="form-template-save.do" method="post" style="display: none;">
		<c:if test="${model != null}">
			<input id="__gef_id__" name="id" value="${model.id}">
		</c:if>
		<input id="__gef_name__" name="name" value="${model.name}"> <input id="__gef_code__" name="code" value="${model.code}">
		<textarea id="__gef_content__" name="content">${model.content}</textarea>
	</form>
	</div>
</body>
</html>