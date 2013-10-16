<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>xhEditor iframe单文件上传</title>
<link href="<%=basePath%>themes/default/style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>themes/css/core.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>js/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<!--[if IE]>
<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" />
<![endif]-->

<script src="<%=basePath%>js/speedup.js" type="text/javascript"></script>
<script src="<%=basePath%>js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/jquery.cookie.js" type="text/javascript"></script>
<script src="<%=basePath%>js/jquery.validate.js" type="text/javascript"></script>
<script src="<%=basePath%>js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="<%=basePath%>js/xheditor/xheditor-1.1.12-zh-cn.js" type="text/javascript"></script>
<script src="<%=basePath%>js/uploadify/swfobject.js" type="text/javascript"></script>
<script src="<%=basePath%>js/uploadify/jquery.uploadify.v2.1.0.js" type="text/javascript"></script>

<link rel="stylesheet" href="<%=basePath%>css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="<%=basePath%>js/ztree/jquery.ztree.all-3.1.js"></script>

<script src="<%=basePath%>js/dwz.core.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.util.date.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.validate.method.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.barDrag.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.drag.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.tree.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.accordion.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.ui.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.navTab.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.theme.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.switchEnv.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.alertMsg.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.contextmenu.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.navTab.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.tab.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.resize.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.dialog.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.cssTable.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.stable.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.taskBar.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.ajax.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.pagination.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.database.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.datepicker.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.effects.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.panel.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.checkbox.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.history.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.combox.js" type="text/javascript"></script>
<script src="<%=basePath%>js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="<%=basePath%>js/common.js" type="text/javascript"></script>
<style>
body{
    padding:5px;
    margin:0px;
    font-size:12px;
}   
</style>
<script type="text/javascript">
$(function(){
    DWZ.init("../dwz.frag.xml", {
      statusCode:{ok:200, error:300, timeout:301}, //【可选】
      debug:false,  // 调试模式 【true|false】
      callback:function(){
          initUI();
      }
    });
});

function uploadImage(){
	$('#impFileInput').uploadifyUpload();  
}

function uploadifyImpOnComplete(event, queueId, fileObj, response, data){
	  var json = DWZ.jsonEval(response);
	    DWZ.ajaxDone(json);
	    if (json.statusCode == DWZ.statusCode.ok && json.fileList){
	    	callback(json.fileList[0].fileUrl);
	    }else{
	      alert("图片上传失败");
	    }
}
</script>
</head> 
<body> 
<div class="pageFormContent" style="padding:0px">
  <div style="margin:1px">
  <div id="impFileQueue" class="fileQueue" style="height:45px;width:320px;"></div>
  <div class="unit">
    <label>
       <input id="impFileInput" type="file" name="image"  
           uploader="<%=basePath%>js/uploadify/uploadify.swf" 
           cancelImg="<%=basePath%>js/uploadify/cancel.png"  
           buttonImg="<%=basePath%>js/uploadify/selectfiles.png" 
           script="<%=basePath%>${param.uploadUrl};jsessionid=${pageContext.session.id}"  
           scriptData="{act:'upload'}"
           fileQueue="impFileQueue" 
           auto="false" 
           onComplete="uploadifyImpOnComplete"
           multi="false" width="70" height="21"/>
    </label>
    <a class="button" href="javascript:uploadImage();"><span>上传文件</span></a>
  </div>
  </div>
</div>
</body> 
</html>
