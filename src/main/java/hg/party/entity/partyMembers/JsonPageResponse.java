package hg.party.entity.partyMembers;

public class JsonPageResponse {
	private int code;

	private String msg;

	private Object data;

	private int count;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public JsonPageResponse() {
	}

	public JsonPageResponse(int code, String msg, Object data, int count) {
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.count = count;
	}
}