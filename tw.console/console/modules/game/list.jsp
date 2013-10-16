<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs" %>
  <form id="pagerForm" method="post" action="game.html?act=list"> 
    <input type="hidden" name="pageNum" value="${games.currentPage}" />
    <input type="hidden" name="numPerPage" value="${games.pageSize}" />
    <input type="hidden" name="id" value="${param.id}" />
    <input type="hidden" name="name" value="${param.name}" />
    <input type="hidden" name="status" value="${param.status}" />
  </form>
  
  <div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="game.html?act=list" method="post" class="pageForm required-validate">
      <div class="searchBar">
      <table class="searchContent">
        <tr>
            <td>ID：<input type="text" name="id" class="digits" value="${param.id}"/></td>
            <td>游戏名称：<input type="text" name="name" value="${param.name}"/></td>
            <td><select name="status" class="combox"><option value="">状态</option><option value="1" <c:if test="${param.status eq 1}">selected</c:if>>有效</option><option value="0" <c:if test="${param.status eq 0}">selected</c:if>>无效</option></select></td>
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
        <li><a class="add" href="game.html?act=goedit" target="dialog" mask="true" close="closedialog" height="500" width="450"><span>添加</span></a></li>
        <li><a class="edit" href="game.html?act=goedit&id={sid_game}" target="dialog" mask="true" close="closedialog" height="500"  width="450"><span>修改</span></a></li>
      </ul>
    </div>
    <table class="table" width="100%" layoutH="138">
      <thead>
        <tr>
          <th width="60">ID</th>
          <th width="80">游戏名称</th>
          <th width="60">游戏序号</th>
          <th width="60">厂商ID</th>
          <th width="140">创建时间</th>
          <th width="60">游戏状态</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach items="${games.resultList}" var="game" varStatus="var">
          <tr target="sid_game" rel="${game.id}">
            <td>${game.id}</td>
            <td>${game.name}</td>
            <td>${game.seqNum}</td>
            <td>${game.cpId}</td>
            <td>${funcs:formatDateTime(game.createdDate, 'yyyy-MM-dd HH:mm:ss')}</td>
            <td>
              <c:if test="${game.status eq 1}"><a href="game.html?act=change&id=${game.id}&status=0&pageNum=${games.currentPage}" target="ajaxTodo" title="确定要停止吗?">点击停止</a></c:if>
              <c:if test="${game.status eq 0}"><a href="game.html?act=change&id=${game.id}&status=1&pageNum=${games.currentPage}" target="ajaxTodo">恢复</a></c:if>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    <div class="panelBar">
      <div class="pages"><span>共${games.total}条</span></div>
      <div class="pagination" targetType="navTab" totalCount="${games.total}" numPerPage="${games.pageSize}" currentPage="${games.currentPage}"></div>
    </div>
</div>

