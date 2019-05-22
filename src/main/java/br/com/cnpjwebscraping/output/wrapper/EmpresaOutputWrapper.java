package br.com.cnpjwebscraping.output.wrapper;

import br.com.cnpjwebscraping.domain.Empresa;
import br.com.cnpjwebscraping.output.EmpresaOutput;

public class EmpresaOutputWrapper extends Wrapper {

    private EmpresaOutput empresa;

    public EmpresaOutputWrapper() {

    }

    public EmpresaOutputWrapper(Empresa empresa) {
        this.setEmpresa(new EmpresaOutput(empresa));
    }

    public EmpresaOutput getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaOutput empresa) {
        this.empresa = empresa;
    }
}
