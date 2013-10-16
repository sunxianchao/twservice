<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.gamephone.common.type.PayTyper"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="funcs" uri="funcs" %>
<title>游戏充值查询</title>
<style>
.totalAmount{text-align:right;}
</style>
<form id="pagerForm" method="post" action="order.html?act=list"> 
    <input type="hidden" name="pageNum" value="${result.currentPage}" />
    <input type="hidden" name="gameId" value="${param.gameId}" />
    <input type="hidden" name="userId" value="${param.userId}" />
    <input type="hidden" name="paySuccess" value="${param.paySuccess}" />
    <input type="hidden" name="payChannelId" value="${param.payChannelId}" />
    <input type="hidden" name="startDate" value="${param.startDate}" />
    <input type="hidden" name="endDate" value="${param.endDate}" />
    <input type="hidden" name="orderId" value="${param.orderId}" />
</form>
<div class="pageHeader">
    <form id="form" onsubmit="return navTabSearch(this);" action="order.html?act=list" method="post" class="pageForm required-validate">
      <div class="searchBar" id="chargeSearchBar">
      <table class="searchContent">
        <tr>
            <td>
               <div style="float:left;margin-top:4px;">选择游戏：</div>
                <div style="float:left;"><select class="combox required" id="gameId" name="gameId">
                <option value="0">--请选择--</option>
                <c:forEach items="${games}" var="game">
                	<option value="${game.id}">${game.name}</option>
                </c:forEach>
               </select>
               </div>
            </td>
            <td>
               <div style="float:left;margin-top:4px;">支付结果：</div>
                <div style="float:left;"><select id="paySuccess" name="paySuccess">
                  <option value="">--请选择--</option>
                  <option value="1" <c:if test="${param.paySuccess==1}">selected='selected'</c:if>>成功</option>
                  <option value="0" <c:if test="${param.paySuccess==0}">selected='selected'</c:if>>失败</option></select>
               </div>
            </td>
            <td>
              <div style="float:left;margin-top:4px;">充值方式：</div>
                <div style="float:left;"><select id="payChannelId" name="payChannelId">
                  <option value="">--请选择--</option>
                  <c:set var="types" value="<%=PayTyper.values()%>"/>
                    <c:forEach items="${types}" var="type">
                        <option value="${type.id}">${type.name}</option>
                    </c:forEach>
                  </select>
               </div>
            </td>
            <td>
              	用户ID：<input type="text" name="userId" id="userId" value="${param.userId}"/>
            </td>
            </tr>
            <tr>
               <td>开始时间：<input type="text" name="startDate" id="startDate" size="11" value="${param.startDate}" class="date"/></td>
               <td>结束时间：<input type="text" name="endDate" id="endDate" size="11" value="${param.endDate}" class="date"/></td>
               <td> 订  单  号：<input type="text" name="orderId" id="orderId" value="${param.orderId}"/></td>
               <td>充值卡号：<input type="text" name="cardNo" id="cardNo" value="${param.cardNo}"/></td>
               <td>
                 <div class="buttonActive"><div class="buttonContent"><button id="searchBtn" type="submit">检索</button></div></div>
               </td>
            </tr>        
      </table>
    </div>
    <div style="clear:both;"></div>
    </form>
</div>
<div class="pageContent">
		<div class="panelBar">
            <ul class="toolBar">
                <li><a class="edit" href="order.html?act=goupdate&id={sid_order}" width="480" height="640" target="dialog" mask="true" close="closedialog"><span>查看订单详情</span></a></li>         
            </ul>
        </div>
        <table class="table" width="100%" layoutH="138">
            <thead>
            <tr>
            	<th width="60">编号</th>
                <th width="80">订单号</th>
                <th width="60">UID</th>
                <th width="180">游戏名</th>
                <th width="100">支付方式</th>
                <th width="100">充值时间</th>
                <th width="70">充值金额(元)</th>
                <th width="50">充值结果</th>
                <th width="50">通知结果</th>
            </tr>
        </thead>
        <tbody>
        
        <c:forEach items="${result.resultList}" var="d" varStatus="var">
            <tr target="sid_order" rel="${d.id}">
              <td>${d.id}</td>
              <td>${d.orderId}</td>
              <td>${d.userId}</td>
              <td>${d.gameName}</td>
              <td>${d.payType.name}</td>
              <td><fmt:formatDate value="${d.resultDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
              <td><fmt:formatNumber  value="${d.amount/100 + 0.0001}"  pattern="#,###,###,###"/></td>
              <td>${d.resultMsg}</td>
              <td <c:if test="${d.notifyStatus ne 1}">style='color:red'</c:if><c:if test="${d.notifyStatus eq 1}">style='color:green'</c:if>><c:if test="${d.notifyStatus eq 1}">通知成功</c:if>
              	<c:if test="${d.notifyStatus ne 1}">通知失败</c:if>
              </td>
            </tr>
        </c:forEach>
        </tbody>
        </table>
        <div class="panelBar">
          <div class="pages">
            <span style='margin-right:20px;'>充值总金额：<fmt:formatNumber  value="${totalAmount/100 + 0.0001}"  pattern="#,###,###,###"/></span><span>共${result.total}条</span>
          </div>
        <div class="pagination" targetType="navTab" totalCount="${result.total}" numPerPage="${result.pageSize}" currentPage="${result.currentPage}"></div>
    </div>
</div>
<script>
$("#gameId").val('${param.gameId}');
$("#paySuccess").val('${param.paySuccess}');
$("#payChannelId").val('${param.payChannelId}');
</script>