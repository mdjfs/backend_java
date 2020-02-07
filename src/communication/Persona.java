package communication;

public class Persona {
	private String nombre = "Hola Mundo desde Persona";
	private int id = 123456;
	

	public String getNombre() {
		return this.nombre;
	}
		
	public int getId() {
		return this.id;
	}
	
	public void prub(float[] fl) {
		System.out.println("prueba");
		System.out.println(fl[0]);
		
	}
	
	public boolean getEstado(int[] id) {
		boolean estado; 
		
		if(id[0] == this.id) {
			estado = true;
		}else {
			estado = false;
		}
		return estado;
	}
	
	
	/*public boolean getEstado(String id) {
	boolean estado; 
	int newid = Integer.parseInt(id);
	
	if(newid == this.id) {
		estado = true;
	}else {
		estado = false;
	}
	return estado;
	}
	 */

	
}
