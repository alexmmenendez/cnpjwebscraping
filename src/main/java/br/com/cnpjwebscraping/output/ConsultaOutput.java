package br.com.cnpjwebscraping.output;

import br.com.cnpjwebscraping.domain.Consulta;
import br.com.cnpjwebscraping.hardcoded.ConsultaStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ConsultaOutput {

    private UUID ticket;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "Brazil/East")
    private Date dataAbertura;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "Brazil/East")
    private Date dataFinalizacao;

    private ConsultaStatus status;

    private EmpresaOutput empresa;

    public ConsultaOutput() {

    }

    public ConsultaOutput(Consulta consulta) {
        this.setTicket(consulta.getTicket());
        this.setDataAbertura(consulta.getDataAbertura());
        this.setDataFinalizacao(consulta.getDataFinalizacao());
        this.setStatus(consulta.getStatus());
        this.setEmpresa(new EmpresaOutput(consulta.getHistorico().getEmpresa()));
    }

    public UUID getTicket() {
        return ticket;
    }

    public void setTicket(UUID ticket) {
        this.ticket = ticket;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Date getDataFinalizacao() {
        return dataFinalizacao;
    }

    public void setDataFinalizacao(Date dataFinalizacao) {
        this.dataFinalizacao = dataFinalizacao;
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
}
