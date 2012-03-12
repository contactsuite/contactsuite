package contactsuite;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FirmenkontaktAnlegen
 */
@WebServlet("/FirmenkontaktAnlegen")
public class FirmenkontaktAnlegen extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FirmenkontaktAnlegen() {
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

					Datenbankverbindung dbConnect = Datenbankverbindung.getInstance();

					Integer benutzerID = (Integer) sitzung.getAttribute("benutzerID");

					String firmenname = request.getParameter("firmenname");
					String ansprechpartner = request.getParameter("ansprechpartner");
					String strasse = request.getParameter("strasse");
					String hausnummer = request.getParameter("hausnummer");
					String plz = request.getParameter("plz");
					String ort = request.getParameter("ort");
					String email = request.getParameter("email");
					String telefonnummer = request.getParameter("telefon")+ "/" + request.getParameter("telefon2");
					int kontaktID = 0;
					if(request.getParameterMap().containsKey("kontaktID"))
						kontaktID = Integer.valueOf(request.getParameter("kontaktID"));
					
					Firmenkontakt kon = new Firmenkontakt();

					kon.setFirmenname(firmenname);
					kon.setAnsprechpartner(ansprechpartner);
					kon.setStrasse(strasse);
					kon.setHausnummer(hausnummer);
					kon.setPlz(plz);
					kon.setOrt(ort);
					kon.setEmail(email);
					kon.setTelefonnummer(telefonnummer);
					kon.setKontaktID(kontaktID);
					kon.setErstelltVon(benutzerID);
					kon.setIstOeffentlich(true);

					dbConnect.speicherDaten(kon);

					request.getRequestDispatcher("Controller?fcode=Firmenkontakte").forward(request, response);
				}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
