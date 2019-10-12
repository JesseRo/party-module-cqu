package hg.party.entity.partyMembers;

public class JsonResponse{
	private boolean result;
	private String message;
	private Object data;
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public JsonResponse(boolean result, String message, Object data) {
		// TODO Auto-generated constructor stub
		this.result = result;
		this.message = message;
		this.data = data;
	}
	public static JsonResponse Success(Object data) {
		return new JsonResponse(true, "success", data);
	}
	public static JsonResponse Success(String message,Object data) {
		return new JsonResponse(true, message, data);
	}
	
	public static JsonResponse Failure(String message) {
		return new JsonResponse(false, message, null);
	}
}