package contactsuite;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import contactsuite.*;

/**
 * Servlet implementation class KontaktAnlegen
 */
@WebServlet("/KontaktAnlegen")
public class KontaktAnlegen extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KontaktAnlegen() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DatabaseConnection dbConnect = DatabaseConnection.getInstance();


		String vorname = request.getParameter("vorname");
		String nachname = request.getParameter("nachname");
		String strasse = request.getParameter("strasse");
		String hausnummer = request.getParameter("hausnummer");
		String plz = request.getParameter("plz");
		String ort = request.getParameter("ort");
		String email = request.getParameter("email");
		String telefonnummer = request.getParameter("telefonnummer");
		int kontaktID = 0;
		if(request.getParameterMap().containsKey("kontaktID"))
			kontaktID = Integer.valueOf(request.getParameter("kontaktID"));

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

		dbConnect.SpeicherDaten(kon);

		request.getRequestDispatcher("Controller?fcode=Kontaktverwaltung").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
