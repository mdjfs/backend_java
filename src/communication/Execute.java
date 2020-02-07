package communication;

import java.lang.reflect.*;

public class Execute {
	private Pojo pojo;

	public Execute(Pojo objeto_pojo) {
		this.pojo = objeto_pojo;
	}
	
	public String run() {
			String resul = null;
			try {
				Class clase = Class.forName("communication."+pojo.getobjName());
				Method metodo[] = clase.getMethods();
				Object objeto;
				
				objeto = clase.newInstance();
				
				for(int i=0; i<metodo.length; i++){
					if(metodo[i].getName().equalsIgnoreCase(pojo.getmethodName())) {
						System.out.println(metodo[i].toGenericString());
						
						Class[] c = metodo[i].getParameterTypes();

						if(c.length == 0) {
							resul = metodo[i].invoke(objeto).toString();
							System.out.println(resul);
							
						}else {
							for(int j=0; j<c.length; j++) {
								
								switch(c[j].getSimpleName()) {
									case "int[]":
										resul = metodo[i].invoke(objeto,pojo.getparamsInt()).toString();
										System.out.println(resul);
										break;
									case "float[]":
										resul = metodo[i].invoke(objeto,pojo.getparamsFloat()).toString();
										System.out.println(resul);
										break;
									case "String[]":
										resul = metodo[i].invoke(objeto,pojo.getparamsString()).toString();
										System.out.println(resul);
										break;
								}
							}
						}
						
						break;
					}
				}
			
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
			return resul;
	}

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
	
	public String getData(String objName,String methodName, Array paramsInt,Array paramsFloat,Array paramsString) {
		String resul = null;
		try {
			Class clase = Class.forName("communication."+objName);
			Method metodo[] = clase.getMethods();
			Object objeto;
			
			objeto = clase.newInstance();
			
			for(int i=0; i<metodo.length; i++){
				if(metodo[i].getName().equalsIgnoreCase(methodName)) {
					resul = metodo[i].invoke(objeto).toString();
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
