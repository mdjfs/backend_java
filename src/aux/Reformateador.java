package aux;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import communication.Pojo;

public class Reformateador {
	private String json;
	private JSONObject jsonobj = null;
	private JSONParser parser = new JSONParser();
	
	public Reformateador(String json) {
		this.json = json;
	}
	
	public Pojo Formatear() {
		try 
		{
			jsonobj = (JSONObject) parser.parse(json);
			String types = jsonobj.get("types").toString();
			String params = jsonobj.get("params").toString();
			DetectarArrays find_array = new DetectarArrays(params, types);
			find_array.ProcesarArrays();
			String json_formatted = "{\"objName\":\"objeto\",\"methodName\":\"metodo\", \"paramsInt\":"+find_array.getparamsInt()+", \"paramsFloat\":"+find_array.getparamsFloat()+", \"paramsString\":"+find_array.getparamsString()+"}";
			Gson gson = new Gson();
			Pojo objeto_gson = gson.fromJson(json_formatted, Pojo.class);
			return objeto_gson;
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
			return null;
		}
	}

}
