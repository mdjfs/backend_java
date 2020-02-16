package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import helper.JSONResponses;

@WebServlet("/Dispatcher")
public class Dispatcher extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StringBuffer buffertext = null;
	private Gson gson = new Gson();
	private JSONResponses json_outputs = new JSONResponses();
       
    public Dispatcher() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		buffertext = new StringBuffer();
		BufferedReader reader = request.getReader();
		String line = "";
		while ((line = reader.readLine()) != null)
			buffertext.append(line);
		String json = buffertext.toString();
		try {
			JsonElement check = new JsonParser().parse(json);
			if(check.isJsonObject()) {
				boolean have_objname = json.contains("\"objName\"");
				boolean have_methodname = json.contains("\"methodName\"");
				boolean have_params = json.contains("\"params\"");
				boolean have_types = json.contains("\"types\"");
				if(have_objname && have_methodname && have_params && have_types)
				{
					Pojo object_reflection = gson.fromJson(json, Pojo.class);
					if(object_reflection.CheckArrays()) {
						object_reflection.setParams();
						Execute run = new Execute();
						out.println(run.invoke(object_reflection));
					}
					else {
						out.println(json_outputs.ReportErrorMessage("The format of params or types is incorrect"));
					}
				}
				else
				{
					out.println(json_outputs.ReportErrorMessage("Missing a parameter !"));
				}
			}
			else
			{
				out.println(json_outputs.ReportErrorMessage("The request is not a json !"));
			}
		}
		catch(JsonSyntaxException e){
			out.println(json_outputs.ReportErrorMessage("The json have a syntax error"));
			e.printStackTrace();
		}
	}
	

}
