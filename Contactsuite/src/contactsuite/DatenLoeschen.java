package contactsuite;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

//import com.sun.xml.internal.bind.v2.TODO;

import contactsuite.*;

/**
 * Das Servlet DatenLoeschen steuert die Serviceklasse
 * "Datenbankverbindung.java" an und teilt dieser mit welche Benutzer,
 * Privatkontakte oder Firmenkontakte aus der Datenbank entfernt werden sollen.
 * 
 * @author Eduard Dojan
 * 
 */
@WebServlet("/DatenLoeschen")
public class DatenLoeschen extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DatenLoeschen() {
		super();
	}

	/**
	 * Spricht die L�schenfunktion der Serviceklasse an und teilt ihr mit welche
	 * Datens�tze zu l�schen sind.
	 * 
	 * @param request
	 *            : Http request
	 * @param response
	 *            : Http response
	 * 
	 * @author Eduard Dojan
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String typ = request.getParameter("typ");

		HttpSession sitzung = request.getSession();

		int loeschID = Integer.valueOf(request.getParameter("ID"));
		int benutezrID = (Integer) sitzung.getAttribute("benutzerID");

		Datenbankverbindung dbConnect = Datenbankverbindung.getInstance();

		PrintWriter out = response.getWriter();

		Kontakt pkontakt = dbConnect.getPrivatkontaktById(loeschID);
		int erstelltVon = pkontakt.getErstelltVon();

		if (typ.equals("privat")) {
			// F�r Privatkontakte
			if (benutezrID == erstelltVon) {

				dbConnect.loescheKontakt(loeschID);
				request.getRequestDispatcher("Controller?fcode=Privatkontakte")
						.forward(request, response);

			} else {

				out.println("<p>Sie haben keine Berechtigung diesen Kontakt zu l�schen</p>");
				request.getRequestDispatcher("Controller?fcode=Privatkontakte")
						.forward(request, response);

			}
		} else if (typ.equals("firma")) {

			// F�r Firmenkontakte
			dbConnect.loescheKontakt(loeschID);
			request.getRequestDispatcher("Controller?fcode=Firmenkontakte")
					.forward(request, response);

		} else if (typ.equals("benutzer")) {
			// F�r Benutzer
			if (benutezrID != loeschID)
				dbConnect.loescheBenutzer(loeschID);
			dbConnect.loescheKontakteByBenutzerId(loeschID);
			request.getRequestDispatcher("Controller?fcode=Benutzer").forward(
					request, response);

		} else {

			out.println("<p>Sie k�nnen sich nicht selber l�schen!</p>");
			request.getRequestDispatcher("Controller?fcode=Benutzer").forward(
					request, response);

		}
	}

	/**
	 * @see doGet
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
