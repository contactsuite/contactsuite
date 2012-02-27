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
<%
String email, passwort;
String mail = request.getParameter("email");
String pw = request.getParameter("passwort");

// TODO: Direkte DB_Ansteuerung durch Aufruf über DB-Connector Klasse ersetzen
try{
	Class.forName("com.mysql.jdbc.Driver");
	Connection conn = DriverManager.getConnection("jdbc:mysql://pma.postopus.de/customers_s001", "customers_s001", "dqJAMKPR2x5B5JV8");
	Statement stmt = conn.createStatement();
	
	String sql = "SELECT * FROM tblbenutzer WHERE email like '"+mail+"'";
	ResultSet res = stmt.executeQuery(sql);
	res.first();
	email = res.getString(2);
	if(mail.equals(email)){
		passwort = res.getString(3);
		if(pw.equals(passwort)){
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
}
catch(ClassNotFoundException err){
	out.println("DB-Driver nicht gefunden.\n");
	out.println(err);
}
catch(SQLException err){
	out.println("Connect nicht moeglich.\n");
	out.println(err);
}
%>
</body>
</html>