package hg.party.server.sms;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SmsMethodJob implements Job {
    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        JobDataMap map = ctx.getMergedJobDataMap();
        String methodName = map.getString("method");
        Object[] args = (Object[])map.get("args");
        try {
            Method[] methods = SmsService.class.getDeclaredMethods();
            for (Method method : methods){
                if (method.getName().equals(methodName)){
                    method.invoke(SmsService.class, args);
                    return;
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}