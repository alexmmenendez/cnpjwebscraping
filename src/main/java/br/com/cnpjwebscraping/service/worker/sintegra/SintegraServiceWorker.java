package br.com.cnpjwebscraping.service.worker.sintegra;

import br.com.cnpjwebscraping.service.worker.sintegra.response.SintegraServiceWorkerResponse;

public interface SintegraServiceWorker {

    public SintegraServiceWorkerResponse consultar(String cnpj) throws Exception;

    public String resolveCaptcha() throws Exception;

}
