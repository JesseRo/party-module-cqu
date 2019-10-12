package hg.party.entity.sms;

public class SmsSendResponse {
    private String res_code;
    private String res_message;
    private String identifier;

    SmsSendResponse() {
    }

    public String getRes_message() {
        return res_message;
    }

    public void setRes_message(String res_message) {
        this.res_message = res_message;
    }

    public String getRes_code() {
        return res_code;
    }

    public void setRes_code(String res_code) {
        this.res_code = res_code;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}