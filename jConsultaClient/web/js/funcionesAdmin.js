/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function updatePanelAdmin(){
    window.setTimeout(function(){
        location.href="http://localhost:8084/jConsultaClient/admin.jsp";
    }, 3000);
}

function updateAll(){
    window.setTimeout(function(){
        $.ajax({
            type: 'POST', // metodo de envio
            url: 'http://localhost:8084/jConsultaClient/updatePanel.do',
            data: {
                typeList: "users"
            }
        }).done(function (respuesta) {
            $("#users").html();
        });
        
        $.ajax({
            type: 'POST', // metodo de envio
            url: 'http://localhost:8084/jConsultaClient/updatePanel.do',
            data: {
                typeList: "ready"
            }
        }).done(function (respuesta) {
            $("#ready").html();
        });
        
        $.ajax({
            type: 'POST', // metodo de envio
            url: 'http://localhost:8084/jConsultaClient/updatePanel.do',
            data: {
                typeList: "messages"
            }
        }).done(function (respuesta) {
            $("#messages").html();
        });
    }, 1000);
}
