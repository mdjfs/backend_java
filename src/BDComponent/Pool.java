package BDComponent;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

public class Pool {
	
	private static BasicDataSource myDs= null;
	
	//metodo que obtiene la fuente de los datos
	public static DataSource getDataSourse() {
		 if(myDs==null) {
			 myDs=new BasicDataSource();
			 myDs.setDriverClassName("postgresql-9.1-901.jdbc4.jar");
			 myDs.setUsername("UserName");
			 myDs.setPassword("Password");
			 myDs.setUrl("URL");
			 //Definiendo el tamanio de pool
			 myDs.setInitialSize(50);
			 myDs.setMaxIdle(10);
			 myDs.setMaxTotal(20);
			 myDs.setMaxWaitMillis(3000);
		 }
		return myDs;
	}
	
	public static Connection getConnetion() throws SQLException{
		return getDataSourse().getConnection();
	}
}
