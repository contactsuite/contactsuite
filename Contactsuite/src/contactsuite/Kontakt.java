package contactsuite;

/**
 * 
 * @author Eduard Dojan
 *
 */

import java.util.*;

public abstract class Kontakt {

	private int kontaktID;
	private String plz;
	private String strasse;
	private String hausnummer;
	private String ort;
	private String email;
	private String telefonnummer;
	private String bildpfad;
	private boolean istOeffentlich;
	Benutzer erstelltVon;
	private Date erstelltAm;
	Benutzer geaendertVon;
	private Date geaendertAm;

	public Kontakt() {
		kontaktID = 0;
		plz = "";
		strasse = "";
		hausnummer = "";
		ort = "";
		email = "";
		telefonnummer = "";
		bildpfad = "";
		istOeffentlich = false;
		erstelltVon = new Benutzer();
		erstelltAm = null;
		geaendertVon = null;
		geaendertAm = null;
	}

	public Kontakt(int kontaktID, String plz, String strasse,
			String hausnummer, String ort, String email, String telefonnummer,
			String bildpfad, boolean istOeffentlich, Benutzer erstelltVon,
			Date erstelltAm) {
		this.kontaktID = kontaktID;
		this.plz = plz;
		this.strasse = strasse;
		this.hausnummer = hausnummer;
		this.ort = ort;
		this.email = email;
		this.telefonnummer = telefonnummer;
		this.bildpfad = bildpfad;
		this.istOeffentlich = istOeffentlich;
		this.erstelltVon = erstelltVon;
		this.erstelltAm = erstelltAm;
	}

	public int getKontaktID() {
		return kontaktID;
	}

	public void setKontaktID(int kontaktID) {
		this.kontaktID = kontaktID;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getHausnummer() {
		return hausnummer;
	}

	public void setHausnummer(String hausnummer) {
		this.hausnummer = hausnummer;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefonnummer() {
		return telefonnummer;
	}

	public void setTelefonnummer(String telefonnummer) {
		this.telefonnummer = telefonnummer;
	}

	public String getBildpfad() {
		return bildpfad;
	}

	public void setBildpfad(String bildpfad) {
		this.bildpfad = bildpfad;
	}

	public boolean isIstOeffentlich() {
		return istOeffentlich;
	}

	public void setIstOeffentlich(boolean istOeffentlich) {
		this.istOeffentlich = istOeffentlich;
	}

	public Benutzer getErstelltVon() {
		return erstelltVon;
	}

	public void setErstelltVon(Benutzer erstelltVon) {
		this.erstelltVon = erstelltVon;
	}

	public Date getErstelltAm() {
		return erstelltAm;
	}

	public void setErstelltAm(Date erstelltAm) {
		this.erstelltAm = erstelltAm;
	}

	public Benutzer getGeaendertVon() {
		return geaendertVon;
	}

	public void setGeaendertVon(Benutzer geaendertVon) {
		this.geaendertVon = geaendertVon;
	}

	public Date getGeaendertAm() {
		return geaendertAm;
	}

	public void setGeaendertAm(Date geaendertAm) {
		this.geaendertAm = geaendertAm;
	}

}
