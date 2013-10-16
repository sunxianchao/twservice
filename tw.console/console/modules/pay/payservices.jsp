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
<form id="pagerForm" method="post" action="pay.html?act=services"> 
    <input type="hidden" name="pageNum" value="${result.currentPage}" />
    <input type="hidden" name="gameId" value="${param.gameId}" />
    <input type="hidden" name="pid" value="${param.pid}" />
</form>
<div class="pageHeader">
    <form id="form" onsubmit="return navTabSearch(this);" action="pay.html?act=services" method="post" class="pageForm required-validate">
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
            <td>
              <div style="float:left;margin-top:4px;">充值方式：</div>
                <div style="float:left;"><select id="pid" name="pid">
                  <option value="">--请选择--</option>
                    <c:forEach items="${result.resultList}" var="d">
                        <option value="${d.id}">${d.payName}</option>
                    </c:forEach>
                  </select>
               </div>
            </td>
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
                <li><a class="edit" href="pay.html?act=servicegoadd" width="480" height="440" target="dialog" mask="true" close="closedialog"><span>添加</span></a></li>         
                <li><a class="edit" href="pay.html?act=serviceupdate&id={sid_order}" width="480" height="440" target="dialog" mask="true" close="closedialog"><span>修改</span></a></li>         
            </ul>
        </div>
        <table class="table" width="100%" layoutH="138">
            <thead>
            <tr>
            	<th width="60">编号</th>
            	<th width="60">游戏名称</th>
                <th width="80">支付名称</th>
                <th width="180">服务名称</th>
                <th width="60">服务ID</th>
                <th width="100">服务金额</th>
                <th width="100">序号</th>
                <th width="70">状态</th>
            </tr>
        </thead>
        <tbody>
        
        <c:forEach items="${services.resultList}" var="d" varStatus="var">
            <tr target="sid_order" rel="${d.id}">
              <td>${d.id}</td>
              <td>${d.gameName}</td>
              <td>${d.payName}</td>
              <td>${d.serverName}</td>
              <td>${d.serverId}</td>
              <td>${d.amount}</td>
              <td>${d.order}</td>
              <td <c:if test="${d.status ne 1}">style='color:red;'</c:if>><c:if test="${d.status eq 1}">使用中</c:if><c:if test="${d.status ne 1}">已暂停</c:if></td>
            </tr>
        </c:forEach>
        </tbody>
        </table>
        <div class="panelBar">
        <div class="pagination" targetType="navTab" totalCount="${services.total}" numPerPage="${services.pageSize}" currentPage="${services.currentPage}"></div>
    </div>
</div>
<script>
$("#gameId").val('${param.gameId}');
$("#pid").val('${param.pid}');
</script>