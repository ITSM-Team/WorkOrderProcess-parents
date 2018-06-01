<%@page language="java" pageEncoding="UTF-8" %>

<!-- page -->
<script src="${cdnPrefix}/jQueryPage/js/jquery-1.8.3.min.js"></script>
<script src="${cdnPrefix}/jQueryPage/js/jquery.page.js"></script>

<script type="text/javascript">
$(function(){
	//分页
	var action=$("#form").attr("action");
	var pageNo="${page.pageNo}";
	$(".tcdPageCode").createPage({
	    pageCount:"${page.pageCount}",
	    current:pageNo,
	    backFn:function(current){
	    	 window.location.href=action+"?pageNo="+current;
	    }
	}); 
})
</script>