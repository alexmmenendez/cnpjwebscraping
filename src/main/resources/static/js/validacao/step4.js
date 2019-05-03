function validaCampo(campo) {

    var id = campo.id
    var required = (document.getElementById(id + 'Required').value === 'true')
    var isSelect = $("#" + id).is('select')
    var html = "<div class='invalid-feedback'>Campo obrigatório.</div>"
    var campoJquery = $("#" + id)

    if (required) {

        if (campo.value === '' || (isSelect && campo.value == '0')) {

            if (campo.classList.contains('is-invalid')) {
                return
            } else {
                campo.classList.add('is-invalid')
                if (!campo.classList.contains('validated')) {
                    campoJquery.parent().append(html)
                }
            }

            campo.classList.add('validated')
        } else {
            campo.classList.remove('is-invalid')
        }

    }

}