package aux;

import com.google.gson.JsonObject;

public class JSONResponses {
	
	
	public JsonObject ReportErrorMessage(String message) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("result", "error");
		jsonObject.addProperty("message", message);
		jsonObject.addProperty("status", "500");
		return jsonObject;
	}
	
	public JsonObject ReportSuccessMessage(String message) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("result", "success");
		jsonObject.addProperty("message", message);
		jsonObject.addProperty("status", "200");
		return jsonObject;
	}
}
