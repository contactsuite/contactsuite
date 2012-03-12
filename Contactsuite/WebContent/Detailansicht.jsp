<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="contactsuite.*" %>
<!-- Auf Gültigkeit der Sitzung prüfen. Im Fall des Nichterfolgs weiterleiten auf eine Info-Seite.
Andernfalls mit dem aktuellen Fenster fortfahren. -->
<%
HttpSession sitzung = request.getSession(false);
Integer benutzerID = (Integer) sitzung.getAttribute("benutzerID");

if(benutzerID == null){
	request.getRequestDispatcher("Controller?fcode=Timeout").forward(request, response);
	return;
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
String typ = request.getParameter("typ");

int id = Integer.valueOf(request.getParameter("ID")) ;


DatabaseConnection dbConnect = DatabaseConnection.getInstance();

// Für Privatkontakte

if(typ.equals("privat")){

	Privatkontakt tmpKontakt = dbConnect.getPrivatkontaktById(id);

	out.println("<h1>Detailansicht</h1>");
	out.println("<p>Name: " + tmpKontakt.getVorname() + "</p>");
	out.println("<p>Nachname: " + tmpKontakt.getNachname() + "</p>");
	out.println("<p>Strasse: " + tmpKontakt.getStrasse() + "</p>");
	out.println("<p>Hausnummer: " + tmpKontakt.getHausnummer() + "</p>");
	out.println("<p>Postleitzahl: " + tmpKontakt.getPlz() + "</p>");
	out.println("<p>Ort: " + tmpKontakt.getOrt() + "</p>");
	out.println("<p>E-Mail: " + tmpKontakt.getEmail() + "</p>");
	out.println("<p>Telefon: " + tmpKontakt.getTelefonnummer() + "</p>");
}else if(typ.equals("firma")){
	
	// Für Firmenkontakte
	
	Firmenkontakt tmpKontakt = dbConnect.getFirmenkontaktById(id);

	out.println("<h1>Detailansicht</h1>");
	out.println("<p>Firmenname: " + tmpKontakt.getFirmenname() + "</p>");
	out.println("<p>Ansprechpartner: " + tmpKontakt.getAnsprechpartner() + "</p>");
	out.println("<p>Strasse: " + tmpKontakt.getStrasse() + "</p>");
	out.println("<p>Hausnummer: " + tmpKontakt.getHausnummer() + "</p>");
	out.println("<p>Postleitzahl: " + tmpKontakt.getPlz() + "</p>");
	out.println("<p>Ort: " + tmpKontakt.getOrt() + "</p>");
	out.println("<p>E-Mail: " + tmpKontakt.getEmail() + "</p>");
	out.println("<p>Telefon: " + tmpKontakt.getTelefonnummer() + "</p>");
}else if(typ.equals("benutzer")){
	
	// Für Benutzer
	
	Benutzer tmpUser = dbConnect.getBenutzerById(id);
	
	out.println("<h1>Detailansicht</h1>");
	out.println("<p>E-Mail: " + tmpUser.getEmail() + "</p>");
	
	boolean freigeschaltet = tmpUser.isIstFreigeschaltet();
	out.println(freigeschaltet ? "freigeschaltet" : "nicht freigeschaltet");
	out.println("<br>");
	
	boolean admin = tmpUser.isIstAdmin();
	out.println(admin ? "Admin" : "kein Admin");
}
%>

</body>
</html>