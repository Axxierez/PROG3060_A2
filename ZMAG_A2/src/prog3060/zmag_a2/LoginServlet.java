package prog3060.zmag_a2;

import java.io.IOException;
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
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		JPABean jpaBean = (JPABean) session.getAttribute("jpaBean");
		
		String user = request.getParameter("username");
		String pass = request.getParameter("password");
		
		if(jpaBean.openConn(user, pass)) {
			jpaBean.openConn(user, pass);
			response.sendRedirect("./geoAreas.jsp");
          	return;
		} else {
	        request.getSession().setAttribute("error", "Incorrect credentials.");
	        response.sendRedirect("./login.jsp");
	        return;
		}
	}
}
