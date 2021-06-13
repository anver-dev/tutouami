// Obtenemos el archivo JSON 
const requestURL = "http://localhost:8080/v1/materias";
const request = new XMLHttpRequest();
request.open('GET', requestURL);
request.responseType = 'json';
request.send();

//Almanenamos el URL del JSON de donde se requiere recuerar la variable
const requestURLAlumno =  "http://localhost:8080/v1/alumnos";
//Creamos una solicitud de tipo XMLHtppRequest(); haciendo una instancia nueva
const requestALumno = new XMLHttpRequest();
//Abrimos la solicutd utilizando el metodo open() de XMLHtppRequest();
requestALumno.open('GET',requestURLAlumno);
//Con esta linea sabemos que el servidor devuelve un JSON
//el cual lo convertira en segundo plano a tipo JS y se envia la solicitud con send()
requestALumno.responseType = 'json';
requestALumno.send();
                    /**
* Funcion para recuperar el Alumno
*/
requestALumno.onload = function(){
    const nombreAlumno = requestALumno.response;
    $("#nombreAlumno").append($('<p class="form-control-static" >'+ nombreAlumno[0].nombre +'</p>'));
    console.log(nombreAlumno[0].idAlumno);
};

/**
*	Esta funcion ubica en el select las materias.
*/				
request.onload = function() {
    const materia = request.response;
    for (var x = 0; x < materia.length; x++) {
        $("#materia").append(
                        $('<option value="'+ materia[x].idMateria +'">'
                                + materia[x].nombre
                                + '</option>'));
    }
};

/*
* Funcion para obtener el valor del radio 
*/
function seleccionaDia(){
    var seleccionado;
    $("#grupoRadios input[name='radios']").change(function(event) {
        console.log("Seleccionado: "+$(this).val());
          alert($(this).val())
          seleccionado = $(this).val();
        });
    return seleccionado;
}

/*
* Funcion para enviar el formulario. 
*/
$(function(){
    $("#formulario-registro").on("submit",function(e){
        e.preventDefault();
        
        var costo = $("#costo").val();
        var detalles =$("#detalles").val();
        //var dia =$("#dia").val();
        var dia = $("#dias").val();
        var horaInicio =$("#horaInicio").val();
        var horaTermino =$("#horaTermino").val();
        var materia =$("#materia").val();
        var tipo =$("#tipo").val();
        var ubicacion =$("#ubicacion").val();
    
        $.ajax({
            url:"http://localhost:8080/v1/alumnos/1/asesoria",
            type: "post",
            dataType: "json",
            data: JSON.stringify({costo:costo,detalles:detalles,dia:dia,horaInicio:horaInicio,horaTermino:horaTermino,materia:materia,tipo:tipo,ubicacion:ubicacion}),
            contentType: 'application/json; charset=utf-8',
            success: function (data) {			                    	
                console.log("SUCCESS : ", data);
                alert("La asesoria fue registrada.");
               
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    });
});