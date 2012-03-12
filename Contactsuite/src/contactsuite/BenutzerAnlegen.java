package contactsuite;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class BenutzerAnlegen
 */
@WebServlet("/BenutzerAnlegen")
public class BenutzerAnlegen extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BenutzerAnlegen() {
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

			String email = request.getParameter("email");
			String passwort = request.getParameter("passwort");
			
			// Ist dieser Benutzer freigeschaltet?
			String istFreigeschaltet = request.getParameter("istFreigeschaltet");
			boolean freigeschaltet = istFreigeschaltet.equals("freigeschaltet") ? true : false;
			
			// Ist dieser Benutzer ein Admin?
			String istAdmin = request.getParameter("istAdmin");
			boolean admin = istAdmin.equals("admin") ? true : false;
			
			int userID = 0;
			if(request.getParameterMap().containsKey("userID"))
				userID = Integer.valueOf(request.getParameter("userID"));
			
			Benutzer user = new Benutzer();
			
			user.setEmail(email);
			user.setPasswort(passwort);
			user.setIstFreigeschaltet(freigeschaltet);
			user.setIstAdmin(admin);
			user.setBenutzerID(userID);

			dbConnect.speicherDaten(user);

			request.getRequestDispatcher("Controller?fcode=Benutzer").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
