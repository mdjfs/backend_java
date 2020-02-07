package communication;

public class Pojo {
	//mdjfs: ok, esta es la clase base
	
	private String objName;
	private String methodName;
	
	// datos tipados
	private int paramsInt[];
	private float paramsFloat[];
	private String paramsString[];
	
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
