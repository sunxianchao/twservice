<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>mycard查询</title>
</head>
<body>
<c:if test="${not empty errorMsg}">
${errorMsg}
</c:if>
<c:if test="${not empty orders}">
<c:forEach items="${orders}" var="order">
	${order.cardNo},${order.userId},${order.cardNo},${order.orderId},<fmt:formatDate value="${order.resultDate}" pattern="yyyy-MM-dd HH:mm:ss"/><BR>
</c:forEach>
</c:if>
</body>
</html>

