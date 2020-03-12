package bussinessObjects;

import com.google.gson.JsonObject;

import helpers.JSONManage;

public class User {
	private JSONManage responses_user = new JSONManage();
	private String name = "Hello world for the User";
	private int id = 123456;
	
	public JsonObject getName() {
		return responses_user.ReportSuccessMessage(name);
	}
		
	

	
	public void getStatusUser() {
		
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
	
	public void getStatusUser(String hola) {
		
	}
	
}
