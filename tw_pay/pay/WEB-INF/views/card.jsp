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
<script src="/js/jquery-1.4.4.min.js" type="text/javascript"></script>  
<script>
$(function(){
	$("#submit").click(function(){
		if($("#cardId").val()==''){
			alert("請填寫充值卡卡號");
			return false;
		}

		if($("cardPwd").val()==''){
			alert("請填寫充值卡密碼");
			return false;
		}
	});
});
</script>
</head>
<body >

<header class="header">
    <h1 class="logo"><a name="top" href="" class="cft"><img src="/img/logo.png" width="80" height="28" alt="" /></a></h1>
    <h2 class="title">儲值中心</h2>
</header>

<!-- 公用报错信息块[[ -->
<article class="warn" <c:if test="${not empty errorMsg}">style="display:''"</c:if>>
    <h2 hidden="hidden" class="hidden">報錯信息</h2>
    <div id="err_info" class="desc">${errorMsg}</div>
</article>
<article class="warn" <c:if test="${returnMsgNo!=1}">style="display:''"</c:if>>
    <h2 hidden="hidden" class="hidden">信息提示</h2>
    <div id="err_info" class="desc">${returnMsg}</div>
</article>
 
<!-- 页面主体[[ -->
<div class="wrap ydt-wrap">
<article class="order" id="orderInfo">
    <h2 class="hidden">订单详情</h2>   
    <div class="order-data">
        <dl class="line">
          <dt>遊戲名稱：</dt>
          <dd id="order_desc">智丰儲值[${game.name}]</dd>
        </dl> 
    </div>    
</article>

<!-- 订单信息]] -->
    <!-- 显示的主要内容[[ -->
    <article class="content">
        <header>
            <h2 class="title">MyCard點卡儲值</h2>
        </header>
        <!-- 支付表单[[ -->
        <div class="form">
        <form action="/mycard/ingame/submit" method="post">
            <input type="hidden" name="authCode" value="${authCode}" /> 
		    <input type="hidden" name="orderId" value="${orderId}" /> 
		    <input type="hidden" name="ext_info" value="${ext_info}" /> 
			<div class="line">
                <span class="lab">請輸入卡號</span>
                <input type="text" name="cardId" id="cardId" value="" placeholder="請輸入儲值卡卡號" required>
            </div>

            <div class="line">
                <div class="lab">請輸入密碼</div>
                <div class="pwd-wrap"><input type="password" name="cardPwd" id="cardPwd" placeholder="請輸入儲值卡密碼" required></div>
            </div>
  
            <button id="submit" type="submit" class="btn-yellow">確認送出</button>
        </form>
        
        </div>
        <!-- 支付表单]] -->
    </article>
    <!-- 显示的主要内容]] -->
    <!-- 导航[[ -->
    <nav class="nav">
    <ul>
      <li class=""><a href="/mycard/billing/show?type=2&serverId=${serverId}&cpId=${cpId}&seqNum=${seqNum}&prtchid=${prtchid}&uId=${uId}&orderId=${orderId}&ext_info=${ext_info}" class="nav-tab">線上多管道儲值<span class="arrow-r"></span>
	  <span class="holder-forandroid">&nbsp;.</span></a></li>
      <li class=""><span class="nav-tab on">MyCard點卡儲值<span class="arrow-t"></span></span></li>
    </ul>
</nav>   
    <!-- 导航]] -->
</div>
<!-- 页面主体]] -->

</div>
<footer class="footer">
    <p>智丰數位科技有限公司<span>|</span>客服Line:pgservice</p>
	<p>客服Mail:dragonwar3c@phonegame.com.tw</p>
    <p class="para">&copy;2013手機版儲值中心<span>|</span><a href="#top" id="gotoTop">回到顶部</a></p>
</footer>
<!-- loading[[ -->
<div class="loading"><span class="ico-loading16">正在加載...</span></div>
<!-- loading]] -->
<script type="text/javascript">
(function(){
document.getElementById("gotoTop").addEventListener("click", function(e){
  e.preventDefault();
  window.scrollTo(0,0);
}, false);
})()
setTimeout(scrollTo,0,0,0);
</script>   
</body>
</html>

