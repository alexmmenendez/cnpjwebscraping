package br.com.cnpjwebscraping.worker.trt;

import br.com.cnpjwebscraping.service.worker.trt.TRT24ServiceWorker;
import br.com.cnpjwebscraping.worker.ServiceWorkerIntegracaoTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class TRT24ServiceWorkerTest extends ServiceWorkerIntegracaoTest {

    @InjectMocks
    private TRT24ServiceWorker worker;

    @Test
    public void consultarSintegra() throws Exception {

        String cpfcnpj = "46158810886";

        String nome = worker.consultaNomeCompletoRazaoSocialPeloCPFCNPJ(cpfcnpj);

        Assert.assertEquals(nome, "ALEX MACHADO MENENDEZ");

    }
}