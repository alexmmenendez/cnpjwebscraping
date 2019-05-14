package br.com.cnpjwebscraping.service.worker.sintegra.response;

import br.com.cnpjwebscraping.hardcoded.ResultScraping;
import org.jsoup.nodes.Document;

public class SintegraServiceWorkerResponse {

	private Document document;

	private String inscricaoEstadual;

	private ResultScraping result;

	public SintegraServiceWorkerResponse() {
	}

	public SintegraServiceWorkerResponse(Document document, ResultScraping result, String inscricaoEstadual) {
		this.setInscricaoEstadual(inscricaoEstadual);
		this.document = document;
		this.result = result;
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

	public ResultScraping getResult() {
		return result;
	}

	public void setResult(ResultScraping result) {
		this.result = result;
	}
}
