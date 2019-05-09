package br.com.cnpjwebscraping.output.wrapper;

import br.com.cnpjwebscraping.domain.Empresa;
import br.com.cnpjwebscraping.output.ConsultaOutput;

public class ConsultaOutputWrapper extends Wrapper {

    private ConsultaOutput consulta;

    public ConsultaOutputWrapper() {

    }

    public ConsultaOutputWrapper(Empresa empresa) {
        this.setConsulta(new ConsultaOutput(empresa));
    }

    public ConsultaOutput getConsulta() {
        return consulta;
    }

    public void setConsulta(ConsultaOutput consulta) {
        this.consulta = consulta;
    }
}
