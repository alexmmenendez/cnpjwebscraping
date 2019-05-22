package br.com.cnpjwebscraping.output;

import br.com.cnpjwebscraping.domain.Empresa;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EmpresaOutput {

    private String razaoSocial;

    private String cnpj;

    private String parentesco;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "Brazil/East")
    private Date dataAbertura;

    private String situacaoCadastral;

    private String logradouro;

    private String numeroLogradouro;

    private String complementoLogradouro;

    private String cep;

    private String bairro;

    private String inscricaoEstadual;

    public EmpresaOutput() {

    }

    public EmpresaOutput(Empresa empresa) {
        this.setCnpj(empresa.getCnpj());
        this.setRazaoSocial(empresa.getRazaoSocial());
        this.setParentesco(empresa.getParentesco());
        this.setDataAbertura(empresa.getDataAbertura());
        this.setSituacaoCadastral(empresa.getSituacaoCadastral());
        this.setLogradouro(empresa.getLogradouro());
        this.setNumeroLogradouro(empresa.getNumeroLogradouro());
        this.setComplementoLogradouro(empresa.getComplementoLogradouro());
        this.setCep(empresa.getCep());
        this.setBairro(empresa.getBairro());
        this.setInscricaoEstadual(empresa.getInscricaoEstadual());
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getSituacaoCadastral() {
        return situacaoCadastral;
    }

    public void setSituacaoCadastral(String situacaoCadastral) {
        this.situacaoCadastral = situacaoCadastral;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumeroLogradouro() {
        return numeroLogradouro;
    }

    public void setNumeroLogradouro(String numeroLogradouro) {
        this.numeroLogradouro = numeroLogradouro;
    }

    public String getComplementoLogradouro() {
        return complementoLogradouro;
    }

    public void setComplementoLogradouro(String complementoLogradouro) {
        this.complementoLogradouro = complementoLogradouro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }
}
