package br.com.cnpjwebscraping.worker.sintegra;

import br.com.cnpjwebscraping.service.worker.sintegra.MGSintegraServiceWorker;
import br.com.cnpjwebscraping.service.worker.sintegra.response.SintegraServiceWorkerResponse;
import br.com.cnpjwebscraping.solver.anticaptcha.Anticaptcha;
import br.com.cnpjwebscraping.worker.ServiceWorkerIntegracaoTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class MGServiceWorkerServiceWorkerTest extends ServiceWorkerIntegracaoTest {

    @InjectMocks
    private MGSintegraServiceWorker worker;

    @Test
    public void consultarSintegra() throws Exception {

        String cnpjMock = "01131570000850";

        worker.setAnticaptcha(new Anticaptcha());

        SintegraServiceWorkerResponse response = worker.consultar(cnpjMock);

        assertSintegraServiceWorkerResponse(response, "0027564920459");

    }
}