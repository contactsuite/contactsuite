package contactsuite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Die Klasse ErrorHandler stellt eine Methode bereit um Exceptions in eine
 * Datenbanktabelle zu schreiben damit man dort Fehler einsehen kann.
 * 
 * @author Dominik Ferber
 * 
 */
public class ErrorHandler {
	/**
	 * Schreibt eine Fehlermeldung in die Datenbank.
	 * 
	 * @param e
	 *            : übergebene Fehlermeldung
	 * @exception ClassNotFoundException
	 *                : Tritt ein falls der Datenbanktreiber nicht installiert
	 *                ist.
	 * @exception SQLException
	 *                : Tritt ein wenn ein Fehler in der SQL-Syntax vorliegt.
	 */
	public static void writeError(Exception e) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://pma.postopus.de/customers_s001",
					"customers_s001", "dqJAMKPR2x5B5JV8");
			Statement stmt = connection.createStatement();
			String sql = String
					.format("INSERT INTO tblerrors (error,location) VALUES ('%s','%s');",
							e.getMessage(), e.getLocalizedMessage());
			stmt.executeUpdate(sql);
		} catch (ClassNotFoundException err) {
			System.out.println(err);
		} catch (SQLException err) {
			System.out.println(err);
		}
	}
}
