package communication;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class Pojo {
	//mdjfs: ok, esta es la clase base
	
	private String objName;
	private String methodName;
	private String[] params;
	private String[] types;
	
	// parametros
	private Class<?>[] classParams;
	private Object[] values;
	
	//metodos para retornar variables una vez convertido a pojo
	public String getobjName() {
		return objName;
	}
	
	public String getmethodName() {
		return methodName;
	}
	
	public Object[] getValues() {
		return values;
	}
	
	public Class<?>[] getclassParams(){
		return classParams;
	}
	
	
	private Class<?> getType(String type){
		if(type.equals("int")) {
			return int.class;
		}
		if(type.equals("Integer")) {
			return Integer.class;
		}
		if(type.equals("float")) {
			return float.class;
		}
		if(type.equals("Float")) {
			return Float.class;
		}
		if(type.equals("double")) {
			return double.class;
		}
		if(type.equals("Double")) {
			return Double.class;
		}
		if(type.equals("string")) {
			return String.class;
		}
		if(type.equals("byte")) {
			return byte.class;
		}
		if(type.equals("Byte")) {
			return Byte.class;
		}
		if(type.equals("object")) {
			return Object.class;
		}
		if(type.equals("char")) {
			return char.class;
		}
		if(type.equals("Character")) {
			return Character.class;
		}
		if(type.equals("boolean")) {
			return boolean.class;
		}
		if(type.equals("Boolean")) {
			return Boolean.class;
		}
		if(type.equals("ArrayList")) {
			return ArrayList.class;
		}
		return null;
	}
	
	private Object getValue(String value, String type) {
		if(type.equals("int")) {
			return Integer.parseInt(value);
		}
		if(type.equals("Integer")) {
			return new Integer(value);
		}
		if(type.equals("float")) {
			return Float.parseFloat(value);
		}
		if(type.equals("Float")) {
			return new Float(value);
		}
		if(type.equals("double")) {
			return Double.parseDouble(value);
		}
		if(type.equals("Double")) {
			return new Double(value);
		}
		if(type.equals("string")) {
			return new String(value);
		}
		if(type.equals("byte")) {
			return Byte.parseByte(value);
		}
		if(type.equals("Byte")) {
			return new Byte(value);
		}
		if(type.equals("char")) {
			return new Character(value.charAt(0));
		}
		if(type.equals("Character")) {
			return new Character(value.charAt(0));
		}
		if(type.equals("boolean")) {
			return Boolean.parseBoolean(value);
		}
		if(type.equals("Boolean")) {
			return new Boolean(value);
		}
		if(type.equals("ArrayList")) {
			ArrayList<Object> newArrayList = new ArrayList<Object>();
			JsonParser parser = new JsonParser();
			JsonArray Array = (JsonArray) parser.parse(value);
			for(int i=0; i<Array.size(); i++)
				newArrayList.add(Array.get(i));
			return newArrayList;
		}
		return null;
	}	
	
	public void setParams() {
		this.classParams = new Class[this.params.length];
		this.values = new Object[this.params.length];
		for(int i=0; i<this.params.length ; i++) {
			values[i] = getValue(this.params[i], this.types[i]);
			classParams[i] = getType(this.types[i].trim());
		}
	}
	
	public boolean CheckArrays() {
		if(this.params.length == this.types.length) {
			for(int i=0; i<this.params.length ; i++)
				if(params[i] == null || types[i] == null)
					return false;
			return true;
		}
		else
		{
			return false;
		}
	}

}
