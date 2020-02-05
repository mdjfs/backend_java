package communication;

import java.sql.*;
import java.util.ArrayList;

public class Conexion {
	private Connection cnn;
	public ResultSet rst;
	public PreparedStatement pstm;
	private ArrayList<String> arrayValor;
	private String query;
	
	
	public Conexion() {
		try {
			Class.forName("org.postgresql.Driver");
			 this.cnn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/trelo","postgres","pablo");
		} 
		catch (SQLException | ClassNotFoundException sqle) {
	            System.out.println("Error: " + sqle);
		}
	}
	
	
	public  PreparedStatement initQuery(String query) {
		try {
			pstm = this.cnn.prepareStatement(query);
			this.query = query;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pstm;
	}
	
	public void query(PreparedStatement pstm) {
		try {
			System.out.println(this.query);
			
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet queryResul(PreparedStatement pstm) {
		try {
			System.out.println(this.query);
			rst = this.pstm.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.rst;
	}

	
	public ResultSet getResul() {
		return this.rst;
	}



}
