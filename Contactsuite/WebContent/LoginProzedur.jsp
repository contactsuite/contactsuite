<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Loginverarbeitung</title>
</head>
<body>
<%@ page import="java.sql.*" %>
<%@page import="contactsuite.*" %>
<%
// Die Loginprozedur überprüft ob die eingegebene E-Mail und das Kennwort in der Datenbank existieren.
// Bei Erfolg erstellt Sie eine neue Sitzung für den Nutzer und navigiert weiter zur Startseite.

String mail = request.getParameter("email");
String pw = request.getParameter("passwort");

DatabaseConnection dbConnect = DatabaseConnection.getInstance();
Benutzer user = dbConnect.getBenutzerByEmail(mail);

if(dbConnect.IstBenutzerVorhanden(user)){
	
		if(pw.equals(user.getPasswort())){
			HttpSession sitzung = request.getSession(true);
			
				sitzung.setAttribute("benutzerID", user.getBenutzerID());
				sitzung.setMaxInactiveInterval(3600);
				
			request.getRequestDispatcher("Controller?fcode=Kontaktverwaltung").forward(request, response);
		}
		else{
			out.print("<b>Das eingegebene Passwort ist falsch.</b><br>");
			out.print("<a href='Login.jsp'>Nochmal versuchen.</a>");
		}
	}
	else{
		out.print("<b>Die eingegebene Email-Adresse ist falsch.</b><br>");
		out.print("<a href='Login.jsp'>Nochmal versuchen.</a>");
	}

%>
</body>
</html>
<!-- dfsfds -->
