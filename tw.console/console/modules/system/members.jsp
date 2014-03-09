<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="funcs" uri="funcs" %>
<form id="pagerForm" method="post" action="system/users.html?act=member"> 
    <input type="hidden" name="pageNum" value="${userListPager.currentPage}" />
    <input type="hidden" name="numPerPage" value="${userListPager.pageSize}" />
    <input type="hidden" name="id" value="${param.id}" />
    <input type="hidden" name="username" value="${param.username}" />
    <input type="hidden" name="email" value="${param.email}" />
    <input type="hidden" name="userType" value="${param.userType}" />
</form>
<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="system/users.html?act=member" method="post" class="pageForm required-validate">
      <div class="searchBar">
      <table class="searchContent">
        <tr>
            <td>
                 ID：<input type="text" name="id" class="digits" value="${param.id}"/> 
                 用户名：<input type="text" name="username" value="${param.userame}"/> 
                 登录邮箱：<input type="text" name="email" value="${param.email}"/>
            </td>
        </tr>        
      </table>
        <div class="subBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
            </ul>
        </div>
		</div>
    </form>
</div>

<div class="pageContent">
        <table class="table" width="100%" layoutH="138">
            <thead>
            <tr>
                <th width="80">ID</th>
                <th width="120">用户名</th>
                <th width="120">登录邮箱</th>
                <th width="100">第三方用户Id</th>
                <th width="100">用户类型</th>
                <th width="100">注册时间</th>
            </tr>
        </thead>
        <tbody>
        
        <c:forEach items="${members.resultList}" var="userTO" varStatus="var">
            <tr target="sid_user" rel="${userTO.id}">
                <td>${userTO.id}</td>
                <td>${userTO.userName}</td>
                <td>${userTO.email}</td>
                <td>${userTO.tid} </td>
                <td><c:if test="${userTO.userType ==0 }">自有用户</c:if>
                <c:if test="${userTO.userType ==1 }">Facebook</c:if>
                <c:if test="${userTO.userType ==2 }">Google</c:if></td>
                <td><fmt:formatDate value="${userTO.signUpDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            </tr>
        </c:forEach>
        </tbody>
        </table>
        <div class="panelBar">
          <div class="pages">
            <span>共${members.total}条</span>
          </div>
        <div class="pagination" targetType="navTab" totalCount="${members.total}" numPerPage="${members.pageSize}" currentPage="${members.currentPage}"></div>

    </div>
</div>

