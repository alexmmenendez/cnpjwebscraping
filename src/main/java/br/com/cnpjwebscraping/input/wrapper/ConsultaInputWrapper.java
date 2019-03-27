package br.com.cnpjwebscraping.input.wrapper;


import br.com.cnpjwebscraping.domain.Consulta;
import br.com.cnpjwebscraping.input.EmpresaInput;

import javax.validation.Valid;

public class ConsultaInputWrapper {

	@Valid
	private EmpresaInput empresa;

	public EmpresaInput getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaInput empresa) {
		this.empresa = empresa;
	}

	@Override
	public String toString() {
		return "ConsultaInputWrapper{" +
				"empresa=" + empresa +
				'}';
	}
}
