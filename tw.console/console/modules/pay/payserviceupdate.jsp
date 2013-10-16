<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="funcs" uri="funcs" %>
<div class="pageContent" id="orderEditor">
  <form method="post" action="pay.html?act=servicesave" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
    <input type="hidden" value="${payChannel.id}" name="id"/>
    <input type="hidden"  name="payNameVal" id="payNameVal"/>
    <div class="pageFormContent" layoutH="97">
    <p>
        <label>游戏名：</label>
        <select class="combox required" id="gameId" name="gameId">
                <option value="0">--请选择--</option>
                <c:forEach items="${games}" var="game">
                	<option value="${game.id}" <c:if test="${payChannel.gameId eq game.id }">selected='selected'</c:if>>${game.name}</option>
                </c:forEach>
               </select>
      </p>
      <p>
        <label>支付方式：</label>
        <select class="combox required" id="payName" name="payName">
                <option value="0">--请选择--</option>
                <c:forEach items="${result.resultList}" var="r">
                	<option value="${r.id}">${r.payName}</option>
                </c:forEach>
               </select>
      </p>
      <p>
        <label>服务名称：</label>
		<input name="serverName" type="text" size="30" class="required" value="${payChannel.serverName}"/>
      </p>
      <p>
         <label>服务ID：</label>
		<input name="serverId" type="text" size="30" class="required" value="${payChannel.serverId}"/>
      </p>
      <p>
         <label>金额：</label>
		<input name="amount" type="text" size="30" class="required" value="${payChannel.amount}"/>
      </p>
      <c:if test="${param.act eq 'serviceupdate'}">
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
<script>
$("#payName").val('${payChannel.pid}');
$("#payName").change(function(){
	$("#payNameVal").val($(this).find("option:selected").text());
});
</script>