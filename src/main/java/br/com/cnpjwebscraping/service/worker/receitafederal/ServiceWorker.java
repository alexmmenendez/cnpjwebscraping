package br.com.cnpjwebscraping.service.worker.receitafederal;

import br.com.cnpjwebscraping.domain.Empresa;

public interface ServiceWorker {
	
	public ServiceWorkerResponse consultar(Empresa empresa) throws Exception;

	public String resolveCaptcha() throws Exception;

}