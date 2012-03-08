package contactsuite;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String typ = request.getParameter("typ");

		int id = Integer.valueOf(request.getParameter("ID")) ;


		DatabaseConnection dbConnect = DatabaseConnection.getInstance();


		if(typ.equals("privat")){
			
			// Für Privatkontakte
			dbConnect.loescheKontakt(id);
			request.getRequestDispatcher("Controller?fcode=Privatkontakte").forward(request, response);
			
		}else if(typ.equals("firma")){
			
			// Für Firmenkontakte
			dbConnect.loescheKontakt(id);
			request.getRequestDispatcher("Controller?fcode=Firmenkontakte").forward(request, response);
			
		}else if(typ.equals("benutzer")){
			
			// Für Benutzer
			dbConnect.loescheBenutzer(id);
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
