package hg.party.entity.organization;

public class Export {
    private  String meeting_type;
    private String meeting_theme;
    private String release_time;
    private String release_person;
    
    public void setRelease_person(String release_person) {
		this.release_person = release_person;
	}
    public String getRelease_person() {
		return release_person;
	}
	public String getMeeting_type() {
		return meeting_type;
	}
	public void setMeeting_type(String meeting_type) {
		this.meeting_type = meeting_type;
	}
	public String getMeeting_theme() {
		return meeting_theme;
	}
	public void setMeeting_theme(String meeting_theme) {
		this.meeting_theme = meeting_theme;
	}
	public String getRelease_time() {
		return release_time;
	}
	public void setRelease_time(String release_time) {
		this.release_time = release_time;
	}
     
}
