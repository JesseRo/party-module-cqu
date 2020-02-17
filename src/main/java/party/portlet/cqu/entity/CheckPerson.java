package party.portlet.cqu.entity;

import com.dt.annotation.Table;

import java.io.Serializable;

@Table(name = "hg_party_check_person")
public class CheckPerson implements Serializable {
    private int id;

    private String user_id;

    private String campus;

    private String type;

    private Integer count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
