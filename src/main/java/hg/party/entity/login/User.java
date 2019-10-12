package hg.party.entity.login;

import com.dt.annotation.Table;

@Table(name = "hg_users_info")
public class User {
    private int id;//id
    private String user_id;//用户id
    private String user_name;//用户名
    private String user_sex;//性别
    private String user_telephone;
    private String user_department_id;
    private String user_mailbox;//邮箱
    private String user_password;
    private String userrole;
    private String state;
    private String job_number;
    private String member_ethnicity;


    public String getMember_ethnicity() {
        return member_ethnicity;
    }

    public void setMember_ethnicity(String member_ethnicity) {
        this.member_ethnicity = member_ethnicity;
    }

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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_telephone() {
        return user_telephone;
    }

    public void setUser_telephone(String user_telephone) {
        this.user_telephone = user_telephone;
    }

    public String getUser_department_id() {
        return user_department_id;
    }

    public void setUser_department_id(String user_department_id) {
        this.user_department_id = user_department_id;
    }

    public String getUser_mailbox() {
        return user_mailbox;
    }

    public void setUser_mailbox(String user_mailbox) {
        this.user_mailbox = user_mailbox;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getJob_number() {
        return job_number;
    }

    public void setJob_number(String job_number) {
        this.job_number = job_number;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", user_id=" + user_id + ", user_name=" + user_name + ", user_sex=" + user_sex
                + ", user_telephone=" + user_telephone + ", user_department_id=" + user_department_id
                + ", user_mailbox=" + user_mailbox + ", user_password=" + user_password + ", userrole=" + userrole
                + ", state=" + state + ", job_number=" + job_number + ", member_ethnicity=" + member_ethnicity
                + "]";
    }


}
