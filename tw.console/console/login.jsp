<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <link href="themes/css/login.css" rel="stylesheet" type="text/css" />
  <head>    
    <title>智丰數位管理後臺</title>
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">    
  <meta http-equiv="keywords" content="">
  <script src="js/jquery-1.4.4.min.js" type="text/javascript"></script>
  
<script type="text/javascript">
<!--
$(document).ready(function(){
  var pageContent=$("#login");
  $("#email", pageContent).focus();
})
//-->
</script>
  </head>
  
  <body>
  <div id="login">
    <div id="login_header">
      <h1 class="login_logo">
        <a href="/"><img src="themes/default/images/logo.png" /></a>
      </h1>
      <div class="login_headerContent">
        
        <h1 style="font-size:250%" class="login_title">智丰數位管理後臺</h1>
      </div>
    </div>
    <div id="login_content">
      <div class="loginForm">
        <form action="dologin.html" method="post">
          <p>
            <label>邮箱：</label>
            <input type="text" value="" id="email" name="email" size="20" class="required" />
          </p>
          <p>
            <label>密码：</label>
            <input type="password" value="" name="password" size="20" class="required" />
          </p>
          <p>
            <label style="color:red;">${model.loginErrInfo}</label><br/>
          </p>
          <div class="login_bar">
            <input class="sub" type="submit" value=" " />
          </div>
        </form>
      </div>
      <div class="login_banner"><img src="themes/default/images/login_banner.jpg" /></div>
      <div class="login_main">
        
        <div class="login_inner">
          
        </div>
      </div>
    </div>
    <div id="login_footer">
      Copyright &copy; 2013 智丰數位  All Rights Reserved.
    </div>
  </div>
</body>
</html>
