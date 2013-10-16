<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="funcs" uri="funcs" %>
<div class="pageContent" id="orderEditor">
  <form method="post" action="pay.html?act=save" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
    <input type="hidden" value="${payChannel.id}" name="id"/>
    <div class="pageFormContent" layoutH="97">
      <p>
        <label>支付方式：</label>
        <input name="payName" type="text" size="30" class="required" value="${payChannel.payName}"/>
      </p>
      <p>
        <label>是否本地：</label>
		<input name="isLocal" type="radio" value="1" <c:if test="${payChannel.isLocal eq 1}">checked='checked'</c:if>/> 是
		<input name="isLocal" type="radio" value="0" <c:if test="${payChannel.isLocal eq 0}">checked='checked'</c:if>/> 否
      </p>
      <c:if test="${param.act eq 'goupdate'}">
      <p>
        <label>序号：</label>
         <input name="order" type="text" size="30" class="required" value="${payChannel.order}"/>
      </p>
      <p>
        <label>状态：</label>
        <input name="status" type="radio" value="1" <c:if test="${payChannel.status eq 1}">checked='checked'</c:if>/> 可用
		<input name="status" type="radio" value="0" <c:if test="${payChannel.status eq 0}">checked='checked'</c:if>/> 禁用
      </p>
      </c:if>
    </div>
    <div class="formBar">
      <ul>
        <li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
        <li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
      </ul>
    </div>
  </form>
</div>