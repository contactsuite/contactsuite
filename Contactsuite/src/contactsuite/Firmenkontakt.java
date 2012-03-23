package contactsuite;

import java.util.Date;

/**
 * Die Klasse Firmenkontakt ist eine Unterklasse der Klasse Kontakt. Sie
 * erweitert den Kontakt um die Attribute Firmenname und Ansprechpartner.
 * 
 * 
 * @author Eduard Dojan
 * 
 */
public class Firmenkontakt extends Kontakt {

	private String firmenname;
	private String ansprechpartner;

	public Firmenkontakt() {
		super();
		firmenname = "";
		ansprechpartner = "";
	}

	Firmenkontakt(int kontaktID, String plz, String strasse, String hausnummer,
			String ort, String email, String telefonnummer, String bildpfad,
			boolean istOeffentlich, int erstelltVon, Date erstelltAm,
			String firmenname, String ansprechpartner) {

		super(kontaktID, plz, strasse, hausnummer, ort, email, telefonnummer,
				bildpfad, istOeffentlich, erstelltVon, erstelltAm);
		this.firmenname = firmenname;
		this.ansprechpartner = ansprechpartner;
	}

	public String getFirmenname() {
		return firmenname;
	}

	public void setFirmenname(String firmenname) {
		this.firmenname = firmenname;
	}

	public String getAnsprechpartner() {
		return ansprechpartner;
	}

	public void setAnsprechpartner(String ansprechpartner) {
		this.ansprechpartner = ansprechpartner;
	}

}
