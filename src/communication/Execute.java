package communication;

import java.lang.reflect.*;

import com.google.gson.JsonObject;

import helpers.JSONManage;

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
}
