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

    public EmpresaOutput() {

    }

    public EmpresaOutput(Empresa empresa) {
        this.setCnpj(empresa.getCnpj());
        this.setRazaoSocial(empresa.getRazaoSocial());
        this.setParentesco(empresa.getParentesco());
        this.setDataAbertura(empresa.getDataAbertura());
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
}
