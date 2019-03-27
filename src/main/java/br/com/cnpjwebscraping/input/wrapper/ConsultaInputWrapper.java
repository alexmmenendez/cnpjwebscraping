package br.com.cnpjwebscraping.input.wrapper;


import br.com.cnpjwebscraping.input.EmpresaInput;

import javax.validation.Valid;

public class ConsultaInputWrapper {

	@Valid
	private EmpresaInput empresaInput;

	public EmpresaInput getEmpresaInput() {
		return empresaInput;
	}

	public void setEmpresaInput(EmpresaInput empresaInput) {
		this.empresaInput = empresaInput;
	}

	@Override
	public String toString() {
		return "ConsultaInputWrapper{" +
				"empresaInput=" + empresaInput +
				'}';
	}
}
