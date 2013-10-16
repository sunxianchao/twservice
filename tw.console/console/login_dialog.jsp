<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
<!--
$(document).ready(function(){
  var pageContent=$("#login_dialog");
  $("#email", pageContent).focus();
})
//-->
</script>
<div class="pageContent" id="login_dialog">  
  <form method="post" action="dologin.html" class="pageForm" onsubmit="return validateCallback(this, dialogAjaxDone)">
    <div class="pageFormContent" layoutH="58">
      <div class="unit">
        <label>邮箱：</label>
        <input type="text" name="email" size="30" class="required" id="email"/>
      </div>
      <div class="unit">
        <label>密码：</label>
        <input type="password" name="password" size="30" class="required"/>
      </div>
    </div>
    <div class="formBar">
      <ul>
        <li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
        <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
      </ul>
    </div>
  </form>  
</div>
