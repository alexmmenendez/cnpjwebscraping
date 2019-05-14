package br.com.cnpjwebscraping.service.worker.sintegra;

import br.com.cnpjwebscraping.hardcoded.ResultScraping;
import br.com.cnpjwebscraping.service.worker.sintegra.response.SintegraServiceWorkerResponse;
import br.com.cnpjwebscraping.solver.anticaptcha.Anticaptcha;
import br.com.cnpjwebscraping.solver.anticaptcha.exception.AnticaptchaException;
import br.com.cnpjwebscraping.solver.request.ReCaptchaRequest;
import br.com.cnpjwebscraping.util.FormatadorString;
import br.com.cnpjwebscraping.util.TrustUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PASintegraServiceWorker implements SintegraServiceWorker {

    private static final String URL = "https://app.sefa.pa.gov.br/sintegra/";

    private Document document;

    @Autowired
    private Anticaptcha anticaptcha;

    @Override
    public SintegraServiceWorkerResponse consultar(String cnpj) throws Exception {

        TrustUtil.setTrustAllCerts();

        Connection.Response response;

        response = Jsoup.connect(URL).timeout(60000).execute();

        Map<String, String> cookies = response.cookies();

        document = response.parse();

        response = Jsoup.connect("https://app.sefa.pa.gov.br/sintegra/consulta.do")
                .method(Connection.Method.POST)
                .cookies(cookies)
                .timeout(60000)
                .data("CNPJ", cnpj)
                .data("CODIGO", resolveCaptcha())
                .execute();

        Document document = response.parse();

        String inscricaoEstadual = FormatadorString.removePontuacao(document.select(".td-conteudo").get(2).text());

        if (!StringUtils.isNumeric(inscricaoEstadual)) {
            throw new Exception("Inscricao not found");
        }

        return new SintegraServiceWorkerResponse(document, ResultScraping.LOCALIZADO, inscricaoEstadual);
    }

    @Override
    public String resolveCaptcha() throws AnticaptchaException {
        String googleKey = document.select(".g-recaptcha").attr("data-sitekey");

        return anticaptcha.solveRecaptcha(new ReCaptchaRequest(googleKey, URL)).getValue();
    }

    public void setAnticaptcha(Anticaptcha anticaptcha) {
        this.anticaptcha = anticaptcha;
    }

}
