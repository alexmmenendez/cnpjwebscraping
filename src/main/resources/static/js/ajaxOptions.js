 $(function(){

        $("[data-toggle=popover]").popover({
            html: true,
            content: function() {
                  return $('#popover-content').html();
                }
        });

        $("[data-toggle=popover]").on('click', function() {

            var id = $(this).data("solicitacao_id");

            $("#id_solicitacao").val(id);

        });

        /*
        $('.solicitacao_body').on('show.bs.collapse', function (event) {

            $(this).collapse('hide');

            $(this).stopPropagation;

            if ($(e.target).hasClass("certidao-situacao") || ($(e.target).prop('tagName') == $("span").prop('tagName') && $(e.target).parent().hasClass("certidao-situacao"))) {

            }

        })
        */

        $("[data-toggle=collapse]").on('click', function(e) {

            if ($(e.target).hasClass("certidao-situacao") || ($(e.target).prop('tagName') == $("span").prop('tagName') && $(e.target).parent().hasClass("certidao-situacao"))) {

            }

        });


        $(document).on('click', '.alter-button', function() {

            var certidaoSituacao = $(".popover #select-situacao").val();
            var id = $("#id_solicitacao").val();
            var url = "/app/dashboard/solicitacao/" + id;
            var _csrf = $("#token").val();

            var jsonData = new Object();
            jsonData.id = id;
            jsonData.certidaoSituacao = certidaoSituacao;
            jsonData._csrf = _csrf;

            var csrfHeader = $("meta[name='_csrf_header']").attr("content");
            var csrfToken  = $("meta[name='_csrf']").attr("content");

            $.ajax({
                contentType: "application/json; charset=utf-8",
                data : JSON.stringify(jsonData),
                url : url,
                method : "PUT",

                beforeSend : function(xhr) {

                    xhr.setRequestHeader(csrfHeader, csrfToken);

                },

                error : function (jqXHR, textStatus, errorThrow) {
                    alert("Erro ao alterar o status" )

                    console.log(textStatus);
                    console.log(errorThrow);
                    console.log(jqXHR);

                }

            }).done(function(json) {

                var situacao = jsonData.certidaoSituacao;

                $("[data-solicitacao_id] div").text(jsonData.certidaoSituacao);

                var className = getClassByCertidaoSituacao(jsonData.certidaoSituacao);

                $("[data-solicitacao_id] div").removeClass();

                $("[data-solicitacao_id] div").addClass(className);

                alert(json.mensagem);

                $("[data-toggle=popover]").popover('hide');

            });

        });

        $(".alter-button").on('click', function() {
            /*
            var certidaoSituacao = $("#select-situacao").val();
            var id = $("#id_solicitacao").val();
            var url = "/app/dashboard/solicitacao/" + id;
            var _csrf = $("#token").val();

            var jsonData = new Object();
            jsonData.id = id;
            jsonData.certidaoSituacao = certidaoSituacao;
            jsonData._csrf = _csrf;

            var csrfHeader = $("meta[name='_csrf_header']").attr("content");
            var csrfToken  = $("meta[name='_csrf']").attr("content");

            alert(csrfHeader, csrfToken);

            $.ajax({
                contentType: "application/json; charset=utf-8",
                data : JSON.stringify(jsonData),
                url : url,
                method : "PUT",

                beforeSend : function(xhr) {

                    xhr.setRequestHeader(csrfHeader, csrfToken);

                },

                error : function (jqXHR, textStatus, errorThrow) {
                    alert("Erro ao alterar o status" )

                    console.log(textStatus);
                    console.log(errorThrow);
                    console.log(jqXHR);

                }

            }).done(function(json) {

                alert(json.mensagem);

            });
            */
        });


        var orgaoFederais = ["TST", "INCRA", "IBAMA", "SICAF", "MF","MPF"];

        var orgaoEstadual = ["TRF", "TF", "TRT", "SEFAZ", "TJ"];

        setValueQuantidade();

        getEstados();

        getTiposErros();

        getClientes();

        ativaDesativaSelectsPorAbrangencia();

        ativaDesativaSelectsPorOrgao();

        getPedidoSaas();

        setPageItemColor();

        $(".check-reprocessar").on('click', function(e) {

            var checkbox = $(this).find("input[type=checkbox]");

            if (checkbox.prop('checked')) {

                checkbox.prop('checked', false);

            } else {

                checkbox.prop('checked', true);

            }

            e.stopPropagation();

        });

        $(".certidao-situacao").on('click', function() {

            $("#id_solicitacao").val($(this).parent().data("solicitacao_id"));

        });

        $("#tipoOrgao").on("change", function() {

            ativaDesativaSelectsPorOrgao();

        });


        $("#reprocessar_button").on('click', function() {

            $("#reprocessar_solicitacoes").submit();

        });

        $("#abrangencia").on('change', function() {

            ativaDesativaSelectsPorAbrangencia();

        });

        $("#uuid").keypress(function() {

            var valor = $(this).val();

            $("input").val("");
            $("select").val("");

            $(this).val(valor);

        });

        $("#uf").on('change', function() {

            var pode = true;

            var tipoOrgao = $("#tipoOrgao").val();

            for(i in orgaoFederais) {

                if (tipoOrgao == orgaoFederais[i]){
                    pode = false;
                }

            }

            for (i in orgaoEstadual) {

                if(tipoOrgao == orgaoEstadual[i]) {
                    pode = false;
                }

            }

            if ($(this).val() == "") {
                desativarSelectCidade();
            }

            if($("#abrangencia").val() == "MUNICIPAL" || $("#tipoOrgao").val() == "SEMFAZ" || ($("#abrangencia").val() == "" && pode)) {
                getCidadeByEstado($(this).val());
                return;
            }

        });

        $(".info-emissor").click(function() {

            var url = "/app/dashboard/getEmissorInfo";

            var emissorId = $(this).attr("id");

            var button = $(this);

            $.ajax({

                data : { emissorId : emissorId },
                method : "GET",
                url : url,

                beforeSend : function() {

                    button.children("span").text("Carregando");

                },

                complete : function() {

                    button.children("span").text("Informações do emissor");

                }

            }).done(function(data){

                var emissor = JSON.parse(data);

                $(".modal-emissor .emissor-nome").text(emissor.nome + "ServiceWorker");
                $(".modal-emissor .worker-status").text(emissor.status);

                var linkForm = emissor.linkForm;
                var linkDominio = emissor.linkDominio;

                if (linkForm.length >  60) {
                    linkForm = linkForm.substring(0, 60);
                    linkForm = linkForm + "...";
                }

                if (linkDominio.length > 60) {
                    linkDominio = linkDominio.substring(0, 60);
                    linkDominio = linkDominio + "...";
                }

                $(".modal-emissor .link-form").text(linkForm);
                $(".modal-emissor .link-form").attr("href", emissor.linkForm);

                $(".modal-emissor .link-dominio").text(linkDominio);
                $(".modal-emissor .link-dominio").attr("href", emissor.linkDominio);

                $(".modal-emissor .ultima-atualizacao").text("Última atualização em " + emissor.ultimaAtualizacao);

            });

        });

        $("#filtro_btn").click(function(){

            var dataInicio = $("#data_inicio").val();
            var dataFim = $("#data_fim").val();
            var tipoOrgao = $("#tipoOrgao").val();
            var resultado = $("#resultado").val();
            var status = $("#status").val();
            var abrangencia = $("#abrangencia").val();
            var uf = $("#uf").val();
            var cidade = $("#cidade").val();
            var uuid = $("#uuid").val();

            var url = "/app/dashboard/filtro";

            $.ajax({

                data : {
                    dataInicio : dataInicio,
                    dataFim : dataFim,
                    tipoOrgao : tipoOrgao,
                    resultado : resultado,
                    status : status,
                    abrangencia : abrangencia,
                    uf : uf,
                    cidade : cidade,
                    uuid : uuid
                },
                url : url,
                method : "GET",

            }).done(function(html) {
''
                $(".solicitacoesContainer").append(html);

            });

        });

        $(".button-falha").click(function() {

            var idSolicitacao = $(this).data("solicitacaoid");
            var endereco = $(this).data("endereco");

            var url = `/app/dashboard/${idSolicitacao}/falha`;

            $.ajax({

                method : "GET",
                data : {

                    idSolicitacao : idSolicitacao

                },
                url : url

            }).done(function(msg) {

                var x = document.getElementById("snackbar");

                x.className = "show";
                x.innerHTML = msg;

                setTimeout(function(){
                    x.className = x.className.replace("show", "");
                    x.text = "";
                }, 3000);

                $("#" + endereco).parent().remove();

            }).fail(function(xhr, status, error) {

                alert("Erro ao enviar solicitação" + xhr.responseText + " " + status + " " + error + " DD");

            });

        });

        $(".certidao-situacao").click(function() {



        });

        $(".snack").click(function(){

            var url =  "/app/dashboard/" + $(this).attr("id");

            var button = $(this);

            $.ajax({
                method : "GET",
                url : url
            }).done(function(msg){

                var x = document.getElementById("snackbar");

                var id  = button.data("target");

                $("#" + id).parent().remove();

                x.className = "show";
                x.innerHTML = msg;
                setTimeout(function(){
                    x.className = x.className.replace("show", "");
                    x.text = "";
                }, 3000);

            }).fail(function(xhr, status, error){
            });

        });

        $(".uuidTooltip").click(function(){

            var $temp = $("<input/>");
            $("body").append($temp);
            $temp.val($(this).attr("title")).select();
            document.execCommand("copy");
            $temp.remove();

        });

        $(".btn-limpar").click(function() {

           limparCampos();

        });

        $(".modal-toggler").click(function() {

            var id = $(this).attr('id');

            var button = $(this);

            $("#" + id + " span").text("Carregando");

            getErrosBySolicitacaoId(id, button);

        });

        function ativaDesativaSelectsPorOrgao() {

           var tipoOrgao =  $("#tipoOrgao").val();

           if (tipoOrgao != "") {

                $("#abrangencia").prop('disabled', true);

                for(i in orgaoFederais) {

                    if (tipoOrgao == orgaoFederais[i]) {
                        desativarSelectUf();
                    }

                }

                for (i in orgaoEstadual) {

                    if(tipoOrgao == orgaoEstadual[i]) {
                        ativarSelectUf();
                        desativarSelectCidade();
                    }

                }

                if (tipoOrgao == "SEMFAZ" && $("#cidade").val() == "") {

                    ativarSelectUf();
                    if( $("#uf").val() != "") {
                        getCidadeByEstado($("#uf").val());
                    }

                }

            } else {

                $("#abrangencia").prop('disabled', false);

                if($("#abrangencia").val() != "NACIONAL") {

                    $("#uf").prop('disabled', false);

                    if( $("#uf").val() != "" && $("#abrangencia").val() != "ESTADUAL" && $("#cidade").val() == "") {
                        getCidadeByEstado($("#uf").val());
                    }

                }

            }

        }

        function ativaDesativaSelectsPorAbrangencia() {

            if ($("#abrangencia").val() == "NACIONAL") {

                desativarSelectUf();

            } else if($("#abrangencia").val() == "ESTADUAL") {

                ativarSelectUf();

            } else {

               ativarSelectUf();

               if ($("#uf").val() != "" && $("#cidade").val() == "") {

                    getCidadeByEstado($("#uf").val());

               }

            }

        }

        function getClassByCertidaoSituacao(certidaoSituacao) {

            var className = "";

            if (certidaoSituacao == 'POSITIVA') {

                className = "certidao-situacao positiva";

            } else {

                className = "certidao-situacao negativa";

            }

            return className;

        }

        function getEstados() {

             var url =  "/app/dashboard/getOptionsUfCidade";

            $.ajax({

                method : "GET",
                url : url,
                async : false,

                beforeSend: function(){

                    desativarSelectUf();
                    $("#uf option:first").text("Carregando...");

                },

                complete: function(){

                    ativarSelectUf();
                    $("#uf option:first").text("Selecione");

                }

            }).done(function(data){

               $.each(JSON.parse(data), function(i, uf){

                    $("#uf").append(`<option value="${uf.id}">${uf.uf}</option>`);

               });

                verificaOptionsPorRequisicaoEstado();

            });

        }

        function getCidadeByEstado(ufId){

            var url =  "/app/dashboard/getOptionsUfCidade";

            if(ufId == "") {
                return;
            }

            $.ajax({

                data : { ufId : ufId },
                url : url,
                method : "GET",
                async : false,


                beforeSend: function() {

                    desativarSelectCidade();
                    $("#cidade option:first").text("Carregando...")


                },

                complete: function() {

                    $("#cidade option:first").text("Selecione");
                    ativarSelectCidade();
                }

            }).done(function(data){

                var first = $("#cidade option:first");

                $("#cidade > option").remove();

                $("#cidade").append(first);

                $("#cidade").val(first.val());

                $.each(JSON.parse(data), function(i, cidade){

                    $("#cidade").append(`<option value="${cidade.id}">${cidade.nome}</option>`);

                });

                verificaOptionsPorRequisicaoCidade();

            });

        }

        function getErrosBySolicitacaoId(solicitacaoId, button) {

            var url = "/app/dashboard/getErros";

            $.ajax({

                url : url,
                method : "GET",
                data : { solicitacaoId : solicitacaoId },

                beforeSend : function() {

                    button.children("span").text("Carregando");

                }

            }).done(function(data) {

                var erros = JSON.parse(data);

                $("#erroAccordion").children().remove();

                for(i in erros) {

                    var download = ``;

                    if (erros[i].arquivoErro != null) {

                        download = `<div class="col-4 justify-content-end">
                                        <a class="btn btn-danger" href="/app/dashboard/arquivo/${erros[i].arquivoErro}" download="${erros[i].arquivoErro}">
                                            <i class="icon" data-icon="1"></i>
                                            Baixar Arquivo
                                        </a>
                                    </div>`;

                     }

                    var card = `<div class="card erros${i}">
                                    <div class="card-header" id="heading${i}">
                                        <div class="d-flex justify-content-between">
                                            <div class="col-4">
                                                <h2 class="mb-0">
                                                    <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#collapse${i}" aria-expanded="false" aria-controls="collapse${i}">
                                                        ${erros[i].tipoErro}
                                                    </button>
                                                </h2>
                                            </div>
                                            <div class="col-4 center">
                                                <span>${erros[i].dataErro}</span>
                                            </div>
                                            <div class="col-4 justify-content-end">
                                            ${download}
                                        </div>
                                    </div>
                                    <div id="collapse${i}" class="collapse" aria-labelledby="heading${i}" data-parent="#erroAccordion">
                                        <div class="card-body" aria-labelledby="heading${i}">
                                            <p class="mensagem"></p>
                                        </div>
                                    </div>
                                </div>`;

                    $("#erroAccordion").append(card);

                    $("#erroAccordion .erros" + i + " .mensagem").text(erros[i].msgErro);

                    button.children("span").text("Ver erros");


                }

            });


        }

        function getPedidoSaas() {

            var url =  "/app/dashboard/getOptionClienteSAAS";

            $.ajax({

                url : url,
                method : "GET",
                async : false,

                complete: function() {

                    $("#clienteSaas option:first").text("Selecione");
                    $("#clienteSaas").prop('disabled', false);

                },

                 beforeSend: function() {
                    $("#clienteSaas").prop('disabled', true);
                    $("#clienteSaas option:first").text("Carregando...")


                },

            }).done(function(data) {

                var first = $("#clienteSaas option:first");

                $("#clienteSaas > option").remove();

                $("#clienteSaas").append(first);

                $("#clienteSaas").val(first.val());

                $.each(JSON.parse(data), function(i, clienteSaas){

                    $("#clienteSaas").append(`<option value="${clienteSaas}">${clienteSaas}</option>`);

                });

                verificarOptionsPorRequisicaoClienteSAAS();

            });

        }

        function getClientes() {

            var url =  "/app/dashboard/getOptionsClientes";

            $.ajax({

                url : url,
                method : "GET",
                async : false,

                complete: function() {

                    $("#cliente option:first").text("Selecione o cliente");
                    $("#cliente").prop('disabled', false);

                }

            }).done(function(data){

                var first = $("#cliente option:first");

                $("#cliente > option").remove();

                $("#cliente").append(first);

                $("#cliente").val(first.val());

                $.each(JSON.parse(data), function(i, cliente){

                    $("#cliente").append(`<option value="${cliente.id}">${cliente.nome}</option>`);

                });

                 verificarOptionsPorRequisicaoCliente();

            });

        }

        function limparCampos() {

            $("#id").val("");
            $("#data_inicio").val("");
            $("#data_fim").val("");
            $("#resultado").val("");
            $("#status").val("");
            $("#abrangencia").val("");
            $("#uf").val("");
            $("#cidade").val("");
            $("#tipoOrgao").val("");
            $("#horaInicio").val("");
            $("#horaFim").val("");
            $("#cliente").val("");
            $("#clienteSaas").val("");
            $("#tipoErro").val("");
            $("#certidao-situacao").val("");

            ativarSelectUf();
            $("#abrangencia").prop('disabled', false);

        }


        function desativarSelectUf() {
            $("#uf").prop('disabled', true);
            $("#uf").val($("#uf option:first").val());
            desativarSelectCidade();
        }

        function desativarSelectCidade() {
             $("#cidade").prop('disabled', true);
             $("#cidade").val($("#cidade option:first").val());
        }

        function ativarSelectUf() {
            $("#uf").prop('disabled', false);
            desativarSelectCidade();
        }

        function ativarSelectCidade() {
            $("#cidade").prop('disabled', false);
        }

        function verificaOptionsPorRequisicaoEstado(){

            if($("#id_uf").val() != "") {

                $("#uf").val($("#id_uf").val());

                //getCidadeByEstado($("#id_uf").val());

                $("#id_uf").val("");

            }

        }

        function verticaOptionPorTipoErro(){

            if($("#textTipoErro").val() != "") {

                $("#tipoErro").val($("#textTipoErro").val());

                $("#textTipoErro").val("");

            }

        }

        function verificaOptionsPorRequisicaoCidade() {

            if($("#id_cidade").val() != "") {

                $("#cidade").val($("#id_cidade").val());

                $("#id_cidade").val("");
            }

        }

        function verificarOptionsPorRequisicaoCliente() {

            if($("#id_cliente").val() != "") {

                $("#cliente").val($("#id_cliente").val());

                $("#id_cliente").val("");

            }

        }

        function verificarOptionsPorRequisicaoClienteSAAS() {

            if($("#clienteSaasNome").val() != "") {

                $("#clienteSaas").val($("#clienteSaasNome").val());

                $("#clienteSaasNome").val("");

            }

        }

        $("#form-certidao").submit(function(event) {

            event.preventDefault();
            /*
            var certidaoSituacao = $(this).find("#select-situacao").val();
            var id = $(this).find("#id_solicitacao").val();
            var url = "/app/dashboard/solicitacao/" + id;
            var _csrf = $("#token").val();

            var jsonData = new Object();
            jsonData.id = id;
            jsonData.certidaoSituacao = certidaoSituacao;
            jsonData._csrf = _csrf;

            var csrfHeader = $("meta[name='_csrf_header']").attr("content");
            var csrfToken  = $("meta[name='_csrf']").attr("content");

            alert(csrfHeader, csrfToken);

            $.ajax({
                contentType: "application/json; charset=utf-8",
                data : JSON.stringify(jsonData),
                url : url,
                method : "PUT",

                beforeSend : function(xhr) {

                    xhr.setRequestHeader(csrfHeader, csrfToken);

                },

                error : function (jqXHR, textStatus, errorThrow) {
                    alert("Erro ao alterar o status" )

                    console.log(textStatus);
                    console.log(errorThrow);
                    console.log(jqXHR);

                }

            }).done(function(json) {

                alert(json.mensagem);

            });
            */

        });

        $(".detalhes-solicitacao").click(function() {

            var solicitacaoId = $(this).children("input").val();

            getParametersValues(solicitacaoId);

        });

        $('.select-qtd-pagina').on('change', function() {

            var value = $(this).val();

            $("#hidden-qtd-pagina").val(value);

            $("#form-filtro").submit();

        });

        $('.page-link').on('click', function() {

            var href = window.location.href;

            if ($("#index").val != "") {
                href = href.replace("&index=" + $("#index").val(), "")
                href = href.replace("?index=" + $("#index").val(), "");
            }

            if (href.indexOf('?') > - 1) {
                href += '&index=' + $(this).text();
            } else {
                href += '?index=' + $(this).text();
            }

            $(this).attr('href', href);

         });

        function setPageItemColor () {

            var index = $("#index").val();

            $(".dashlink" + index).parent().addClass("active");

        }

        function setValueQuantidade() {

            var quantidade = $("#hidden-qtd-pagina").val();

            if (quantidade == ""){
                quantidade = "60";
            }

            $(".select-qtd-pagina").val(quantidade);

        }

        function getTiposErros() {

            var url =  "/app/dashboard/getTiposErros";

            $.ajax ({

               method : "GET",
               url : url,
               async : false,

                complete: function() {

                   $("#tipoErro option:first").text("Selecione");
                   $("#tipoErro").prop('disabled', false);

               },

                beforeSend: function() {
                   $("#tipoErro").prop('disabled', true);
                   $("#tipoErro option:first").text("Carregando...")

               }

            }).done(function(data) {

                var first = $("#tipoErro option:first");

                $("#tipoErro > option").remove();

                $("#tipoErro").append(first);

                $("#tipoErro").val(first.val());

                $.each(JSON.parse(data), function(i, dashboardVO){

                    $("#tipoErro").append(`<option value="${dashboardVO.tipoErro}">${dashboardVO.tipoErro}</option>`);

                });

                verticaOptionPorTipoErro();

            });

        }

        camelize = function camelize(str) {
          return str.replace(/\W+(.)/g, function(match, chr)
           {
                return chr.toUpperCase();
            });
        }


});

