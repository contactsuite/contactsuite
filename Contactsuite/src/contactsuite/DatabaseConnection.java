/**
 * 
 */
package contactsuite;

import java.io.Console;
import java.sql.*;
import java.util.*;

/**
 * @author Dominik Ferber
 * 
 *
 */
public class DatabaseConnection {
	private static DatabaseConnection instance = null;
	
	private final String dbBenutzername = "customers_s001";
	private final String dbPasswort = "dqJAMKPR2x5B5JV8";
	private final String dbName = "jdbc:mysql://pma.postopus.de/customers_s001";
	private final String tblBenutzer = "tblbenutzer";
	private final String tblKontakt = "tblkontakt";
	private Connection connection;
	
	private DatabaseConnection(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection(
					this.dbName, this.dbBenutzername, this.dbPasswort);
		} 
		catch (ClassNotFoundException err) {
			System.out.println("Datenbankverbindung kann nicht hergestellt werden.");
		} 
		catch (SQLException err) {
			System.out.println("Datenbankverbindung kann nicht hergestellt werden.");
		}
	}
	
	public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
	
	/**
	 * Methode zum speichern von Benutzern. Falls der übergebene Benutzer schon
	 * vorhanden ist wird dieser geupdated. Sonst wird ein neuer Datensatz eingetragen.
	 * @author Dominik Ferber
	 * @param benutzer
	 * @return Anzahl der veränderten Datensätze
	 */
	public int SpeicherDaten(Benutzer benutzer)
	{
		int geaenderteDatensaetze = 0;
		String sql;
		try {
			//Wenn der Benutzer schon in der Datenbank existiert.
			if(benutzer.getBenutzerID() != 0){
				sql = String.format("UPDATE %s "+
						"SET email = '%s', " +
						"passwort = '%s', " +
						"istAdmin = %d, " +
						"istFreigeschaltet = %d, " +
						"geaendertVon = -1, " +
						"geaendertAm = CURRENT_TIMESTAMP;",
						tblBenutzer,
						benutzer.getEmail(),
						benutzer.getPasswort(),
						((benutzer.isIstAdmin())?1:0),
						((benutzer.isIstFreigeschaltet())?1:0));
			}
			//Wenn der Benutzer noch nicht existiert.
			else {
				sql = String.format("INSERT INTO %s " +
						"(email,passwort,istAdmin,istFreigeschaltet,erstelltVon) " +
						"VALUES " +
						"('%s'," +
						"'%s'," +
						"false," +
						"false," +
						"-1);",
						tblBenutzer,
						benutzer.getEmail(),
						benutzer.getPasswort());
			}
			Statement stmt = connection.createStatement();
			geaenderteDatensaetze = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}
		return geaenderteDatensaetze;
	}
	
	/**
	 * Gibt Kontakt zurück, die zu einem übergebene Suchterm passen.
	 * @author Dominik Ferber
	 * @param searchTerm
	 * @param benutzerId
	 * @return
	 */
	public List<Kontakt> getKontakte(String searchTerm, int benutzerId){
		List<Kontakt> lstKontakte = new ArrayList<Kontakt>();
		lstKontakte.addAll(getPrivatkontakte(searchTerm, benutzerId));
		lstKontakte.addAll(getFirmenkontakte(searchTerm));
		return lstKontakte;
	}
	
	/**
	 * Gibt alle Privatkontakte zurück, die zu einem übergebene Suchterm und BenutzerId passen.
	 * @author Dominik Ferber
	 * @param searchTerm
	 * @param benutzerId
	 * @return
	 */
	public List<Privatkontakt> getPrivatkontakte(String searchTerm, int benutzerId) {
		List<Privatkontakt> lstKontakte = new ArrayList<Privatkontakt>();
		
		String sql = String.format("SELECT * " +
				"FROM %s " +
				"WHERE " +
				"vorname LIKE '%s%' OR " +
				"nachname LIKE '%s%' AND " +
				"istFirmenkontakt = 0 AND " +
				"istGeloescht = 0 AND " +
				"(istOeffentlich = 1 OR " +
				"(istOeffentlich = 0 AND erstelltVon = %d)) " +
				"ORDER BY nachname;",tblKontakt, searchTerm, searchTerm, benutzerId);
		try{
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
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
				tmpKontakt.setIstOeffentlich(result.getBoolean("istOeffentlich"));
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
	
	public List<Firmenkontakt> getFirmenkontakte(String searchTerm){
		List<Firmenkontakt> lstKontakte = new ArrayList<Firmenkontakt>();
		String sql = String.format("SELECT * " +
				"FROM %s " +
				"WHERE firmenname LIKE '%s%' AND " +
				"istFirmenkontakt = 1 " +
				"AND istGeloescht = 0 " +
				"ORDER BY firmenname;",tblKontakt, searchTerm);
		try{
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				Firmenkontakt tmpKontakt = new Firmenkontakt();
				tmpKontakt.setKontaktID(result.getInt("kontaktID"));
				tmpKontakt.setPlz(result.getString("plz"));
				tmpKontakt.setStrasse(result.getString("strasse"));
				tmpKontakt.setHausnummer(result.getString("hausnummer"));
				tmpKontakt.setOrt(result.getString("ort"));
				tmpKontakt.setEmail(result.getString("email"));
				tmpKontakt.setTelefonnummer(result.getString("telefonnummer"));
				tmpKontakt.setBildpfad(result.getString("bildpfad"));
				tmpKontakt.setAnsprechpartner(result.getString("ansprechpartner"));
				tmpKontakt.setFirmenname(result.getString("firmenname"));
				tmpKontakt.setIstOeffentlich(result.getBoolean("istOeffentlich"));
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
	 * Methode prüft ob ein übergebener Privatkontakt bereits in der Datenbank vorhanden ist.
	 * @author Dominik Ferber
	 * @param privatkontakt
	 * @return
	 */
	public boolean istKontaktBereitsVorhanden(Privatkontakt privatkontakt){
		String sql = String.format("SELECT kontaktId " +
				"FROM %s " +
				"WHERE " +
				"plz = '%s' AND " +
				"strasse = '%s' AND " +
				"hausnummer = '%s' AND " +
				"ort = '%s' AND " +
				"email = '%s' AND " +
				"telefonnummer = '%s' AND " +
				"vorname = '%s' AND " +
				"nachname = '%s' AND " +
				"istFirmenkontakt = 0 AND " +
				"erstelltVon = %d;", 
				tblKontakt, 
				privatkontakt.getPlz(), 
				privatkontakt.getStrasse(), 
				privatkontakt.getHausnummer(),
				privatkontakt.getOrt(),
				privatkontakt.getEmail(),
				privatkontakt.getTelefonnummer(),
				privatkontakt.getVorname(),
				privatkontakt.getNachname(),
				privatkontakt.getErstelltVon());
		try {
			Statement stmt = this.connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			//Kontakt ist schon vorhanden
			if(result.next()){
				return true;
			}
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}
		return false;		
	}

	/**
	 * Methode prüft ob ein übergebener Firmenkontakt bereits in der Datenbank vorhanden ist.
	 * @author Dominik Ferber
	 * @param firmenkontakt
	 * @return
	 */
	public boolean istKontaktBereitsVorhanden(Firmenkontakt firmenkontakt){
		String sql = String.format("SELECT kontaktId " +
				"FROM %s " +
				"WHERE " +
				"plz = '%s' AND " +
				"strasse = '%s' AND " +
				"hausnummer = '%s' AND " +
				"ort = '%s' AND " +
				"email = '%s' AND " +
				"telefonnummer = '%s' AND " +
				"firmenname = '%s' AND " +
				"ansprechpartner = '%s' AND " +
				"istFirmenkontakt = 0 AND " +
				"erstelltVon = %d;", 
				tblKontakt, 
				firmenkontakt.getPlz(), 
				firmenkontakt.getStrasse(), 
				firmenkontakt.getHausnummer(),
				firmenkontakt.getOrt(),
				firmenkontakt.getEmail(),
				firmenkontakt.getTelefonnummer(),
				firmenkontakt.getFirmenname(),
				firmenkontakt.getAnsprechpartner(),
				firmenkontakt.getErstelltVon());
		try {
			Statement stmt = this.connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			//Kontakt ist schon vorhanden
			if(result.next()){
				return true;
			}
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}
		return false;		
	}
	
	/**
	 * Methode zum speichern von Firmenkontakten. Falls der übergebene Firmenkontakt schon
	 * vorhanden ist wird dieser geupdated. Sonst wird ein neuer Datensatz eingetragen.
	 * @author Dominik Ferber
	 * @param firmenkontakt
	 * @return Anzahl der veränderten Datensätze
	 */
	public int SpeicherDaten(Firmenkontakt firmenkontakt)
	{
		int geaenderteDatensaetze = 0;
		String sql;
		try {
			//Kontakt ist schon vorhanden
			if(firmenkontakt.getKontaktID() != 0){				
				sql = String.format("UPDATE %s "+
						"SET plz = '%s', " +
						"strasse = '%s', " +
						"hausnummer = '%s', " +
						"ort = '%s', " +
						"email = '%s', " +
						"telefonnummer = '%s', " +
						"bildpfad = '%s', " +
						"firmenname = '%s', " +
						"ansprechpartner = '%s', " +
						"vorname = '', " +
						"nachname = '', " +
						"istFirmenkontakt = 1, " +
						"istOeffentlich = %d, " +
						"geaendertVon = %d, " +
						"geaendertAm = CURRENT_TIMESTAMP;",
						tblKontakt,
						firmenkontakt.getPlz(),
						firmenkontakt.getStrasse(),
						firmenkontakt.getHausnummer(),
						firmenkontakt.getOrt(),
						firmenkontakt.getEmail(),
						firmenkontakt.getTelefonnummer(),
						firmenkontakt.getBildpfad(),
						firmenkontakt.getFirmenname(),
						firmenkontakt.getAnsprechpartner(),
						((firmenkontakt.isIstOeffentlich())?1:0),
						firmenkontakt.getGeaendertVon());
			}
			//Neuer Benutzer
			else {
				sql = String.format("INSERT INTO %s " +
						"(plz,strasse,hausnummer,ort,email,telefonnummer,bildpfad,firmenname,ansprechpartner,istFirmenkontakt,istOeffentlich,erstelltVon) " +
						"VALUES " +
						"('%s', " +
						"'%s', " +
						"'%s', " +
						"'%s', " +
						"'%s', " +
						"'%s', " +
						"'%s', " +
						"'%s', " +
						"'%s', " +
						"1, " +
						"%d, " +
						"%d);",
						tblKontakt,
						firmenkontakt.getPlz(),
						firmenkontakt.getStrasse(),
						firmenkontakt.getHausnummer(),
						firmenkontakt.getOrt(),
						firmenkontakt.getEmail(),
						firmenkontakt.getTelefonnummer(),
						firmenkontakt.getBildpfad(),
						firmenkontakt.getFirmenname(),
						firmenkontakt.getAnsprechpartner(),
						((firmenkontakt.isIstOeffentlich())?1:0),
						firmenkontakt.getErstelltVon());
			}
			Statement stmt = connection.createStatement();
			geaenderteDatensaetze = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}
		return geaenderteDatensaetze;
	}
	
	/**
	 * Methode zum speichern von Privatkontakten. Falls der übergebene Firmenkontakt schon
	 * vorhanden ist wird dieser geupdated. Sonst wird ein neuer Datensatz eingetragen.
	 * @author Dominik Ferber
	 * @param privatkontakt
	 * @return Anzahl der veränderten Datensätze
	 */
	public int SpeicherDaten(Privatkontakt privatkontakt)
	{
		int geaenderteDatensaetze = 0;
		String sql;
		try {
			//Kontakt ist schon vorhanden
			if(privatkontakt.getKontaktID() != 0){
				sql = String.format("UPDATE %s "+
						"SET plz = '%s', " +
						"strasse = '%s', " +
						"hausnummer = '%s', " +
						"ort = '%s', " +
						"email = '%s', " +
						"telefonnummer = '%s', " +
						"bildpfad = '%s', " +
						"firmenname = '', " +
						"ansprechpartner = '', " +
						"vorname = '%s', " +
						"nachname = '%s', " +
						"istFirmenkontakt = 0, " +
						"istOeffentlich = %d, " +
						"geaendertVon = %d, " +
						"geaendertAm = CURRENT_TIMESTAMP",
						tblKontakt,
						privatkontakt.getPlz(),
						privatkontakt.getStrasse(),
						privatkontakt.getHausnummer(),
						privatkontakt.getOrt(),
						privatkontakt.getEmail(),
						privatkontakt.getTelefonnummer(),
						privatkontakt.getBildpfad(),
						privatkontakt.getVorname(),
						privatkontakt.getNachname(),
						((privatkontakt.isIstOeffentlich())?1:0),
						privatkontakt.getGeaendertVon());
			}
			//Neuer Benutzer
			else {
				sql = String.format("INSERT INTO %s " +
						"(plz,strasse,hausnummer,ort,email,telefonnummer," +
						"bildpfad,vorname,nachname,istFirmenkontakt,istOeffentlich,erstelltVon) " +
						"VALUES " +
						"('%s', " +
						"'%s', " +
						"'%s', " +
						"'%s', " +
						"'%s', " +
						"'%s', " +
						"'%s', " +
						"'%s', " +
						"'%s', " +
						"0, " +
						"%d, " +
						"%d);",
						tblKontakt,
						privatkontakt.getPlz(),
						privatkontakt.getStrasse(),
						privatkontakt.getHausnummer(),
						privatkontakt.getOrt(),
						privatkontakt.getEmail(),
						privatkontakt.getTelefonnummer(),
						privatkontakt.getBildpfad(),
						privatkontakt.getVorname(),
						privatkontakt.getNachname(),
						((privatkontakt.isIstOeffentlich())?1:0),
						privatkontakt.getErstelltVon());
			}
			Statement stmt = connection.createStatement();
			geaenderteDatensaetze = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}
		return geaenderteDatensaetze;
	}
	
	/**
	 * Methode prüft ob ein übergebener Benutzer bereits in der Datenbank vorhanden ist.
	 * @author Dominik Ferber
	 * @param benutzer
	 * @return true falls Benutzer bereits vorhanden, sonst false.
	 */
	public boolean IstBenutzerVorhanden(Benutzer benutzer){	
		String sql = String.format("SELECT benutzerID " +
				"FROM %s " +
				"WHERE email = '%s';", tblBenutzer, benutzer.getEmail());
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			//Kontakt ist schon vorhanden
			if(result.next()){
				return true;
			}
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Liest alle Privatkontakte aus der Datenbank aus.
	 * @author Dominik Ferber
	 * @return
	 */
	public List<Privatkontakt> getPrivatkontakte(){
		List<Privatkontakt> lstKontakte = new ArrayList<Privatkontakt>();
		
		String sql = String.format("SELECT * " +
				"FROM %s " +
				"WHERE " +
				"istFirmenkontakt = 0 " +
				"AND istGeloescht = 0 " +
				"ORDER BY nachname;",tblKontakt);
		try{
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
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
				tmpKontakt.setIstOeffentlich(result.getBoolean("istOeffentlich"));
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
	 * Liest alle Firmenkontakte aus der Datenbank aus.
	 * @author Dominik Ferber
	 * @return
	 */
	public List<Firmenkontakt> getFirmenkontakte(){
		List<Firmenkontakt> lstKontakte = new ArrayList<Firmenkontakt>();
		String sql = String.format("SELECT * " +
				"FROM %s " +
				"WHERE istFirmenkontakt = 1 " +
				"AND istGeloescht = 0 " +
				"ORDER BY firmenname;",tblKontakt);
		try{
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				Firmenkontakt tmpKontakt = new Firmenkontakt();
				tmpKontakt.setKontaktID(result.getInt("kontaktID"));
				tmpKontakt.setPlz(result.getString("plz"));
				tmpKontakt.setStrasse(result.getString("strasse"));
				tmpKontakt.setHausnummer(result.getString("hausnummer"));
				tmpKontakt.setOrt(result.getString("ort"));
				tmpKontakt.setEmail(result.getString("email"));
				tmpKontakt.setTelefonnummer(result.getString("telefonnummer"));
				tmpKontakt.setBildpfad(result.getString("bildpfad"));
				tmpKontakt.setAnsprechpartner(result.getString("ansprechpartner"));
				tmpKontakt.setFirmenname(result.getString("firmenname"));
				tmpKontakt.setIstOeffentlich(result.getBoolean("istOeffentlich"));
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
	 * Liesst alle Privatkontakte anhand einer BenutzerId aus.
	 * @author Dominik Ferber
	 * @param benutzerId
	 * @return
	 */
	public List<Privatkontakt> getPrivatkontakteByBenutzerId(int benutzerId){
		List<Privatkontakt> lstPrivatkontakte = new ArrayList<Privatkontakt>();		
		String sql = String.format("SELECT * " +
				"FROM %s " +
				"WHERE erstelltVon = %d " +
				"AND istFirmenkontakt = 0 " +
				"AND istGeloescht = 0 " +
				"ORDER BY nachname;", tblKontakt, benutzerId);
		try{
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);			
			while(result.next()){
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
				tmpKontakt.setIstOeffentlich(result.getBoolean("istOeffentlich"));
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
	 * Liefert einen Privatkontakt anhand der KontaktId.
	 * @author Dominik Ferber
	 * @param privatkontaktId
	 * @return
	 */
	public Privatkontakt getPrivatkontaktById(int privatkontaktId){
		Privatkontakt privatkontakt = new Privatkontakt();
		String sql = String.format("SELECT * " +
				"FROM %s " +
				"WHERE kontaktId = %d;", tblKontakt, privatkontaktId);
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			System.out.println(result.toString());			
			while(result.next()){
				privatkontakt.setKontaktID(result.getInt("kontaktID"));
				privatkontakt.setPlz(result.getString("plz"));
				privatkontakt.setStrasse(result.getString("strasse"));
				privatkontakt.setHausnummer(result.getString("hausnummer"));
				privatkontakt.setOrt(result.getString("ort"));
				privatkontakt.setEmail(result.getString("email"));
				privatkontakt.setTelefonnummer(result.getString("telefonnummer"));
				privatkontakt.setBildpfad(result.getString("bildpfad"));
				privatkontakt.setVorname(result.getString("vorname"));
				privatkontakt.setNachname(result.getString("nachname"));
				privatkontakt.setIstOeffentlich(result.getBoolean("istOeffentlich"));
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
	 * Liest einen Benutzer anhand von einer übergebenen benutzerId aus.
	 * @author Dominik Ferber
	 * @param benutzerId
	 * @return Benutzer mit der benutzerId
	 */
	public Benutzer getBenutzerById(int benutzerId) {
		Benutzer benutzer = new Benutzer();
		String sql = String.format("SELECT * " +
				"FROM %s " +
				"WHERE benutzerId = %d " +
				"AND istGeloescht = 0;", tblBenutzer, benutzerId);
		try{
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				benutzer.setBenutzerID(result.getInt("benutzerId"));
				benutzer.setEmail(result.getString("email"));
				benutzer.setPasswort(result.getString("passwort"));
				benutzer.setIstAdmin(result.getBoolean("istAdmin"));
				benutzer.setIstFreigeschaltet(result.getBoolean("istFreigeschaltet"));
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
	 * Liefert einen Benutzer anhand einer übergebenen email
	 * @author Dominik Ferber
	 * @param email
	 * @return
	 */
	public Benutzer getBenutzerByEmail(String email){
		Benutzer benutzer = new Benutzer();
		String sql = String.format("SELECT * " +
				"FROM %s " +
				"WHERE email = '%s' " +
				"AND istGeloescht = 0;", tblBenutzer, email);
		try{
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				benutzer.setBenutzerID(result.getInt("benutzerId"));
				benutzer.setEmail(result.getString("email"));
				benutzer.setPasswort(result.getString("passwort"));
				benutzer.setIstAdmin(result.getBoolean("istAdmin"));
				benutzer.setIstFreigeschaltet(result.getBoolean("istFreigeschaltet"));
				benutzer.setErstelltAm(result.getDate("erstelltAm"));
				benutzer.setIstGeloescht(false);
			}
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();			
		}
		return benutzer;
	}

	public int executeUpdateQuery(String sqlCommand){
		int manipulierteDatensaetze = 0;
		try {
			Statement stmt = this.connection.createStatement();
			manipulierteDatensaetze = stmt.executeUpdate(sqlCommand);
		} catch (SQLException e) {
			ErrorHandler.writeError(e);
			e.printStackTrace();
		}		
		return manipulierteDatensaetze;
	}
}
