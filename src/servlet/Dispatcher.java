package servlet;

import communication.Execute;
import communication.Pojo;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import helpers.JSONManage;
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String[] json_keys = {"objName", "methodName", "params", "types"};
		response.setContentType("application/json");
		out = response.getWriter();
		session = request.getSession(true);
		String json = json_manage.readRaw(request.getReader());
		try {
			JsonElement check = new JsonParser().parse(json);
			if(check.isJsonObject()) {
				boolean is_all_keys = json_manage.isAllKeys(json, json_keys);
				if(is_all_keys)
				{
					Pojo object_reflection = gson.fromJson(json, Pojo.class);
					if(object_reflection.CheckArrays()) {
						object_reflection.setParams();
						User session_user = (User) session.getAttribute("user");
						if(session_user==null) {
							session.setAttribute("user", new User());
							session_user = (User) session.getAttribute("user");
						}
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
	

