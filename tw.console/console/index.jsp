<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>智丰數位管理後臺</title>

<link href="themes/default/style.css" rel="stylesheet" type="text/css" />
<link href="themes/css/core.css" rel="stylesheet" type="text/css" />
<link href="js/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<!--[if IE]>
<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" />
<![endif]-->

<script src="js/speedup.js" type="text/javascript"></script>
<script src="js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script src="js/jquery.cookie.js" type="text/javascript"></script>
<script src="js/jquery.validate.js" type="text/javascript"></script>
<script src="js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="js/xheditor/xheditor-1.1.12-zh-cn.js" type="text/javascript"></script>
<script src="js/uploadify/swfobject.js" type="text/javascript"></script>
<script src="js/uploadify/jquery.uploadify.v2.1.0.js" type="text/javascript"></script>

<link rel="stylesheet" href="css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="js/ztree/jquery.ztree.all-3.1.js"></script>

<script src="js/dwz.core.js" type="text/javascript"></script>
<script src="js/dwz.util.date.js" type="text/javascript"></script>
<script src="js/dwz.validate.method.js" type="text/javascript"></script>
<script src="js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="js/dwz.barDrag.js" type="text/javascript"></script>
<script src="js/dwz.drag.js" type="text/javascript"></script>
<script src="js/dwz.tree.js" type="text/javascript"></script>
<script src="js/dwz.accordion.js" type="text/javascript"></script>
<script src="js/dwz.ui.js" type="text/javascript"></script>
<script src="js/dwz.navTab.js" type="text/javascript"></script>
<script src="js/dwz.theme.js" type="text/javascript"></script>
<script src="js/dwz.switchEnv.js" type="text/javascript"></script>
<script src="js/dwz.alertMsg.js" type="text/javascript"></script>
<script src="js/dwz.contextmenu.js" type="text/javascript"></script>
<script src="js/dwz.navTab.js" type="text/javascript"></script>
<script src="js/dwz.tab.js" type="text/javascript"></script>
<script src="js/dwz.resize.js" type="text/javascript"></script>
<script src="js/dwz.dialog.js" type="text/javascript"></script>
<script src="js/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="js/dwz.cssTable.js" type="text/javascript"></script>
<script src="js/dwz.stable.js" type="text/javascript"></script>
<script src="js/dwz.taskBar.js" type="text/javascript"></script>
<script src="js/dwz.ajax.js" type="text/javascript"></script>
<script src="js/dwz.pagination.js" type="text/javascript"></script>
<script src="js/dwz.database.js" type="text/javascript"></script>
<script src="js/dwz.datepicker.js" type="text/javascript"></script>
<script src="js/dwz.effects.js" type="text/javascript"></script>
<script src="js/dwz.panel.js" type="text/javascript"></script>
<script src="js/dwz.checkbox.js" type="text/javascript"></script>
<script src="js/dwz.history.js" type="text/javascript"></script>
<script src="js/dwz.combox.js" type="text/javascript"></script>
<!--
<script src="bin/dwz.min.js" type="text/javascript"></script>
-->
<script src="js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="js/common.js" type="text/javascript"></script>

<script type="text/javascript">
$(function(){
  DWZ.init("dwz.frag.xml", {
    loginUrl:"login_dialog.html", loginTitle:"登录",  // 弹出登录对话框
    statusCode:{ok:200, error:300, timeout:301}, //【可选】
    pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
    debug:false,  // 调试模式 【true|false】
    callback:function(){
      initEnv();
      $("#themeList").theme({themeBase:"themes"}); // themeBase 相对于index页面的主题base路径
      var setting = {
    		  data: {
    		    simpleData: {
    		      enable: true
    		    }
    		  }
    	};
      $.fn.zTree.init($("#treeDemo"), setting, zNodes);
      var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
 	  treeObj.expandAll(true);
    }
  });
});
</script>
<script type="text/javascript">
 
function openTab(obj, url){
	 navTab.openTab($(obj).attr("target"), url,{title:$(obj).text(), fresh:true});
}
function expandNode(obj){
	 var id=$(obj).attr("id");
	 var btId=id.substring(0, id.lastIndexOf("_")+1) +"switch";
	 $("#"+btId).click();
}
var zNodes =[
  <c:forEach items="${menus}" var="m" varStatus="s">
  {<c:if test="${empty m.url}">click:"expandNode(this);", </c:if><c:if test="${not empty m.url}">click:"openTab(this, '${m.url}');", target:"navTab${m.id}", </c:if>id:"${m.id}", pId:"${m.parent.id}", name:"${m.name}"}<c:if test="${not s.last}">,</c:if>
  </c:forEach>
];
</script>
</head>

<body scroll="no">
  <div id="layout">
    <div id="header">
      <div class="headerNav">
        <span class="logo" href="/">智丰數位</span>
        <ul class="nav">
          <li><a href="#">(${funcs:getSessionUser(pageContext.session).realname})</a></li>
          <li><a href="logout.html">退出</a></li>
        </ul>
        <ul class="themeList" id="themeList">
          <li theme="default"><div class="selected">蓝色</div></li>
          <li theme="green"><div>绿色</div></li>
          <li theme="purple"><div>紫色</div></li>
          <li theme="silver"><div>银色</div></li>
          <li theme="azure"><div>天蓝</div></li>
        </ul>
      </div>
      <!-- navMenu -->
    </div>

    <div id="leftside">
      <div id="sidebar_s"><div class="collapse"><div class="toggleCollapse"><div></div></div>
        </div>
      </div>
      <div id="sidebar">
        <div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>
        <div class="accordion" fillSpace="sidebar">
          <div class="accordionContent ztree" id="treeDemo"></div>
      </div>
    </div>
  </div>
  <div id="container">
      <div id="navTab" class="tabsPage">
        <div class="tabsPageHeader">
          <div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
            <ul class="navTab-tab">
              <li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">首页</span></span></a></li>
            </ul>
          </div>
          <div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
          <div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
          <div class="tabsMore">more</div>
        </div>
        <div class="navTab-panel tabsPageContent layoutBox">
          <div class="page unitBox">            
            <div class="pageFormContent" layoutH="80" style="margin-right:230px"></div>
          </div>          
        </div>
      </div>
    </div>
  </div>
  <div id="footer">Copyright &copy; 2013 智丰數位</div>
</body>
</html>
