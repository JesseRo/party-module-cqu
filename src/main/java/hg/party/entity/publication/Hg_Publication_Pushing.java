package hg.party.entity.publication;
/**
 * 内容摘要： 
 * 创建人 　： Zhong LiMei
 * 创建日期： 2017年10月25日下午6:34:15
 */


public class Hg_Publication_Pushing {
	/**id*/
	private Integer id;
	/**推送类型(文章或链接等)*/
	private String publication_type;
	/**推送人用户名*/
	private String rocket_chat_user;
	/**推送人密码*/
	private String rocket_chat_password;
	/**推送地址192.168.0.253:3000*/
	private String rocket_chat_url;
	/**接受人或群*/
	private String chat_channel;
	/**推送人昵称（或中文名）*/
	private String user_alias;
	/**推送内容*/
	private String publication_text;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPublication_type() {
		return publication_type;
	}
	public void setPublication_type(String publication_type) {
		this.publication_type = publication_type;
	}
	public String getRocket_chat_user() {
		return rocket_chat_user;
	}
	public void setRocket_chat_user(String rocket_chat_user) {
		this.rocket_chat_user = rocket_chat_user;
	}
	public String getRocket_chat_password() {
		return rocket_chat_password;
	}
	public void setRocket_chat_password(String rocket_chat_password) {
		this.rocket_chat_password = rocket_chat_password;
	}
	public String getRocket_chat_url() {
		return rocket_chat_url;
	}
	public void setRocket_chat_url(String rocket_chat_url) {
		this.rocket_chat_url = rocket_chat_url;
	}
	public String getChat_channel() {
		return chat_channel;
	}
	public void setChat_channel(String chat_channel) {
		this.chat_channel = chat_channel;
	}
	public String getUser_alias() {
		return user_alias;
	}
	public void setUser_alias(String user_alias) {
		this.user_alias = user_alias;
	}
	public String getPublication_text() {
		return publication_text;
	}
	public void setPublication_text(String publication_text) {
		this.publication_text = publication_text;
	}
	@Override
	public String toString() {
		return "Hg_Publication_Pushing [id=" + id + ", publication_type=" + publication_type + ", rocket_chat_user="
				+ rocket_chat_user + ", rocket_chat_password=" + rocket_chat_password + ", rocket_chat_url="
				+ rocket_chat_url + ", chat_channel=" + chat_channel + ", user_alias=" + user_alias
				+ ", publication_text=" + publication_text + "]";
	}
	
	
}

