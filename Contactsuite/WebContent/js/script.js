/* Author: E-Topal

*/

$(document).ready(function() {

$('#advancedSearchBox a').click(function() {
  $('#advancedSearch').slideToggle('slow');
  $('#searchfield').toggle('slow');
});

$('#loginReg a').click(function() {
  $('#Regristrieren').slideToggle('slow');
  $('#loginForm').slideToggle('slow');
  $("#loginbox").animate({"height": "290px"}, "slow");
});

$('#regClose a').click(function() {
  $('#Regristrieren').slideToggle('slow');
  $('#loginForm').slideToggle('slow');
  $("#loginbox").animate({"height": "160px"}, "slow");
});



 
    $('#anlegenButton').click(function() { 
		
		// Error Klassen unsichtbarstellen
        $(".error").hide();
		//		$("#errorForm").hide();

		// Variablen deklarieren
		var hasError = false;
		
		
		var vornameVal = $("#vorname").val();
		var nachnameVal = $("#nachname").val();
		var strasseVal = $("#strasse").val();
		var hnVal = $("#hn").val();
		var plzVal = $("#plz").val();
		var ortVal = $("#ort").val();
		var emailaddressVal = $("#kontaktEmail").val();
		var telefonVal = $("#telefon").val();
		
		// E-Mail Validierung
		var emailValid = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;

		var numberValid = /^[0-9]+$/gi;
		
		// E-Mail
        if(emailaddressVal == '') {
			$("#kontaktEmail").addClass("errorRed");
            $("#errorOl").after('<li class="error"><span>Bitte tragen Sie die E-Mail Adresse ein.</span></li>');
			hasError = true;
        }
        else if(!emailValid.test(emailaddressVal)) {
			$("#kontaktEmail").addClass("errorRed");
            $("#errorOl").after('<li class="error"><span>Bitte tragen Sie die GÜLTIGE E-Mail Adresse ein.</span>');
            hasError = true;
        }
		
		// Telefon
		 if(telefonVal == '') {
			$("#telefon").addClass("errorRed");
            $("#errorOl").after('<li class="error"><span>Bitte tragen Sie die Telefonnummer ein.</span></li>');
			hasError = true;
        }else if (!numberValid.test(telefonVal)){
				$("#telefon").addClass("errorRed");
				$("#errorOl").after('<li class="error"><span>Bitte tragen Sie die GÜLTIGE Telefonnummer ein.</span></li>');
				hasError = true;
		}  else{
				$("#telefon").removeClass("errorRed");
		}
		
		// Ort
		 if(ortVal == '') {
			$("#ort").addClass("errorRed");
            $("#errorOl").after('<li class="error"><span>Bitte tragen Sie den Ort ein.</span></li>');
			hasError = true;
        }else if (ortVal != ''){
			$("#ort").removeClass("errorRed");
		}
		
		// Postleitzahl
		 if(plzVal == '') {
			$("#plz").addClass("errorRed");
            $("#errorOl").after('<li class="error"><span>Bitte tragen Sie die Postleizahl ein.</span></li>');
			hasError = true;
        } else if (!numberValid.test(plzVal)||plzVal.length!=5){
				$("#plz").addClass("errorRed");
				$("#errorOl").after('<li class="error"><span>Bitte tragen Sie die GÜLTIGE Postleizahl ein.</span></li>');
				hasError = true;
		}  else{
				$("#plz").removeClass("errorRed");
		}
		
		// Hausnummer
		 if(hnVal == '') {
			$("#hn").addClass("errorRed");
            $("#errorOl").after('<li class="error"><span>Bitte tragen Sie die Hausnummer ein.</span></li>');
			hasError = true;
        } else if (hnVal != '')	{
			$("#hn").removeClass("errorRed");
		}
		
		// Strasse
		 if(strasseVal == '') {
			$("#strasse").addClass("errorRed");
            $("#errorOl").after('<li class="error"><span>Bitte tragen Sie die Strasse ein.</span></li>');
			hasError = true;
        }else if (strasseVal != ''){
			$("#strasse").removeClass("errorRed");
		}
		
		// Nachname
		 if(nachnameVal == '') {
			$("#nachname").addClass("errorRed");
            $("#errorOl").after('<li class="error"><span>Bitte tragen Sie den Nachname ein.</span></li>');
			hasError = true;
        }else if (nachnameVal != ''){
			$("#nachname").removeClass("errorRed");
		}
		
		// Vorname
		 if(vornameVal == '') {
			$("#vorname").addClass("errorRed");
            $("#errorOl").after('<li class="error"><span>Bitte tragen Sie den Vornamen ein.</span></li>');
			hasError = true;
        }else if (vornameVal != ''){
			$("#vorname").removeClass("errorRed");
		}
 
 
 
        if(hasError == true) { 
			return false; 
		}
 
    });
});