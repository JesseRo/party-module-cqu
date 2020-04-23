package hg.util.date;

import java.sql.Timestamp;

/**
 * 根据日期查询对象
 */
public class DateQueryVM {
    private Timestamp startTime;
    private Timestamp endTime;

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
