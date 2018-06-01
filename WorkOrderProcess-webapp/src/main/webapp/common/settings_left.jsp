<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title></title>
<%@include file="/common/s3.jsp"%>

<script type="text/javascript">
$(function(){	
	//导航切换
	$(".menuson li").click(function(){
		$(".menuson li.active").removeClass("active")
		$(this).addClass("active");
	});
	
	$('.title').click(function(){
		var $ul = $(this).next('ul');
		$('dd').find('ul').slideUp();
		if($ul.is(':visible')){
			$(this).next('ul').slideUp();
		}else{
			$(this).next('ul').slideDown();
		}
	});
})	
</script>

</head>
<body style="background: #f0f9fd;">
	<div class="lefttop"><span></span>系统配置</div>

	<dl class="leftmenu">
		<dd>
			<div class="title">
				<span><img src="${cdnPrefix}/template/images/process_design.png" /></span>流程设计
			</div>
			<ul class="menuson">
				<li class="active"><cite></cite><a href="${ctx}/modeler/modeler-list.do" target="rightFrame">流程设计器</a><i></i></li>
				<li><cite></cite><a href="${ctx }/form/form-template-list" target="rightFrame">表单设计器</a><i></i></li>
			</ul>
		</dd>
		
		<dd>
			<div class="title">
				<span><img src="${cdnPrefix}/template/images/process_management.png" /></span>流程管理
			</div>
			<ul class="menuson">
				<li><cite></cite><a href="${ctx }/deploy/listProcessDefinitions" target="rightFrame">流程定义</a><i></i></li>
				<li><cite></cite><a href="${ctx }/system/processDeployment.jsp" target="rightFrame">流程部署</a><i></i></li>
				<li><cite></cite><a href="${ctx }/system/processSort.jsp" target="rightFrame">流程分类配置</a><i></i></li>
				<li><cite></cite><a href="${ctx }/system/workOrderTimeoutReminder.jsp" target="rightFrame">工单超时提醒</a><i></i></li>
				<li><cite></cite><a href="${ctx }/system/formBusinessValidation.jsp" target="rightFrame">表单业务验证配置</a><i></i></li>
			</ul>
		</dd>
		
		<dd>
			<div class="title">
				<span><img src="${cdnPrefix}/template/images/work_order_management.png" /></span>工单管理
			</div>
			<ul class="menuson">
				<li><cite></cite><a href="${ctx }/system/deleteWorkOrder.jsp" target="rightFrame">删除工单</a><i></i></li>
			</ul>
		</dd>
		
		<dd>
			<div class="title">
				<span><img src="${cdnPrefix}/template/images/config.png" /></span>系统配置
			</div>
			<ul class="menuson">
				<li><cite></cite><a href="${ctx }/system/menuSettings.jsp" target="rightFrame">菜单配置</a><i></i></li>
				<li><cite></cite><a href="${ctx }/system/organizationStructure.jsp" target="rightFrame">组织管理</a><i></i></li>
				<li><cite></cite><a href="${ctx }/system/userManagement.jsp" target="rightFrame">用户管理</a><i></i></li>
				<li><cite></cite><a href="${ctx }/system/roleManagement.jsp" target="rightFrame">角色管理</a><i></i></li>
				<li><cite></cite><a href="${ctx }/system/annexManagement.jsp" target="rightFrame">附件管理</a><i></i></li>
				<li><cite></cite><a href="${ctx }/system/dataDictionary.jsp" target="rightFrame">数据字典</a><i></i></li>
				<li><cite></cite><a href="${ctx }/system/mailTemplate.jsp" target="rightFrame">模板管理</a><i></i></li>
				<li><cite></cite><a href="${ctx }/system/operatingHours.jsp" target="rightFrame">工作时间管理</a><i></i></li>
			</ul>
		</dd>

	</dl>
</body>
</html>