/**
 * $Id: ErrorCode.java,v 1.30 2012/05/08 08:04:02 jiayu.qiu Exp $
 */
package com.gamephone.common;

import com.gamephone.common.to.ErrorCodeTO;


/**
 * 错误代码,使用4数字来定义错误代码
 */
public interface ErrorCode {

    ErrorCodeTO UNKOWN_ERROR=new ErrorCodeTO("0000", "未知错误");

    ErrorCodeTO NO_CPID_ERROR=new ErrorCodeTO("1000", "没有厂商编号");

    ErrorCodeTO NO_SIGN_ERROR=new ErrorCodeTO("1001", "没有签名参数");

    ErrorCodeTO SIGN_ERROR=new ErrorCodeTO("1002", "签名验证失败");

    ErrorCodeTO EXIST_USER_NAME=new ErrorCodeTO("1003", "您输入的用户名已被注册！");

    ErrorCodeTO EXIST_EMAIL=new ErrorCodeTO("1004", "您输入邮箱已被注册！");

    ErrorCodeTO EXIST_MOBILE_NUMBER=new ErrorCodeTO("1005", "您输入手机号已被注册！");

    ErrorCodeTO ERROR_CODE_INPUT=new ErrorCodeTO("1006", "您输入的验证码不正确！");

    ErrorCodeTO NULL_PASSWORD_INPUT=new ErrorCodeTO("1007", "请输入正确的密码！");

    ErrorCodeTO NULL_REPASSWORD_INPUT=new ErrorCodeTO("1008", "请输入正确的确认密码！");

    ErrorCodeTO NOTEQUAL_PASSWORD_REPASSWORD=new ErrorCodeTO("1009", "您输入密码与确认密码不一致！");

    ErrorCodeTO ERROR_ACCOUNT_INPUT=new ErrorCodeTO("1010", "您输入的账号不合法！");

    ErrorCodeTO ACCOUNT_NOTEXIST=new ErrorCodeTO("1011", "您输入的账号不存在！");

    ErrorCodeTO ERROR_PASSWORD_INPUT=new ErrorCodeTO("1012", "您输入的账号不存在或密码不正确！");

    ErrorCodeTO NULL_NEW_PASSWORD=new ErrorCodeTO("1013", "请您输入的新密码！");

    ErrorCodeTO NULL_OLD_PASSWORD=new ErrorCodeTO("1014", "请您输入的旧密码！");

    ErrorCodeTO ERROR_OLD_PASSWORD=new ErrorCodeTO("1015", "您输入的旧密码不正确！");

    ErrorCodeTO NULL_QUESTION_INPUT=new ErrorCodeTO("1016", "请您选择密保问题！");

    ErrorCodeTO NONE_PWD_QUESTION=new ErrorCodeTO("1017", "您还未设置密保问题！");

    ErrorCodeTO ERROR_PWD_QUESTION=new ErrorCodeTO("1018", "密保问题回答错误！");

    ErrorCodeTO EXIST_ACCOUNT=new ErrorCodeTO("1019", "您输入的账号已被注册！");

    ErrorCodeTO TOO_MANY_USERS=new ErrorCodeTO("1020", "当前在线人数过多，请您选择其它服务器！");

    ErrorCodeTO NULL_CODE_INPUT=new ErrorCodeTO("1021", "请您输入验证码！");

    ErrorCodeTO SAME_QUESTION_INPUT=new ErrorCodeTO("1022", "请您选择不同的密保问题！");

    ErrorCodeTO NULL_ANSWER_INPUT=new ErrorCodeTO("1023", "请您输入密保答案！");

    ErrorCodeTO NULL_TITLE_INPUT=new ErrorCodeTO("1024", "请您输入标题！");

    ErrorCodeTO NULL_SUGGESTION_INPUT=new ErrorCodeTO("1025", "请您输入意见内容！");

    ErrorCodeTO ERROR_EMAIL_MOBILENUM_INPUT=new ErrorCodeTO("1101", "您输入的邮箱或手机号格式不正确！");

    ErrorCodeTO ERROR_USERNAME_INPUT=new ErrorCodeTO("1102", "您输入的个性账号不合法！");

    ErrorCodeTO ERROR_MOBILENUM_INPUT=new ErrorCodeTO("1103", "请您输入正确的手机号！");

    ErrorCodeTO ERROR_EMAIL_INPUT=new ErrorCodeTO("1104", "请您输入正确的邮箱！");

    ErrorCodeTO IDCARD_INPUT_ERROR=new ErrorCodeTO("1105", "请您输入正确的身份证号！");

    ErrorCodeTO GAME_NOT_EXIST=new ErrorCodeTO("1106", "游戏不存在或不允许登录！");

    ErrorCodeTO GAMESERVER_NOT_EXIST=new ErrorCodeTO("1107", "您选择的游戏服务器不存在！");
    
    ErrorCodeTO UPLOAD_FAIL=new ErrorCodeTO("1108", "头像上传失败，请您重新选择！");

    ErrorCodeTO DB_ERROR=new ErrorCodeTO("2000", "数据库操作错误");

    ErrorCodeTO IO_ERROR=new ErrorCodeTO("2001", "IO错误");

    ErrorCodeTO NEED_LOGIN_ERROR=new ErrorCodeTO("2002", "您还未登录，请先登录！");

    ErrorCodeTO SESSION_TIME_OUT_ERROR=new ErrorCodeTO("2003", "您的请求已过期，请重新登录！");

    ErrorCodeTO TYPE_NUDEFINDE=new ErrorCodeTO("2004", "参数错误！");

    ErrorCodeTO GOODS_NOT_EXIST=new ErrorCodeTO("2005", "该商品不存在！");

    ErrorCodeTO ADDRESS_IS_NULL=new ErrorCodeTO("2006", "收货地址不能为空！");

    ErrorCodeTO GOODS_IS_NULL=new ErrorCodeTO("2007", "商品不能为空！");

    ErrorCodeTO ERROR_ADD_ADDRESS=new ErrorCodeTO("2008", "地址信息错误！");

    ErrorCodeTO INSUFFICIENT_BLANCE=new ErrorCodeTO("2009", "积分余额不足！");

    ErrorCodeTO ERROR_GOODS_TYPE=new ErrorCodeTO("2010", "虚拟物品和实物不能同时交易！");

    ErrorCodeTO SYSTEM_BUSY=new ErrorCodeTO("2011", "系统繁忙，请稍候重试！");

    ErrorCodeTO NOT_ENOUGH_BLANCE=new ErrorCodeTO("2012", "余额不足！");
    
    ErrorCodeTO ERROR_AMOUNT=new ErrorCodeTO("2013", "充值金额不正确！");
    
    ErrorCodeTO ERROR_PAY_CHANNEL=new ErrorCodeTO("2014", "请选择正确的充值方式！");
    
    ErrorCodeTO ERROR_CACHE=new ErrorCodeTO("2015", "缓存错误");
    
    ErrorCodeTO ERROR_PAY = new ErrorCodeTO("2016", "支付请求错误");

    ErrorCodeTO DUOKU_PASSWORD_INPUT_ERROR = new ErrorCodeTO("108", "密码不符合规则");
    
    ErrorCodeTO DUOKU_ACCOUNT_ERROR = new ErrorCodeTO("107", "用户名不符合规则");
    
    ErrorCodeTO DUOKU_ACCOUNT_EXIT_ERROR = new ErrorCodeTO("106", "用户名已注册");
    
    ErrorCodeTO DUOKU_INSUFFICIENT_BALANCE_ERROR = new ErrorCodeTO("105", "余额不足");
    
    ErrorCodeTO DUOKU_ACCESSTOKEN_EXPIREDE_ERROR = new ErrorCodeTO("102", "登录超时，请重新登录");
    
    ErrorCodeTO DUOKU_NOT_EXIT_ACCOUNT_ERROR = new ErrorCodeTO("13", "用户名不存在");
    
    ErrorCodeTO DUOKU_PASSWORD_ERROR = new ErrorCodeTO("12", "密码错误");
}
