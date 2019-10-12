package party.portlet.report.entity;

import com.dt.annotation.Table;

import java.io.Serializable;
import java.sql.Timestamp;

@Table(name = "hg_party_report_task_org")
public class ReportOrgTask implements Serializable {
    private int id;

    private String task_id;

    private String org_id;

    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }
}
