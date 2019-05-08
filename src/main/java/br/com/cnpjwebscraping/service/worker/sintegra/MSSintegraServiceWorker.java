package br.com.cnpjwebscraping.service.worker.sintegra;

import br.com.cnpjwebscraping.service.worker.sintegra.response.SintegraServiceWorkerResponse;
import br.com.cnpjwebscraping.solver.deathbycaptchav2.DeathbycaptchaV2;
import br.com.cnpjwebscraping.solver.request.TextCaptchaRequest;
import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;
import java.util.UUID;

@Service
public class MSSintegraServiceWorker implements SintegraServiceWorker {

    private static final String URL = "https://servicos.efazenda.ms.gov.br/consultacci";

    @Override
    public SintegraServiceWorkerResponse consultar(String cnpj) throws Exception {

        return null;
    }

    @Override
    public String resolveCaptcha() throws Exception {
        return null;
    }

    //07.526.557/0058-45

    public static void main(String[] args) throws Exception {

        Connection.Response response = Jsoup.connect(URL).execute();

        Map<String, String> cookies = response.cookies();

        response = Jsoup.connect("https://servicos.efazenda.ms.gov.br/consultacci/Home/GetCaptcha")
                .cookies(cookies)
                .ignoreContentType(true)
                .execute();

        File file = new File("/tmp/captcha_init" + UUID.randomUUID());

        FileUtils.writeByteArrayToFile(file, response.bodyAsBytes());

        String captcha = new DeathbycaptchaV2().solveImageCaptcha(new TextCaptchaRequest(file)).getValue().toLowerCase();

        response = Jsoup.connect("https://servicos.efazenda.ms.gov.br/consultacci/Home/Buscar")
                .header("X-Requested-With", "XMLHttpRequest")
                .method(Connection.Method.POST)
                .data("cnpj", "07.526.557/0014-24")
                .data("captcha", captcha)
                .ignoreHttpErrors(true)
                .cookies(cookies)
                .execute();

        System.out.println(response.parse());
    }

}
