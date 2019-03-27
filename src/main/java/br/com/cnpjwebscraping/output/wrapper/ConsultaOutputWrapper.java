package br.com.cnpjwebscraping.output.wrapper;

import br.com.cnpjwebscraping.domain.Consulta;
import br.com.cnpjwebscraping.output.ConsultaOutput;

public class ConsultaOutputWrapper extends Wrapper {

    private ConsultaOutput consulta;

    public ConsultaOutputWrapper() {

    }

    public ConsultaOutputWrapper(Consulta consulta) {
        this.setConsulta(new ConsultaOutput(consulta));
    }

    public ConsultaOutput getConsulta() {
        return consulta;
    }

    public void setConsulta(ConsultaOutput consulta) {
        this.consulta = consulta;
    }
}
