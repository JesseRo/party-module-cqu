package party.portlet.report.entity;

public class BrunchReportContent {
    private int paid_number;

    private int total_number;

    private int should_fee;

    private int paid_fee;

    private String orgId;

    private String orgName;


    public int getPaid_fee() {
        return paid_fee;
    }

    public void setPaid_fee(int paid_fee) {
        this.paid_fee = paid_fee;
    }

    public int getShould_fee() {
        return should_fee;
    }

    public void setShould_fee(int should_fee) {
        this.should_fee = should_fee;
    }

    public int getPaid_number() {
        return paid_number;
    }

    public void setPaid_number(int paid_number) {
        this.paid_number = paid_number;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgName() {
        return orgName;
    }
}
