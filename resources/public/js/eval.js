$("#todoSubmitButton").click(function(e) {
  e.preventDefault();
  $.post("/eval", { evalInput: $("#evalInput").val() } ,
                    function(response) {
    $("#display").html(response);
  })
    .done(function() {
      $("#evalInput").val("");
    });
});