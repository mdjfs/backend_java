package communication;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import aux.Reformateador;

@WebServlet("/Dispacher")
public class Dispacher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Dispacher() {
        super();
    }

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		// json to tests: {\"objName\":\"Persona\",\"methodName\":\"getEstado\",\"params\":[1],\"types\":[\"int\"]}
		String json = request.getParameter("json");
		Reformateador formato = new Reformateador(json);
		Pojo objeto_json = formato.getFormatPojo();
		Execute reflect= new Execute(objeto_json);
		String result = reflect.run();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		JSONObject result_json = new JSONObject();
		result_json.put("result",result);
		result_json.put("message","success");
		out.print(result_json);
	}
	
	/* protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String objName = request.getParameter("objName");
		String methodName = request.getParameter("methodName");
		String params = request.getParameter("params");
		
		//String json = "{'objName':'objeto','methodName':'metodo', 'paramsInt':[54,53], 'paramsFloat':[43.3,32.3], 'paramsString':['hola','como']}";
		String json = "{'objName':'objeto','methodName':'metodo'}";

		Gson gson = new Gson();
		Pojo objeto_gson = gson.fromJson(json, Pojo.class);
		
		Execute  ex = new Execute(objeto_gson);
		
		String resul1 = ex.getData(objName,methodName);
		//String resul2 = ex.getData(objName,methodName,params);
		
		System.out.println(params+objName);
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<h1>"+resul1+"</h1>");
		//out.println("<h1>"+resul2+"</h1>");
		out.close();
	} */

}
