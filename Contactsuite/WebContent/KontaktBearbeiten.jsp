<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="contactsuite.*" %>
<%
int id = Integer.valueOf(request.getParameter("kontaktID")) ;

DatabaseConnection dbConnect = DatabaseConnection.getInstance();
Privatkontakt tmpKontakt = dbConnect.getPrivatkontaktById(id);

String vorname = tmpKontakt.getVorname();
String nachname = tmpKontakt.getNachname();
String strasse = tmpKontakt.getStrasse();
String hausnummer = tmpKontakt.getHausnummer();
String plz = tmpKontakt.getPlz();
String ort = tmpKontakt.getOrt();


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
  <script src="js/slider.js"></script>
</head>
<body>
  <!-- Prompt IE 6 users to install Chrome Frame. Remove this if you support IE 6.
       chromium.org/developers/how-tos/chrome-frame-getting-started -->
  <!--[if lt IE 7]><p class=chromeframe>Your browser is <em>ancient!</em> <a href="http://browsehappy.com/">Upgrade to a different browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">install Google Chrome Frame</a> to experience this site.</p><![endif]-->
  <header>

  </header>
  <div role="main">
	<div id="mainFrame">
		<div id="header">
			<div id="logo">
			</div>
		</div>
		<div id="navi">
			<ul id="navi">
				<li id="liLeft" class="active">
					<a href="#"><span>Firmenkontakte</span></a>
				</li>
				<li>
					<a href="#"><span>Privatkunden</span></a>
				</li>
				<li>
					<a href="#"><span>Benutzer</span></a>
				</li>
			</ul>
		</div>
		<div id="content">
			<div id="mainContent">
				<div id="kontaktForms">
					<form id="kontaktForm" name="Eingabe" action="http://localhost:8080/Contactsuite/Controller?fcode=KontaktSpeichern" method="post">
						<div id="neuKontaktBeschriftung">
							<p>Vorname*:</p>
							<p>Nachname*:</p>
							<p>Strasse*:</p>
							<p>Hausnummer*:</p>
							<p>Postleitzahl*:</p>
							<p>Wohnort*:</p>
							<p>E-Mail*:</p>
							<p>Telefon*:</p>
						</div>
						<div id="neuKontaktInput">
							<input name="vorname" type="text" size="30" maxlength="30" id="vorname">
							<input name="nachname" type="text" size="30" maxlength="30" id="nachname">
							<input name="strasse" type="text" size="30" maxlength="30" id="strasse">
							<input name="hausnummer" type="text" size="30" maxlength="30" id="hn">
							<input name="plz" type="text" size="30" maxlength="30" id="plz">
							<input name="ort" type="text" size="30" maxlength="30" id="ort">
							<input name="email" type="E-Mail" size="30" maxlength="30" id="kontaktEmail">
							<input name="telefon" type="text" size="30" maxlength="30" id="telefon">
						</div>
						<div id="oeffentlich">
							 <p>M�chten Sie das dieser Kontakt �ffentlich Angezeigt wird? </p>
							  <p>
								<input type="radio" name="istOeffentlich" value="istOeffentlich"> Ja<br>
								<input type="radio" name="istOeffentlich" value="istOeffentlich" checked> Nein<br>
							  </p>
						</div>
						<input type="Submit" id="anlegenButton" name="anlegenButton" value="Anlegen" >
					</form>
					<div id="errorForm">
						Folgende Felder enthalten Fehler:
						<div id="errorFormSelect">
							<ol id="errorOl">
							</ol>
						</div>
					</div>
				</div>
			</div>	
			<div id="sideBox">
				<div id="searchBox">
					<form action="BITTE NACHTRAGEN">
						<input name="seachField" type="text" size="20" maxlength="30"><br>
						<input type="button" id="searchButton" name="search" value="Suchen" onclick="BITTE NACHTRAGEN">
					</form>
				</div>
				<div id="sideNavi">
				</div>
				<div id="options">
				</div>
			</div>
		</div>
	</div>
  </div>
  <footer>

  </footer>


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