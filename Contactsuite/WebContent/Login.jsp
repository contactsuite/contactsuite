<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> --%>
<%@page import="contactsuite.DatabaseConnection"%>
<%@page import="contactsuite.Benutzer"%>
<% String id = "Login"; %>
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
				<form name="Logineingabe" action="Controller" method="post">  
					<div id="loginBeschriftung">
						<p>Benutzer:</p>
						<p>Passwort:</p>

					</div>
					<div id="loginInput">
						<input name="email" type="text" size="30" maxlength="30"><br>
						<input name="passwort" type="password" size="30" maxlength="30">
					</div>
					<input type="submit" id="loginButton" name=fcode value="Login">
				</form>
				<div id="loginReg">
					<p>Sie sind noch nicht Registriert? <a href="javascript: return false">Jetzt Registrieren!</a> </p>

				</div>
			</div>
			<div id="Regristrieren">
				<p>Bitte füllen Sie folgendes Formular aus. Unser Administrator wird Sie per E-Mail benarichtigen, falls die Regristrierung erfolgt ist.</p>
				<form name="Registrierung" action="" method="post">
					<div id="loginBeschriftung">
						<p>E-Mail:</p>
						<p>Passwort:</p>

						<p>Passwort (wiederholen):</p>
					</div>
					<div id="loginInput">
						<input name="email" type="text" size="30" maxlength="30">
						<input name="passwort" type="password" size="30" maxlength="30">
						<input name="passwort2" type="password" size="30" maxlength="30">
					</div>
					<input type="submit" id="loginButton" name="Login" value="Abschicken!">

					<div id="regClose"><a href="javascript: return false">Zurück zum Login<a></div>
				</form>
			</div>
		</fieldset>
	</div>
	<%-- <%
	if(request.getParameterMap().containsKey("Login")){
		
		String email = request.getParameter("email");
		String passwort = request.getParameter("passwort");
		String passwort2 = request.getParameter("passwort2");
		
		
		Benutzer neuerBenutzer = new Benutzer();
		neuerBenutzer.setEmail(email);
		neuerBenutzer.setPasswort(passwort);
		//Abfragen ob der Benutzer schon existiert
		if(DatabaseConnection.getInstance().IstBenutzerVorhanden(neuerBenutzer)){
			out.println("Der Benutzer existierts bereits");
		}
		else if(DatabaseConnection.getInstance().SpeicherDaten(neuerBenutzer)==1){
			out.println("Der Benutzer wurde erfolgreich gespeichert.");
		}
		else{
			out.println("Der Benutzer wurde nicht erfolgreich gespeichert.");
		}

		}
	%> --%>

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