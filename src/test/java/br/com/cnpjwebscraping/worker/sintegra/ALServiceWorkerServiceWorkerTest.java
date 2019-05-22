package br.com.cnpjwebscraping.worker.sintegra;

import br.com.cnpjwebscraping.service.worker.sintegra.ALSintegraServiceWorker;
import br.com.cnpjwebscraping.service.worker.sintegra.response.SintegraServiceWorkerResponse;
import br.com.cnpjwebscraping.worker.ServiceWorkerIntegracaoTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class ALServiceWorkerServiceWorkerTest extends ServiceWorkerIntegracaoTest {

    @InjectMocks
    private ALSintegraServiceWorker worker;

    @Test
    public void consultarSintegra() throws Exception {

        String cnpjMock = "56228356011338";

        SintegraServiceWorkerResponse response = worker.consultar(cnpjMock);

        assertSintegraServiceWorkerResponse(response, "24261233");

    }
}
