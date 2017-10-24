package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongoTools.MongoWriter;
import tools.Tools;

@WebServlet("/suppress")
public class Suppress extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestStr = Tools.decoder(Tools.getContent(request));

        MongoWriter mongoWriter = new MongoWriter();
        mongoWriter.writeSuppress(requestStr);
        mongoWriter.writeLog("suppress", requestStr, "");
	}	
}

