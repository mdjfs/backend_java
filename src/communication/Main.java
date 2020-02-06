package communication;
import com.google.gson.*;

public class Main {

	public static void main(String[] args) {
		/* Execute  ex = new Execute();
		
		ex.getData("Persona","getParam");
		ex.getData("Persona","getNombre");
		ex.getData("Persona","getEstado","123456");
		ex.getData("Persona","getEstado","86298139"); */
		String json = "{\"method\":"
						+ "\"nombredemetodo\","
						+ "\"id\":"
						+ "5";
		Gson gson = new Gson();
		System.out.println(gson.fromJson(json, User.class));
		
	}

}
