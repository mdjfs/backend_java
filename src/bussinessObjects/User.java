package bussinessObjects;

import com.google.gson.JsonObject;

import helper.JSONResponses;

public class User {
	private JSONResponses responses_user = new JSONResponses();
	private String name = "Hello world for the User";
	private int id = 123456;
	
	public JsonObject getName() {
		return responses_user.ReportSuccessMessage(name);
	}
		
	public JsonObject getId() {
		return responses_user.ReportSuccessMessage("the user id is: "+ id);
	}
	
	public JsonObject getStatusUser(int id) {
		
		if(id == this.id) {
			return responses_user.ReportSuccessMessage("correct ID");
		}else {
			return responses_user.ReportSuccessMessage("incorrect ID");
		}
	}
	
}
