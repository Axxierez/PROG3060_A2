package prog3060.zmag_a2;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/MenuManagerServlet")
public class MenuManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String id = request.getParameter("id");
        String code = request.getParameter("code");
        String level = request.getParameter("level");
        String altCode = request.getParameter("altCode");
        String geoAreaName = request.getParameter("geoAreaName");
        
        String ACTION = request.getParameter("action");

        if ("".equals(id)) {session.setAttribute("id", null); id = null;} 
        else {session.setAttribute("id", id);}
        
        if ("".equals(code)) {session.setAttribute("code", null); code = null;} 
        else {session.setAttribute("code", code);}
        
        if ("".equals(level)) {session.setAttribute("level", null); level = null;} 
        else {session.setAttribute("level", level);}
        
        if ("".equals(altCode)) {session.setAttribute("altCode", null); altCode = null;} 
        else {session.setAttribute("altCode", altCode);}
        
        if ("".equals(geoAreaName)) {session.setAttribute("geoAreaName", null); geoAreaName = null;} 
        else {session.setAttribute("geoAreaName", geoAreaName);}
        
        if ("".equals(ACTION)) {session.setAttribute("action", null); ACTION = null;} 
        else {session.setAttribute("action", ACTION);}

        if("VIEW_ALL_CLASSIFICATIONS".equals(ACTION)){
            response.sendRedirect("./geoClassifications.jsp");
            return;
        }
        if("VIEW_ALL_GEOAREAS".equals(ACTION)){
            response.sendRedirect("./geoAreas.jsp");
            return;
        }
        if("VIEW_DETAILS".equals(ACTION)){
            response.sendRedirect("./geoArea.jsp");
            return;
        }
        
        response.sendRedirect("./geoAreas.jsp");
        return;
	}
}
