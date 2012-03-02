package contactsuite;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.*;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
       
    }

    //Steuer und Parameterliste
    static String codes[][] = {
    	new String [] { "Start", "/Login.jsp"},
    	new String [] { "Login", "/LoginProzedur.jsp"},
    	new String [] { "Logout", "/Logout.jsp"},
    	new String [] { "Kontaktverwaltung", "/Kontaktverwaltung.jsp"},
    	new String [] { "Details", "/Detailansicht.jsp"},
    	new String [] { "KontaktAnlegen", "/KontaktAnlegen.html"},
    	new String [] { "KontaktSpeichern", "KontaktAnlegen"},
    	new String [] { "KontaktBearbeiten", "/KontaktBearbeiten.jsp" },
    	new String [] { "KontaktSuche", "/KontaktSuche.jsp" },
    	new String [] { "Benutzerverwaltung", "/Benutzerverwaltung.jsp"},
    	new String [] { "BenutzerAnlegen", "/BenutzerAnlegen.jsp"},
    	new String [] { "Registrierung", "/Registrierung.jsp"},
    	new String [] { "Impressum", "/Impressum.jsp"}
    	
    };
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String fcode =  request.getParameter("fcode");
		fcode = fcode == null ? codes[0][0] : fcode;
		
		String target = null;
		boolean gefunden = false;
		
		for(int i = 0; i < codes.length; i++){
			if(fcode.equals(codes[i][0])){
				request.setAttribute(fcode, codes[i]);
				target = codes[i][1];
				gefunden = true;
				break;
			}	
		}
		PrintWriter out = response.getWriter();
		
		if(gefunden){
			request.getRequestDispatcher(target).forward(request, response);
			out.print(response);
		}
		else{
			out.print("<h1>Kein Ziel für diese Anforderung!</h1>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
