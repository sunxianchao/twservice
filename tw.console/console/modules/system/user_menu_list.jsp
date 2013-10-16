<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="funcs" uri="funcs" %>
<script type="text/javascript">
<!--
var setting = {
 check: {
   enable: true,
   chkStyle: "checkbox"
 },
 data: {
   simpleData: {
     enable: true
   }
  }
};
		
var menu_list_node =[
<c:forEach items="${menus}" var="m" varStatus="s">
  <c:set var="ischecked" value="false"/>
  <c:forEach items="${userMenuIds}" var="id">
    <c:if test="${id == m.id}">
    <c:set var="ischecked" value="true"/>
    </c:if>
  </c:forEach>
  {id:"${m.id}", pId:"${m.parent.id}", name:"${m.name}", checked:${ischecked}, open:true}<c:if test="${not s.last}">,</c:if>
  <c:set var="ischecked" value="false"/>
</c:forEach>
];

var treeObj;
$(function(){
  treeObj=$.fn.zTree.init($("#userMenuList"), setting, menu_list_node);
});

function saveUserTreeNode(form){
	  var nodes=treeObj.getCheckedNodes(true);
    if(nodes.length==0){
        return false;
    }
    alertMsg.confirm("确定要提交吗？", {
        okCall: function(){
        	var checkedId="";
          $(nodes).each(function(i,e){
            checkedId+=e.id+",";
          });
          $("#treecheckbox", $(form)).val(checkedId);
          $.post($(form).attr("action"), $(form).serialize(), function(data){
        	  if(data.statusCode == DWZ.statusCode.ok){
        		  $.pdialog.closeCurrent();
        	  }else{
        		  alertMsg.error(data.msg);
        	  }
          }, "json");
        }
    });
    return false;
}
//-->
</script>
<form onsubmit="return saveUserTreeNode(this);" class="pageForm required-validate" action="system/user_menu.html" method="post">
  <input type="hidden" name="act" value="save">
  <input type="hidden" name="userid" class="required" value="${param.userid}">
  <input type="hidden" name="treecheckbox" class="required" id="treecheckbox" value="">
  <div class="pageContent">
    <div id="userMenuList" class="ztree" layoutH="50">
    </div>
    <div class="formBar">
      <ul>
        <li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
        <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
      </ul>
    </div>
  </div>
</form>