package hg.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;

import com.liferay.portal.kernel.util.Validator;

/**
 * 
 * 文件名称： party<br>
 * 内容摘要： 发送邮件<功能描述><br>
 * 创建人 　： caoxm<br>
 * 创建日期： 2018年1月5日下午1:56:01<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : 重庆和贯科技有限公司<br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 *
 */
public class MailSend {
	/** 发送人账号 */
	protected static String myEmailAccount;
	/** 发送人密码(不是qq邮箱密码，是qq邮箱授权码) */
	protected static String myEmailPassword;
	
	
	/** 收邮件人账号
	 *  收邮件人可以分三种发送
	 *  单人发送
	 *  多人的list发送
	 *  多人的StringBuffer发送
	 *  */
	protected static String receiveMailAccount;
	protected static List<String> receiveMailAccountList;
	protected static StringBuffer receiveMailAccountBuf;
	
	
	@SuppressWarnings("static-access")
	public MailSend(String myEmailAccount, String myEmailPassword, 
			String receiveMailAccount, List<String> receiveMailAccountList, StringBuffer receiveMailAccountBuf){
		this.myEmailAccount = myEmailAccount;
		this.myEmailPassword = myEmailPassword;
		this.receiveMailAccount = receiveMailAccount;
		this.receiveMailAccountList = receiveMailAccountList;
		this.receiveMailAccountBuf = receiveMailAccountBuf;
	}
	
	
	
	public static Boolean sendMail(String subject, String content){
		try {
	        MailUtil mailUtil = new MailUtil(myEmailAccount, myEmailPassword);
	        Map<String,String> map= new HashMap<String,String>();
	        map.put("mail.smtp.host", "smtp.swu.edu.cn");
	        map.put("mail.smtp.auth", "true");
//	        map.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        map.put("mail.smtp.port", "25");
			map.put("mail.smtp.ssl.enable", "false");
//			map.put("mail.smtp.socketFactory.port", "465");
	        
	        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
	        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
	        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
	        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
	        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
	        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
	        CommandMap.setDefaultCommandMap(mc);
	        
	        mailUtil.setPros(map);
	        mailUtil.initMessage();
	        if(Validator.isNotNull(receiveMailAccount)){
	        	mailUtil.setRecipient(receiveMailAccount);
	        }else if(Validator.isNotNull(receiveMailAccountBuf)){
	        	mailUtil.setRecipients(receiveMailAccountBuf);
	        }else if(Validator.isNotNull(receiveMailAccountList)){
	        	mailUtil.setRecipients(receiveMailAccountList);
	        }
	        mailUtil.setSubject(subject);mailUtil.setFrom("MY");
	        mailUtil.setDate(new Date());
	        mailUtil.setContent(content, "text/html; charset=UTF-8");
	        mailUtil.sendMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
