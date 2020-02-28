package BDComponent;

import helpers.ConfigComponent;
import helpers.QueryHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DBComponent {
	
	private Connection conn = null;
	private ConfigComponent config_db = new ConfigComponent("/home/mdjfs/Documentos/db_config.properties");
	private Properties db_properties = null;
	private QueryHandler querys = new QueryHandler();
	
	public DBComponent(){
		try{
			db_properties = config_db.getObjectProperties();
			Class.forName(db_properties.getProperty("db.driver"));
			this.conn = DriverManager.getConnection(db_properties.getProperty("db.url")
													,db_properties.getProperty("db.username")
													,db_properties.getProperty("db.password"));
		} 
		catch (SQLException | ClassNotFoundException sqle){
	            System.out.println("Error connecting in SQL: " + sqle);
		}
	}
	
	public List<Map<String, Object>> ExeQuery(String id, Object[] params) {
		String query_execute = querys.GetQuery(id);
		List<Map<String , Object>>	result  = new ArrayList<Map<String,Object>>();
		try {
			PreparedStatement sentence = conn.prepareStatement(query_execute);
			char[] detect_params = query_execute.toCharArray();
			int params_count = 0;
			for(int i=0; i<detect_params.length;i++)
			{
				if(detect_params[i] == '?')
				{
					params_count += 1;
					sentence.setObject(params_count, params[params_count]);
				}
			}
			ResultSet rs = sentence.executeQuery();
			ResultSetMetaData meta_data = rs.getMetaData();
			while(rs.next()) {
				Map<String, Object> querys = new HashMap<String, Object>();
				for(int i=0; i< meta_data.getColumnCount() ; i++) {
					String column_name = meta_data.getColumnName(i);
					Object column_value = rs.getObject(i);
					querys.put(column_name, column_value);
				}
				result.add(querys);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public void exeBacth(ArrayList<String> list_query) throws SQLException {
		//El Metodo exeBacth recibe un arrayList de String donde recorre el arreglo y ejecuta cada query.
		String query = null;
		try {
			//para usar los metodos rollback y commit el autocommit debe estar desactivado. 
		    conn.setAutoCommit(false);
		    
			for(int i=0; i<list_query.size(); i++) {
				PreparedStatement pre = conn.prepareStatement(list_query.get(i));
				query = list_query.get(i);
				pre.executeUpdate();
			}
			
			conn.commit();
			conn.setAutoCommit(true);
			System.out.println("Operacion exitosa");
			
		} catch (SQLException e) {
			//Si hay un error de cualquier tipo en el query se ejecuta el metodo rollback que deshace los cambios de la base de datos.
			conn.rollback();
			System.out.println(e.getMessage()+"\n\nQuery: "+query);
			}
	}
	
}
