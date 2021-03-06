<!-- Autor: Ercument Topal
	 Stellt eine Maske f�r die Ausgabe einer Liste aller Benutzer dar. 
	 Die Daten werden mittels der Serviceklasse "Datenbankverbindung" ausgelesen und in Form einer Tabelle angezeigt.
	 Zus�tzliche Funktionalit�ten die �ber diese Maske aufgerufen werden k�nnen: - Detailansicht der Benutzer 
	 											  								 - Neuen Benutzer anlegen
	 											  								 - Benutzer bearbeiten
	 											  								 - Benutzer l�schen
	 											  								 - Anzeige der nicht freigeschalteten Benutzer	-->
	 											  								 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!doctype html>

<!-- Auf G�ltigkeit der Sitzung pr�fen. Im Fall des Nichterfolgs weiterleiten auf eine Info-Seite.
Andernfalls mit dem aktuellen Fenster fortfahren. -->
<%
	HttpSession sitzung = request.getSession(false);
Integer benutzerID = (Integer) sitzung.getAttribute("benutzerID");

if(benutzerID == null){
	request.getRequestDispatcher("Controller?fcode=Timeout").forward(request, response);
	return;
}

Datenbankverbindung dbConnect = Datenbankverbindung.getInstance();
Benutzer user = dbConnect.getBenutzerById(benutzerID);

boolean isAdmin = user.isIstAdmin();

if(!isAdmin){
	request.getRequestDispatcher("Controller?fcode=ZugriffVerweigert").forward(request, response);
}
%>

<!-- paulirish.com/2008/conditional-stylesheets-vs-css-hacks-answer-neither/ -->

<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->

<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->

<!--[if IE 8]>    <html class="no-js lt-ie9" lang="en"> <![endif]-->

<!-- Consider adding a manifest.appcache: h5bp.com/d/Offline -->

<!--[if gt IE 8]><!-->
<html class="no-js" lang="en">
<!--<![endif]-->

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

	<header> </header>

	<div role="main">

		<div id="mainFrame" class="clear">

			<div id="header">

				<div id="logo"></div>

			</div>

			<div id="navi">

				<ul id="navi">

					<li id="liLeft"><a
						href="Controller?fcode=Firmenkontakte"><span>Firmenkontakte</span></a>

					</li>

					<li><a href="Controller?fcode=Privatkontakte"><span>Privatkontakte</span></a>

					</li>

					<li class="active"><a href="Controller?fcode=Benutzer"><span>Benutzer</span></a>

					</li>

				</ul>

			</div>

			<div id="content">

				<div id="mainContent">
					<%@ page import="contactsuite.*"%>
					<%@ page import="java.util.*"%>
					<%
				List<Benutzer> lstUser = dbConnect.getBenutzer();
				
				int gesperrteBenutzer = 0;
				boolean farbig = false;
				
				out.println("<table id=\"mainTable\">");	
				
				out.println("<tr id=\"tabFarbig\"><td>");
				out.println("<b>Benutzername</b>");
				out.println("</td><td colspan=\"2\">");
				out.println("<b>Status</b>");
				out.println("</td><td>");
				out.println("<b>Rechte</b>");
				out.println("</td><td colspan=\"3\">");
				out.println("<b>Aktion</b>");
				out.println("</td><td>");
				
				for(Benutzer  tmpUser : lstUser){

					if(farbig){
						out.println("<tr id=\"tabFarbig\"><td>");
						out.println(tmpUser.getEmail());
						out.println("</td><td>");
						boolean freigeschaltet = tmpUser.isIstFreigeschaltet();
						out.println(freigeschaltet ? "freigeschaltet" : "nicht freigeschaltet");
						out.println("</td><td>");
						boolean online = tmpUser.isIstGeloescht();
						out.println("</td><td>");
						boolean admin = tmpUser.isIstAdmin();
						out.println(admin ? "Admin" : "kein Admin");
						out.println("</td><td>");
						out.println("<a href=Controller?fcode=Details&typ=benutzer&ID=" + tmpUser.getBenutzerID() + ">Details</a>");
						out.println("</td><td>");
						out.println("<a href=Controller?fcode=BenutzerBearbeiten&ID="+tmpUser.getBenutzerID()+">Bearbeiten</a>");
						out.println("</td><td>");
						out.println("<a href=Controller?fcode=Loeschen&typ=benutzer&ID=" + tmpUser.getBenutzerID() + ">L�schen</a>");
						out.println("</td></tr>");
					}
					else{
						out.println("<tr id=\"tabStandard\"><td>");
						out.println(tmpUser.getEmail());
						out.println("</td><td>");
						boolean freigeschaltet = tmpUser.isIstFreigeschaltet();
						out.println(freigeschaltet ? "freigeschaltet" : "nicht freigeschaltet");
						out.println("</td><td>");
						boolean online = tmpUser.isIstGeloescht();
						out.println("</td><td>");
						boolean admin = tmpUser.isIstAdmin();
						out.println(admin ? "Admin" : "kein Admin");
						out.println("</td><td>");
						out.println("<a href=Controller?fcode=Details&typ=benutzer&ID=" + tmpUser.getBenutzerID() + ">Details</a>");
						out.println("</td><td>");
						out.println("<a href=Controller?fcode=BenutzerBearbeiten&ID="+tmpUser.getBenutzerID()+">Bearbeiten</a>");
						out.println("</td><td>");
						out.println("<a href=Controller?fcode=Loeschen&typ=benutzer&ID=" + tmpUser.getBenutzerID() + ">L�schen</a>");
						out.println("</td></tr>");
					}
					
					if(!(tmpUser.isIstFreigeschaltet())){
						gesperrteBenutzer++;
					}
					farbig = !farbig;
				}
				
				out.println("</table>");
				
				%>
				</div>

				<div id="sideBox">

					<div id="searchBox">

						<!-- <form action="Controller?fcode=KontaktSuche" method="post">

							<input name="searchField" type="text" size="20" maxlength="30">

							<input type="submit" id="searchButton" name="search"
								value=""> <input type="hidden" id="loginButton"
								name="fcode" value="KontaktSuche">
						</form> -->

					</div>

					<div id="sideNavi">

						<ul>
							<li><a href="Controller?fcode=BenutzerAnlegen">Neuer
									Benutzer</a></li>
							<li><a href="Controller?fcode=Logout">Logout</a></li>
						</ul>

					</div>

					<div id="options">
						<%
						if(gesperrteBenutzer != 0){
							out.write("<b>Es warten noch " + gesperrteBenutzer + " Benutzer auf Freischaltung </bS>"); 
						}
						
					%>
					</div>

				</div>

			</div>

		</div>

	</div>

	<footer> </footer>





	<!-- JavaScript at the bottom for fast page loading -->



	<!-- Grab Google CDN's jQuery, with a protocol relative URL; fall back to local if offline -->

	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>

	<script>
		window.jQuery
				|| document
						.write('<script src="js/libs/jquery-1.7.1.min.js"><\/script>')
	</script>



	<!-- scripts concatenated and minified via build script -->

	<script src="js/plugins.js"></script>

	<script src="js/script.js"></script>

	<!-- end scripts -->



	<!-- Asynchronous Google Analytics snippet. Change UA-XXXXX-X to be your site's ID.

       mathiasbynens.be/notes/async-analytics-snippet -->

	<script>
		var _gaq = [ [ '_setAccount', 'UA-XXXXX-X' ], [ '_trackPageview' ] ];

		(function(d, t) {
			var g = d.createElement(t), s = d.getElementsByTagName(t)[0];

			g.src = ('https:' == location.protocol ? '//ssl' : '//www')
					+ '.google-analytics.com/ga.js';

			s.parentNode.insertBefore(g, s)
		}(document, 'script'));
	</script>

</body>

</html>
