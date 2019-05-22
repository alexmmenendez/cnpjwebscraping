package br.com.cnpjwebscraping.service.worker.sintegra;

import br.com.cnpjwebscraping.hardcoded.ResultScraping;
import br.com.cnpjwebscraping.service.worker.sintegra.response.SintegraServiceWorkerResponse;
import br.com.cnpjwebscraping.solver.anticaptcha.Anticaptcha;
import br.com.cnpjwebscraping.solver.anticaptcha.exception.AnticaptchaException;
import br.com.cnpjwebscraping.solver.deathbycaptchav2.DeathbycaptchaV2;
import br.com.cnpjwebscraping.solver.request.ReCaptchaRequest;
import br.com.cnpjwebscraping.solver.request.TextCaptchaRequest;
import br.com.cnpjwebscraping.util.FormatadorString;
import br.com.cnpjwebscraping.util.TrustUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ThreadUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;
import java.util.UUID;

@Service
public class ESSintegraServiceWorker implements SintegraServiceWorker {

    private static final String URL = "http://www.sintegra.es.gov.br/";

    private String googleKey;

    @Autowired
    private Anticaptcha anticaptcha;

    @Override
    public SintegraServiceWorkerResponse consultar(String cnpj) throws Exception {

        Connection.Response response;

        Map<String, String> cookies;

        Document document;

        TrustUtil.setTrustAllCerts();

        response = Jsoup.connect(URL).execute();

        cookies = response.cookies();

        document = response.parse();

        googleKey = document.select(".g-recaptcha").attr("data-sitekey");

        response = Jsoup.connect("http://www.sintegra.es.gov.br/resultado.php")
                .method(Connection.Method.POST)
                .cookies(cookies)
                .data("num_cnpj", cnpj)
                .data("g-recaptcha-response", resolveCaptcha())
                .data("botao", "Consultar")
                .execute();

        document = response.parse();

        String inscricaoEstadual = FormatadorString.removePontuacao(document.select("table tbody tr td .valor").get(1).text());

        if (!StringUtils.isNumeric(inscricaoEstadual)) {
            throw new Exception("Inscricao not found");
        }

        return new SintegraServiceWorkerResponse(document, ResultScraping.LOCALIZADO, inscricaoEstadual);

    }

    @Override
    public String resolveCaptcha() throws AnticaptchaException {
        return anticaptcha.solveRecaptcha(new ReCaptchaRequest(googleKey, URL)).getValue();
    }

    public void setAnticaptcha(Anticaptcha anticaptcha) {
        this.anticaptcha = anticaptcha;
    }
}