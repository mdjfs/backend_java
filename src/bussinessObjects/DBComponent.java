package bussinessObjects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBComponent {
	
	private Connection conn = null;
	private ConfigComponent config_db = new ConfigComponent("/home/mdjfs/Documentos/db_config.properties");
	private Properties db_properties = null;
	
	public DBComponent() 
	{
		try 
		{
			db_properties = config_db.getObjectProperties();
			Class.forName(db_properties.getProperty("db.driver"));
			this.conn = DriverManager.getConnection(db_properties.getProperty("db.url")
													,db_properties.getProperty("db.username")
													,db_properties.getProperty("db.password"));
		} 
		catch (SQLException | ClassNotFoundException sqle) 
		{
	            System.out.println("Error connecting in SQL: " + sqle);
		}
	}
	
	public void ExeBath() {
		
	}
	
}
