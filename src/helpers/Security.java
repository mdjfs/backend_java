package helpers;

import dbComponent.Pool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import communication.Execute;
import dbComponent.DBComponent;

public class Security {
		private HashMap<Integer, HashMap<String, String[]>> permissions = new HashMap<Integer, HashMap<String, String[]>>();
	
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
				// carga los perfiles
				ResultSet rs = database.exeQueryRS("select.profile", new Object[] {});
				while(rs.next()) {
					permissions.put(rs.getInt(1), null);
				}
				// se extrae los permisos
				permissions.forEach((id, map) -> 
					{
						try {
							ResultSet result = database.exeQueryRS("select.where.id_profile_permissions", new Object[] {id});
							ArrayList<Integer> idmethods = new ArrayList<Integer>();
							while(result.next()) {
								idmethods.add(result.getInt(2));
							}
							HashMap<String, ArrayList<String>> profilepermissions = new HashMap<String, ArrayList<String>>();
							result = database.exeQueryRS("innerjoin.object.method", new Object[] {});
							while(result.next()) {
								if(!profilepermissions.containsKey(result.getString(3)))
									profilepermissions.put(result.getString(3), new ArrayList<String>());
								if(idmethods.contains(result.getInt(1))) {
									ArrayList<String> addmethod = profilepermissions.get(result.getString(3));
									addmethod.add(result.getString(2));
									profilepermissions.put(result.getString(3), addmethod);
								}
							}
							HashMap<String, String[]> profilepermissionsarray = new HashMap<String, String[]>();
							profilepermissions.forEach((object, methods) -> {
								int i=0;
								String[] methodsarray = new String[methods.size()];
								for(String method : methods) {
									System.out.println(method);
									methodsarray[i] = method;
									i++;
								}
								System.out.println(object);
								profilepermissionsarray.put(object, methodsarray);
							});
							permissions.put(id, profilepermissionsarray);
							System.out.println(id);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					});
				Pool.returnDBInstance(database);
			}
		}
		
		public boolean isHavePermissions(int id, String method, String object) {
			HashMap<String, String[]> checker = permissions.get(id);
			String[] methods = checker.get(object);
			for(String methodchecker : methods) {
				if(methodchecker.equals(method))
					return true;
			}
			return false;
		}
}
