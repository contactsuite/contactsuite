<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Kontakt anlegen</title>
</head>
<body>
<%@ page import="contactsuite.*" %>
<%
DatabaseConnection dbConnect = DatabaseConnection.getInstance();


String vorname = request.getParameter("vorname");
String nachname = request.getParameter("nachname");
String strasse = request.getParameter("strasse");
String hausnummer = request.getParameter("hausnummer");
String plz = request.getParameter("plz");
String ort = request.getParameter("ort");
String email = request.getParameter("email");
String telefonnummer = request.getParameter("telefonnummer");

Privatkontakt kon = new Privatkontakt();

kon.setVorname(vorname);
kon.setNachname(nachname);
kon.setStrasse(strasse);
kon.setHausnummer(hausnummer);
kon.setPlz(plz);
kon.setOrt(ort);
kon.setEmail(email);
kon.setTelefonnummer(telefonnummer);

dbConnect.SpeicherDaten(kon);

%>
<p>Kontakt Anlegen JSP</p>
</body>
</html>