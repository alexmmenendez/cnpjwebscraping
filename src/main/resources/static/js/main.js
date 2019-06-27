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
    $("#inscricaoEstadual").addClass('d-none');
    $("#situacaoCadastral").addClass('d-none');
    $("#dataAbertura").addClass('d-none');
    $("#parentesco").addClass('d-none');
    $("#logradouro").addClass('d-none');
    $("#numeroLogradouro").addClass('d-none');
    $("#complementoLogradouro").addClass('d-none');
    $("#cep").addClass('d-none');
    $("#bairro").addClass('d-none');

    var cpfcnpj = $("#cpfcnpj").val();

    $("#btn-consultar").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/v1/cnpjwebscraping/consulta/cpf-cnpj",
        data: JSON.stringify({"cpfcnpj":cpfcnpj}),
        dataType: 'json',
        cache: false,
        timeout: 60000,
        success: function (data) {

            var nomeRazaoSocial = data.consulta.empresa.razaoSocial;
            var inscricaoEstadual = data.consulta.empresa.inscricaoEstadual;
            var dataAbertura = data.consulta.empresa.dataAbertura;
            var situacaoCadastral = data.consulta.empresa.situacaoCadastral;
            var parentesco = data.consulta.empresa.parentesco;
            var logradouro = data.consulta.empresa.logradouro;
            var numeroLogradouro = data.consulta.empresa.numeroLogradouro;
            var complementoLogradouro = data.consulta.empresa.complementoLogradouro;
            var cep = data.consulta.empresa.cep;
            var bairro = data.consulta.empresa.bairro;

            $("#nomeRazaoSocial span").text(nomeRazaoSocial);
            $("#nomeRazaoSocial").toggleClass('d-none');

            if (inscricaoEstadual) {
                $("#inscricaoEstadual span").text("Inscrição Estadual: " + inscricaoEstadual);
                $("#inscricaoEstadual").toggleClass('d-none');
            }

            if (situacaoCadastral) {
                $("#situacaoCadastral span").text("Situação Cadastral: " + situacaoCadastral);
                $("#situacaoCadastral").toggleClass('d-none');
            }

            if (dataAbertura) {
                $("#dataAbertura span").text("Data de Abertura: " + dataAbertura);
                $("#dataAbertura").toggleClass('d-none');
            }

            if (parentesco) {
                $("#parentesco span").text("Tipo Empresa: " + parentesco);
                $("#parentesco").toggleClass('d-none');
            }

            if (logradouro) {
                $("#logradouro span").text("Logradouro: " + logradouro);
                $("#logradouro").toggleClass('d-none');
            }

            if (numeroLogradouro) {
                $("#numeroLogradouro span").text("Número: " + numeroLogradouro);
                $("#numeroLogradouro").toggleClass('d-none');
            }

            if (complementoLogradouro) {
                $("#complementoLogradouro span").text("Complemento: " + complementoLogradouro);
                $("#complementoLogradouro").toggleClass('d-none');
            }

            if (cep) {
                $("#cep span").text("CEP: " + cep);
                $("#cep").toggleClass('d-none');
            }

            if (bairro) {
                $("#bairro span").text("Bairro: " + bairro);
                $("#bairro").toggleClass('d-none');
            }

            $("#btn-consultar").prop("disabled", false);

            console.log("SUCCESS : ", nomeRazaoSocial);
            console.log("INSCRICAO ESTADUAL: ", inscricaoEstadual)

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