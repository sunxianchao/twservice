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
		var paymentId=$("#paychannelId").val();
		var serviceId=$("#inputServiceId").val();
		if(paymentId=="" || serviceId==""){
			alert("請選擇金流");
			return false;
		}
	});
	
	$("#paymentList, #paymentList2").change(function(){
		$(".loading").show();
		var paymentId=$(this).val();
		if(paymentId==""){
			alert("請選擇金流");
			return false;
		}
		var data=$(this).attr("data-value");
		var serviceObj;
		if(data==0){
			serviceObj=$("#serviceId");
		}else{
			serviceObj=$("#serviceId2");
		}
		$(serviceObj).empty();
		$.ajax({
			  type: "GET",
			  url: "/mycard/billing/productlist",
			  data: "paymentId="+paymentId+"&gameId=${game.id}",
			  success: function(data){
		   		  $(".loading").hide();
				  $(data).each(function(i, e){
					  if(i==0){
						  $(serviceObj).append("<option value=''>請選擇...</option>");
					  }
					  $(serviceObj).append("<option value='"+e.serverId+"' am='"+e.amount+"'>"+e.serverName+"</option>"); 
				  });
			  }
		});
	});
	
	$("#serviceId, #serviceId2").change(function(){
		$("#amount").val($(this).find("option:selected").attr("am"));
		$("#Mycard_Serviceid").val($(this).find("option:selected").val());
	});

	$("#select-tab a").click(function(){
		var data=$(this).attr("data-value");
		if(data==1){
			$("#js_id_tab_list").removeClass("show-deposit").addClass("show-credit");
			//$("#js_id_credit").html($("#js_id_deposit").html());
		}else{
			$("#js_id_tab_list").removeClass("show-credit").addClass("show-deposit");
			//$("#js_id_deposit").html($("#js_id_credit").html());
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
<article class="warn" <c:if test="${empty errorMsg}">style="display:'none'"</c:if>>
    <h2 hidden="hidden" class="hidden">報錯信息</h2>
    <div id="err_info" class="desc">${errorMsg}</div>
</article>
 

<!-- 页面主体[[ -->
<div class="wrap fast-wrap">
<article class="order" id="orderInfo">
    <h2 class="hidden">訂單詳情</h2>   
    <div class="order-data">
        <dl class="line">
          <dt>遊戲名稱：</dt>
          <dd id="order_desc">智丰儲值[${game.name}]</dd>
        </dl>
    </div>    
      
</article>
    <!-- 显示的主要内容[[ -->
    <article class="content">
		<header>
            <h2 class="title">線上多管道儲值</h2>
        </header>
        <!-- 银行列表[[ -->
		<section>
			<div class="bank-select show-deposit" id="js_id_tab_list">
				<div class="bank-select-tab" id="select-tab">
					<a href="#" class="deposit js-cls-tab" data-value="0">台灣本地儲值</a>
					<a href="#" class="credit js-cls-tab" data-value="1">海外線上儲值</a>
					<span class="current-flag"><!--当前状态标识--></span>
				</div>
				<form action="/mycard/billing/author" method="post">
				<input type="hidden" value="${serverSeqNum}" name="serverId"/>
				<input type="hidden" value="${seqNum}" name="seqNum"/>
				<input type="hidden" value="${cpId}" name="cpId"/>
				<input type="hidden" value="${uId}" name="uId"/>
				<input type="hidden" value="${type}" name="type"/>
				<input type="hidden" value="${ext_info}" name="ext_info"/>
				<input type="hidden" value="${orderId}" name="orderId"/>
				<input type="hidden" name="amount" id="amount"/>
				<input type="hidden" name="Mycard_Serviceid" id="Mycard_Serviceid"/>
				<div class="bank-select-cnt" id='js_id_container'>
					<ul class="deposit" id='js_id_deposit'>
                   	<p>請選擇儲值方式:</p>	
	  				<div class="select-blue" style="margin-bottom:20px;">
						<select name="paymentList" id="paymentList" data-value="0">
							<option value="">請選擇...</option>
							<c:forEach items="${payments}" var="payment">
								<c:if test="${payment.value.isLocal eq 1}">
									<option value="${payment.key}">${payment.value.payName}</option>
								</c:if>
							</c:forEach>
						</select>
                    </div>
					<p>請選擇儲值金額:</p>	
					<div class="select-blue">
                       <select name="serviceId" id="serviceId">
							<option  value="">請選擇...</option>
						</select>
                    </div>
					</ul>
					<ul class="credit" id='js_id_credit'>
					<p>請選擇儲值方式:</p>	
	  				<div class="select-blue" style="margin-bottom:20px;">
						<select name="paymentList2" id="paymentList2" data-value="1">
							<option value="">請選擇...</option>
							<c:forEach items="${payments}" var="payment">
								<c:if test="${payment.value.isLocal eq 0}">
									<option value="${payment.key}">${payment.value.payName}</option>
								</c:if>
							</c:forEach>
						</select>
                    </div>
					<p>請選擇儲值金額:</p>	
					<div class="select-blue">
                       <select name="serviceId2" id="serviceId2">
							<option  value="">請選擇...</option>
						</select>
                    </div>			
					</ul>
				</div>
				<div style="margin-top:20px"><button id="submit" type="submit" class="btn-yellow">確認送出</button></div>
				</form>       
			</div>
		</section>
        <!-- 银行列表]] -->
    </article>
    <!-- 显示的主要内容]] -->

    <!-- 导航[[ -->
    <nav class="nav">
    <ul>
      <li class=""><span class="nav-tab on">線上多管道儲值<span class="arrow-t"></span></span></li>
      <li class=""><a href="/mycard/ingame/author?type=1&serverId=${serverSeqNum}&cpId=${cpId}&seqNum=${seqNum}&prtchid=${prtchid}&uId=${uId}&orderId=${orderId}&ext_info=${ext_info}" class="nav-tab">MyCard點卡儲值<span class="arrow-r"></span>
	  <span class="holder-forandroid">&nbsp;.</span></a></li>
    </ul>
</nav>
    <!-- 导航]] -->
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

<!-- 关联账户提示层[[ -->
<!-- 介绍弹出层[[ -->
<div class="layer layer-qa">
    <article class="layer-wrap">
        <header class="layer-header">
            <h3 class="layer-header-title">title</h3>
        </header>
        <div class="layer-cnt">
          contents
        </div>
        <div class="layer-btnline">
            <span class="btn-trans"><button type="button" class="btn-trans-txt js-hidden-qa">确认</button></span>
        </div>
    </article> 
</div>
<!-- 介绍弹出层]] -->   
</body>
</html>