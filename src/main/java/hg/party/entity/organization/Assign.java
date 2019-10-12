package hg.party.entity.organization;

import java.io.Serializable;

import com.dt.annotation.Table;

@Table(name="hg_party_assigne")
public class Assign { 
		private int id;
        private String assigne_user_id;//身份证号
        private String assigne_name;
        private int state;
        private String department_name;//部门id、
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getAssigne_user_id() {
			return assigne_user_id;
		}
		public void setAssigne_user_id(String assigne_user_id) {
			this.assigne_user_id = assigne_user_id;
		}
		public String getAssigne_name() {
			return assigne_name;
		}
		public void setAssigne_name(String assigne_name) {
			this.assigne_name = assigne_name;
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public String getDepartment_name() {
			return department_name;
		}
		public void setDepartment_name(String department_name) {
			this.department_name = department_name;
		}
		@Override
		public String toString() {
			return "Assign [id=" + id + ", assigne_user_id=" + assigne_user_id + ", assigne_name=" + assigne_name
					+ ", state=" + state + ", department_name=" + department_name + "]";
		}
        
        
}
