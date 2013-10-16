<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.gamephone.common.type.PayTyper"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="funcs" uri="funcs" %>
<h2 class="contentTitle">订单编号：${order.id}<span style="margin-left:250px;">${order.resultMsg}</span></h2>
<div class="pageContent" id="orderEditor">
  <form method="post" action="order.html?act=save" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
    <div class="pageFormContent" layoutH="97">
      <p>
        <label>游戏名：</label><label>${order.gameName}</label>
      </p>
      <p>
        <label>用户Id：</label><label>${order.userId }</label>
      </p>
      <p>
        <label>充值服务器ID：</label><label>${order.gameServerId }</label>      
      </p>
      <p>
        <label>充值金额：</label>
        <label><fmt:formatNumber  value="${order.amount/100 + 0.0001}"  pattern="#,###,###,###"/> </label>
      </p>
      <p>
        <label>充值方式：</label>
        <label>${order.payType.name}</label>
      </p>
      <p>
        <label>订单号：</label><label>${order.orderId }  </label>
      </p>
      <c:if test="${order.payType.id eq 1}">
      	<p><label>卡号：</label><label>${order.cardNo }</label></p>
      	<p><label>卡密：</label><label>${order.cardPwd }</label></p>
      </c:if>
      <c:if test="${order.payType.id eq 2}">
      	<p><label>支付代码：</label><label>${order.cardNo }</label></p>
      </c:if>
      <c:if test="${order.payType.id eq 3}">
      	<p><label>google产品ID：</label><label>${order.productId }</label></p>
      </c:if>
      <p>
        <label>交易号：</label><label>${order.tradeNo } </label> 
      </p>
      <p>
        <label>订单创建时间：</label><label><fmt:formatDate value="${order.createdDate}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
      </p>
      <p>
        <label>订单状态：</label><label><c:if test="${order.resultCode eq 1}">支付完成</c:if><c:if test="${order.resultCode ne 1}">未完成支付</c:if></label>
      </p>
      <p>
        <label>订单完成时间：</label><label><fmt:formatDate value="${order.resultDate}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
      </p>
      <p>
        <label>游戏发放游戏币结果：</label><label><c:if test="${order.notifyStatus eq 1}">通知成功</c:if><c:if test="${order.notifyStatus ne 1}">通知失败</c:if></label>
      </p>
      <p>
        <label>通知游戏时间：</label><label><fmt:formatDate value="${order.notifyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
      </p>
    </div>
    <div class="formBar">
      <ul>
        <li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
      </ul>
    </div>
  </form>
</div>