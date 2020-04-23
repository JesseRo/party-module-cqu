package hg.util;

import hg.util.date.DateQueryVM;
import party.constants.DateQueryEnum;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HgDateQueryUtil {
    public static DateQueryVM toDateQueryVM(DateQueryEnum dateQueryEnum){
        DateQueryVM dateQueryVM = new DateQueryVM();
        if(dateQueryEnum == null){
            return dateQueryVM;
        }
        Calendar calendar=Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Timestamp startTime = null;
        switch(dateQueryEnum){
            case DAY_1:
                startTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
                break;
            case DAY_7:
                calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),day-7);
                startTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
                break;
            case MONTH_1:
                calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)-1,day);
                startTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
                break;
            case MORE:
                startTime = null;
                break;
        }
        dateQueryVM.setStartTime(startTime);
        return dateQueryVM;
    }
}
