package bussinessObjects;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Calendar;

import com.google.gson.JsonObject;

import dbComponent.DBComponent;
import dbComponent.Pool;
import helpers.HashPassword;
import helpers.JSONManage;
import helpers.Query;

public class Register {
	
	private JSONManage json_manage = new JSONManage();
	
	public JsonObject registerUser(String name, String surname, String email, String password) {
		HashPassword hashing = new HashPassword();
		try 
		{
			String hash = hashing.toHashPassword(password);
			DBComponent database = Pool.getDBInstance();
			if( database != null) {
				String creationtime = "Fecha: " + Calendar.DAY_OF_MONTH + "/"
						+ Calendar.MONTH + "/" + Calendar.YEAR + " Hora: "
						+ Calendar.HOUR_OF_DAY + ":" + Calendar.MINUTE + ":" + Calendar.SECOND;
				if( email.contains("@")) {
					database.exeSimple(new Query("insert.users", new Object[] {name, surname, email, hash, creationtime}));
					Pool.returnDBInstance(database);
					return json_manage.ReportSuccessMessage("Welcome "+name+" "+surname+" You're registered !");
				}
				else {
					return json_manage.ReportErrorMessage("Verify your email address");
				}
			}
			else {
				return json_manage.ReportErrorMessage("Error in database consults");
			}
		} catch (NoSuchAlgorithmException | SQLException e) 
		{
			e.printStackTrace();
			return json_manage.ReportErrorMessage(e.getMessage());
		}
	}
}
