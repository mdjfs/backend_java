package communication;

import java.io.IOException;
import com.google.gson.Gson;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet{
	// mdjfs: declaration of this servlet in WebContent/WEB-INF/web.xml

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// mdjfs: returns Hello World ! on servlet with method GET
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<h1> hello powerpoint!</h1>");
			out.close();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		System.out.println(request.getParameter("json"));
	}

}
