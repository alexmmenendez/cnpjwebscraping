package br.com.cnpjwebscraping.worker.sintegra;

import br.com.cnpjwebscraping.service.worker.sintegra.SESintegraServiceWorker;
import br.com.cnpjwebscraping.service.worker.sintegra.response.SintegraServiceWorkerResponse;
import br.com.cnpjwebscraping.solver.deathbycaptchav2.DeathbycaptchaV2;
import br.com.cnpjwebscraping.worker.ServiceWorkerIntegracaoTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SEServiceWorkerServiceWorkerTest extends ServiceWorkerIntegracaoTest {

    @InjectMocks
    private SESintegraServiceWorker worker;

    @Test
    public void consultarSintegra() throws Exception {

        String cnpjMock = "56228356009279";

        worker.setDeathbycaptchaV2(new DeathbycaptchaV2());

        SintegraServiceWorkerResponse response = worker.consultar(cnpjMock);

        assertSintegraServiceWorkerResponse(response, "271344334");

    }
}