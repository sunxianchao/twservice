<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="funcs" uri="funcs" %>
<title>mycard支付方式管理</title>
<style>
.totalAmount{text-align:right;}
</style>
<div class="pageContent">
		<div class="panelBar">
            <ul class="toolBar">
                <li><a class="add" href="pay.html?act=goadd" width="400" height="400" target="dialog" mask="true" close="closedialog"><span>添加</span></a></li>
                <li><a class="edit" href="pay.html?act=goupdate&id={sid_pay}" width="400" height="400" target="dialog" mask="true" close="closedialog"><span>修改</span></a></li>         
            </ul>
        </div>
        <table class="table" width="100%" layoutH="138">
            <thead>
            <tr>
            	<th width="60">编号</th>
                <th width="80">支付方式</th>
                <th width="60">是否本地</th>
                <th width="60">序号</th>
                <th width="180">状态</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${result.resultList}" var="d" varStatus="var">
            <tr target="sid_pay" rel="${d.id}">
              <td>${d.id}</td>
              <td>${d.payName}</td>
              <td><c:if test="${d.isLocal eq 1}">是</c:if><c:if test="${d.isLocal ne 1}">否</c:if></td>
              <td>${d.order}</td>
              <td <c:if test="${d.status ne 1}">style='color:red'</c:if>><c:if test="${d.status eq 1}">使用中</c:if><c:if test="${d.status ne 1}">已暂停</c:if></td>
            </tr>
        </c:forEach>
        </tbody>
        </table>
    </div>
</div>