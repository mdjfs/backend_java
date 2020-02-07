package communication;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import aux.DetectarArrays;
import aux.Reformateador;

public class Main {

	public static void main(String[] args) {
		/* Execute  ex = new Execute();
		
		ex.getData("Persona","getParam");
		ex.getData("Persona","getNombre");
		ex.getData("Persona","getEstado","123456");
		ex.getData("Persona","getEstado","86298139"); */
		String json_unformatted = "{\"objName\":\"Persona\",\"methodName\":\"getPersona\",\"params\":[1, 2, 3],\"types\":[\"int\", \"int\", \"int\"]}";
		Reformateador formato = new Reformateador(json_unformatted);
		Pojo hola = formato.getFormatPojo();
		/* JSONObject json = null;    Todo esto esta en la clase Reformateador
		JSONParser parser = new JSONParser();
		try {
			json = (JSONObject) parser.parse(json_unformatted);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String types = json.get("types").toString();
		String params = json.get("params").toString();
		DetectarArrays find_array = new DetectarArrays(params, types);
		find_array.ProcesarArrays();
		String json_formatted = "{\"objName\":\"objeto\",\"methodName\":\"metodo\", \"paramsInt\":"+find_array.getparamsInt()+", \"paramsFloat\":"+find_array.getparamsFloat()+", \"paramsString\":"+find_array.getparamsString()+"}";
		Gson gson = new Gson();
		Pojo objeto_gson = gson.fromJson(json_formatted, Pojo.class); //Pojo es la clase base
		//se convierten a pojo exitosamente
		System.out.println(objeto_gson.getobjName());
		System.out.println(objeto_gson.getmethodName());
		System.out.println(objeto_gson.getparamsInt()[0]);
		System.out.println(objeto_gson.getparamsFloat()[0]);
		System.out.println(objeto_gson.getparamsFloat()[1]);
		System.out.println(objeto_gson.getparamsString()[0]); */
		
	}

}
