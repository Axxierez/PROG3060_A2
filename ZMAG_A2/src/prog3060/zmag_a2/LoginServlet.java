package prog3060.zmag_a2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    static final String CONNECTION_STRING = "jdbc:derby://localhost:1527/CanadaCensusDB";
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username= request.getParameter("username");
		String password = request.getParameter("password");
		
		try {
	        request.getSession().setAttribute("error", null);
			OpenConnection(username, password, request);
		} catch (Exception e) {
	        request.getSession().setAttribute("error", "Incorrect credentials.");
            response.sendRedirect("./login.jsp");
            return;
		}
		MenuManagerServlet s = new MenuManagerServlet();
		s.doGet(request, response);
	}
	
	
	  private void OpenConnection(String username, String password,HttpServletRequest request) throws SQLException, ClassNotFoundException
	    {
	        Properties connProperties = new Properties();
	        connProperties.put("user", username);
	        connProperties.put("password", password);
	        
	        Class.forName("org.apache.derby.jdbc.ClientDriver");
	        Connection conn = DriverManager.getConnection(CONNECTION_STRING, connProperties);
	        conn.setAutoCommit(false);
	        conn.createStatement().executeUpdate("SET SCHEMA APP");

	        request.getSession().setAttribute("dbConnection", conn);
	    }

}
