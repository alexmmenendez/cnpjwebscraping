package br.com.cnpjwebscraping.hardcoded;

import org.apache.commons.lang3.StringUtils;

public enum CNPJDados {
	DATA_ABERTURA("DATA DE ABERTURA"),
	NOME_EMPRESARIAL("NOME EMPRESARIAL"),
	LOGRADOURO("LOGRADOURO"),
	NUMERO_LOGRADOURO("NÚMERO"),
	COMPLEMENTO_LOGRADOURO("COMPLEMENTO"),
	CEP("CEP"),
	BAIRRO_LOGRADOURO("BAIRRO/DISTRITO"),
	MUNICIPIO("MUNICÍPIO"),
	UF("UF"),
	SITUACAO_CADASTRAL("SITUAÇÃO CADASTRAL");

	private String descricao;

	CNPJDados(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public static boolean hasEnum(String name) {

		for (CNPJDados cnpjDados : CNPJDados.values()) {
			if (StringUtils.equalsIgnoreCase(cnpjDados.getDescricao(), name)) {
				return true;
			}
		}

		return false;
	}

	public static CNPJDados getEnum(String name) {

		for (CNPJDados cnpjDados : CNPJDados.values()) {
			if (StringUtils.equalsIgnoreCase(cnpjDados.getDescricao(), name)) {
				return cnpjDados;
			}
		}

		return null;
	}
}
