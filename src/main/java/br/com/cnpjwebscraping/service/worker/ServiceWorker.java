package br.com.cnpjwebscraping.service.worker;

import br.com.cnpjwebscraping.domain.Consulta;

public interface ServiceWorker {
	
	public ServiceWorkerResponse consultar(Consulta consulta) throws Exception;

}