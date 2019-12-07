package party.portlet.unit;

import com.dt.annotation.Table;

import java.sql.Time;
import java.sql.Timestamp;

@Table(name = "hg_party_unit")
public class Unit {
    private int id;

    private String unit_code;

    private String unit_name;

    private String update_member_id;

    private Timestamp update_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnit_code() {
        return unit_code;
    }

    public void setUnit_code(String unit_code) {
        this.unit_code = unit_code;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getUpdate_member_id() {
        return update_member_id;
    }

    public void setUpdate_member_id(String update_member_id) {
        this.update_member_id = update_member_id;
    }

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }
}
