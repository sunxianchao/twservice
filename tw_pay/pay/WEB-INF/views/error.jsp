<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>智丰數位儲值中心-手机版</title>
<meta charset="utf-8">
<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0;" name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta content="email=no" name="format-detection">   
<link href="/css/global.css" rel="stylesheet"> 
</head>
<body >

<header class="header">
    <h1 class="logo"><a name="top" href="" class="cft"><img src="/img/logo.png" width="80" height="28" alt="" /></a></h1>
    <h2 class="title">儲值中心</h2>
</header>
<div class="wrap mpay-wrap">
    <!-- 失败提示[[ -->
    <article>
        <div class="feedback">
          <div class="main-tips">
            <c:if test="${not empty errorMsg}">
	            <span class="feedback-ico ico-warn"><!--icon--></span><!--成功ico-ok；红色叉ico-err；叹号ico-warn；infomation符号ico-info-->
	            <div class="desc">${errorMsg}</div>
            </c:if>
            
          </div>          
        </div>
    </article>
    <!-- 失败提示]] -->
</div>


</div>
<footer class="footer">
    <p>智丰數位科技有限公司<span>|</span>客服Line:pgservice</p>
	<p>客服Mail:dragonwar3c@phonegame.com.tw</p>
    <p class="para">&copy;2013手機版儲值中心<span>|</span><a href="#top" id="gotoTop">回到顶部</a></p>
</footer>
</body>
</html>

