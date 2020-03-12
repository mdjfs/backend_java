package test;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Findfolders {

	public static void main(String[] args) {
		File folder = new File("src/bussinessObjects");
		String [] files = folder.list();
		ArrayList<String> objects = new ArrayList<String>();
		for(int i = 0; i < files.length ; i++)
		{
			if(files[i].contains(".java")) {
				objects.add(files[i].replaceAll(".java", ""));
			}
		}
		
		for(String reflect : objects) {
			Class<?> obj_reflect;
			try {
				obj_reflect = Class.forName("bussinessObjects."+reflect);
				Method[] methods = obj_reflect.getDeclaredMethods();
				String last_method = "";
				for(Method method : methods) {
					if(! last_method.equals(method.getName()))
					{

						System.out.println(reflect + method.getName());
					}
					last_method = method.getName();
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
