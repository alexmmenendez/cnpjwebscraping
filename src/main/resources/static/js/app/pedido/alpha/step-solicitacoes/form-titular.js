function clearSelectCidadesAndCreateEmptyOption(selectCidades) {
	selectCidades.html('');

	var optionEmpty = $('<option/>', {
		"value" : "",
		"text" : "Cidade",
        "selected" : "true",
        "disabled" : "disabled",
	});

	selectCidades.append(optionEmpty);
}

function clearSelectCidades(selectCidades) {
	selectCidades.html('');
}

function inverteRequired() {
	$('.matricula-group').removeAttr('required');
	$('.endereco-group').attr('required', true);
}

function closeEnderecoDetalhe() {
	$('#form-endereco-detalhe').slideUp();
}

$(document).ready(function() {

	$('.select2').select({ width: '100%' });

	jQuery.validator.addMethod("apenasLetras", function(value, element) {
      return this.optional(element) || /^[A-Z a-z áãâäàéêëèíîïìóõôöòúûüùçñ ÁÃÂÄÀÉÊËÈÍÎÏÌÓÕÔÖÒÚÛÜÙÇÑ \s]*$/.test(value);
    }, "Digite apenas letras");

	$('form').validate({
		errorElement: 'div',
		errorClass: 'invalid-feedback',
		errorElementClass: 'is-invalid',
		highlight: function(element, errorClass, validClass) {
			$(element).addClass(this.settings.errorElementClass).removeClass(errorClass);
		},
		unhighlight: function(element, errorClass, validClass) {
			$(element).removeClass(this.settings.errorElementClass).removeClass(errorClass);
		}
	});


	$('#cnpj').inputmask();
	$('#cpf').inputmask();

	var cliente = $('#clienteUrl').val();
	var fluxo = $('#fluxo').val();

	$('#estados').change(function () {
		var id = $(this).val();
		var url = '/app/estados/' + id + '/cidades/options';
		var selectCidades = $('#cidades');

		if(id != '') {
			$.ajax({
				"url": url,
				"method": "GET",
				"success": function (html) {
					clearSelectCidades(selectCidades);
					selectCidades.append(html);
                    var cidadeSelecionadaAnteriormente = $('#cidadeSelecionadaAnteriormente').val();
                    selectCidades.val(cidadeSelecionadaAnteriormente);
				}
			});
		} else {
            clearSelectCidadesAndCreateEmptyOption(selectCidades);
			selectCidades.attr('readonly', true);
		}

	});

	$('#cep').inputmask({
		"oncleared": function() { closeEnderecoDetalhe();},
		"onincomplete": function() { closeEnderecoDetalhe();}
	});
	
	$('#cep').keyup(function() {
		if($(this).inputmask('unmaskedvalue').length == 8 ){
			var url = '/app/ajax/cep/' + $(this).inputmask('unmaskedvalue') + '/endereco';
			$.ajax({
				"url" : url,
				"success" :  function(endereco) {
					$('#bairroDistrito').val(endereco.bairro);
					$('#endereco').val(endereco.endereco);
					$('#estados').val(endereco.idEstado);
					$('#estados').trigger('change');
					setTimeout(function() {
						$('#cidades').val(endereco.idCidade);
					}, 1000);
					
					$('#form-endereco-detalhe').slideDown();
					
					$('#numero').focus();
				},
				"error" :  function(response, status, xhr) {
					$('#form-endereco-detalhe').slideDown();
				}
			});
		}
	});

	$('#btn-link-abre-endereco').click(function() {
		$('#form-endereco').slideDown();
		$(this).closest('div').slideUp();

		inverteRequired();
	});

	if($('#cep').inputmask('isComplete')) {
		$('#btn-link-abre-endereco').click();
		$('#form-endereco-detalhe').slideDown();
	}

    $('#estados').change();
});