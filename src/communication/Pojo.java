package communication;

public class Pojo {
	//mdjfs: ok, esta es la clase base
	
	// datos que no necesito preformatear
	private String objName;
	private String methodName;
	
	// datos que necesito preformatear
	private static int paramsInt[];
	private static float paramsFloat[];
	private static String paramsString[];
	
	// metodos para preformatear
	public static void setSizeInt(int size) {
		paramsInt = new int[size];
	}
	
	public static void setSizeFloat(int size) {
		paramsFloat = new float[size];
	}
	
	public static void setSizeString(int size) {
		paramsString = new String[size];
	}
	
	//metodos para retornar variables una vez convertido a pojo
	public String getobjName() {
		return objName;
	}
	
	public String getmethodName() {
		return methodName;
	}
	
	public int[] getparamsInt() {
		return paramsInt;
	}
	
	public float[] getparamsFloat() {
		return paramsFloat;
	}
	
	public String[] getparamsString() {
		return paramsString;
	}

}
