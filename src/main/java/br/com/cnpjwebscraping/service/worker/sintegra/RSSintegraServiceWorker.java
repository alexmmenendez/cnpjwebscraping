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
public class RSSintegraServiceWorker implements SintegraServiceWorker {

    private static final String URL = "http://www.sefaz.rs.gov.br/consultas/contribuinte";

    private Document document;

    @Autowired
    private Anticaptcha anticaptcha;

    @Override
    public SintegraServiceWorkerResponse consultar(String cnpj) throws Exception {

        TrustUtil.setTrustAllCerts();

        Connection.Response response = Jsoup.connect(URL).timeout(60000).execute();

        Map<String, String> cookies = response.cookies();

        document = response.parse();

        String recaptcha = resolveCaptcha();

        response = Jsoup.connect("http://www.sefaz.rs.gov.br/consultas/contribuinte/Home/Consulta")
                .method(Connection.Method.POST)
                .cookies(cookies)
                .timeout(60000)
                .data("recaptchaValor", recaptcha)
                .data("g-recaptcha-response", recaptcha)
                .data("CodInscrCnpj", cnpj)
                .data("validateButton", "Pesquisar")
                .data("__RequestVerificationToken", document.select("[name=__RequestVerificationToken]").val())
                .execute();

        document = response.parse();

        String inscricaoEstadual = document.select(".col-xs-4").first().text();

        if (!StringUtils.isNumeric(FormatadorString.removePontuacao(document.select(".col-xs-4").first().text()))) {
            throw new Exception("Inscricao not found");
        }

        return new SintegraServiceWorkerResponse(document, ResultScraping.LOCALIZADO, inscricaoEstadual);
    }

    @Override
    public String resolveCaptcha() throws Exception {
        String googleKey = document.select(".g-recaptcha").attr("data-sitekey");

        return anticaptcha.solveRecaptcha(new ReCaptchaRequest(googleKey, URL)).getValue();
    }

    public static void main(String[] args) throws Exception {

        SintegraServiceWorkerResponse response = new RSSintegraServiceWorker().consultar("07526557003710");

        System.out.println(response.getDocument().html());

        System.out.println(response.getInscricaoEstadual());
    }

}
