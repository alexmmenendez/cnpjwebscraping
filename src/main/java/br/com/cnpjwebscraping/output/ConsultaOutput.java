package br.com.cnpjwebscraping.output;

import br.com.cnpjwebscraping.domain.Empresa;
import br.com.cnpjwebscraping.hardcoded.ConsultaStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ConsultaOutput {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "Brazil/East")
    private Date dataConsultaCriacao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "Brazil/East")
    private Date ultimaAtualizacaoReceitaFederal;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "Brazil/East")
    private Date ultimaAtualizacaoSintegra;

    private ConsultaStatus status;

    private EmpresaOutput empresa;

    public ConsultaOutput() {

    }

    public ConsultaOutput(Empresa empresa) {
        this.setDataConsultaCriacao(empresa.getConsultaDataCriacao());
        this.setStatus(empresa.getStatus());
        this.setEmpresa(new EmpresaOutput(empresa));
        this.setUltimaAtualizacaoReceitaFederal(empresa.getDataUltimaAtualizacaoReceitaFederal());
        this.setUltimaAtualizacaoSintegra(empresa.getDataUltimaAtualizacaoSintegra());
    }

    public Date getDataConsultaCriacao() {
        return dataConsultaCriacao;
    }

    public void setDataConsultaCriacao(Date dataConsultaCriacao) {
        this.dataConsultaCriacao = dataConsultaCriacao;
    }

    public ConsultaStatus getStatus() {
        return status;
    }

    public void setStatus(ConsultaStatus status) {
        this.status = status;
    }

    public EmpresaOutput getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaOutput empresa) {
        this.empresa = empresa;
    }


    public Date getUltimaAtualizacaoReceitaFederal() {
        return ultimaAtualizacaoReceitaFederal;
    }

    public void setUltimaAtualizacaoReceitaFederal(Date ultimaAtualizacaoReceitaFederal) {
        this.ultimaAtualizacaoReceitaFederal = ultimaAtualizacaoReceitaFederal;
    }

    public Date getUltimaAtualizacaoSintegra() {
        return ultimaAtualizacaoSintegra;
    }

    public void setUltimaAtualizacaoSintegra(Date ultimaAtualizacaoSintegra) {
        this.ultimaAtualizacaoSintegra = ultimaAtualizacaoSintegra;
    }
}

