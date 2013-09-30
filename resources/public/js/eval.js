function doEval(e) {
  $.post("/eval", { evalInput: $("#evalInput").val() } ,
                    function(response) {
    $("#display").html(response);
  })
    .done(function() {
      $("#evalInput").val("");
    });
}
$("#todoSubmitButton").click(function(e) {
  e.preventDefault();
  doEval(e);
});
$("#evalInput").keydown(function(e) {
  if (e.keyCode == 13) {
    doEval(e);
    return false;
  }
});
$(document).ready(function () {
  i = 0;
  $.each($(".taskIndex"), function(index, content) {
    $(content).text(index);
    i++;
  })
});