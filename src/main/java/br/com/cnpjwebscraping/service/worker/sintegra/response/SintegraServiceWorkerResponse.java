package br.com.cnpjwebscraping.service.worker.sintegra.response;

import org.jsoup.nodes.Document;

public class SintegraServiceWorkerResponse {

	private Document document;

	private String inscricaoEstadual;

	public SintegraServiceWorkerResponse() {
	}

	public SintegraServiceWorkerResponse(Document document, String inscricaoEstadual) {
		this.setInscricaoEstadual(inscricaoEstadual);
		this.document = document;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}
}
