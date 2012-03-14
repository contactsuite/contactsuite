package contactsuite;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import contactsuite.*;

/**
 * Servlet implementation class Validierung
 */
@WebServlet("/Validierung")
public class Validierung extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Validierung() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameterMap().containsKey("Registrieren")){
			PrintWriter out = response.getWriter();
			
			String email = request.getParameter("email");
			String passwort = request.getParameter("passwort");
			String passwort2 = request.getParameter("passwort2");			
			
			out.println(email+" "+passwort+" "+passwort2);
			Benutzer neuerBenutzer = new Benutzer();
			neuerBenutzer.setEmail(email);
			neuerBenutzer.setPasswort(passwort);
			//Abfragen ob der Benutzer schon existiert
			if(!passwort.equals("") && passwort.equals(passwort2)){
				if(Datenbankverbindung.getInstance().istBenutzerVorhanden(neuerBenutzer)){
					request.setAttribute("Meldung", "Benutzer ist schon vorhande.n");
					request.getRequestDispatcher("Controller?fcode=Start").forward(request, response);
				}
				else if(Datenbankverbindung.getInstance().speicherDaten(neuerBenutzer)==1){
					request.setAttribute("Meldung", "Benutzer erfolgreich angelegt.");
					request.getRequestDispatcher("Controller?fcode=Start").forward(request, response);
				}
				else{
					request.setAttribute("Meldung", "Benutzer nicht erfolgreich gespeichert.");
					request.getRequestDispatcher("Controller?fcode=Start").forward(request, response);
				}
			} else {
				request.setAttribute("Meldung", "Die Passw&ouml;rter strimmen nicht &uuml;berein.");
				request.getRequestDispatcher("Controller?fcode=Start").forward(request, response);
			}

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
