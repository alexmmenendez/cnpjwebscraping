package br.com.cnpjwebscraping.hardcoded;

public enum ResultScraping {

    NAO_POSSUI("Não possui"),
    LOCALIZADO("Localizado");

    private String descricao;

    ResultScraping(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
