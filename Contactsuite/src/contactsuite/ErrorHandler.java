package contactsuite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Klasse stellt eine Methode bereit um Exceptions in eine Datenbanktabelle zu schreiben damit man vor dort
 * Fehler einsehen kann.
 * @author Dominik Ferber
 * 
 */
public class ErrorHandler {
	public static void writeError(Exception e){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://pma.postopus.de/customers_s001", "customers_s001", "dqJAMKPR2x5B5JV8");
			Statement stmt = connection.createStatement();
			String sql = "INSERT INTO tblerrors (error,location) VALUES (\""+e.getMessage()+"\",\""+e.getLocalizedMessage()+"\");";
			stmt.executeUpdate(sql);
		} 
		catch (ClassNotFoundException err) {
			System.out.println(err);
		} 
		catch (SQLException err) {
			System.out.println(err);
		}
	}
}
