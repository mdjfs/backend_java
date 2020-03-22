package communication;

import java.io.File;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonObject;

import helpers.JSONManage;
import properties.Properties;

public class Execute {
	private JSONManage responses_invoke = new JSONManage();
	
	public JsonObject invoke(Pojo objeto_pojo) {
		try 
		{
			Class<?> clase = Class.forName("bussinessObjects."+objeto_pojo.getobjName());
			Object obj = clase.newInstance();
			Method method = clase.getDeclaredMethod(objeto_pojo.getmethodName(), objeto_pojo.getclassParams());
			return (JsonObject) method.invoke(obj, objeto_pojo.getValues());
		} 
		catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return responses_invoke.ReportErrorMessage(e.getMessage());
		}
	}
	
	private ArrayList<String> getNameObjects() {
		ArrayList<String> name_objects = new ArrayList<String>();
		File folder = new File(Properties.BussinesObjectsURI);
		String[] files = folder.list();
		for(int i = 0; i < files.length ; i++)
		{
			if(files[i].contains(".java")) {
				name_objects.add(files[i].replaceAll(".java", ""));
			}
		}
		return name_objects;
	}
	
	public HashMap<String, String[]> getObjectsAndMethods() throws ClassNotFoundException {
		HashMap<String, String[]> objects_and_methods = new HashMap<String, String[]>();
		ArrayList<String> objects = getNameObjects();
		for(String reflect : objects) {
			Class<?> obj_reflect = Class.forName("bussinessObjects."+reflect);
			Method[] methods = obj_reflect.getDeclaredMethods();
			String last_method = "";
			String[] methods_names = new String[methods.length];
			int i=0;
			for(Method method : methods) {
				if( ! last_method.equals(method.getName())) {
					methods_names[i] = method.getName();
				}
				else
				{
					methods_names[i] = "";
				}
				last_method = method.getName();
				i++;
			}
			objects_and_methods.put(reflect, methods_names);
		}
		return objects_and_methods;
	}
}
