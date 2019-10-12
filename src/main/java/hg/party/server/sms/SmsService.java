package hg.party.server.sms;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hg.party.dao.sms.SmsDao;
import hg.party.entity.sms.SmsSendResponse;
import hg.party.entity.sms.SmsStatus;
import hg.party.entity.sms.Status;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class SmsService {
    private static Scheduler scheduler;
    private static final String SMS_SEND_URL = "http://sms.swu.edu.cn:8080/interface/sendSms";
    private static final String SMS_STATUS_URL = "http://sms.swu.edu.cn:8080/interface/querySmsStatus";
    private static String SMS_ACCOUNT = "dwpt";
    private static String SMS_ACCESS_TOKEN = "97424D78AD5B03D922663DABBFA21CED";

//    static {
//        Properties pps = new Properties();
//        try {
////            pps.load(SmsService.class.getClassLoader().getResourceAsStream("/sms.properties"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        SMS_ACCESS_TOKEN = pps.getProperty("sms.access_token");
//        SMS_ACCOUNT = pps.getProperty("sms.account");
//    }

    private static SmsDao smsDao = new SmsDao();

    private static Gson gson = new Gson();
    private static RestTemplate restTemplate = new RestTemplate();
    // execute job implemented in @jClass within next @seconds..
    private static String later(Class<? extends Job> jClass, Map<String, Object> jobDataMap, long seconds) throws SchedulerException {
        Date datetime = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Shanghai")).plusSeconds(seconds));
        return once(jClass, jobDataMap, datetime);
    }

    // execute job implemented in @jClass at time @datetime
    private static String once(Class<? extends Job> jClass, Map<String, Object> jobDataMap, Date datetime) throws SchedulerException {
        if (scheduler == null){
            scheduler = StdSchedulerFactory.getDefaultScheduler();
        }
        scheduler.start();

        JobDetail job = JobBuilder.newJob(jClass).usingJobData(new JobDataMap(jobDataMap))
                .build();

        if (datetime == null){
            datetime = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Shanghai")));
        }
        Trigger trigger = TriggerBuilder.newTrigger()
                .startAt(datetime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0))
                .build();

        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(job, trigger);
        return trigger.getKey().getName();
    }
    
    private static String everyDay(Class<? extends Job> jClass, Map<String, Object> jobDataMap, int hour) throws SchedulerException {
        if (scheduler == null){
            scheduler = StdSchedulerFactory.getDefaultScheduler();
        }
        scheduler.start();

        JobDetail job = JobBuilder.newJob(jClass).usingJobData(new JobDataMap(jobDataMap))
                .build();

        
        Date datetime = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Shanghai")));
        
        Trigger trigger = TriggerBuilder.newTrigger()
                .startAt(datetime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatSecondlyForever(5))
                .build();

        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(job, trigger);
        return trigger.getKey().getName();
    }

    protected static void sendAll(String eventId, String message, List<String> receiver) throws SchedulerException {
        while (receiver.size() > 500){
            List<String> batch = receiver.subList(0, 500);
            receiver = receiver.subList(500, receiver.size());
            send(eventId, message, batch);
        }
        send(eventId, message, receiver);
    }
    
    public static void notice(String eventId, String message, List<String> receiver) throws SchedulerException {
        MultiValueMap<String, String > params = new LinkedMultiValueMap<>();
        params.add("account", SMS_ACCOUNT);
        params.add("access_token", SMS_ACCESS_TOKEN);
        params.add("receiver", String.join(",", receiver));
        params.add("smscontent", message);
        params.add("extcode", "0");
        Object[] sendArgs = new Object[]{eventId, message, receiver};
        Map<String, Object> sendMap = new HashMap<>();
        sendMap.put("method", "send");
        sendMap.put("args", sendArgs);
        
        
        try {
            String url  = UriComponentsBuilder.fromHttpUrl(SMS_SEND_URL).build().toString();
            String r = restTemplate.postForObject(url, params, String.class);
            System.out.println(r);
            SmsSendResponse res = gson.fromJson(r, SmsSendResponse.class);
            if (res.getRes_code().equals("0")){
            	//发送短信成功后5分钟查询短信发送状态
            	Object[] statusArgs = new Object[]{eventId, res.getIdentifier()};
                Map<String, Object> statusMap = new HashMap<>();
                statusMap.put("method", "status");
                statusMap.put("args", statusArgs);
            	everyDay(SmsMethodJob.class, statusMap, 8);
            }
        }catch (Exception e){
            e.printStackTrace();
            later(SmsMethodJob.class, sendMap,5L);
        }
    }

    protected static void send(String eventId, String message, List<String> receiver) throws SchedulerException {
        MultiValueMap<String, String > params = new LinkedMultiValueMap<>();
        params.add("account", SMS_ACCOUNT);
        params.add("access_token", SMS_ACCESS_TOKEN);
        params.add("receiver", String.join(",", receiver));
        params.add("smscontent", message);
        params.add("extcode", "0");
        Object[] sendArgs = new Object[]{eventId, message, receiver};
        Map<String, Object> sendMap = new HashMap<>();
        sendMap.put("method", "send");
        sendMap.put("args", sendArgs);
        
        
        try {
            String url  = UriComponentsBuilder.fromHttpUrl(SMS_SEND_URL).build().toString();
            String r = restTemplate.postForObject(url, params, String.class);
            System.out.println(r);
            SmsSendResponse res = gson.fromJson(r, SmsSendResponse.class);
            if (res.getRes_code().equals("0")){
            	//发送短信成功后5分钟查询短信发送状态
            	Object[] statusArgs = new Object[]{eventId, res.getIdentifier()};
                Map<String, Object> statusMap = new HashMap<>();
                statusMap.put("method", "status");
                statusMap.put("args", statusArgs);
            	later(SmsMethodJob.class, statusMap, 5L * 60);
            }
        }catch (Exception e){
            e.printStackTrace();
            later(SmsMethodJob.class, sendMap,5L);
        }
    }

    protected static void status(String eventId, String identifier) throws SchedulerException {
        MultiValueMap<String, String > params = new LinkedMultiValueMap<>();
        params.add("account", SMS_ACCOUNT);
        params.add("access_token", SMS_ACCESS_TOKEN);
        params.add("identifier", identifier);


        Object[] args = new Object[]{eventId, identifier};
        Map<String, Object> map = new HashMap<>();
        map.put("method", "status");
        map.put("args", args);
        try {
            String url  = UriComponentsBuilder.fromHttpUrl(SMS_STATUS_URL).build().toString();
            String r = restTemplate.postForObject(url, params, String.class);
            System.out.println(r);
            SmsSendResponse res = gson.fromJson(r, SmsSendResponse.class);
            if (res.getRes_code().equals("0")){
                String statusMessage = res.getRes_message();
                String[] status = statusMessage.split(";");
                List<Status> statuses = Arrays.stream(status).map(p -> {
                    String[] fields = p.split(",");
                    Status smsStatus = new Status();
                    if (fields.length == 3) {
                        smsStatus.setReceiver(fields[1]);
                        smsStatus.setErrorMessage(fields[2]);
                        smsStatus.setStatus(fields[2].equals("0") || fields[2].equals(Status.SUCCEED));
                    }
                    return smsStatus;
                }).collect(Collectors.toList());
                SmsStatus smsStatus = new SmsStatus(eventId, identifier, gson.toJson(statuses));
                smsDao.saveOne(smsStatus);
            }
        }catch (Exception e){
            e.printStackTrace();
            later(SmsMethodJob.class, map,5L);
        }

    }

    public static String smsSend(String eventId, String message, List<String> receiver, Date datetime) throws SchedulerException {
        Object[] args = new Object[]{eventId, message, receiver};
        Map<String, Object> map = new HashMap<>();
        map.put("method", "sendAll");
        map.put("args", args);
        return once(SmsMethodJob.class, map, datetime);
    }

    public static String smsSend(String eventId, String message, List<String>  receiver) throws SchedulerException {
        return smsSend(eventId, message, receiver, null);
    }

    public static List<Status> smsStatus(String eventId){
        List<SmsStatus> smsStatuses = smsDao.findByEvent(eventId);
        List<Status> result = new ArrayList<>();
        for (SmsStatus smsStatus : smsStatuses){
            result.addAll(gson.fromJson(smsStatus.getSms_status(), new TypeToken<List<Status>>(){}.getType()));
        }
        return result;
    }

   public static void notic(String eventId, String message, List<String> receiver){
	   MultiValueMap<String, String > params = new LinkedMultiValueMap<>();
       params.add("account", SMS_ACCOUNT);
       params.add("access_token", SMS_ACCESS_TOKEN);
       params.add("receiver", String.join(",", receiver));
       params.add("smscontent", message);
       params.add("extcode", "0");
       Object[] sendArgs = new Object[]{eventId, message, receiver};
       Map<String, Object> sendMap = new HashMap<>();
       sendMap.put("method", "send");
       sendMap.put("args", sendArgs);
       try {
           String url  = UriComponentsBuilder.fromHttpUrl(SMS_SEND_URL).build().toString();
           String r = restTemplate.postForObject(url, params, String.class);
           System.out.println(r);
           
       }catch (Exception e) {
	
	   }
     }

}
