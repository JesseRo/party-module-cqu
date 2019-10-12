package hg.party.entity.organization;

import java.io.Serializable;
import com.dt.annotation.Table;
@Table(name="hg_partymeeting")
public class AssignedPerson implements Serializable{
	private static final long serialVersionUID = 1L;
	       private int id;
		   private String meetingType;
           private String meetingTheme;
           private String meetingDate;
           private String meetingPlace;
           private String personsNumber;
           private String comperePerson;
           private String telephone;
           private String linkMan;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getMeetingType() {
			return meetingType;
		}
		public void setMeetingType(String meetingType) {
			this.meetingType = meetingType;
		}
		public String getMeetingTheme() {
			return meetingTheme;
		}
		public void setMeetingTheme(String meetingTheme) {
			this.meetingTheme = meetingTheme;
		}
		public String getMeetingDate() {
			return meetingDate;
		}
		public void setMeetingDate(String meetingDate) {
			this.meetingDate = meetingDate;
		}
		public String getMeetingPlace() {
			return meetingPlace;
		}
		public void setMeetingPlace(String meetingPlace) {
			this.meetingPlace = meetingPlace;
		}
		public String getPersonsNumber() {
			return personsNumber;
		}
		public void setPersonsNumber(String personsNumber) {
			this.personsNumber = personsNumber;
		}
		public String getComperePerson() {
			return comperePerson;
		}
		public void setComperePerson(String comperePerson) {
			this.comperePerson = comperePerson;
		}
		public String getTelephone() {
			return telephone;
		}
		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}
		public String getLinkMan() {
			return linkMan;
		}
		public void setLinkMan(String linkMan) {
			this.linkMan = linkMan;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		@Override
		public String toString() {
			return "AssignedPerson [id=" + id + ", meetingType=" + meetingType + ", meetingTheme=" + meetingTheme
					+ ", meetingDate=" + meetingDate + ", meetingPlace=" + meetingPlace + ", personsNumber="
					+ personsNumber + ", comperePerson=" + comperePerson + ", telephone=" + telephone + ", linkMan="
					+ linkMan + "]";
		}
           
       
}
