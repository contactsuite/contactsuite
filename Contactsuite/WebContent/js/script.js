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
		
		$(".error").hide();
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
		var numberValid= /^[0-9]+$/gi;
		
		// E-Mail
        if(emailaddressVal == '') {
			$("#kontaktEmail").addClass("errorRed");
			$("#errorEmail").show();
			hasError = true;
        }
        else if(!emailValid.test(emailaddressVal)) {
			$("#kontaktEmail").addClass("errorRed");
			$("#errorEmailG").show();
            hasError = true;
        }else{
			$("#errorEmailG").hide();
			$("#errorEmail").hide();
			$("#kontaktEmail").removeClass("errorRed");
		}
		
		// Telefon
		var numberValid= /^[0-9]+$/gi;
		if(telefonVal == '') {
			$("#telefon").addClass("errorRed");
			$("#errorTn").show();
			hasError = true;
        }else if (!numberValid.test(telefonVal)){
				$("#telefon").addClass("errorRed");
				$("#errorTnG").show();
				hasError = true;
		}  else{
				$("#errorTnG").hide();
				$("#errorTn").hide();
				$("#telefon").removeClass("errorRed");
		}
		
		// Postleitzahl
		var numberValid= /^[0-9]+$/gi;
		 if(plzVal == '') {
			$("#plz").addClass("errorRed");
            $("#errorPlz").show();			
			hasError = true;
        } else if (!numberValid.test(plzVal)||plzVal.length!=5){
				$("#plz").addClass("errorRed");
				$("#errorPlzG").show();
				hasError = true;
		}  else{
				$("#plz").removeClass("errorRed");
				$("#errorPlz").hide();
				$("#errorPlzG").hide();
		}
		
		// Ort
		 if(ortVal == '') {
			$("#ort").addClass("errorRed");
            $("#errorOrt").show();
			hasError = true;
        }else if (ortVal != ''){
			$("#ort").removeClass("errorRed");
			$("#errorOrt").hide();
		}
		

		
		// Hausnummer
		 if(hnVal == '') {
			$("#hn").addClass("errorRed");
            $("#errorHn").show();
			hasError = true;
        } else if (hnVal != '')	{
			$("#hn").removeClass("errorRed");
			$("#errorHn").hide();
		}
		
		// Strasse
		 if(strasseVal == '') {
			$("#strasse").addClass("errorRed");
            $("#errorStrasse").show();
			hasError = true;
        }else if (strasseVal != ''){
			$("#strasse").removeClass("errorRed");
			$("#errorStrasse").hide();
		}
		
		// Nachname
		 if(nachnameVal == '') {
			$("#nachname").addClass("errorRed");
            $("#errorNachname").show();
			hasError = true;
        }else if (nachnameVal != ''){
			$("#nachname").removeClass("errorRed");
			$("#errorNachname").hide();
		}
		
		// Vorname
		 if(vornameVal == '') {
			$("#vorname").addClass("errorRed");
            $("#errorVorname").show();
			hasError = true;
        }else if (vornameVal != ''){
			$("#vorname").removeClass("errorRed");
			$("#errorVorname").hide();
		}
 
 
 
        if(hasError == true) {
			$("#errorForm").slideDown("slow");
			return false;
		}
 
    });
});