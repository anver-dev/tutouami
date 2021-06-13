$(document).ready(function(){
    $.get(
        "http://localhost:8080/v1/materias",
        
        function (data) {
          // Vemos respuesta de invocacion
          console.log(`datos de respuesta: ${data}`);
        }
      )
        // Si la peticion responde correctamente cargamos la playlist
        .done(function (data, textStatus, xhr) {
            console.log(`datos de respuesta: ${data}`);
        })
        // Si la peticion arroja un error 404 ejecutamos la funcion de error
        .fail(function (data, textStatus, xhr) {
          console.log("error", data.status);
          console.log("STATUS: " + xhr);
        });
    
})