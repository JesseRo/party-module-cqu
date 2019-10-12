package hg.party.entity.login;

import java.sql.Timestamp;

import com.dt.annotation.Table;

@Table(name="hg_reset_passwd")
public class ResetPasswd {
	private int id;
	private String user_id;//用户id
	private String user_name;//用户名
	private String passwd;//新密码
	private Timestamp update_time;//修改密码的时间
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
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public Timestamp getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
	@Override
	public String toString() {
		return "ResetPasswd [id=" + id + ", user_id=" + user_id + ", user_name=" + user_name + ", passwd=" + passwd
				+ ", update_time=" + update_time + "]";
	}
	
	
}
