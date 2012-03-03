package contactsuite;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import contactsuite.*;

/**
 * Servlet implementation class KontaktAnlegen
 */
@WebServlet("/PrivatkontaktAnlegen")
public class PrivatkontaktAnlegen extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrivatkontaktAnlegen() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Prüfung auf die Existenz einer gültigen Sitzung
		HttpSession sitzung = request.getSession(false);
		if(sitzung.getAttribute("benutzerID") == null){
			request.getRequestDispatcher("Controller?fcode=Timeout").forward(request, response);
		}
		else{

			DatabaseConnection dbConnect = DatabaseConnection.getInstance();

			Integer benutzerID = (Integer) sitzung.getAttribute("benutzerID");

			String vorname = request.getParameter("vorname");
			String nachname = request.getParameter("nachname");
			String strasse = request.getParameter("strasse");
			String hausnummer = request.getParameter("hausnummer");
			String plz = request.getParameter("plz");
			String ort = request.getParameter("ort");
			String email = request.getParameter("email");
			String telefonnummer = request.getParameter("telefon")+ "/" + request.getParameter("telefon2");
			int kontaktID = 0;
			if(request.getParameterMap().containsKey("kontaktID"))
				kontaktID = Integer.valueOf(request.getParameter("kontaktID"));
			
			// wird der Kontakt öffentlich (für Alle Nutzer sichtbar) oder privat (nur für den Ersteller sichtbar) abgespeichert?
			String istOeffentlich = request.getParameter("istOeffentlich");
			boolean oeffentlich = istOeffentlich.equals("oeffentlich") ? true : false;

			Privatkontakt kon = new Privatkontakt();

			kon.setVorname(vorname);
			kon.setNachname(nachname);
			kon.setStrasse(strasse);
			kon.setHausnummer(hausnummer);
			kon.setPlz(plz);
			kon.setOrt(ort);
			kon.setEmail(email);
			kon.setTelefonnummer(telefonnummer);
			kon.setKontaktID(kontaktID);
			kon.setErstelltVon(benutzerID);
			kon.setIstOeffentlich(oeffentlich);

			dbConnect.SpeicherDaten(kon);

			request.getRequestDispatcher("Controller?fcode=Privatkontakte").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
