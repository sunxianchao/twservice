<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs" %>
<form id="pagerForm" method="post" action="system/users.html?act=list"> 
    <input type="hidden" name="pageNum" value="${userListPager.currentPage}" />
    <input type="hidden" name="numPerPage" value="${userListPager.pageSize}" />
    <input type="hidden" name="id" value="${param.id}" />
    <input type="hidden" name="realname" value="${param.realname}" />
    <input type="hidden" name="email" value="${param.email}" />
</form>
<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="system/users.html?act=list" method="post" class="pageForm required-validate">
      <div class="searchBar">
      <table class="searchContent">
        <tr>
            <td>
                 ID：<input type="text" name="id" class="digits" value="${param.id}"/> 
                                             实名：<input type="text" name="realname" value="${param.realname}"/> 
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
        <div class="panelBar">
            <ul class="toolBar">
                <li><a class="add" href="system/users.html?act=goadd" width="580" height="340" target="dialog" mask="true" close="closedialog"><span>添加</span></a></li>
                <li><a class="delete" href="system/users.html?act=del&id={sid_user}" target="ajaxTodo" title="确定要关闭该用户吗?"><span>关闭</span></a></li>
                <li><a class="edit" href="system/users.html?act=goupdate&id={sid_user}" width="580" height="340" target="dialog" mask="true" close="closedialog"><span>修改</span></a></li>         
            </ul>
        </div>
        
        <table class="table" width="100%" layoutH="138">
            <thead>
            <tr>
                <th width="80">ID</th>
                <th width="120">登录邮箱</th>
                <th width="120">实名</th>
                <th width="100">管理员</th>
                <th width="100">状态</th>
                <th width="100">所属游戏</th>
                <th width="100">操作</th>
            </tr>
        </thead>
        <tbody>
        
        <c:forEach items="${userListPager.resultList}" var="userTO" varStatus="var">
            <tr target="sid_user" rel="${userTO.id}">
                <td>${userTO.id}</td>
                <td>${userTO.email}</td>
                <td>${userTO.realname}</td>
                <td>${userTO.isadmin.name} </td>
                <td>${userTO.statusType.name} </td>
                <td>${userTO.gameids}</td>
                <td>
                <a href="system/user_menu.html?act=list&userid=${userTO.id}" target="dialog" mask="true" minable="false" width="300" height="700"><span>权限菜单</span></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
        </table>
        <div class="panelBar">
          <div class="pages">
            <span>共${userListPager.total}条</span>
          </div>
        <div class="pagination" targetType="navTab" totalCount="${userListPager.total}" numPerPage="${userListPager.pageSize}" currentPage="${userListPager.currentPage}"></div>

    </div>
</div>

