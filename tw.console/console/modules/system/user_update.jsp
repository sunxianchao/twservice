<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.gamephone.common.type.StatusType"%>
<%@page import="com.gamephone.common.type.YesNoType"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs" %>

<div class="pageContent">
    <form method="post" action="system/users.html?act=update" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
        <p>
                <label>邮    箱：</label>
                <input name="email" type="text" size="30" value="${model.user.email}" readonly="readonly"/>
        </p>
        <p> 
                <label>用 户 名：</label>
                <input name="name" type="text" size="30" value="${model.user.realname}" class="required"/>
        </p>
        <p>
                <label>密    码：</label>
                <input name="password" type="password" size="30" class="alphanumeric" minlength="5" maxlength="20" alt="字母、数字、下划线 5-20位"/>
        </p>
         
        <p>
                <label>是否为管理员：</label>
                <select name="isadmin" class="required combox">
                    <c:set var="types" value="<%=YesNoType.values()%>"/>
                    <c:forEach items="${types}" var="type">
                        <option value="${type.id}" ${model.user.isadmin == type ? "selected" : ""}>${type.name}</option>
                    </c:forEach>
                </select>
            </p>
        <p>
                <label>账户状态：</label>
                <select name="status" class="required combox">
                    <c:set var="statuss" value="<%=StatusType.values()%>"/>
                    <c:forEach items="${statuss}" var="status">
                        <option value="${status.id}" ${model.user.statusType == status ? "selected" : ""}>${status.name}</option>
                    </c:forEach>
                </select>
            </p>
        </div>
        <input name="userId" type="hidden" value="${model.user.id}"/>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
                </li>
            </ul>
        </div>
    </form>
</div>
<script>
$(function(){
	$("#gameId").change(function(){
		var val=$(this).val();
		if(val == 0) return;
		var checkText=$(this).find("option:selected").text();
		var isadd=toAddcheck(val);
		addOrDel(val, checkText, this, isadd);
	});
});

function toAddcheck(gameid){
	var gameids=$("#gameids").val();
	if(gameids=="") return true;
	var idarr=gameids.split(",");
	for(var i=0;i<idarr.length;i++){
		if(idarr[i]==gameid){
			return false;	
		}
	}
	return true;
}

function addOrDel(gameid, gamename, selectObj, isadd){
	var gameids=$("#gameids").val();
	var gamenames=$("#gamenames").val();
	if(isadd){
		gamenames=(gamenames=='') ? gamename : (gamenames+","+gamename);
		gameids=(gameids=='') ? gameid : (gameids+","+gameid);
		$("#gameids").val(gameids);
		$("#gamenames").val(gamenames);
	}else{
		var splitstr="";
		if(gameids.indexOf(",")>0){
			splitstr=",";
		}
		var newids=gameids.replace(gameid+ splitstr, "");
		var newnames=gamenames.replace(gamename+ splitstr, "");
		$("#gameids").val(newids);
		$("#gamenames").val(newnames);
	}
}
</script>