package br.com.cnpjwebscraping.worker.sintegra;

import br.com.cnpjwebscraping.service.worker.sintegra.ESSintegraServiceWorker;
import br.com.cnpjwebscraping.service.worker.sintegra.GOSintegraServiceWorker;
import br.com.cnpjwebscraping.service.worker.sintegra.response.SintegraServiceWorkerResponse;
import br.com.cnpjwebscraping.solver.anticaptcha.Anticaptcha;
import br.com.cnpjwebscraping.worker.ServiceWorkerIntegracaoTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class GOServiceWorkerServiceWorkerTest extends ServiceWorkerIntegracaoTest {

    @InjectMocks
    private GOSintegraServiceWorker worker;

    @Test
    public void consultarSintegra() throws Exception {

        String cnpjMock = "09456788000110";

        SintegraServiceWorkerResponse response = worker.consultar(cnpjMock);

        assertSintegraServiceWorkerResponse(response, "104299584");

    }
}
