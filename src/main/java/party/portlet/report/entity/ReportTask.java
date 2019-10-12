package party.portlet.report.entity;

import com.dt.annotation.Table;

import java.io.Serializable;
import java.sql.Timestamp;

@Table(name = "hg_party_report_task")
public class ReportTask implements Serializable {


    private int id;

    private String task_id;

    private String theme;

    private String description;

    private String files;

    private String content;

    private int status;

    private String publisher;

    private Timestamp publish_time;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public Timestamp getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(Timestamp publish_time) {
        this.publish_time = publish_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
