<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title></title>
<%@include file="/common/s3.jsp"%>
<script language="javascript">
	$(function(){
    $('.error5').css({'position':'absolute','left':($(window).width()-490)/2});
	$(window).resize(function(){  
    $('.error5').css({'position':'absolute','left':($(window).width()-490)/2});
    })  
});  
</script> 
</head>
<body style="background: #edf6fa;">

	<div class="place">
		<span>位置：</span>
    <ul class="placeul">
    <li><a href="#">500错误提示</a></li>
    </ul>
    </div>
    
    <div class="error5">
    
    <h2>非常遗憾，您访问的页面不存在！</h2>
    <p>看到这个提示，就自认倒霉吧!</p>
    <div class="reindex">
			<a href="${ctx }/common/main.jsp" target="_parent">返回首页</a>
		</div>
    
    </div>

</body>
</html>