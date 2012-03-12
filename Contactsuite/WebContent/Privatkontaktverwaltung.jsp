<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="contactsuite.*"%>
<%@ page import="java.util.*"%>
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

		<div id="mainFrame">

			<div id="header">

				<div id="logo"></div>

			</div>

			<div id="navi">

				<ul id="navi">

					<li id="liLeft" class="active"><a
						href="Controller?fcode=Firmenkontakte"><span>Firmenkontakte</span></a>

					</li>

					<li><a href="Controller?fcode=Privatkontakte"><span>Privatkontakte</span></a>

					</li>


					<%
					DatabaseConnection dbConnect = DatabaseConnection.getInstance();
					Benutzer user = dbConnect.getBenutzerById(benutzerID);
					
					boolean admin = user.isIstAdmin();
					
					if(admin){
						out.println("<li>");
						out.println("<a href=\"Controller?fcode=Benutzer\"><span>Benutzer</span></a>");
						out.println("</li>");
					}
				%>


				</ul>

			</div>

			<div id="content">

				<div id="mainContent">

				<%
				List<Privatkontakt> lstKontakt = (request.getParameterMap().containsKey("searchField"))?dbConnect.getPrivatkontakte(request.getParameter("searchField"), benutzerID):dbConnect.getPrivatkontakteByBenutzerId(benutzerID);
				
				boolean farbig = false;
				
				out.println("<table id=\"mainTable\">");	
				
				out.println("<tr id=\"tabFarbig\"><td>");
				out.println("<b>Vorname</b>");
				out.println("</td><td>");
				out.println("<b>Nachname</b>");
				out.println("</td><td>");
				out.println("<b>Ort</b>");
				out.println("</td><td>");
				out.println("<b>Status</b>");
				out.println("</td><td colspan=\"3\">");
				out.println("<b>Aktion</b>");
				out.println("</td><td>");
				
				for(Privatkontakt  tmpKontakt : lstKontakt){

					
					
					if(farbig){
						out.println("<tr id=\"tabFarbig\"><td>");
						out.println(tmpKontakt.getVorname());
						out.println("</td><td>");
						out.println(tmpKontakt.getNachname());
						out.println("</td><td>");
						out.println(tmpKontakt.getOrt());
						out.println("</td><td>");
						String status = new String();
						if (!tmpKontakt.isIstOeffentlich()){
							 status = "Privat";
						} else {
							status = "�ffentlich";
						}
						out.println(status);
						out.println("</td><td>");
						out.println("<a href=Controller?fcode=Details&typ=privat&ID=" + tmpKontakt.getKontaktID() + ">Details</a>");
						out.println("</td><td>");
						out.println("<a href=Controller?fcode=KontaktBearbeiten&typ=privat&ID=" + tmpKontakt.getKontaktID() + ">Bearbeiten</a>");
						out.println("</td><td>");
						out.println("<a href=Controller?fcode=Loeschen&typ=privat&ID=" + tmpKontakt.getKontaktID() +"&benutzerID="+benutzerID+ ">L�schen</a>");
						out.println("</td><td>");
						
					}
					else{
						out.println("<tr id=\"tabStandard\"><td>");
						out.println(tmpKontakt.getVorname());
						out.println("</td><td>");
						out.println(tmpKontakt.getNachname());
						out.println("</td><td>");
						out.println(tmpKontakt.getOrt());
						out.println("</td><td>");
						String status = new String();
						if (!tmpKontakt.isIstOeffentlich()){
							 status = "Privat";
						} else {
							status = "�ffentlich";
						}
						out.println(status);
						out.println("</td><td>");
						out.println("<a href=Controller?fcode=Details&typ=privat&ID=" + tmpKontakt.getKontaktID() + ">Details</a>");
						out.println("</td><td>");
						out.println("<a href=Controller?fcode=KontaktBearbeiten&typ=privat&ID=" + tmpKontakt.getKontaktID() + ">Bearbeiten</a>");
						out.println("</td><td>");
						out.println("<a href=Controller?fcode=Loeschen&typ=privat&ID=" + tmpKontakt.getKontaktID()+"&benutzerID="+ benutzerID + ">L�schen</a>");
						out.println("</td><td>");
						
					}
					
					farbig = !farbig;
				}
				
				out.println("</table>");
				
				%>
				</div>

				<div id="sideBox">

				<div id="searchBox">
					<form action="Controller?fcode=Privatkontakte" method="post">
						<%
							String sucheingabe = (request.getParameterMap().containsKey("searchField"))?request.getParameter("searchField"):"";
							out.println("<input name=\"searchField\" type=\"text\" size=\"20\" maxlength=\"30\" value=\""+sucheingabe+"\"><br>");
						%>
						<input type="submit" id="searchButton" name="search" value="Suche">
					</form>
				</div>

					<div id="sideNavi">

						<ul>
							<li><a href="Controller?fcode=PrivatkontaktAnlegen">Neuer
									Kontakt</a></li>
							<li><a href=#>Aktion</a></li>
							<li><a href=#>Aktion</a></li>
							<li><a href=#>Aktion</a></li>
							<li><a href="Controller?fcode=Logout">Logout</a></li>
						</ul>

					</div>

					<div id="options"></div>

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
