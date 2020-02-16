<<<<<<< HEAD:src/helper/JSONResponses.java
package helper;
=======
package auxiliar;
>>>>>>> 5bf0ea679499de63ae8e73d8f052f0ae2b047d3b:src/aux/JSONResponses.java

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
