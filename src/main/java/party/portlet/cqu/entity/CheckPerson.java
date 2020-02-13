package party.portlet.cqu.entity;

import com.dt.annotation.Table;

import java.io.Serializable;

@Table(name = "hg_party_check_person")
public class CheckPerson implements Serializable {
    private int id;

    private String user_id;

    private String campus;

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
}
