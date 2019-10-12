package party.portlet.form;

public class FormDesign {
	String id;
	String name;
	String description;
	String submit_command;
	String render_jsp;
	String render_command;
	String design;

	boolean show = true;
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public String getRender_command() {
		return render_command;
	}
	public void setRender_command(String render_command) {
		this.render_command = render_command;
	}
	public String getRender_jsp() {
		return render_jsp;
	}
	public void setRender_jsp(String render_jsp) {
		this.render_jsp = render_jsp;
	}
	public String getSubmit_command() {
		return submit_command;
	}
	public void setSubmit_command(String submit_command) {
		this.submit_command = submit_command;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDesign() {
		return design;
	}
	public void setDesign(String design) {
		this.design = design;
	}
}