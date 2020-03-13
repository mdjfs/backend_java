package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import communication.Execute;
import dbComponent.DBComponent;
import dbComponent.Pool;
import helpers.Query;

public class ChargeObjects {

	public static void main(String[] args) {
		DBComponent database = Pool.getDBInstance();
		ArrayList<Query> querys = new ArrayList<Query>();
		Execute getNames = new Execute();
		HashMap<String, String[]> names;
		try {
			names = getNames.getObjectsAndMethods();
			for(String key : names.keySet()) {
				// verifica si estan todos los objetos, si no, añade el objeto faltante a la lista de querys
				ResultSet rs = database.exeQueryRS("select.where.name_object", new Object[] {key});
				if ( ! rs.next() ) {
					querys.add(new Query("insert.object", new Object[] {key}));
				}
			}
			// hace el update de los objetos
			database.exeBatch(querys);
			//se limpia el hashmap para añadir metodos
			querys.clear();
			for(String key : names.keySet()) {
				//verifica si estan todos los metodos, si no, obtiene la ID del objeto y añade el metodo a la lista de querys
				String[] name_methods = names.get(key);
				for(String method : name_methods) {
					if( ! method.equals("")) {
						ResultSet rs = database.exeQueryRS("select.where.name_method", new Object[] {method});
						if( ! rs.next() ) {
							ResultSet rsobj = database.exeQueryRS("select.where.name_object", new Object[] {key});
							while(rsobj.next())
							{
								int id_obj = rsobj.getInt("id_object");
								querys.add(new Query("insert.method", new Object[] {method, id_obj}));
							}
						}
					}
				}
			}
			database.exeBatch(querys);
			Pool.returnDBInstance(database);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
