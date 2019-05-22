package br.com.cnpjwebscraping.input;

import org.hibernate.validator.constraints.NotBlank;

public class EmpresaInput {
	
	@NotBlank
	private String cnpj;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
}
