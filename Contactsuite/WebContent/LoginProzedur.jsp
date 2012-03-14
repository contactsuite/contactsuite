<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> --%>
<%@page import="contactsuite.Datenbankverbindung"%>
<%@page import="contactsuite.Benutzer"%>
<%
	String id = "Login";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- paulirish.com/2008/conditional-stylesheets-vs-css-hacks-answer-neither/ -->
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!-- Consider adding a manifest.appcache: h5bp.com/d/Offline -->
<!--[if gt IE 8]><!--> <html class="no-js" lang="en"> <!--<![endif]-->
<head>
  <meta charset="utf-8">

  <!-- Use the .htaccess and remove these lines to avoid edge case issues.
       More info: h5bp.com/i/378 -->
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

  <title></title>
  <meta name="description" content="">

  <!-- Mobile viewport optimized: h5bp.com/viewport -->
  <meta name="viewport" content="width=device-width">

  <!-- Place favicon.ico and apple-touch-icon.png in the root directory: mathiasbynens.be/notes/touch-icons -->

  <link rel="stylesheet" href="css/style.css">

  <!-- More ideas for your <head> here: h5bp.com/d/head-Tips -->

  <!-- All JavaScript at the bottom, except this Modernizr build.
       Modernizr enables HTML5 elements & feature detects for optimal performance.
       Create your own custom Modernizr build: www.modernizr.com/download/ -->
  <script src="js/libs/modernizr-2.5.2.min.js"></script>
</head>
<body>
  <!-- Prompt IE 6 users to install Chrome Frame. Remove this if you support IE 6.
       chromium.org/developers/how-tos/chrome-frame-getting-started -->
  <!--[if lt IE 7]><p class=chromeframe>Your browser is <em>ancient!</em> <a href="http://browsehappy.com/">Upgrade to a different browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">install Google Chrome Frame</a> to experience this site.</p><![endif]-->

	<div id="loginbox">
		<fieldset name="login" id="fieldLogin">
			<legend>Benutzer-Login</legend> 
			<div id="loginForm">
				<%@ page import="java.sql.*" %>
				<%@page import="contactsuite.*" %>
				<%
					// Die Loginprozedur überprüft ob die eingegebene E-Mail und das Kennwort in der Datenbank existieren.
				// Bei Erfolg erstellt Sie eine neue Sitzung für den Nutzer und navigiert weiter zur Startseite.
				
				String mail = request.getParameter("email");
				String pw = request.getParameter("passwort");
				
				Datenbankverbindung dbConnect = Datenbankverbindung.getInstance();
				Benutzer user = dbConnect.getBenutzerByEmail(mail);
				
				// Existiert der Benutzer in der Datenbank?
				if(dbConnect.istBenutzerVorhanden(user)){
					
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
			</div>
		</fieldset>	
	</div>

  <!-- JavaScript at the bottom for fast page loading -->

  <!-- Grab Google CDN's jQuery, with a protocol relative URL; fall back to local if offline -->

  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
  <script>window.jQuery || document.write('<script src="js/libs/jquery-1.7.1.min.js"><\/script>')</script>

  <!-- scripts concatenated and minified via build script -->
  <script src="js/plugins.js"></script>
  <script src="js/script.js"></script>
  <!-- end scripts -->

  <!-- Asynchronous Google Analytics snippet. Change UA-XXXXX-X to be your site's ID.
       mathiasbynens.be/notes/async-analytics-snippet -->
  <script>
    var _gaq=[['_setAccount','UA-XXXXX-X'],['_trackPageview']];
    (function(d,t){var g=d.createElement(t),s=d.getElementsByTagName(t)[0];
    g.src=('https:'==location.protocol?'//ssl':'//www')+'.google-analytics.com/ga.js';
    s.parentNode.insertBefore(g,s)}(document,'script'));
  </script>
</body>
</html>

