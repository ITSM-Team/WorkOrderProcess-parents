<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title>工单管理系统</title>
<%@include file="/common/s3.jsp"%>
</head>
<frameset rows="88,*" cols="*" frameborder="no" border="0" framespacing="0">
	<frame src="settings_top.jsp" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
	<frameset cols="187,*" frameborder="no" border="0" framespacing="0">
		<frame src="settings_left.jsp" name="leftFrame" scrolling="No" noresize="noresize" id="leftFrame" title="leftFrame" />
		<frame src="${ctx}/modeler/modeler-list.do" name="rightFrame" id="rightFrame" title="rightFrame" />
	</frameset>
</frameset>
<noframes>
	<body>
	</body>
</noframes>
</html>