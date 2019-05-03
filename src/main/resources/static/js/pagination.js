$(function () {

    var range = 10;
    var index = $("#index").val();
    var qtdPaginas = $("#qtdPaginas").val();

    var indexScale = getIndexScale(index, range);
    var scale = getTotalScale(qtdPaginas, range);

    var begin = (indexScale - 1) * range + 1 ;
    var end = indexScale * range;

    if (indexScale >= scale) {
        end = scale * range - ((scale * range) - qtdPaginas);
    }

    if (index % range == 0) {
        begin = begin - 2;
    }

    for (var i = begin; i <= end; i++) {

        $(".pagination").append(`<li class="page-item"><a class="page-link dashlink${i}" href="#">${i}</a></li>`);

    }

    if (qtdPaginas > 10) {
        $(".pagination").append(`<p class="qtd-paginas">${qtdPaginas} páginas</p>`);
    }

    function getIndexScale(index, range) {

        index = parseInt(index);

        index++;


        var indexScale = Math.ceil(index / range);

        if (index < range) {
            indexScale = 1;
        }

        return indexScale;

    }

    function getTotalScale(qtdPaginas, range) {

        var scale = Math.ceil(qtdPaginas / range);

        return scale;

    }

    $(".dashlink" + index + " a ").addClass("active");

});
