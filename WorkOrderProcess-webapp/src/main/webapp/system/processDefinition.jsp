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
		<span><spring:message code="position" text="位置" />：</span>
		<ul class="placeul">
			<li><a href="#"><spring:message code="systemSettings"
						text="系统设置" /></a></li>
			<li><a href="#"><spring:message code="processManage"
						text="流程设计" /></a></li>
			<li><a href="#"><spring:message code="processDefinition"
						text="流程定义" /></a></li>
		</ul>
	</div>

	<div class="rightinfo">
		<table class="tablelist">
			<thead>
				<tr>
					<th><spring:message code="id" text="编号" /></th>
					<th><spring:message code="code" text="代码" /></th>
					<th><spring:message code="name" text="名称" /></th>
					<th><spring:message code="category" text="分类" /></th>
					<th><spring:message code="version" text="版本" /></th>
					<th><spring:message code="description" text="描述" /></th>
					<th><spring:message code="status" text="状态" /></th>
					<th><spring:message code="operating" text="操作" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach varStatus="status" var="processDefinition"
					items="${page.result}">
					<tr>
						<td>${processDefinition.id}</td>
						<td>${processDefinition.key}</td>
						<td>${processDefinition.name}</td>
						<td>${processDefinition.category}</td>
						<td>${processDefinition.version}</td>
						<td>${processDefinition.description}</td>
						<td><c:choose>
								<c:when test="${processDefinition.suspensionState==1}">
									<spring:message code="activation" text="激活" />
								</c:when>
								<c:otherwise>
									<spring:message code="hang" text="挂起" />
								</c:otherwise>
							</c:choose> （<a href="javascript:void(0);"
							name-status="${processDefinition.suspensionState}"
							name-processDefinitionId="${processDefinition.id}"
							class="ope a_post"> <c:choose>
									<c:when test="${processDefinition.suspensionState==1}">
										<spring:message code="hang" text="挂起" />
									</c:when>
									<c:otherwise>
										<spring:message code="activation" text="激活" />
									</c:otherwise>
								</c:choose>
						</a>）</td>
						<td><a
							href="${ctx}/deploy/graphProcessDefinition?processDefinitionId=${processDefinition.id}"
							target="_blank" class="ope"><spring:message code="process"
									text="流程" />
								<spring:message code="diagram" text="图" /></a>&nbsp; <a
							href="${ctx}/deploy/viewXml?processDefinitionId=${processDefinition.id}"
							target="_blank" class="ope"><spring:message code="view"
									text="查看" />XML</a>&nbsp;&nbsp; <a href="#" target="_blank"
							class="ope"><spring:message code="diagram-viewer"
									text="图标查看器" /></a></td>
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
<script type="text/javascript">
$(function(){
	$(".a_post").click(function(){
		var status=$(this).attr('name-status');
		var processDefinitionId=$(this).attr('name-processDefinitionId');
		//挂起
		var ctx=${ctx}/+"deploy/suspendProcessDefinition";
		if(processDefinitionId==null){return;}		
		if(status!=1){
			//恢复
			ctx=${ctx}/+"deploy/activeProcessDefinition";
		}
		$.ajax({
			type:"POST",
			url:ctx,
			data:({
				"processDefinitionId":processDefinitionId,
			}),
			success:function(result){
				window.location.href="listProcessDefinitions.do";
			},
			error:function(){}
		});
	});
	
});
	

</script>
</html>