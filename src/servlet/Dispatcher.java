package servlet;

import communication.Execute;
import communication.Pojo;
import dbComponent.DBComponent;
import dbComponent.Pool;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import helpers.HashPassword;
import helpers.JSONManage;
import helpers.Query;
import helpers.Security;
import helpers.User;

@WebServlet("/Dispatcher")
public class Dispatcher extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson = new Gson();
	private JSONManage json_manage = new JSONManage();
	private PrintWriter out;
	private HttpSession session;
	private Security security;
	private HashPassword hash = new HashPassword();
	private Pattern namesValidator = Pattern.compile("^([a-z ñáéíóú]{2,60})$");
	private Pattern emailValidator = Pattern.compile("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,4})+$");
	private Pattern usernameValidator = Pattern.compile("^[a-z0-9_-]{3,16}$");
	private Pattern passwordValidator = Pattern.compile("^[A-Za-z0-9\\s]+$");
       
    public Dispatcher() throws ClassNotFoundException, SQLException {
        super();
        try {
        	security = new Security();
            security.chargePermissions();
        }
        catch(ClassNotFoundException e) {
        	System.out.println("Please check your URI");
        	System.out.println(e);
        }
    }
    
    private void newProfile(String jsonText, JsonObject Gson) {
    	String[] json_keys = {"action", "email", "name_profile", "password"};
    	boolean is_all_keys = json_manage.isAllKeys(jsonText, json_keys);
    	if(is_all_keys) {
    		String email = Gson.get("email").getAsString();
    		DBComponent db = Pool.getDBInstance();
    		try {
				ResultSet rs = db.exeQueryRS("select.users.where.email_users", new Object[] {email});
				if(rs.next()) {
					if(rs.getString("password_users").equals(hash.toHashPassword(Gson.get("password").getAsString()))) {
						if(usernameValidator.matcher(Gson.get("name_profile").getAsString()).find()) {
							int id = rs.getInt("id_users");
							int idprofile = 0;
							db.exeSimple(new Query("insert.profile", new Object[] {Gson.get("name_profile").getAsString()}));
							ResultSet rs2 = db.exeQueryRS("select.profile.where.username_profile", new Object[] {Gson.get("name_profile").getAsString()});
							while(rs2.next()) {
								idprofile = rs2.getInt("id_profile");
								db.exeSimple(new Query("insert.users_profile", new Object[] {idprofile, id}));
								out.println(json_manage.ReportSuccessMessage(rs.getString("name_users") + " you new profile " +
								Gson.get("name_profile").getAsString() + " is registered"));
								break;
							}
						}
						else {
							out.println(json_manage.ReportErrorMessage("Check your username"));
						}
						
					}
					else {
						out.println(json_manage.ReportErrorMessage("The password is wrong !"));
					}
				}
				else {
					out.println(json_manage.ReportErrorMessage("User don't exist"));
				}
				
			} catch (SQLException | NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				out.println(json_manage.ReportErrorMessage(e.getMessage()));
				e.printStackTrace();
			}
    		finally {
    			Pool.returnDBInstance(db);
    		}
    	}
    	else {
    		out.println(json_manage.ReportErrorMessage("Missing a parameter ! (You don't have session) You need this keys: "
					+ json_manage.sayKeys(json_keys)));
    	}
    }
    
    private void login(String jsonText, JsonObject Gson) {
    	String[] json_keys = {"action", "username", "password"};
    	boolean is_all_keys = json_manage.isAllKeys(jsonText, json_keys);
    	if(is_all_keys) {
    		DBComponent db = Pool.getDBInstance();
    		try {
				ResultSet rs = db.exeQueryRS("select.profile.where.username_profile", new Object[] {Gson.get("username").getAsString()});
				if(rs.next()) {
					int idprofile = rs.getInt("id_profile");
					ResultSet rs2 = db.exeQueryRS("select.users_profile.where.id_profile", new Object[] {idprofile});
					if(rs2.next()) {
						int iduser = rs2.getInt("id_users");
						ResultSet rs3 = db.exeQueryRS("select.users.where.id_users", new Object[] {iduser});
						while(rs3.next()) {
							if(rs3.getString("password_users").equals(hash.toHashPassword(Gson.get("password").getAsString()))) {
								session.setAttribute("user", new User(idprofile));
								out.println(json_manage.ReportSuccessMessage("You're Log-In"));
							}
							else {
								out.println(json_manage.ReportErrorMessage("The	password is wrong !"));
							}
							break;
						}
					}
					else {
						out.println(json_manage.ReportErrorMessage("The profile not have user"));
					}
				}
				else {
					out.println(json_manage.ReportErrorMessage("Profile don't exist"));
				}
    		} catch (SQLException | NoSuchAlgorithmException e) {
				out.println(json_manage.ReportErrorMessage(e.getMessage()));
				e.printStackTrace();
			}
    		
    	}
    	else {
    		out.println(json_manage.ReportErrorMessage("Missing a parameter ! (You don't have session) You need this keys: "
					+ json_manage.sayKeys(json_keys)));
    	}
    }
    
    private void register(String jsonText, JsonObject Gson) {
    	String[] json_keys = {"action", "name", "surname", "email", "password"};
    	boolean is_all_keys = json_manage.isAllKeys(jsonText, json_keys);
    	if(is_all_keys) {
    		String name = Gson.get("name").getAsString();
    		String email = Gson.get("email").getAsString();
    		String surname = Gson.get("surname").getAsString();
    		String passwordHash;
			try {
				passwordHash = hash.toHashPassword(Gson.get("password").getAsString());
	    		if(namesValidator.matcher(name).find() && namesValidator.matcher(surname).find()) {
	    			if(emailValidator.matcher(email).find()) {
	    				if(passwordValidator.matcher(Gson.get("password").getAsString()).find()) {
	    		    		DBComponent db = Pool.getDBInstance();
	    		    		LocalDateTime dateTime = LocalDateTime.now();
	    		    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	    		    		try {
								db.exeSimple(new Query("insert.users", new Object[] {name, surname, email, passwordHash, dateTime.format(formatter).toString()}));
								out.println(json_manage.ReportSuccessMessage("You're registered"));
	    		    		} catch (SQLException e) {
								out.println(json_manage.ReportErrorMessage(e.getMessage()));
								e.printStackTrace();
							}
	    		    		finally {
	    		    			Pool.returnDBInstance(db);
	    		    		}
	    				}
	    				else {
	    					out.println(json_manage.ReportErrorMessage("Check your password."));
	    				}
	    			}
	    			else {
	    				out.println(json_manage.ReportErrorMessage("Check your email"));
	    			}
	    		}
	    		else {
	    			out.println(json_manage.ReportErrorMessage("Check your names"));
	    		}
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				out.println(json_manage.ReportErrorMessage(e1.getMessage()));
				e1.printStackTrace();
			}
    	}
    	else {
    		out.println(json_manage.ReportErrorMessage("Missing a parameter ! (You don't have session) You need this keys: "
					+ json_manage.sayKeys(json_keys)));
    	}
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("application/json");
		out = response.getWriter();
		session = request.getSession(true);
		String jsonText = json_manage.readRaw(request.getReader());
		User session_user = (User) session.getAttribute("user");
		try {
			JsonElement check = new JsonParser().parse(jsonText);
			if(check.isJsonObject()) {
				if(session_user!=null) {
					String[] json_keys = {"objName", "methodName", "params", "types"};
					boolean is_all_keys = json_manage.isAllKeys(jsonText, json_keys);
				 	if(is_all_keys)
				 	{
				 		Pojo object_reflection = gson.fromJson(jsonText, Pojo.class);
				 		if(object_reflection.CheckArrays()) {
				 			object_reflection.setParams();
				 			int id = session_user.getIDProfile();
				 			String object = object_reflection.getobjName();
				 			String method = object_reflection.getmethodName();
							if(security.isHavePermissions(id, method, object)) {
								Execute run = new Execute();
								out.println(run.invoke(object_reflection));
							}
							else {
									out.println(json_manage.ReportErrorMessage("You don't have permissions !!"));
							}
						}
						else {
								out.println(json_manage.ReportErrorMessage("The format of params or types is incorrect"));
						}
					}
					else
					{
							out.println(json_manage.ReportErrorMessage("Missing a parameter ! You need this keys: "
																		+ json_manage.sayKeys(json_keys)));
					}
				}
				else {
					String[] json_keys = {"action"};
					boolean is_all_keys = json_manage.isAllKeys(jsonText, json_keys);
					if(is_all_keys) {
						JsonObject Gson = check.getAsJsonObject();
						String action = Gson.get("action").getAsString();
						System.out.println(action);
						if(action.equals("register")) {
							register(jsonText, Gson);
						}
						else if(action.equals("login")) {
							login(jsonText, Gson);
						}
						else if(action.equals("new profile")) {
							newProfile(jsonText, Gson);
						}
						else {
							out.println(json_manage.ReportErrorMessage("Only actions is 'register', 'new profile' and 'login'"));
						}
					}
					else {
						out.println(json_manage.ReportErrorMessage("Missing a parameter ! (You don't have session) You need this keys: "
								+ json_manage.sayKeys(json_keys)));
					}
				}
			}
			else
			{
					out.println(json_manage.ReportErrorMessage("The request is not a json !"));
			}
		}
		catch(JsonSyntaxException e){
			out.println(json_manage.ReportErrorMessage("The json have a syntax error"));
			e.printStackTrace();
		}
	}
}
	

