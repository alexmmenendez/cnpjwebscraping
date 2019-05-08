package br.com.cnpjwebscraping.service.worker.receitafederal;

import br.com.cnpjwebscraping.domain.Consulta;

public interface ServiceWorker {
	
	public ServiceWorkerResponse consultar(Consulta consulta) throws Exception;

	public String resolveCaptcha() throws Exception;

}