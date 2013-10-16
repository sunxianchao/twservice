<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs" %>
<script type="text/javascript">
<!--
var setting = {
  check: {
    enable: true,
    chkStyle: "checkbox",
    chkboxType: {"Y":"s","N":"s"}
  },
  data: {
    simpleData: {
      enable: true
    }
   },
  callback: {
    onCheck: onCheck,
    onClick: onClick
  }
};
var menu_list_node =[
<c:forEach items="${menus}" var="m" varStatus="s">
  {id:"${m.id}", pId:"${m.parent.id}", name:"${m.name}", open:true<c:if test="${s.first}"> ,doCheck:false</c:if>}<c:if test="${not s.last}">,</c:if>
</c:forEach>
];

function onCheck(e, treeId, treeNode) {
  var link=$("#deleteMenuForm");
  var url=link.attr("href")+"&id="+treeNode.id;
  link.attr("href", url);
}

function onClick(e, treeId, treeNode){
	var link=$("#addMenuButton");
	var url=link.attr("href")+"&id="+treeNode.id;
	link.attr("href", url);
	$("#addMenuButton").click();
}

var treeObj;
$(function(){
  treeObj=$.fn.zTree.init($("#menuList"), setting, menu_list_node);
});

function deleteTreeNode(form){
    var nodes=treeObj.getCheckedNodes(true);
    if(nodes.length==0){
        return false;
    }
    alertMsg.confirm("确定要删除吗？", {
        okCall: function(){
          var checkedId="";
          $(nodes).each(function(i,e){
            checkedId+=e.id+",";
          });
          var data={"act":"del", "treecheckbox": checkedId};
          $.post($(form).attr("action"), data, function(json){
             if(json.statusCode == DWZ.statusCode.ok){
               navTabPageBreak({}, json.rel);
              }else{
                alertMsg.error(json.msg);
              }
          }, "json");
        }
    });
    return false;
}
//-->
</script>
<form id="pagerForm" action="system/menu.html" method="get">
  <input type="hidden" name="pageNum" value="1" />
  <input type="hidden" name="act" value="list">
</form>
<form id="deleteMenuForm" onsubmit="return deleteTreeNode(this);" action="system/menu.html" method="post">
  <input type="hidden" name="act" value="del">
  <div class="panel collapse" minH="30">
    <h1>系统菜单管理</h1>
    <div>
      <ul class="rightTools">
        <li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="navTabPageBreak();">查询</button></div></div></li>
        <li><a id="addMenuButton" class="button" target="dialog" rel="menuEditor" href="system/menu.html?act=goedit" mask="true" minable="false" close="closedialog" width="600" height="230"><span>添加</span></a></li>
        <li><div class="buttonActive"><div class="buttonContent"><button type="submit">删除</button></div></div></li>
      </ul>
    </div>
  </div>
<div class="pageContent">
<div id="menuList" class="ztree">
</div>
</div>
</form>