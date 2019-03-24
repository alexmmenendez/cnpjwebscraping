package br.com.cnpjwebscraping.hardcoded;

import java.util.Arrays;

public enum ConsultaStatus {
	NOVA("Nova"),
	PROCESSANDO("Processando"),
	CONCLUIDO("Concluido"),
	REPROCESSAR("Reprocessar"),
	FALHA("Falha");

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
