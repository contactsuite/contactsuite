package contactsuite;

import java.util.Date;

/**
 * Die Klasse Benutzer kapselt s�mtliche Daten die notwendig sind um einen
 * Benutzer im Rahmen des Systems zu repr�sentieren. Es handelt sich hierbei um
 * eine Klasse die lediglich zur tempor�ren Datenhaltung ben�tigt wird und keine Dienste zur
 * Verf�gung stellt.
 * 
 * @author Fabian Ostermeier
 * 
 * 
 */

public class Benutzer {

	private int benutzerID;

	private Benutzer erstelltVon;
	private Benutzer geaendertVon;

	private String email;
	private String passwort;

	private boolean istAdmin;
	private boolean istOnline;
	private boolean istFreigeschaltet;
	private boolean istGeloescht;

	private Date erstelltAm;
	private Date geaendertAm;

	public Benutzer() {
		benutzerID = 0;

		erstelltVon = null;
		geaendertVon = null;

		email = "";
		passwort = "";

		istAdmin = false;
		istOnline = false;
		istFreigeschaltet = false;
		istGeloescht = false;

		erstelltAm = null;
		geaendertAm = null;

	}

	public Benutzer(int benutzerID, Benutzer erstelltVon,
			Benutzer geaendertVon, String email, String passwort,
			boolean istAdmin, boolean istFreigeschaltet, boolean istGeloescht,
			Date erstelltAm, Date geaendertAm) {
		this.benutzerID = benutzerID;
		this.erstelltVon = erstelltVon;
		this.geaendertVon = geaendertVon;
		this.email = email;
		this.passwort = passwort;
		this.istAdmin = istAdmin;
		this.istFreigeschaltet = istFreigeschaltet;
		this.istGeloescht = istGeloescht;
		this.erstelltAm = erstelltAm;
		this.geaendertAm = geaendertAm;

	}

	public int getBenutzerID() {
		return benutzerID;
	}

	public void setBenutzerID(int benutzerID) {
		this.benutzerID = benutzerID;
	}

	public Benutzer getErstelltVon() {
		return erstelltVon;
	}

	public void setErstelltVon(Benutzer erstelltVon) {
		this.erstelltVon = erstelltVon;
	}

	public Benutzer getGeaendertVon() {
		return geaendertVon;
	}

	public void setGeaendertVon(Benutzer geaendertVon) {
		this.geaendertVon = geaendertVon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public boolean isIstAdmin() {
		return istAdmin;
	}

	public void setIstAdmin(boolean istAdmin) {
		this.istAdmin = istAdmin;
	}

	public boolean isIstOnline() {
		return istOnline;
	}

	public void setIstOnline(boolean online) {
		this.istOnline = online;
	}

	public boolean isIstFreigeschaltet() {
		return istFreigeschaltet;
	}

	public void setIstFreigeschaltet(boolean istFreigeschaltet) {
		this.istFreigeschaltet = istFreigeschaltet;
	}

	public boolean isIstGeloescht() {
		return istGeloescht;
	}

	public void setIstGeloescht(boolean istGeloescht) {
		this.istGeloescht = istGeloescht;
	}

	public Date getErstelltAm() {
		return erstelltAm;
	}

	public void setErstelltAm(Date erstelltAm) {
		this.erstelltAm = erstelltAm;
	}

	public Date getGeaendertAm() {
		return geaendertAm;
	}

	public void setGeaendertAm(Date geaendertAm) {
		this.geaendertAm = geaendertAm;
	}

}
