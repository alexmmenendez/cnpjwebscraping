package br.com.cnpjwebscraping.input;

import org.hibernate.validator.constraints.NotBlank;

public class EmpresaInput {
	
	@NotBlank
	private String cnpj;

	private String urlCidade;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getUrlCidade() {
		return urlCidade;
	}

	public void setUrlCidade(String urlCidade) {
		this.urlCidade = urlCidade;
	}
}
