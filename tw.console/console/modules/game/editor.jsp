<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs" %>

  <div class="pageContent">
    <form method="post" action="game.html?act=save" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
      <input type="hidden" name="id" value="${game.id}" />
      <div class="pageFormContent" layoutH="50">
        <p>
          <label>游戏名称：</label>
          <input name="name" type="text" size="30" class="required" value="${game.name}"/>
        </p>
        <p>
          <label>厂商ID：</label>
          <input name="cpid" type="text" size="30" class="required" value="${game.cpId}"/>
        </p>
        <p>
          <label>游戏序号：</label>
          <input name="seqnum" type="text" size="30" class="required" value="${game.seqNum}"/>
        </p>
        <p>
          <label>请求密钥：</label>
          <input name="secretKey" type="text" size="30" class="required" value="${game.secretKey}"/>
        </p>
        <p>
          <label>通知密钥：</label>
          <input name="notifyKey" type="text" size="30" class="required" value="${game.notifyKey}"/>
        </p>
        <p>
          <label>通知地址：</label>
          <input name="notifyUrl" type="text" size="30" class="required" value="${game.notifyUrl}"/>
        </p>
        <p>
          <label>游戏状态：</label>
          <select name="status">
          	<option value="1" <c:if test="${game.status eq 1}">selected='selected'</c:if>>正常</option>
          	<option value="0" <c:if test="${game.status eq 0}">selected='selected'</c:if>>停止</option>
          </select>
        </p>
      </div>
      <div class="formBar">
        <ul>
          <li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
          <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
        </ul>
      </div>
    </form>
</div>