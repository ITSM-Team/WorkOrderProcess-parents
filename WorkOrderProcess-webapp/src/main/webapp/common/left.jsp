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
	<div class="lefttop"><span></span>流程首页</div>

	<dl class="leftmenu">
		<dd>
			<a href="${ctx}/bpm/newProcess.jsp" target="rightFrame">
				<div class="title">
					<span><img src="${cdnPrefix}/template/images/newWorkOrder.png" /></span> 新建工单
				</div>
			</a>
		</dd>
		
		<dd>
			<a href="${ctx}/bpm/draftBox.jsp" target="rightFrame">
				<div class="title">
					<span><img src="${cdnPrefix}/template/images/draftBox.png" /></span> 草稿箱
				</div>
			</a>
		</dd>
		
		<dd>
			<a href="${ctx}/bpm/pending.jsp" target="rightFrame">
				<div class="title">
					<span><img src="${cdnPrefix}/template/images/pending.png" /></span> 待处理
				</div>
			</a>
		</dd>

		<dd>
			<a href="${ctx }/bpm/toBeClaimed.jsp" target="rightFrame">
				<div class="title">
					<span><img src="${cdnPrefix}/template/images/toBeClaimed.png" /></span> 待认领
				</div>
			</a>
		</dd>

		<dd>
			<a href="${ctx }/bpm/unread.jsp" target="rightFrame">
				<div class="title">
					<span><img src="${cdnPrefix}/template/images/read.png" /></span>待阅
				</div>
			</a>
		</dd>

		<dd>
			<div class="title">
				<span><img src="${cdnPrefix}/template/images/workOrder.png" /></span>我的工单
			</div>
			<ul class="menuson">
				<li class="active"><cite></cite><a href="${ctx }/bpm/myWorkOrder.jsp" target="rightFrame">我发起的工单</a><i></i></li>
				<li><cite></cite><a href="${ctx }/bpm/participateWorkOrder.jsp" target="rightFrame">我参与的工单</a><i></i></li>
				<li><cite></cite><a href="${ctx }/bpm/read.jsp" target="rightFrame">已阅的工单</a><i></i></li>
				<li><cite></cite><a href="${ctx }/bpm/myManagementWorkOrder.jsp" target="rightFrame">我管理的工单</a><i></i></li>
				<li><cite></cite><a href="${ctx }/bpm/myAttentionWorkOrder.jsp" target="rightFrame">我关注的工单</a><i></i></li>
			</ul>
		</dd>

		<dd>
			<a href="${ctx }/bpm/workOrderInquiry.jsp" target="rightFrame">
				<div class="title">
					<span><img src="${cdnPrefix}/template/images/query.png" /></span>工单查询
				</div>
			</a>
		</dd>

		<dd>
			<a href="${ctx }/bpm/workOrderEntrust.jsp" target="rightFrame">
				<div class="title">
					<span><img src="${cdnPrefix}/template/images/delegate.png" /></span>工单委托
				</div>
			</a>	
		</dd>


		<dd>
			<a href="${ctx }/bpm/processConfiguration.jsp" target="rightFrame">
				<div class="title">
					<span><img src="${cdnPrefix}/template/images/config.png" /></span>流程配置
				</div>
			</a>
		</dd>

	</dl>
</body>
</html>