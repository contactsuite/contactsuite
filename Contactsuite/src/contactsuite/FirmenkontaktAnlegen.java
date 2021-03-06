package contactsuite;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Das Servlet FirmenkontaktAnlegen ist f�r die korrekte Weiterleitung von
 * Kontaktdaten an die Serviceklasse "Datenbankverbindung.java" zust�ndig. Es
 * empf�ngt diese Daten von der JSP "FirmenkontaktAnlegen.jsp".
 * 
 * @author Ercument Topal
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
	 * Leitet die Kontaktdaten an die Serviceklasse weiter.
	 * 
	 * @param request
	 *            : Http request
	 * @param response
	 *            : Http response
	 * 
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Pr�fung auf die Existenz einer g�ltigen Sitzung
		HttpSession sitzung = request.getSession(false);
		if (sitzung.getAttribute("benutzerID") == null) {
			request.getRequestDispatcher("Controller?fcode=Timeout").forward(
					request, response);
		} else {

			Datenbankverbindung dbConnect = Datenbankverbindung.getInstance();

			Integer benutzerID = (Integer) sitzung.getAttribute("benutzerID");

			String firmenname = request.getParameter("firmenname");
			String ansprechpartner = request.getParameter("ansprechpartner");
			String strasse = request.getParameter("strasse");
			String hausnummer = request.getParameter("hausnummer");
			String plz = request.getParameter("plz");
			String ort = request.getParameter("ort");
			String email = request.getParameter("email");
			String telefonnummer = request.getParameter("telefon") + "/"
					+ request.getParameter("telefon2");
			int kontaktID = 0;
			if (request.getParameterMap().containsKey("kontaktID"))
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

			request.getRequestDispatcher("Controller?fcode=Firmenkontakte")
					.forward(request, response);
		}
	}

	/**
	 * @see doGet
	 * 
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
