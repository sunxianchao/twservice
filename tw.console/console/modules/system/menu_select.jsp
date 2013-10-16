<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs" %>
<script type="text/javascript">
var setting = {
  data: {
    simpleData: {
      enable: true
    }
  },
  callback: {
	  onClick: selectNode
  }
};

function selectNode(event, treeId, treeNode, clickFlag){
	$("#parentId", $("#menuEditor")).val(treeNode.id);
	$("#parentName", $("#menuEditor")).val(treeNode.name);
	var path=treeNode.getParentIds();
	$("#parentIdPath", $("#menuEditor")).val(path);
	$.pdialog.closeCurrent();
}

var zNodes =[
<c:forEach items="${menus}" var="m" varStatus="s">
  { id:"${m.id}", pId:"${m.parent.id}", name:"${m.name}"<c:if test="${s.first}">, open:true</c:if>}<c:if test="${not s.last}">,</c:if>
</c:forEach>
];
var treeObj;
$(function(){
	treeObj=$.fn.zTree.init($("#menuSelectList"), setting, zNodes);
});
</script>
<div class="pageContent">
<div class="ztree" id="menuSelectList" layoutH="20"></div>
</div>