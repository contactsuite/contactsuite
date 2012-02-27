/* Author: E-Topal

*/


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