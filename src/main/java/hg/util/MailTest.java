package hg.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 文件名称： party<br>
 * 内容摘要： <功能描述><br>
 * 创建人 　： caoxm<br>
 * 创建日期： 2018年1月5日下午3:14:26<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : 重庆和贯科技有限公司<br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 *
 */
public class MailTest {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		String myEmailAccount = "438793644@qq.com";//发送人
		String myEmailPassword = "fbsyoolbvrfgbhbh";//授权码
		
        /*
		 * 注意事项
		 * receiveMailAccount receiveMailAccountList receiveMailAccountBuf
		 * 上面三个参数， 只能有一个不为null
         * 添加收件人有三种方法：
         * 1,单人添加(单人发送)调用setRecipient(str);发送String类型
         * 2,多人添加(群发)调用setRecipients(list);发送list集合类型
         * 3,多人添加(群发)调用setRecipients(sb);发送StringBuffer类型
         */
		String receiveMailAccount = "";
		List<String> receiveMailAccountList = null;
		StringBuffer receiveMailAccountBuf = null;
		
		
		//收件人 单人 的时候
		receiveMailAccount = "839023812@qq.com";
		
		//收件人多人 list方式
//		receiveMailAccountList = new ArrayList<String>();
//		receiveMailAccountList.add("990274227@qq.com");
//		receiveMailAccountList.add("18323159137@163.com");

		//收件人多人 StringBuffer方式
//		receiveMailAccountBuf = new StringBuffer();
//		receiveMailAccountBuf.append("990274227@qq.com");
//		receiveMailAccountBuf.append(",");
//		receiveMailAccountBuf.append("18323159137@163.com");
		
		MailSend mail = new MailSend(myEmailAccount, myEmailPassword, receiveMailAccount, receiveMailAccountList, receiveMailAccountBuf);
		String subject = "标题测试3";
		String content = "内容测试3";
		System.out.println("发送状态：" + mail.sendMail(subject, content));
	}

}
