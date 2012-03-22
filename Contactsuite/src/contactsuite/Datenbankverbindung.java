/**
 * 
 */
package contactsuite;

import java.io.Console;
import java.sql.*;
import java.util.*;

/**
 * Die Klasse Datenbankverbindung ist eine Serviceklasse, die Dienste für das
 * Lesen aus der Datenbank bzw. das Schreiben in die Datenbank zur Verfügung
 * stellt.
 * 
 * 
 * @author Dominik Ferber
 * 
 * 
 */
public class Datenbankverbindung {
	private static Datenbankverbindung instance = null;

	private final String dbBenutzername = "customers_s001";
	private final String dbPasswort = "dqJAMKPR2x5B5JV8";
	private final String dbName = "jdbc:mysql://pma.postopus.de/customers_s001";
	private final String tblBenutzer = "tblbenutzer";
	private final String tblKontakt = "tblkontakt";
	private Connection verbindung;

	/**
	 * Der Konstruktor baut eine Verbindung zur Datenbank auf.
	 * 
	 * @exception ClassNotFoundException
	 *                : Tritt ein falls der Datenbanktreiber nicht installiert
	 *                ist.
	 * @exception SQLException
	 *                : Tritt ein wenn ein Fehler in der SQL-Syntax vorliegt.
	 * 
	 */
	private Datenbankverbindung() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.verbindung = DriverManager.getConnection(this.dbName,
					this.dbBenutzername, this.dbPasswort);
		} catch (ClassNotFoundException err) {
			System.out
					.println("Datenbankverbindung kann nicht hergestellt werden.");
		} catch (SQLException err) {
			System.out
					.println("Datenbankverbindung kann nicht hergestellt werden.");
		}
	}

	/**
	 * Gibt eine Referenz auf die Datenbankverbindung zurück falls diese bereits
	 * existiert. Andernfalls wird eine neue Datenbankverbindung erstellt.
	 * 
	 * @return instance: Datenbankverbindung
	 */
	public static Datenbankverbindung getInstance() {
		if (instance == null) {
			instance = new Datenbankverbindung();
		}
		return instance;
	}

	/**
	 * Gibt alle Kontakte zurück, die zu einem übergebenen Suchterm passen und
	 * der Bentzer-ID passen.
	 * 
	 * 
	 * @param suchTerm
	 *            : übergebener Suchterm
	 * @param benutzerId
	 *            : übergebene Benutzer-ID
	 * @return lstKontakte: Liste aller gefundenen Kontakte
	 */
	public List<Kontakt> getKontakte(int benutzerId, String suchTerm) {
		List<Kontakt> lstKontakte = new ArrayList<Kontakt>();
		lstKontakte.addAll(getPrivatkontakte(suchTerm, benutzerId, "nachname"));
		lstKontakte.addAll(getFirmenkontakte(suchTerm));
		return lstKontakte;
	}

	/**
	 * Gibt alle Kontakte zurück, die zu einem Benutzer gehören.
	 * 
	 * @param benutzerId
	 *            : übergebene Benutzer-ID
	 * @return lstKontakte: Liste aller gefundenen Kontakte
	 */
	public List<Kontakt> getKontakte(int benutzerId) {
		List<Kontakt> lstKontakte = new ArrayList<Kontakt>();
		lstKontakte.addAll(getPrivatkontakte("", benutzerId, "nachname"));
		lstKontakte.addAll(getFirmenkontakte(""));
		return lstKontakte;
	}

	/**
	 * Gibt alle Privatkontakte zurück, die zu einem übergebenen Suchterm und
	 * der Benutzer-ID passen.
	 * 
	 * @param suchTerm
	 *            : übergebener Suchterm
	 * @param benutzerId
	 *            : übergebene Benutzer-ID
	 * @exception SQLException
	 *                : Tritt ein falls Fehler in der MySQL Syntax vorliegen.
	 * @return lstKontakte: Liste aller gefundenen Privatkontakte
	 */
	public List<Privatkontakt> getPrivatkontakte(String suchTerm,
			int benutzerId, String sortierSpalte) {
		List<Privatkontakt> lstKontakte = new ArrayList<Privatkontakt>();

		String sql = String.format("SELECT * " + "FROM %s " + "WHERE "
				+ "(vorname LIKE '%s' OR " + "nachname LIKE '%s') AND "
				+ "istFirmenkontakt = 0 AND " + "istGeloescht = 0 AND "
				+ "(istOeffentlich = 1 OR "
				+ "(istOeffentlich = 0 AND erstelltVon = %d)) "
				+ "ORDER BY %s;", tblKontakt, suchTerm + '%', suchTerm + '%',
				benutzerId, sortierSpalte);
		try {
			Statement stmt = verbindung.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()) {
				Privatkontakt tmpKontakt = new Privatkontakt();
				tmpKontakt.setKontaktID(result.getInt("kontaktID"));
				tmpKontakt.setPlz(result.getString("plz"));
				tmpKontakt.setStrasse(result.getString("strasse"));
				tmpKontakt.setHausnummer(result.getString("hausnummer"));
				tmpKontakt.setOrt(result.getString("ort"));
				tmpKontakt.setEmail(result.getString("email"));
				tmpKontakt.setTelefonnummer(result.getString("telefonnummer"));
				tmpKontakt.setBildpfad(result.getString("bildpfad"));
				tmpKontakt.setVorname(result.getString("vorname"));
				tmpKontakt.setNachname(result.getString("nachname"));
				tmpKontakt.setIstOeffentlich(result
						.getBoolean("istOeffentlich"));
				tmpKontakt.setErstelltVon(result.getInt("erstelltVon"));
				tmpKontakt.setErstelltAm(result.getDate("erstelltAm"));
				lstKontakte.add(tmpKontakt);
			}
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}
		return lstKontakte;
	}

	/**
	 * Gibt alle Firmenkontakte zurück, die zu einem übergebenen Suchterm
	 * passen.
	 * 
	 * @param suchTerm
	 *            : übergebener Suchterm
	 * @param sortierSpalte
	 *            : Ist das Attribut nach welchem die Sortierung der Liste
	 *            erfolgt.
	 * @exception SQLException
	 *                : Tritt ein falls Fehler in der MySQL Syntax vorliegen.
	 * @return lstKontakte: Liste aller gefundenen Firmenkontakte
	 */
	public List<Firmenkontakt> getFirmenkontakte(String suchTerm,
			String sortierSpalte) {
		List<Firmenkontakt> lstKontakte = new ArrayList<Firmenkontakt>();
		String sql = String.format("SELECT * " + "FROM %s "
				+ "WHERE firmenname LIKE '%s' AND " + "istFirmenkontakt = 1 "
				+ "AND istGeloescht = 0 " + "ORDER BY %s;", tblKontakt,
				suchTerm + '%', sortierSpalte);
		try {
			Statement stmt = verbindung.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()) {
				Firmenkontakt tmpKontakt = new Firmenkontakt();
				tmpKontakt.setKontaktID(result.getInt("kontaktID"));
				tmpKontakt.setPlz(result.getString("plz"));
				tmpKontakt.setStrasse(result.getString("strasse"));
				tmpKontakt.setHausnummer(result.getString("hausnummer"));
				tmpKontakt.setOrt(result.getString("ort"));
				tmpKontakt.setEmail(result.getString("email"));
				tmpKontakt.setTelefonnummer(result.getString("telefonnummer"));
				tmpKontakt.setBildpfad(result.getString("bildpfad"));
				tmpKontakt.setAnsprechpartner(result
						.getString("ansprechpartner"));
				tmpKontakt.setFirmenname(result.getString("firmenname"));
				tmpKontakt.setIstOeffentlich(result
						.getBoolean("istOeffentlich"));
				tmpKontakt.setErstelltVon(result.getInt("erstelltVon"));
				tmpKontakt.setErstelltAm(result.getDate("erstelltAm"));
				lstKontakte.add(tmpKontakt);
			}
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}
		return lstKontakte;
	}

	/**
	 * Gibt alle Privatkontakte aus der Datenbank zurück.
	 * 
	 * @param sortierSpalte
	 *            : Ist das Attribut nach welchem die Sortierung der Liste
	 *            erfolgt.
	 * @exception SQLException
	 *                : Tritt ein falls Fehler in der MySQL Syntax vorliegen.
	 * @return lstKontakte: Liste aller gefundenen Privatkontakte
	 */
	public List<Privatkontakt> getPrivatkontakte(String sortierSpalte) {
		List<Privatkontakt> lstKontakte = new ArrayList<Privatkontakt>();

		String sql = String.format("SELECT * " + "FROM %s " + "WHERE "
				+ "istFirmenkontakt = 0 " + "AND istGeloescht = 0 "
				+ "ORDER BY %s;", tblKontakt, sortierSpalte);
		try {
			Statement stmt = verbindung.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()) {
				Privatkontakt tmpKontakt = new Privatkontakt();
				tmpKontakt.setKontaktID(result.getInt("kontaktID"));
				tmpKontakt.setPlz(result.getString("plz"));
				tmpKontakt.setStrasse(result.getString("strasse"));
				tmpKontakt.setHausnummer(result.getString("hausnummer"));
				tmpKontakt.setOrt(result.getString("ort"));
				tmpKontakt.setEmail(result.getString("email"));
				tmpKontakt.setTelefonnummer(result.getString("telefonnummer"));
				tmpKontakt.setBildpfad(result.getString("bildpfad"));
				tmpKontakt.setVorname(result.getString("vorname"));
				tmpKontakt.setNachname(result.getString("nachname"));
				tmpKontakt.setIstOeffentlich(result
						.getBoolean("istOeffentlich"));
				tmpKontakt.setErstelltVon(result.getInt("erstelltVon"));
				tmpKontakt.setErstelltAm(result.getDate("erstelltAm"));
				lstKontakte.add(tmpKontakt);
			}
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}
		return lstKontakte;
	}

	/**
	 * Gibt alle Firmenkontakte aus der Datenbank zurück.
	 * 
	 * @param sortierSpalte
	 *            : Ist das Attribut nach welchem die Sortierung der Liste
	 *            erfolgt.
	 * @exception SQLException
	 *                : Tritt ein falls Fehler in der MySQL Syntax vorliegen.
	 * @return lstKontakt: Liste aller gefundenen Firmenkontakte
	 */
	public List<Firmenkontakt> getFirmenkontakte(String sortierSpalte) {
		List<Firmenkontakt> lstKontakte = new ArrayList<Firmenkontakt>();
		String sql = String.format("SELECT * " + "FROM %s "
				+ "WHERE istFirmenkontakt = 1 " + "AND istGeloescht = 0 "
				+ "ORDER BY %s;", tblKontakt, sortierSpalte);
		try {
			Statement stmt = verbindung.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()) {
				Firmenkontakt tmpKontakt = new Firmenkontakt();
				tmpKontakt.setKontaktID(result.getInt("kontaktID"));
				tmpKontakt.setPlz(result.getString("plz"));
				tmpKontakt.setStrasse(result.getString("strasse"));
				tmpKontakt.setHausnummer(result.getString("hausnummer"));
				tmpKontakt.setOrt(result.getString("ort"));
				tmpKontakt.setEmail(result.getString("email"));
				tmpKontakt.setTelefonnummer(result.getString("telefonnummer"));
				tmpKontakt.setBildpfad(result.getString("bildpfad"));
				tmpKontakt.setAnsprechpartner(result
						.getString("ansprechpartner"));
				tmpKontakt.setFirmenname(result.getString("firmenname"));
				tmpKontakt.setIstOeffentlich(result
						.getBoolean("istOeffentlich"));
				tmpKontakt.setErstelltVon(result.getInt("erstelltVon"));
				tmpKontakt.setErstelltAm(result.getDate("erstelltAm"));
				lstKontakte.add(tmpKontakt);
			}
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}
		return lstKontakte;
	}

	/**
	 * Gibt alle Privatkontakte anhand einer Benutzer-ID zurück.
	 * 
	 * @param benutzerId
	 *            : übergebene Benutzer-ID
	 * @param sortierSpalte
	 *            : Ist das Attribut nach welchem die Sortierung der Liste
	 *            erfolgt.
	 * @exception SQLException
	 *                : Tritt ein falls Fehler in der MySQL Syntax vorliegen.
	 * @return lstKontakte: Liste aller gefundenen Privatkontakte
	 */
	public List<Privatkontakt> getPrivatkontakteByBenutzerId(int benutzerId,
			String sortierSpalte) {
		List<Privatkontakt> lstPrivatkontakte = new ArrayList<Privatkontakt>();
		String sql = String.format("SELECT * " + "FROM %s "
				+ "WHERE (istOeffentlich = 1 " + "OR erstelltVon = %d) "
				+ "AND istFirmenkontakt = 0 " + "AND istGeloescht = 0 "
				+ "ORDER BY %s;", tblKontakt, benutzerId, sortierSpalte);
		try {
			Statement stmt = verbindung.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()) {
				Privatkontakt tmpKontakt = new Privatkontakt();
				tmpKontakt.setKontaktID(result.getInt("kontaktID"));
				tmpKontakt.setPlz(result.getString("plz"));
				tmpKontakt.setStrasse(result.getString("strasse"));
				tmpKontakt.setHausnummer(result.getString("hausnummer"));
				tmpKontakt.setOrt(result.getString("ort"));
				tmpKontakt.setEmail(result.getString("email"));
				tmpKontakt.setTelefonnummer(result.getString("telefonnummer"));
				tmpKontakt.setBildpfad(result.getString("bildpfad"));
				tmpKontakt.setVorname(result.getString("vorname"));
				tmpKontakt.setNachname(result.getString("nachname"));
				tmpKontakt.setIstOeffentlich(result
						.getBoolean("istOeffentlich"));
				tmpKontakt.setErstelltVon(result.getInt("erstelltVon"));
				tmpKontakt.setErstelltAm(result.getDate("erstelltAm"));
				lstPrivatkontakte.add(tmpKontakt);
			}
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}
		return lstPrivatkontakte;
	}

	/**
	 * Gibt einen Privatkontakt anhand der Kontakt-ID zurück.
	 * 
	 * @param privatkontaktId
	 *            : übergebene Privatkontakt-ID
	 * @exception SQLException
	 *                : Tritt ein falls Fehler in der MySQL Syntax vorliegen.
	 * @return privatkontakt: Der gefundene Kontakt
	 */
	public Privatkontakt getPrivatkontaktById(int privatkontaktId) {
		Privatkontakt privatkontakt = new Privatkontakt();
		String sql = String.format("SELECT * " + "FROM %s "
				+ "WHERE kontaktId = %d " + "AND istGeloescht = 0;",
				tblKontakt, privatkontaktId);
		try {
			Statement stmt = this.verbindung.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()) {
				privatkontakt.setKontaktID(result.getInt("kontaktID"));
				privatkontakt.setPlz(result.getString("plz"));
				privatkontakt.setStrasse(result.getString("strasse"));
				privatkontakt.setHausnummer(result.getString("hausnummer"));
				privatkontakt.setOrt(result.getString("ort"));
				privatkontakt.setEmail(result.getString("email"));
				privatkontakt.setTelefonnummer(result
						.getString("telefonnummer"));
				privatkontakt.setBildpfad(result.getString("bildpfad"));
				privatkontakt.setVorname(result.getString("vorname"));
				privatkontakt.setNachname(result.getString("nachname"));
				privatkontakt.setIstOeffentlich(result
						.getBoolean("istOeffentlich"));
				privatkontakt.setErstelltVon(result.getInt("erstelltVon"));
				privatkontakt.setErstelltAm(result.getDate("erstelltAm"));
			}
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}
		return privatkontakt;
	}

	/**
	 * Gibt einen Firmenkontakt anhand der Kontakt-ID zurück.
	 * 
	 * 
	 * @param firmenkontaktId
	 *            : übergebene Firmenkontakt-ID
	 * @exception SQLException
	 *                : Tritt ein falls Fehler in der MySQL Syntax vorliegen.
	 * @return firmenkontakt: Der gefundene Firmenkontakt
	 */
	public Firmenkontakt getFirmenkontaktById(int firmenkontaktId) {
		Firmenkontakt firmenkontakt = new Firmenkontakt();
		String sql = String.format("SELECT * " + "FROM %s "
				+ "WHERE kontaktId = %d " + "AND istGeloescht = 0;",
				tblKontakt, firmenkontaktId);
		try {
			Statement stmt = this.verbindung.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()) {
				firmenkontakt.setKontaktID(result.getInt("kontaktID"));
				firmenkontakt.setPlz(result.getString("plz"));
				firmenkontakt.setStrasse(result.getString("strasse"));
				firmenkontakt.setHausnummer(result.getString("hausnummer"));
				firmenkontakt.setOrt(result.getString("ort"));
				firmenkontakt.setEmail(result.getString("email"));
				firmenkontakt.setTelefonnummer(result
						.getString("telefonnummer"));
				firmenkontakt.setBildpfad(result.getString("bildpfad"));
				firmenkontakt.setFirmenname(result.getString("firmenname"));
				firmenkontakt.setAnsprechpartner(result
						.getString("ansprechpartner"));
				firmenkontakt.setIstOeffentlich(result
						.getBoolean("istOeffentlich"));
				firmenkontakt.setErstelltVon(result.getInt("erstelltVon"));
				firmenkontakt.setErstelltAm(result.getDate("erstelltAm"));
			}
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}
		return firmenkontakt;
	}

	/**
	 * Gibt eine Liste aller Benutzer zurück.
	 * 
	 * @exception SQLException
	 *                : Tritt ein wenn ein Fehler in der SQL-Syntax vorliegt.
	 * @return lstBenutzer: Liste aller gefundenen Benutzer
	 */
	public List<Benutzer> getBenutzer() {
		List<Benutzer> lstBenutzer = new ArrayList<Benutzer>();
		String sql = String.format("SELECT * " + "FROM %s "
				+ "WHERE istGeloescht = 0 " + "ORDER BY email;", tblBenutzer);
		try {
			Statement stmt = verbindung.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()) {
				Benutzer tmpBenutzer = new Benutzer();
				tmpBenutzer.setBenutzerID(result.getInt("benutzerId"));
				tmpBenutzer.setEmail(result.getString("email"));
				tmpBenutzer.setIstAdmin(result.getBoolean("istAdmin"));
				tmpBenutzer.setIstFreigeschaltet(result
						.getBoolean("istFreigeschaltet"));
				lstBenutzer.add(tmpBenutzer);
			}
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}
		return lstBenutzer;
	}

	/**
	 * Gibt einen Benutzer anhand von einer übergebenen Benutzer-ID zurück.
	 * 
	 * @param benutzerId
	 *            : übergebene Benutzer-ID
	 * @exception SQLException
	 *                : Tritt ein wenn ein Fehler in der SQL-Syntax vorliegt.
	 * @return benutzer: Der gefundene Benutzer
	 */
	public Benutzer getBenutzerById(int benutzerId) {
		Benutzer benutzer = new Benutzer();
		String sql = String.format("SELECT * " + "FROM %s "
				+ "WHERE benutzerId = %d " + "AND istGeloescht = 0;",
				tblBenutzer, benutzerId);
		try {
			Statement stmt = verbindung.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()) {
				benutzer.setBenutzerID(result.getInt("benutzerId"));
				benutzer.setEmail(result.getString("email"));
				benutzer.setPasswort(result.getString("passwort"));
				benutzer.setIstAdmin(result.getBoolean("istAdmin"));
				benutzer.setIstFreigeschaltet(result
						.getBoolean("istFreigeschaltet"));
				benutzer.setErstelltAm(result.getDate("erstelltAm"));
				benutzer.setIstGeloescht(false);
			}
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}
		return benutzer;
	}

	/***
	 * Gibt einen Benutzer anhand einer übergebenen E-Mail Adresse zurück.
	 * 
	 * @param email
	 *            : übergebene E-Mail Adresse
	 * @exception SQLException
	 *                : Tritt ein wenn ein Fehler in der SQL-Syntax vorliegt.
	 * @return benutzer: Der gefundene Benutzer
	 */
	public Benutzer getBenutzerByEmail(String email) {
		Benutzer benutzer = new Benutzer();
		String sql = String.format("SELECT * " + "FROM %s "
				+ "WHERE email = '%s' " + "AND istGeloescht = 0;", tblBenutzer,
				email);
		try {
			Statement stmt = verbindung.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()) {
				benutzer.setBenutzerID(result.getInt("benutzerId"));
				benutzer.setEmail(result.getString("email"));
				benutzer.setPasswort(result.getString("passwort"));
				benutzer.setIstAdmin(result.getBoolean("istAdmin"));
				benutzer.setIstFreigeschaltet(result
						.getBoolean("istFreigeschaltet"));
				benutzer.setErstelltAm(result.getDate("erstelltAm"));
				benutzer.setIstGeloescht(false);
			}
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}
		return benutzer;
	}

	/**
	 * Gibt die Anzahl der Firmenkontakte zurück, die ein Benutzer angelegt hat.
	 * 
	 * @param benutzerId
	 *            : übergebene Benutzer-ID
	 * @exception SQLException
	 *                : Tritt ein falls Fehler in der MySQL Syntax vorliegen.
	 * @return anzahl: Anzahl der Firmenkontakte
	 */
	public int getAnzahlFirmenkontakte(int benutzerId) {
		String sql = String.format("SELECT count(kontaktId) AS anzahl "
				+ "FROM %s " + "WHERE erstelltVon = %d "
				+ "AND istFirmenkontakt = 1 " + "AND istGeloescht = 0;",
				tblKontakt, benutzerId);
		try {
			Statement stmt = verbindung.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()) {
				return result.getInt("anzahl");
			}
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
		}
		return 0;
	}

	/**
	 * Gibt die Anzahl der Privatkontakte zurück, die ein Benutzer angelegt hat.
	 * 
	 * @param benutzerId
	 *            : übergebene Benutzer-ID
	 * @exception SQLException
	 *                : Tritt ein wenn ein Fehler in der SQL-Syntax vorliegt.
	 * @return anzahl: Anzahl der Privatkontakte
	 */
	public int getAnzahlPrivatkontakte(int benutzerId) {
		String sql = String.format("SELECT count(kontaktId) AS anzahl "
				+ "FROM %s " + "WHERE erstelltVon = %d "
				+ "AND istFirmenkontakt = 0 " + "AND istGeloescht = 0;",
				tblKontakt, benutzerId);
		try {
			Statement stmt = verbindung.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()) {
				return result.getInt("anzahl");
			}
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
		}
		return 0;
	}

	/**
	 * Gibt an, wie viele Kontakte ein Benutzer insgesamt angelegt hat.
	 * 
	 * @param benutzerId
	 *            : übergebene Benutzer-ID
	 * @exception SQLException
	 *                : Tritt ein wenn ein Fehler in der SQL-Syntax vorliegt.
	 * @return anzahl: Anzahl der Kontakte, die durch den Benutzer angelegt
	 *         wurden
	 */
	public int getAnzahlKontakte(int benutzerId) {
		String sql = String.format("SELECT count(kontaktId) AS anzahl "
				+ "FROM %s " + "WHERE erstelltVon = %d "
				+ "AND istGeloescht = 0;", tblKontakt, benutzerId);
		try {
			Statement stmt = verbindung.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()) {
				return result.getInt("anzahl");
			}
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
		}
		return 0;
	}

	/**
	 * Löscht den Kontakt mit der übergebenen Kontakt-ID aus der Datenbank.
	 * 
	 * @param kontaktId
	 *            : übergebene Kontakt-ID
	 * @return boolean: true wenn Löschvorgang erfolgreich war, sonst false.
	 */
	public boolean loescheKontakt(int kontaktId) {
		String sql = String.format("UPDATE %s " + "SET istGeloescht = 1 "
				+ "WHERE kontaktID = %d " + "AND istGeloescht = 0;",
				tblKontakt, kontaktId);
		return (befehlAusfuehren(sql) > 0);
	}

	// TODO Auto-generated method stub

	/**
	 * Löscht alle Kontakte die zu einem Benutzer gehören.
	 * 
	 * @param benutzerId
	 *            : übergebene Benutzer-ID
	 * @return
	 */
	public boolean loescheKontakteByBenutzerId(int benutzerId) {
		String sql = String.format("UPDATE %s " + "SET istGeloescht = 1 "
				+ "WHERE erstelltVon = %d AND " + "istFirmenkontakt = 0 AND "
				+ "istGeloescht = 0;", tblKontakt, benutzerId);
		return (befehlAusfuehren(sql) > 0);
	}

	/**
	 * Löscht den Benutzer mit der übergebenen Benutzer-ID aus der Datenbank.
	 * 
	 * @param benutzerId
	 *            : übergebene Benutzer-ID
	 * @return boolean: true wenn der Löschvorgang erfolgreich war, sonst false.
	 */
	public boolean loescheBenutzer(int benutzerId) {
		String sql = String.format(
				"UPDATE %s SET istGeloescht = 1 WHERE benutzerID = %d;",
				tblBenutzer, benutzerId);
		return (befehlAusfuehren(sql) > 0);
	}

	/**
	 * Prüft ob ein übergebener Privatkontakt bereits in der Datenbank vorhanden
	 * ist.
	 * 
	 * @param privatkontakt
	 *            : übergebener Privatkontakt
	 * @exception SQLException
	 *                : Tritt ein wenn ein Fehler in der SQL-Syntax vorliegt.
	 * @return boolean: true wenn der Kontakt bereits existiert, sonst false.
	 */
	public boolean istKontaktBereitsVorhanden(Privatkontakt privatkontakt) {
		String sql = String.format("SELECT kontaktId " + "FROM %s " + "WHERE "
				+ "plz = '%s' AND " + "strasse = '%s' AND "
				+ "hausnummer = '%s' AND " + "ort = '%s' AND "
				+ "email = '%s' AND " + "telefonnummer = '%s' AND "
				+ "vorname = '%s' AND " + "nachname = '%s' AND "
				+ "istFirmenkontakt = 0 AND " + "erstelltVon = %d;",
				tblKontakt, privatkontakt.getPlz(), privatkontakt.getStrasse(),
				privatkontakt.getHausnummer(), privatkontakt.getOrt(),
				privatkontakt.getEmail(), privatkontakt.getTelefonnummer(),
				privatkontakt.getVorname(), privatkontakt.getNachname(),
				privatkontakt.getErstelltVon());
		try {
			Statement stmt = this.verbindung.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			// Kontakt ist schon vorhanden
			if (result.next()) {
				return true;
			}
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Prüft ob ein übergebener Firmenkontakt bereits in der Datenbank vorhanden
	 * ist.
	 * 
	 * @param firmenkontakt
	 *            : übergebener Firmenkontakt
	 * @exception SQLException
	 *                : Tritt ein wenn ein Fehler in der SQL-Syntax vorliegt.
	 * @return boolean: true wenn der Kontakt bereits existiert, sonst false.
	 */
	public boolean istKontaktBereitsVorhanden(Firmenkontakt firmenkontakt) {
		String sql = String.format("SELECT kontaktId " + "FROM %s " + "WHERE "
				+ "plz = '%s' AND " + "strasse = '%s' AND "
				+ "hausnummer = '%s' AND " + "ort = '%s' AND "
				+ "email = '%s' AND " + "telefonnummer = '%s' AND "
				+ "firmenname = '%s' AND " + "ansprechpartner = '%s' AND "
				+ "istFirmenkontakt = 0 AND " + "erstelltVon = %d;",
				tblKontakt, firmenkontakt.getPlz(), firmenkontakt.getStrasse(),
				firmenkontakt.getHausnummer(), firmenkontakt.getOrt(),
				firmenkontakt.getEmail(), firmenkontakt.getTelefonnummer(),
				firmenkontakt.getFirmenname(),
				firmenkontakt.getAnsprechpartner(),
				firmenkontakt.getErstelltVon());
		try {
			Statement stmt = this.verbindung.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			// Kontakt ist schon vorhanden
			if (result.next()) {
				return true;
			}
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Prüft ob ein übergebener Benutzer bereits in der Datenbank vorhanden ist.
	 * 
	 * @param benutzer
	 *            : übergebener Benutzer
	 * @exception SQLException
	 *                : Tritt ein wenn ein Fehler in der SQL-Syntax vorliegt.
	 * @return boolean: true wenn der Benutzer bereits vorhanden ist, sonst
	 *         false.
	 */
	public boolean istBenutzerVorhanden(Benutzer benutzer) {
		String sql = String.format("SELECT benutzerID " + "FROM %s "
				+ "WHERE email = '%s' " + "AND istGeloescht = 0;", tblBenutzer,
				benutzer.getEmail());
		try {
			Statement stmt = verbindung.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			// Kontakt ist schon vorhanden
			if (result.next()) {
				return true;
			}
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Schreibt einen Benutzer in die Datenbank. Falls der übergebene Benutzer
	 * schon vorhanden ist wird dieser überschrieben. Andernfalls wird ein neuer
	 * Datensatz angelegt.
	 * 
	 * @param benutzer
	 *            : übergebener Benutzer
	 * @exception SQLException
	 *                : Tritt ein wenn ein Fehler in der SQL-Syntax vorliegt.
	 * @return geaenderteDatensaetze: Anzahl der veränderten Datensätze
	 */
	public int speicherDaten(Benutzer benutzer) {
		int geaenderteDatensaetze = 0;
		String sql;
		String md5Passwort = MD5.getMd5Hash(benutzer.getPasswort());
		try {
			// Wenn der Benutzer schon in der Datenbank existiert.
			if (benutzer.getBenutzerID() != 0) {
				String sqlPwChange = (!benutzer.getPasswort().equals("")) ? String
						.format("passwort = '%s', ", md5Passwort) : "";
				sql = String.format("UPDATE %s " + "SET email = '%s', " + "%s"
						+ "istAdmin = %d, " + "istFreigeschaltet = %d, "
						+ "geaendertVon = -1, "
						+ "geaendertAm = CURRENT_TIMESTAMP "
						+ "WHERE benutzerId = %d;", tblBenutzer,
						benutzer.getEmail(), sqlPwChange,
						((benutzer.isIstAdmin()) ? 1 : 0),
						((benutzer.isIstFreigeschaltet()) ? 1 : 0),
						benutzer.getBenutzerID());
			}
			// Wenn der Benutzer noch nicht existiert.
			else {
				sql = String
						.format("INSERT INTO %s "
								+ "(email,passwort,istAdmin,istFreigeschaltet,erstelltVon) "
								+ "VALUES " + "('%s'," + "'%s'," + "%d,"
								+ "%d," + "-1);", tblBenutzer,
								benutzer.getEmail(), md5Passwort,
								((benutzer.isIstAdmin()) ? 1 : 0),
								((benutzer.isIstFreigeschaltet()) ? 1 : 0));
			}
			Statement stmt = verbindung.createStatement();
			geaenderteDatensaetze = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}
		return geaenderteDatensaetze;
	}

	/**
	 * Schreibt einen Firmenkontakt in die Datenbank. Falls der übergebene
	 * Firmenkontakt schon vorhanden ist wird dieser überschrieben. Andernfalls
	 * wird ein neuer Datensatz angelegt.
	 * 
	 * @param firmenkontakt
	 *            : übergebener Firmenkontakt
	 * @exception SQLException
	 *                : Tritt ein wenn ein Fehler in der SQL-Syntax vorliegt.
	 * @return geaenderteDatensaetze: Anzahl der veränderten Datensätze
	 */
	public int speicherDaten(Firmenkontakt firmenkontakt) {
		int geaenderteDatensaetze = 0;
		String sql;
		try {
			// Kontakt ist schon vorhanden
			if (firmenkontakt.getKontaktID() != 0) {
				sql = String.format("UPDATE %s " + "SET plz = '%s', "
						+ "strasse = '%s', " + "hausnummer = '%s', "
						+ "ort = '%s', " + "email = '%s', "
						+ "telefonnummer = '%s', " + "bildpfad = '%s', "
						+ "firmenname = '%s', " + "ansprechpartner = '%s', "
						+ "vorname = '', " + "nachname = '', "
						+ "istFirmenkontakt = 1, " + "istOeffentlich = %d, "
						+ "geaendertVon = %d, "
						+ "geaendertAm = CURRENT_TIMESTAMP "
						+ "WHERE kontaktId = %d;", tblKontakt,
						firmenkontakt.getPlz(), firmenkontakt.getStrasse(),
						firmenkontakt.getHausnummer(), firmenkontakt.getOrt(),
						firmenkontakt.getEmail(),
						firmenkontakt.getTelefonnummer(),
						firmenkontakt.getBildpfad(),
						firmenkontakt.getFirmenname(),
						firmenkontakt.getAnsprechpartner(),
						((firmenkontakt.isIstOeffentlich()) ? 1 : 0),
						firmenkontakt.getGeaendertVon(),
						firmenkontakt.getKontaktID());
			}
			// Neuer Benutzer
			else {
				sql = String
						.format("INSERT INTO %s "
								+ "(plz,strasse,hausnummer,ort,email,telefonnummer,bildpfad,firmenname,ansprechpartner,istFirmenkontakt,istOeffentlich,erstelltVon) "
								+ "VALUES " + "('%s', " + "'%s', " + "'%s', "
								+ "'%s', " + "'%s', " + "'%s', " + "'%s', "
								+ "'%s', " + "'%s', " + "1, " + "%d, " + "%d);",
								tblKontakt, firmenkontakt.getPlz(),
								firmenkontakt.getStrasse(),
								firmenkontakt.getHausnummer(),
								firmenkontakt.getOrt(),
								firmenkontakt.getEmail(),
								firmenkontakt.getTelefonnummer(),
								firmenkontakt.getBildpfad(),
								firmenkontakt.getFirmenname(),
								firmenkontakt.getAnsprechpartner(),
								((firmenkontakt.isIstOeffentlich()) ? 1 : 0),
								firmenkontakt.getErstelltVon());
			}
			Statement stmt = verbindung.createStatement();
			geaenderteDatensaetze = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}
		return geaenderteDatensaetze;
	}

	/**
	 * Schreibt einen Privatkontakt in die Datenbank. Falls der übergebene
	 * Privatkontakt schon vorhanden ist wird dieser überschrieben. Andernfalls
	 * wird ein neuer Datensatz angelegt.
	 * 
	 * @param privatkontakt
	 *            : übergebener Privatkontakt
	 * @exception SQLException
	 *                : Tritt ein wenn ein Fehler in der SQL-Syntax vorliegt.
	 * @return geaenderteDatensaetze: Anzahl der veränderten Datensätze
	 */
	public int speicherDaten(Privatkontakt privatkontakt) {
		int geaenderteDatensaetze = 0;
		String sql;
		try {
			// Kontakt ist schon vorhanden
			if (privatkontakt.getKontaktID() != 0) {
				sql = String.format("UPDATE %s " + "SET plz = '%s', "
						+ "strasse = '%s', " + "hausnummer = '%s', "
						+ "ort = '%s', " + "email = '%s', "
						+ "telefonnummer = '%s', " + "bildpfad = '%s', "
						+ "firmenname = '', " + "ansprechpartner = '', "
						+ "vorname = '%s', " + "nachname = '%s', "
						+ "istFirmenkontakt = 0, " + "istOeffentlich = %d, "
						+ "geaendertVon = %d, "
						+ "geaendertAm = CURRENT_TIMESTAMP "
						+ "WHERE kontaktId = %d;", tblKontakt,
						privatkontakt.getPlz(), privatkontakt.getStrasse(),
						privatkontakt.getHausnummer(), privatkontakt.getOrt(),
						privatkontakt.getEmail(),
						privatkontakt.getTelefonnummer(),
						privatkontakt.getBildpfad(),
						privatkontakt.getVorname(),
						privatkontakt.getNachname(),
						((privatkontakt.isIstOeffentlich()) ? 1 : 0),
						privatkontakt.getGeaendertVon(),
						privatkontakt.getKontaktID());
			}
			// Neuer Benutzer
			else {
				sql = String
						.format("INSERT INTO %s "
								+ "(plz,strasse,hausnummer,ort,email,telefonnummer,"
								+ "bildpfad,vorname,nachname,istFirmenkontakt,istOeffentlich,erstelltVon) "
								+ "VALUES " + "('%s', " + "'%s', " + "'%s', "
								+ "'%s', " + "'%s', " + "'%s', " + "'%s', "
								+ "'%s', " + "'%s', " + "0, " + "%d, " + "%d);",
								tblKontakt, privatkontakt.getPlz(),
								privatkontakt.getStrasse(),
								privatkontakt.getHausnummer(),
								privatkontakt.getOrt(),
								privatkontakt.getEmail(),
								privatkontakt.getTelefonnummer(),
								privatkontakt.getBildpfad(),
								privatkontakt.getVorname(),
								privatkontakt.getNachname(),
								((privatkontakt.isIstOeffentlich()) ? 1 : 0),
								privatkontakt.getErstelltVon());
			}
			Statement stmt = verbindung.createStatement();
			geaenderteDatensaetze = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}
		return geaenderteDatensaetze;
	}

	/**
	 * Führt einen übergeben SQL UPDATE oder INSERT befehl aus und gibt die
	 * Anzahl der betroffenen Datensätze zurück.
	 * 
	 * @param sqlCommand
	 *            : übergebener Befehl
	 * @exception SQLException
	 *                : Tritt ein wenn ein Fehler in der SQL-Syntax vorliegt.
	 * @return manipulierteDatensaetze: Anzahl der betroffenen Datensätze.
	 */
	private int befehlAusfuehren(String sqlCommand) {
		int manipulierteDatensaetze = 0;
		try {
			Statement stmt = this.verbindung.createStatement();
			manipulierteDatensaetze = stmt.executeUpdate(sqlCommand);
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}
		return manipulierteDatensaetze;
	}
}
