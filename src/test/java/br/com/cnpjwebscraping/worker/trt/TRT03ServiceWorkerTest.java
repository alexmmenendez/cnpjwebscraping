package br.com.cnpjwebscraping.worker.trt;

import br.com.cnpjwebscraping.service.worker.trt.TRT03ServiceWorker;
import br.com.cnpjwebscraping.worker.ServiceWorkerIntegracaoTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class TRT03ServiceWorkerTest extends ServiceWorkerIntegracaoTest {

    @InjectMocks
    private TRT03ServiceWorker worker;

    @Test
    public void consultarSintegra() throws Exception {

        String cpfcnpj = "01.131.570/0002-64";

        String nome = worker.consultaNomeCompletoRazaoSocialPeloCPFCNPJ(cpfcnpj);

        Assert.assertEquals(nome, "CERVEJARIA ZX S.A.");

    }

}
