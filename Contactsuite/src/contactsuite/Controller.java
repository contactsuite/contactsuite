package contactsuite;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.*;

/**
 * Das Servlet Controller �bernimmt die Navigation des Anwenders durch das
 * System. S�mtliche Oberfl�chen die in dem System umgesetzt sind, senden
 * Anfragen an den Controller, welche ausgewertet und an das entsprechende Ziel
 * weitergeleitet werden.
 * 
 * @author Eduard Dojan
 * 
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

	// Steuer und Parameterliste
	static String codes[][] = {
			new String[] { "Start", "/Login.jsp" },
			new String[] { "Login", "/LoginProzedur.jsp" },
			new String[] { "Logout", "/Logout.jsp" },
			new String[] { "Privatkontakte", "/Privatkontaktverwaltung.jsp" },
			new String[] { "Firmenkontakte", "/Firmenkontaktverwaltung.jsp" },
			new String[] { "Benutzer", "/Benutzerverwaltung.jsp" },
			new String[] { "Details", "/Detailansicht.jsp" },
			new String[] { "PrivatkontaktAnlegen", "/PrivatkontaktAnlegen.jsp" },
			new String[] { "FirmenkontaktAnlegen", "/FirmenkontaktAnlegen.jsp" },
			new String[] { "PrivatkontaktSpeichern", "PrivatkontaktAnlegen" },
			new String[] { "FirmenkontaktSpeichern", "FirmenkontaktAnlegen" },
			new String[] { "BenutzerSpeichern", "BenutzerAnlegen" },
			new String[] { "KontaktBearbeiten", "/KontaktBearbeiten.jsp" },
			new String[] { "Benutzerverwaltung", "/Benutzerverwaltung.jsp" },
			new String[] { "BenutzerAnlegen", "/BenutzerAnlegen.jsp" },
			new String[] { "Loeschen", "DatenLoeschen" },
			new String[] { "Registrierung", "/Registrierung.jsp" },
			new String[] { "ZugriffVerweigert", "/ZugriffVerweigert.jsp" },
			new String[] { "Timeout", "/Timeout.html" },
			new String[] { "BenutzerBearbeiten", "/BenutzerBearbeiten.jsp" },
			new String[] { "Validator", "Validierung" }

	};

	/**
	 * 
	 * 
	 * 
	 * @param request: Http request
	 * @param response: Http response
	 * 
	 * 
	 * 
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String fcode = request.getParameter("fcode");
		fcode = fcode == null ? codes[0][0] : fcode;

		String target = null;
		boolean gefunden = false;

		for (int i = 0; i < codes.length; i++) {
			if (fcode.equals(codes[i][0])) {
				request.setAttribute(fcode, codes[i]);
				target = codes[i][1];
				gefunden = true;
				break;
			}
		}
		PrintWriter out = response.getWriter();

		if (gefunden) {
			request.getRequestDispatcher(target).forward(request, response);
			out.print(response);
		} else {
			out.print("<h1>Kein Ziel f�r diese Anforderung!</h1>");
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
