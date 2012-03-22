package contactsuite;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Das Servlet Logout handhabt den Logout eines Nutzers aus dem System. Hierbei
 * werden Sitzungen von Nutzern gelöscht die sich im System abmelden.
 * 
 * @author Fabian Ostermeier
 * 
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Logout() {
		super();
	}

	/**
	 * 
	 * Löscht die Sitzung eines Nutzers der sich abmeldet.
	 * 
	 * @param request
	 *            : Http request
	 * @param response
	 *            : Http response
	 * 
	 * 
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession sitzung = request.getSession(false);

		if (sitzung == null) {
			request.getRequestDispatcher("Controller?fcode=Timeout").forward(
					request, response);
		} else {
			sitzung.invalidate();
		}

		request.getRequestDispatcher("Controller?fcode=Start").forward(request,
				response);
	}

	/**
	 * @see doGet
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
