package communication;
import com.google.gson.Gson;

public class Main {

	public static void main(String[] args) {
		/* Execute  ex = new Execute();
		
		ex.getData("Persona","getParam");
		ex.getData("Persona","getNombre");
		ex.getData("Persona","getEstado","123456");
		ex.getData("Persona","getEstado","86298139"); */
		
		//mdjfs: ok, aqui tengo un json, lo convierto a un objeto
		//para eso necesito una clase base que contenga las variables
		//que aparecen en el json
		/*String json = "{'objName':'objeto','methodName':'metodo', 'paramsInt':[54,53], 'paramsFloat':[43.3,32.3], 'paramsString':['hola','como']}";
		Gson gson = new Gson();
		Pojo objeto_gson = gson.fromJson(json, Pojo.class); //Pojo es la clase base
		//se convierten a pojo exitosamente
		System.out.println(objeto_gson.getobjName());
		System.out.println(objeto_gson.getmethodName());
		System.out.println(objeto_gson.getparamsInt()[0]);
		System.out.println(objeto_gson.getparamsInt()[1]);
		System.out.println(objeto_gson.getparamsFloat()[0]);
		System.out.println(objeto_gson.getparamsFloat()[1]);
		System.out.println(objeto_gson.getparamsString()[0]);
		System.out.println(objeto_gson.getparamsString()[1]);
		*/
		
		String json = "{'objName':'Persona','methodName':'getEstado','paramsInt':[123456]}";

		Gson gson = new Gson();
		Pojo objeto_gson = gson.fromJson(json, Pojo.class);
		
		Execute  ex = new Execute(objeto_gson);
		ex.run();
	}

}
