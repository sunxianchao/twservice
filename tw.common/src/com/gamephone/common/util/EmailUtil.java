/**
 * $Id: EmailUtil.java,v 1.9 2012/03/15 06:20:15 jiayu.qiu Exp $
 */
package com.gamephone.common.util;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * @author qinchao.zhang@downjoy.com
 */
public class EmailUtil {

    private static final String USERNAME="service@phonegame.com.tw";

    private static final String PASSWORD="service5513";

    private static final String HOST="smtp.gmail.com";

    /**
     *用spring mail 发送邮件,依赖jar：spring.jar，activation.jar，mail.jar
     */
    public static void sendMail(String userAddress, String title, String content, boolean isHtml) {
        JavaMailSenderImpl senderImpl=new JavaMailSenderImpl();
        Properties prop=new Properties();
        prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
        prop.put("mail.smtp.timeout", "25000");  
        prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  

        // 设定mail server
        senderImpl.setHost(HOST);
        senderImpl.setUsername(USERNAME);
        senderImpl.setPassword(PASSWORD);
        senderImpl.setDefaultEncoding("UTF-8");
        senderImpl.setPort(465); 
        senderImpl.setJavaMailProperties(prop);
        // 建立HTML邮件消息
        MimeMessage mailMessage=senderImpl.createMimeMessage();
        MimeMessageHelper messageHelper; // false表示开始普通模式/ ture 附件模式
        try {
            messageHelper=new MimeMessageHelper(mailMessage, false, "UTF-8");
            messageHelper.setFrom(USERNAME);
            messageHelper.setTo(userAddress);
            messageHelper.setSubject(title);
            messageHelper.setText(content, isHtml); // true 表示启动HTML格式的邮件
        } catch(MessagingException e) {
            e.printStackTrace();
        }
        try {
            senderImpl.send(mailMessage);
        } catch(MailException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        String content="您好，這是一封重要郵件，您的新密碼為 # ，請您妥善保管好新密碼！\n如您有其他問題，請聯繫我們：service@phonegame.com.tw";
        String newpwd=RadomUtil.getPass(6);
        content=content.replace("#", newpwd);
        sendMail("ajian@phonegame.com.tw", "智丰會員帳號密碼修改（重要）！", content, false);
    }
}
