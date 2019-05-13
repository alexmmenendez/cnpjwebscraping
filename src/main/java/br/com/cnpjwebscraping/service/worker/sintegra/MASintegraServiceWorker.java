package br.com.cnpjwebscraping.service.worker.sintegra;

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
public class MASintegraServiceWorker implements SintegraServiceWorker {

    private static final String URL = "http://aplicacoes.ma.gov.br/sintegra/jsp/consultaSintegra/consultaSintegraFiltro.jsf";

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

        response = Jsoup.connect(URL)
                .method(Connection.Method.POST)
                .cookies(cookies)
                .data("AJAXREQUEST", "_viewRoot")
                .data("form1", "form1")
                .data("form1:tipoEmissao", "2")
                .data("form1:j_id14:", "form1:j_id14")
                .data("AJAX:EVENTS_COUNT", "1")
                .data("javax.faces.ViewState", document.select("[name=javax.faces.ViewState]").val())
                .execute();

        document = response.parse();

        response = Jsoup.connect(URL)
                .method(Connection.Method.POST)
                .cookies(cookies)
                .data("form1", "form1")
                .data("form1:tipoEmissao", "2")
                .data("form1:cpfCnpj", cnpj)
                .data("g-recaptcha-response", resolveCaptcha())
                .data("form1:j_id27", "Consulta")
                .data("form1:panel_loadingOpenedState", "")
                .data("javax.faces.ViewState", document.select("[name=javax.faces.ViewState]").val())
                .execute();

        document = response.parse();

        String inscricaoEstadual = FormatadorString.removePontuacao(document.select("form div table tbody tr td table tbody tr td div span.textoPequeno").first().text());

        if (!StringUtils.isNumeric(inscricaoEstadual)) {
            return new SintegraServiceWorkerResponse(document, "Não possui.");
        }

        return new SintegraServiceWorkerResponse(document, inscricaoEstadual);
    }

    @Override
    public String resolveCaptcha() throws AnticaptchaException {
        return anticaptcha.solveRecaptcha(new ReCaptchaRequest(googleKey, URL)).getValue();
    }

    public static void main(String[] args) throws Exception {
        SintegraServiceWorkerResponse response = new MASintegraServiceWorker().consultar("07526557005683");

        System.out.println(response.getDocument().html());

        System.out.println(response.getInscricaoEstadual());
    }

}