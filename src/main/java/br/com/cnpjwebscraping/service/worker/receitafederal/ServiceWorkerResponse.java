package br.com.cnpjwebscraping.service.worker.receitafederal;

import org.jsoup.nodes.Document;

public class ServiceWorkerResponse {

	private Document document;

	public ServiceWorkerResponse() {
	}

	public ServiceWorkerResponse(Document document) {
		this.document = document;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
}
