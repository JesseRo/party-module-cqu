package party.portlet;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



import org.apache.log4j.Logger;
import org.aspectj.weaver.patterns.IfPointcut.IfFalsePointcut;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import org.springframework.util.StringUtils;



import hg.party.dao.sms.SmsDao;

import hg.party.entity.partyMembers.Member;
import hg.party.server.sms.SmsService;

public class Init implements BundleActivator {
	Logger logger = Logger.getLogger(Init.class);

	@Override
	public void start(BundleContext context) throws Exception {
		// Thread.sleep(1000*60*5);
		birthdayNotic();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

	public static void birthdayNotic() {
		Date datetime = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Shanghai")).withHour(8).withMinute(0).withSecond(0).withNano(0));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
		SimpleDateFormat simplYear = new SimpleDateFormat("yyyy");
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy年MM月dd日");
		SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
		SmsDao smsDao = new SmsDao();
		new Thread(() -> {
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				public void run() {
				    Date datetime = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Shanghai")).withHour(8).withMinute(0).withSecond(0));
					String dateString = simpleDateFormat.format(datetime);
					String like="%-"+simpleDateFormat.format(datetime);
					List<Member> ms = smsDao.findMembers(like);
					List<String> telephones = new ArrayList<>();
					for (Member m : ms) {
						if (!StringUtils.isEmpty(m.getMember_join_date())) {
							if (dateString.equals(m.getMember_join_date().substring(5))) {
								telephones.add(m.getMember_phone_number());
								String join = "";
								int years=0;
								try {
									join = simpleDateFormat2.format(simpleDateFormat3.parse(m.getMember_join_date()));
									years = Integer.parseInt(simplYear.format(datetime))
										     - Integer.parseInt(m.getMember_join_date().substring(0, 4));
								} catch (ParseException e) {
									
								}
								String notice="";
								if (ms.size()>1) {
									notice="（今天，西南大学还有 "+(ms.size()-1)+" 位同志与您同过政治生日）";
								}
								String msg = String.format(		
										"\r\n%s 同志："+
										"\r\n今天是您的政治生日，%s 您光荣加入中国共产党，在您入党 %s周年之际，学校党委向您致以生日的问候，"
										+ "对您入党以来为党组织所做的工作致以衷心的感谢和崇高的敬意。"
										+ "让我们不忘初心、牢记使命，永葆先进本色，为党和人民的事业作出积极贡献！"
										+ "同时，祝您及家人身体健康，工作顺利，生活愉快！"+
										"\r\n %s "+
										"\r\n中共西南大学委员会"+
										"\r\n%s",
										m.getMember_name(), 
										join,
										years,
										notice,
										simpleDateFormat2.format(datetime));
									if (years>0) {
										SmsService.notic(m.getMember_identity(), msg, telephones);
									}		
					           	telephones.clear();
							}
						}
					}
				}
			};
			timer.scheduleAtFixedRate(task, datetime, 1000*60*60*24);

		}).start();

	}

//	public static void main(String[] args) {
//		//birthdayNotic();
//		TestDemo testDemo=new TestDemo();
//	}

}
