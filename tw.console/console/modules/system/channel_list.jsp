<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs" %>
<script type="text/javascript">
 
</script>
<form id="pagerForm" action="system/paychannels.html" method="get">
  <input type="hidden" name="pageNum" value="1" />
  <input type="hidden" name="act" value="list">
</form>
<form id="payChannelForm" onsubmit="return navTabSearch(this);" action="system/paychannels.html" method="post">
  <div class="panel collapse" minH="30">
    <h1>支付方式管理</h1>
  </div>
</form>
<div class="pageContent">
        <table class="table" width="100%" layoutH="138">
            <thead>
            <tr>
                <th width="80">ID</th>
                <th width="120">支付方式</th>
                <th width="120">别名</th>
                <th width="100">状态</th>
                <th width="100">类型</th>
                <th width="100">操作</th>
            </tr>
        </thead>
        <tbody>
        
        <c:forEach items="${channels}" var="channel" varStatus="var">
            <tr>
                <td>${channel.id}</td>
                <td>${channel.name}</td>
                <td>${channel.alias}</td>
                <td>${channel.statusType.name} </td>
                <td><c:if test="${channel.type==0}">手机支付</c:if>
		                <c:if test="${channel.type==1}">web支付</c:if>
		                <c:if test="${channel.type==2}">通用</c:if></td>
                <td>
		              <c:if test="${channel.statusType.id eq 1}"><a href="system/paychannels.html?act=update&id=${channel.id}&status=0" target="ajaxTodo" title="确定要停止吗?">停止</a></c:if>
		              <c:if test="${channel.statusType.id eq 0}"><a href="system/paychannels.html?act=update&id=${channel.id}&status=1" target="ajaxTodo">恢复</a></c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
        </table>
</div>