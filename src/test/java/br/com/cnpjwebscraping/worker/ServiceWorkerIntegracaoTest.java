package br.com.cnpjwebscraping.worker;

import br.com.cnpjwebscraping.hardcoded.ResultScraping;
import br.com.cnpjwebscraping.service.worker.sintegra.response.SintegraServiceWorkerResponse;
import org.junit.Assert;

public class ServiceWorkerIntegracaoTest {

    protected void assertSintegraServiceWorkerResponse(SintegraServiceWorkerResponse response, String inscricaoMock) {

        Assert.assertNotNull(response.getDocument());

        Assert.assertEquals(ResultScraping.LOCALIZADO, response.getResult());

        Assert.assertEquals(inscricaoMock, response.getInscricaoEstadual());

    }

}
