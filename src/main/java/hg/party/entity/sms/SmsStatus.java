package hg.party.entity.sms;

import com.dt.annotation.Table;

@Table(name = "hg_party_sms")
public class SmsStatus {
    private String event_id;

    private String sms_identifier;

    private String sms_status;

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getSms_identifier() {
        return sms_identifier;
    }

    public void setSms_identifier(String sms_identifier) {
        this.sms_identifier = sms_identifier;
    }

    public String getSms_status() {
        return sms_status;
    }

    public void setSms_status(String sms_status) {
        this.sms_status = sms_status;
    }
    public SmsStatus(String event_id, String sms_identifier, String sms_status){
        this.event_id = event_id;
        this.sms_identifier = sms_identifier;
        this.sms_status = sms_status;
    }
    public SmsStatus(){

    }
}
