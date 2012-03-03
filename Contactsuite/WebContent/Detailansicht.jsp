<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="contactsuite.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
int id = Integer.valueOf(request.getParameter("kontaktID")) ;
String typ = request.getParameter("typ");

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
}
%>

</body>
</html>