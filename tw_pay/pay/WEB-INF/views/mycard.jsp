<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/taglib.jsp"%>
<html>
<head>
<title>mycard支付</title>
<script src="/js/jquery-1.4.4.min.js" type="text/javascript"></script>
</head>
<body>
<c:set var="params" value="cpId=${cpId}&seqNum=${seqNum}&serverId=${serverId}&uId=${uId}"/>
	<p><a href="/mycard/ingame/author?${params}&type=1">ingame</a></p>
	<p><a href="/mycard/billing/show?${params}&type=2">billing</a></p>
</body>

</html>
