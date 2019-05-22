package br.com.cnpjwebscraping.worker.sintegra;

import br.com.cnpjwebscraping.service.worker.sintegra.PBSintegraServiceWorker;
import br.com.cnpjwebscraping.service.worker.sintegra.response.SintegraServiceWorkerResponse;
import br.com.cnpjwebscraping.solver.anticaptcha.Anticaptcha;
import br.com.cnpjwebscraping.worker.ServiceWorkerIntegracaoTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class PBServiceWorkerServiceWorkerTest extends ServiceWorkerIntegracaoTest {

    @InjectMocks
    private PBSintegraServiceWorker worker;

    @Test
    public void consultarSintegra() throws Exception {

        String cnpjMock = "07526557001343";

        worker.setAnticaptcha(new Anticaptcha());

        SintegraServiceWorkerResponse response = worker.consultar(cnpjMock);

        assertSintegraServiceWorkerResponse(response, "162187157");
    }
}