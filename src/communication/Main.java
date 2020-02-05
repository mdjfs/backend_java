package communication;

public class Main {

	public static void main(String[] args) {
		Execute  ex = new Execute();
		
		ex.getData("Persona","getParam");
		ex.getData("Persona","getNombre");
		ex.getData("Persona","getEstado","123456");
		ex.getData("Persona","getEstado","86298139");
		
	}

}
