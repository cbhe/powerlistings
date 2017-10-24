package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongoTools.MongoWriter;

@WebServlet("/cancel")
public class Cancel extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Cancel() {
        super();
    }
    
    
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		String dataid = (String)request.getSession().getAttribute("dataId");	
		MongoWriter mongoWriter = new MongoWriter();
		mongoWriter.writeCancel(dataid);
		mongoWriter.writeLog("cancel", dataid, "");
	}
}



























