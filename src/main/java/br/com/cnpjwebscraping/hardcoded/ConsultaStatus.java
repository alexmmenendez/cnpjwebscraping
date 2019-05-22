package br.com.cnpjwebscraping.hardcoded;

import java.util.Arrays;

public enum ConsultaStatus {
	NOVA("Nova"),
	CONSULTANDO_RECEITA_FEDERAL("Consultando receita federal..."),
	CONSULTANDO_SINTEGRA("Consultando sintegra..."),
	CONCLUIDA_RECEITA_FEDERAL("Consulta da Receita Federal concluida"),
	CONCLUIDA_SINTEGRA("Consulta da Sintegra concluida"),
	FALHA_CONSULTA_RECEITA_FEDERAL("Falha"),
	FALHA_CONSULTA_SINTEGRA("Falha"),
	CONCLUIDO("Concluido");

	private String descricao;

	ConsultaStatus(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public static ConsultaStatus[] getSortedValue() {

		ConsultaStatus[] valores = ConsultaStatus.values();

		Arrays.sort(valores, (s1, s2)->s1.name().compareTo(s2.name()));

		return valores;

	}

}
