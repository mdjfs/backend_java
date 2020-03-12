
package helpers;

import java.io.BufferedReader;
import java.io.IOException;

import org.json.simple.JSONObject;

import com.google.gson.JsonObject;

public class JSONManage {
	
	
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
	
	public String say_keys(String[] json_keys) {
		/* metodo que devuelve el atributo con arreglo de las llaves que necesita el servlet
		 *  en string */
		String keys = "";
		for(int i=0; i < json_keys.length ; i++) {
			if(i == json_keys.length - 1)
				keys += json_keys[i];
			else
				keys += json_keys[i]+", ";
		}
		return keys;
	}
	
	public Boolean is_all_keys(JSONObject json_request, String[] json_keys) {
		/* metodo que verifica si todas las llaves que necesita el servlet
		 *  estan en el json */
		for(int i=0; i < json_keys.length ; i++) {
			if( ! json_request.containsKey(json_keys[i]) ) {
				return false;
			}
		}
		return true;
	}

	public String readRaw(BufferedReader buffer) throws IOException {
		/* metodo que lee texto enviado en raw y lo devuelve como string */
		StringBuffer buffertext = new StringBuffer();
		BufferedReader reader = buffer;
		String line = "";
		while ((line = reader.readLine()) != null)
			buffertext.append(line);
		return buffertext.toString();
	}
}
