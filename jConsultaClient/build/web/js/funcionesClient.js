function sendMessage(e) {
    textMsg = document.getElementById("txtMsg").value;
    tecla = e.keyCode;
    alert("Tecla: "+tecla);
    if (textMsg != null && tecla == 13) {
        $.ajax({
            type: 'POST', // metodo de envio
            url: 'http://localhost:8084/jConsultaClient/sendMessage.do',
            // data tiene todos los parametros que le enviare por post
            data: {
                text: textMsg
            }
        }).done(function (respuesta) {
            $("#txtMsg").html();
        });
    }
}

function changeButton(){
    
}



