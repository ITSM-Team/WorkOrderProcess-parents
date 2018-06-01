<%@page language="java" pageEncoding="UTF-8" %>
    <!--[if lt IE 9]>
    <script type="text/javascript">
	//alert('您使用的浏览器版本太低，请使用IE9+，或者FireFox，Chrome浏览。');
	</script>
    <![endif]-->


    <!-- html5 -->
    <!--[if lt IE 9]>
	<script src="${cdnPrefix}/respond/1.4.2/respond.min.js"></script>
    <script type="text/javascript" src="${cdnPrefix}/html5/html5shiv.js"></script>
    <![endif]-->

    <!-- 模板样式 -->
	<link type="text/css" rel="stylesheet" href="${cdnPrefix}/template/css/select.css" />
	<link type="text/css" rel="stylesheet" href="${cdnPrefix}/template/css/style.css" />
	<script type="text/javascript" src="${cdnPrefix}/template/js/jquery.js"></script>
	<%-- <script type="text/javascript" src="${cdnPrefix}/template/js/cloud.js"></script> --%>
	<script type="text/javascript" src="${cdnPrefix}/template/js/jquery.ba-resize.min.js"></script>
	<script type="text/javascript" src="${cdnPrefix}/template/js/jquery.gvChart-1.0.1.min.js"></script>
	<script type="text/javascript" src="${cdnPrefix}/template/js/jquery.idTabs.min.js"></script>
	<script type="text/javascript" src="${cdnPrefix}/template/js/jsapi.js"></script>
	<script type="text/javascript" src="${cdnPrefix}/template/js/format+zh_CN,default,corechart.I.js"></script>
	<script type="text/javascript" src="${cdnPrefix}/template/js/select-ui.min.js"></script>
	
	<!--消息提示  -->
	<link type="text/css" rel="stylesheet" href="${cdnPrefix}/DialogJS/style/dialog.css" />
	<script type="text/javascript" src="${cdnPrefix}/DialogJS/javascript/zepto.min.js"></script>
	<script type="text/javascript" src="${cdnPrefix}/DialogJS/javascript/dialog.min.js"></script>	
	    
<script type="text/javascript">
	$(function(){
		//全选、全取消
		$("#selectAll").click(function(){
			if($(this).is(":checked")){
				$("input[name='checkbox']").attr("checked","true"); 
			}else{
				$("input[name='checkbox']").removeAttr("checked"); 
			}
		});
		
		//限制修改只能选择一条数据
		var href=$(".update a").attr("href");
		$(".update").click(function(){
			 if($("input[name='checkbox']:checked").length !=1){
				$(".update a").attr("href","javascript:void(0)");
			}else{
				$(".update a").attr("href",href);
			} 
		});
	})
</script>
	
	