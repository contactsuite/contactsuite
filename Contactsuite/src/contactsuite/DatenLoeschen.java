package contactsuite;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

//import com.sun.xml.internal.bind.v2.TODO;

import contactsuite.*;

/**
 * Servlet implementation class DatenLoeschen
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String typ = request.getParameter("typ");

		HttpSession sitzung = request.getSession();

		int loeschID = Integer.valueOf(request.getParameter("ID"));
		int benutezrID = (Integer) sitzung.getAttribute("benutzerID");

		DatabaseConnection dbConnect = DatabaseConnection.getInstance();

		PrintWriter out = response.getWriter();

		Kontakt pkontakt = dbConnect.getPrivatkontaktById(loeschID);
		int erstelltVon = pkontakt.getErstelltVon();

		if (typ.equals("privat")) {
			// Für Privatkontakte
			if (benutezrID == erstelltVon) {

				dbConnect.loescheKontakt(loeschID);
				request.getRequestDispatcher("Controller?fcode=Privatkontakte")
						.forward(request, response);

			} else {

				out.println("<p>Sie haben keine Berechtigung diesen Kontakt zu löschen</p>");
				request.getRequestDispatcher("Controller?fcode=Privatkontakte")
						.forward(request, response);

			}
		} else if (typ.equals("firma")) {

			// Für Firmenkontakte
			dbConnect.loescheKontakt(loeschID);
			request.getRequestDispatcher("Controller?fcode=Firmenkontakte")
					.forward(request, response);

		} else if (typ.equals("benutzer")) {
			// Für Benutzer
			if (benutezrID != loeschID)
				dbConnect.loescheBenutzer(loeschID);
			request.getRequestDispatcher("Controller?fcode=Benutzer").forward(
					request, response);

		} else {

			out.println("<p>Sie können sich nicht selber löschen!</p>");
			request.getRequestDispatcher("Controller?fcode=Benutzer").forward(
					request, response);

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
