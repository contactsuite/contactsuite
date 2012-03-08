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

// Existiert der Benutzer in der Datenbank?
if(dbConnect.IstBenutzerVorhanden(user)){
	
		// Ist das eingegebene Passwort korrekt?
		if(MD5.getMd5Hash(pw).equals(user.getPasswort())){
			
			// Ist der Nutzer durch einen Administrator freigeschaltet worden?
			if(user.isIstFreigeschaltet()){
				HttpSession sitzung = request.getSession(true);
				
				sitzung.setAttribute("benutzerID", user.getBenutzerID());
				sitzung.setMaxInactiveInterval(240);  // Verbindungs-Timeout nach 4 Minuten
				
			request.getRequestDispatcher("Controller?fcode=Privatkontakte").forward(request, response);
			}else
			{
				out.print("<b>Sie wurden noch nicht durch unsere Administratoren zur Nutzung der Plattform freigeschaltet.</b><br>");
				out.print("<p>Bitte versuchen Sie es noch einmal, wenn die Freischaltung abgschlossen ist.</p>");
			}
		}
		else{
			out.print("<b>Das eingegebene Passwort ist falsch.</b><br>");
			out.print("<a href=Controller?fcode=Start>Nochmal versuchen.</a>");
		}
	}
	else{
		out.print("<b>Die eingegebene Email-Adresse ist falsch.</b><br>");
		out.print("<a href=Controller?fcode=Start>Nochmal versuchen.</a>");
	}

%>
</body>
</html>
<!-- dfsfds -->
