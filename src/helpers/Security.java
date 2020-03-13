package helpers;

import dbComponent.Pool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import communication.Execute;
import dbComponent.DBComponent;

public class Security {
		private HashMap<String, HashMap<String, String[]>> permissions = new HashMap<String, HashMap<String, String[]>>();
	
		public Security() throws ClassNotFoundException, SQLException {
			/* El constructor se encarga de cargar los nombres de metodos y objetos en la base de datos */ 
			DBComponent database = Pool.getDBInstance();
			if(database != null)
			{
				ArrayList<Query> querys = new ArrayList<Query>();
				Execute getNames = new Execute();
				HashMap<String, String[]> names = getNames.getObjectsAndMethods();
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
				// hace el update de los metodos
				database.exeBatch(querys);
				Pool.returnDBInstance(database);
			}
		}
		
		public void chargePermissions() throws SQLException {
			DBComponent database = Pool.getDBInstance();
			if(database != null) {
				// se extrae los permisos
				ResultSet rs = database.exeQueryRS("select.permissions", new Object[] {});
			}
		}
}
