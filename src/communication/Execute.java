package communication;

import java.lang.reflect.*;
import java.util.ArrayList;

public class Execute {
	
	public String getData(String objName,String methodName) {
		String resul = null;
		try {
			Class clase = Class.forName("communication."+objName);
			Method metodo[] = clase.getMethods();
			Object objeto;
			
			objeto = clase.newInstance();
			
			for(int i=0; i<metodo.length; i++){
				if(metodo[i].getName().equalsIgnoreCase(methodName)) {
					resul = (String) metodo[i].invoke(objeto).toString();
					System.out.println(resul);
					break;
				}
			}
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return resul;
	}
	
	public String getData(String objName,String methodName, String params) {
		String resul = null;
		try {
			Class clase = Class.forName("communication."+objName);
			Method metodo[] = clase.getMethods();
			Object objeto;
			
			objeto = clase.newInstance();
			
			for(int i=0; i<metodo.length; i++){
				if(metodo[i].getName().equalsIgnoreCase(methodName)) {
					resul = metodo[i].invoke(objeto,params).toString();
					System.out.println(resul);
					break;
				}
			}
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return resul;
	}
	
}
