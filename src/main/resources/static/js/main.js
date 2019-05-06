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


                console.log("SUCCESS : ", nomeRazaoSocial);
                $("#btn-consultar").prop("disabled", false);

            },
            error: function (e) {

                console.log("ERROR : ", e);
                $("#btn-consultar").prop("disabled", false);

            }
    });

}
