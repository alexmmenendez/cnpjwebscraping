package br.com.cnpjwebscraping.service.worker.sintegra;

import br.com.cnpjwebscraping.hardcoded.ResultScraping;
import br.com.cnpjwebscraping.service.worker.sintegra.response.SintegraServiceWorkerResponse;
import br.com.cnpjwebscraping.solver.deathbycaptchav2.DeathbycaptchaV2;
import br.com.cnpjwebscraping.solver.request.TextCaptchaRequest;
import br.com.cnpjwebscraping.util.TrustUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;
import java.util.UUID;

@Service
public class MSSintegraServiceWorker implements SintegraServiceWorker {

    private static final String URL = "https://servicos.efazenda.ms.gov.br/consultacci";

    private Connection.Response response;

    private Map<String, String> cookies;

    @Autowired
    private DeathbycaptchaV2 deathbycaptchaV2;

    @Override
    public SintegraServiceWorkerResponse consultar(String cnpj) throws Exception {

        TrustUtil.setTrustAllCerts();

        cookies = Jsoup.connect(URL).execute().cookies();

        response = Jsoup.connect("https://servicos.efazenda.ms.gov.br/consultacci/Home/Buscar")
                .method(Connection.Method.POST)
                .cookies(cookies)
                .data("captcha", resolveCaptcha())
                .data("ie", "")
                .data("cnpj", cnpj)
                .execute();

        Document document = response.parse();

        String inscricaoEstadual = document.select("#inscricaoEstadual").val();

        if (!StringUtils.isNumeric(inscricaoEstadual)) {
            throw new Exception("Inscricao not found");
        }

        return new SintegraServiceWorkerResponse(document, ResultScraping.LOCALIZADO, inscricaoEstadual);
    }

    @Override
    public String resolveCaptcha() throws Exception {

        response = Jsoup.connect("https://servicos.efazenda.ms.gov.br/consultacci/Home/GetCaptcha")
                .cookies(cookies)
                .ignoreContentType(true)
                .execute();

        cookies.putAll(response.cookies());

        File file = new File("/tmp/captcha_init" + UUID.randomUUID());

        FileUtils.writeByteArrayToFile(file, response.bodyAsBytes());

        String result = new DeathbycaptchaV2().solveImageCaptcha(new TextCaptchaRequest(file)).getValue();

        FileUtils.deleteQuietly(file);

        return result;
    }

    public static void main(String[] args) throws Exception {

        SintegraServiceWorkerResponse response = new MSSintegraServiceWorker().consultar("07.526.557/0014-24");

        System.out.println(response.getDocument());

        System.out.println(response.getInscricaoEstadual());
    }

    public void setDeathbycaptchaV2(DeathbycaptchaV2 deathbycaptchaV2) {
        this.deathbycaptchaV2 = deathbycaptchaV2;
    }
}
