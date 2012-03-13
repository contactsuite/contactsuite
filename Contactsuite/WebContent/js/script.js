/* Author: E-Topal

*/
(function($) {

    $.circulate = function(el, options) {
    
        var base = this,
            origWidth, origHeight, newWidth, origLeft, origTop;   
              
        base.$el = $(el);
        base.$el.data("circulate", base);
        
        base.stopAnimation = function() {
            base.options.keepGoing = false;
        }
        
        base.runAnimation = function() {
        
            if (base.options.keepGoing) {
                
                origWidth = base.$el.width();
                origHeight = base.$el.height();
                
                origLeft = base.$el.position().left;
                origTop = base.$el.position().top;
                
                if (base.options.sizeAdjustment == 100) {
                    newWidth = origWidth;
                    newHeight = origHeight;
                    halfWayWidth = origWidth;
                    halfWayHeight = origHeight;
                } else {
                    newWidth = parseInt(origWidth) * (base.options.sizeAdjustment / 100);
                    newHeight = parseInt(origHeight) * (base.options.sizeAdjustment / 100);
                    halfWayWidth = (parseInt(origWidth) + newWidth) / 2;
                    halfWayHeight = (parseInt(origHeight) + newHeight) / 2;
                };
                
                if (base.$el.css("position") != "absolute") {
                    base.$el.css("position", "relative");
                }
                base.$el.css("z-index", base.options.zIndexValues[0]); 
                
                // Would be nice to only start animations if currently unanimated. Like this:
                // base.$el.filter(':not(:animated)').animate({
                // But this is screwing up loops (returns empty set on second go-around)

                base.$el.animate({
                    top: ["+=" + (base.options.height / 2) + "px", 'easeInQuad'],
                    left: ["+=" + (base.options.width / 2) + "px", 'easeOutQuad'],
                    width: [halfWayWidth, 'linear'],
                    height: [halfWayHeight, 'linear'],
                    opacity: 1
                }, base.options.speed, function() { base.$el.css("z-index", base.options.zIndexValues[1]); })
                .animate({
                    top: ["+=" + (base.options.height / 2) + "px", 'easeOutQuad'],
                    left: ["-=" + (base.options.width / 2) + "px", 'easeInQuad'],
                    width: [newWidth, 'linear'],
                    height: [newHeight, 'linear']
                }, base.options.speed, function() { base.$el.css("z-index", base.options.zIndexValues[2]); })
                .animate({
                    top: ["-=" + (base.options.height / 2) + "px", 'easeInQuad'],
                    left: ["-=" + (base.options.width / 2) + "px", 'easeOutQuad'],
                    width: [halfWayWidth, 'linear'],
                    height: [halfWayHeight, 'linear']
                }, base.options.speed, function() { base.$el.css("z-index", base.options.zIndexValues[3]); })
                .animate({
                    top: ["-=" + (base.options.height / 2) + "px", 'easeOutQuad'],
                    left: ["+=" + (base.options.width / 2) + "px", 'easeInQuad'],
                    width: [origWidth, 'linear'],
                    height: [origHeight, 'linear']
                }, base.options.speed, function() {
                
                        base.$el.css("z-index", base.options.zIndexValues[0]);
                                                                
                        if (base.options.loop === true) {
                            base.runAnimation();
                        }
                    
                    });
                
            }

        };
        
        base.init = function() {
                
            base.options = $.extend({},$.circulate.defaultOptions, options);
            
            base.runAnimation();
                        
        };
                
        base.init();
        
    };
    
    $.circulate.defaultOptions = {
        speed: 400,
        height: 200,
        width: 200,
        sizeAdjustment: 100,  // percentage
        loop: false,          // recurrsive?
        zIndexValues: [1, 1, 1, 1],
        keepGoing: true       // internal only
    };
    
    $.fn.circulate = function(options) {
        if (typeof(options) === "string") {
			return this.each(function() { 
			    var safeGuard = $(this).data('circulate');
			    if (safeGuard) { safeGuard.stopAnimation(); }
			});
        } else { 
            return this.each(function() {
                (new $.circulate(this, options));
            });
        } 
    };
    
})(jQuery);

/*
 * jQuery EasIng v1.1.2 - http://gsgd.co.uk/sandbox/jquery.easIng.php
 *
 * Uses the built In easIng capabilities added In jQuery 1.1
 * to offer multiple easIng options
 *
 * Copyright (c) 2007 George Smith
 * Licensed under the MIT License:
 *   http://www.opensource.org/licenses/mit-license.php
 */

// t: current time, b: begInnIng value, c: change In value, d: duration

jQuery.extend(jQuery.easing,
{
	easeInQuad: function (x, t, b, c, d) {
		return c*(t/=d)*t + b;
	},
	easeOutQuad: function (x, t, b, c, d) {
		return -c *(t/=d)*(t-2) + b;
	},
	easeInOutQuad: function (x, t, b, c, d) {
		if ((t/=d/2) < 1) return c/2*t*t + b;
		return -c/2 * ((--t)*(t-2) - 1) + b;
	},
	easeInCubic: function (x, t, b, c, d) {
		return c*(t/=d)*t*t + b;
	},
	easeOutCubic: function (x, t, b, c, d) {
		return c*((t=t/d-1)*t*t + 1) + b;
	},
	easeInOutCubic: function (x, t, b, c, d) {
		if ((t/=d/2) < 1) return c/2*t*t*t + b;
		return c/2*((t-=2)*t*t + 2) + b;
	},
	easeInQuart: function (x, t, b, c, d) {
		return c*(t/=d)*t*t*t + b;
	},
	easeOutQuart: function (x, t, b, c, d) {
		return -c * ((t=t/d-1)*t*t*t - 1) + b;
	},
	easeInOutQuart: function (x, t, b, c, d) {
		if ((t/=d/2) < 1) return c/2*t*t*t*t + b;
		return -c/2 * ((t-=2)*t*t*t - 2) + b;
	},
	easeInQuint: function (x, t, b, c, d) {
		return c*(t/=d)*t*t*t*t + b;
	},
	easeOutQuint: function (x, t, b, c, d) {
		return c*((t=t/d-1)*t*t*t*t + 1) + b;
	},
	easeInOutQuint: function (x, t, b, c, d) {
		if ((t/=d/2) < 1) return c/2*t*t*t*t*t + b;
		return c/2*((t-=2)*t*t*t*t + 2) + b;
	},
	easeInSine: function (x, t, b, c, d) {
		return -c * Math.cos(t/d * (Math.PI/2)) + c + b;
	},
	easeOutSine: function (x, t, b, c, d) {
		return c * Math.sin(t/d * (Math.PI/2)) + b;
	},
	easeInOutSine: function (x, t, b, c, d) {
		return -c/2 * (Math.cos(Math.PI*t/d) - 1) + b;
	},
	easeInExpo: function (x, t, b, c, d) {
		return (t==0) ? b : c * Math.pow(2, 10 * (t/d - 1)) + b;
	},
	easeOutExpo: function (x, t, b, c, d) {
		return (t==d) ? b+c : c * (-Math.pow(2, -10 * t/d) + 1) + b;
	},
	easeInOutExpo: function (x, t, b, c, d) {
		if (t==0) return b;
		if (t==d) return b+c;
		if ((t/=d/2) < 1) return c/2 * Math.pow(2, 10 * (t - 1)) + b;
		return c/2 * (-Math.pow(2, -10 * --t) + 2) + b;
	},
	easeInCirc: function (x, t, b, c, d) {
		return -c * (Math.sqrt(1 - (t/=d)*t) - 1) + b;
	},
	easeOutCirc: function (x, t, b, c, d) {
		return c * Math.sqrt(1 - (t=t/d-1)*t) + b;
	},
	easeInOutCirc: function (x, t, b, c, d) {
		if ((t/=d/2) < 1) return -c/2 * (Math.sqrt(1 - t*t) - 1) + b;
		return c/2 * (Math.sqrt(1 - (t-=2)*t) + 1) + b;
	},
	easeInElastic: function (x, t, b, c, d) {
		var s=1.70158;var p=0;var a=c;
		if (t==0) return b;  if ((t/=d)==1) return b+c;  if (!p) p=d*.3;
		if (a < Math.abs(c)) { a=c; var s=p/4; }
		else var s = p/(2*Math.PI) * Math.asin (c/a);
		return -(a*Math.pow(2,10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )) + b;
	},
	easeOutElastic: function (x, t, b, c, d) {
		var s=1.70158;var p=0;var a=c;
		if (t==0) return b;  if ((t/=d)==1) return b+c;  if (!p) p=d*.3;
		if (a < Math.abs(c)) { a=c; var s=p/4; }
		else var s = p/(2*Math.PI) * Math.asin (c/a);
		return a*Math.pow(2,-10*t) * Math.sin( (t*d-s)*(2*Math.PI)/p ) + c + b;
	},
	easeInOutElastic: function (x, t, b, c, d) {
		var s=1.70158;var p=0;var a=c;
		if (t==0) return b;  if ((t/=d/2)==2) return b+c;  if (!p) p=d*(.3*1.5);
		if (a < Math.abs(c)) { a=c; var s=p/4; }
		else var s = p/(2*Math.PI) * Math.asin (c/a);
		if (t < 1) return -.5*(a*Math.pow(2,10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )) + b;
		return a*Math.pow(2,-10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )*.5 + c + b;
	},
	easeInBack: function (x, t, b, c, d, s) {
		if (s == undefined) s = 1.70158;
		return c*(t/=d)*t*((s+1)*t - s) + b;
	},
	easeOutBack: function (x, t, b, c, d, s) {
		if (s == undefined) s = 1.70158;
		return c*((t=t/d-1)*t*((s+1)*t + s) + 1) + b;
	},
	easeInOutBack: function (x, t, b, c, d, s) {
		if (s == undefined) s = 1.70158; 
		if ((t/=d/2) < 1) return c/2*(t*t*(((s*=(1.525))+1)*t - s)) + b;
		return c/2*((t-=2)*t*(((s*=(1.525))+1)*t + s) + 2) + b;
	},
	easeInBounce: function (x, t, b, c, d) {
		return c - jQuery.easing.easeOutBounce (x, d-t, 0, c, d) + b;
	},
	easeOutBounce: function (x, t, b, c, d) {
		if ((t/=d) < (1/2.75)) {
			return c*(7.5625*t*t) + b;
		} else if (t < (2/2.75)) {
			return c*(7.5625*(t-=(1.5/2.75))*t + .75) + b;
		} else if (t < (2.5/2.75)) {
			return c*(7.5625*(t-=(2.25/2.75))*t + .9375) + b;
		} else {
			return c*(7.5625*(t-=(2.625/2.75))*t + .984375) + b;
		}
	},
	easeInOutBounce: function (x, t, b, c, d) {
		if (t < d/2) return jQuery.easing.easeInBounce (x, t*2, 0, c, d) * .5 + b;
		return jQuery.easing.easeOutBounce (x, t*2-d, 0, c, d) * .5 + c*.5 + b;
	}
});


$(document).ready(function() {


	$("#logo").css("left", $("#logo").position().left).circulate({
		sizeAdjustment: 160,
		speed: 2000,
		width: -820,
		height: 50,
		loop: true,
		zIndexValues: [1, 50, 50, 1]
		}); 


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
		var telefon1Val = $("#telefon1").val();
		
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
		var number2Valid= /^[0-9]+$/gi;
		if(telefonVal == '' && telefon1Val=='') {
			$("#telefon").addClass("errorRed");
			$("#telefon2").addClass("errorRed");
			$("#errorTn").show();
			hasError = true;
        }else if (!numberValid.test(telefonVal)&&!number2Valid.test(telefon1Val)){
				$("#telefon").addClass("errorRed");
				$("#telefon2").addClass("errorRed");
				$("#errorTnG").show();
				hasError = true;
		}  else{
				$("#errorTnG").hide();
				$("#errorTn").hide();
				$("#telefon").removeClass("errorRed");
				$("#telefon2").removeClass("errorRed");
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

