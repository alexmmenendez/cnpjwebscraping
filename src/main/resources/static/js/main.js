$(document).ready(function () {

    $("#form").submit(function (event) {
       //stop submit the form, we will post it manually.
       event.preventDefault();

       fire_ajax_submit();
    });

    $( "#img-copy" ).click(function() {
        copyToClipboard($("#nomeRazaoSocial span"));
    });

});

function mascaraMutuario(o,f){
    v_obj=o
    v_fun=f
    setTimeout('execmascara()',1)
}

function execmascara(){
    v_obj.value=v_fun(v_obj.value)
}

function cpfCnpj(v){

    //Remove tudo o que não é dígito
    v=v.replace(/\D/g,"")

    if (v.length < 14) { //CPF

        //Coloca um ponto entre o terceiro e o quarto dígitos
        v=v.replace(/(\d{3})(\d)/,"$1.$2")

        //Coloca um ponto entre o terceiro e o quarto dígitos
        //de novo (para o segundo bloco de números)
        v=v.replace(/(\d{3})(\d)/,"$1.$2")

        //Coloca um hífen entre o terceiro e o quarto dígitos
        v=v.replace(/(\d{3})(\d{1,2})$/,"$1-$2")

    } else { //CNPJ

        //Coloca ponto entre o segundo e o terceiro dígitos
        v=v.replace(/^(\d{2})(\d)/,"$1.$2")

        //Coloca ponto entre o quinto e o sexto dígitos
        v=v.replace(/^(\d{2})\.(\d{3})(\d)/,"$1.$2.$3")

        //Coloca uma barra entre o oitavo e o nono dígitos
        v=v.replace(/\.(\d{3})(\d)/,".$1/$2")

        //Coloca um hífen depois do bloco de quatro dígitos
        v=v.replace(/(\d{4})(\d)/,"$1-$2")

    }

    return v
}

function copyToClipboard(element) {
    var $temp = $("<input>");
    $("body").append($temp);
    $temp.val($(element).text()).select();
    document.execCommand("copy");
    $temp.remove();
}

function fire_ajax_submit() {

    $("#nomeRazaoSocial").addClass('d-none');

    var cpfcnpj = $("#cpfcnpj").val();

    $("#btn-consultar").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/v1/cnpjwebscraping/consulta/cpf-cnpj",
        data: JSON.stringify(cpfcnpj),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            var nomeRazaoSocial = data.nome;

            $("#nomeRazaoSocial span").text(nomeRazaoSocial);

            $("#nomeRazaoSocial").toggleClass('d-none');

            $("#btn-consultar").prop("disabled", false);

            console.log("SUCCESS : ", nomeRazaoSocial);
            console.log("INSCRICAO ESTADUAL: ", data.inscricaoEstadual)

        },
        error: function (e) {

            console.log("ERROR : ", e);
            $("#btn-consultar").prop("disabled", false);

        }
    });

}

/*function mascaraMutuario(o,f){
   v_obj=o
   v_fun=f
   setTimeout('execmascara()',1)
}

function execmascara(){
   v_obj.value=v_fun(v_obj.value)
}

function cpfCnpj(v){

   //Remove tudo o que não é dígito
   v=v.replace(/\D/g,"")

   if (v.length <= 14) { //CPF

       //Coloca um ponto entre o terceiro e o quarto dígitos
       v=v.replace(/(\d{3})(\d)/,"$1.$2")

       //Coloca um ponto entre o terceiro e o quarto dígitos
       //de novo (para o segundo bloco de números)
       v=v.replace(/(\d{3})(\d)/,"$1.$2")

       //Coloca um hífen entre o terceiro e o quarto dígitos
       v=v.replace(/(\d{3})(\d{1,2})$/,"$1-$2")

   } else { //CNPJ

       //Coloca ponto entre o segundo e o terceiro dígitos
       v=v.replace(/^(\d{2})(\d)/,"$1.$2")

       //Coloca ponto entre o quinto e o sexto dígitos
       v=v.replace(/^(\d{2})\.(\d{3})(\d)/,"$1.$2.$3")

       //Coloca uma barra entre o oitavo e o nono dígitos
       v=v.replace(/\.(\d{3})(\d)/,".$1/$2")

       //Coloca um hífen depois do bloco de quatro dígitos
       v=v.replace(/(\d{4})(\d)/,"$1-$2")

   }

   return v*/