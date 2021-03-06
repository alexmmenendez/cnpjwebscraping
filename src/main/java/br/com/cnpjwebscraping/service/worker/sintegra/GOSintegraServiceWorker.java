package br.com.cnpjwebscraping.service.worker.sintegra;

import br.com.cnpjwebscraping.hardcoded.ResultScraping;
import br.com.cnpjwebscraping.service.worker.sintegra.response.SintegraServiceWorkerResponse;
import br.com.cnpjwebscraping.solver.anticaptcha.Anticaptcha;
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
public class GOSintegraServiceWorker implements SintegraServiceWorker {

    private static final String URL = "http://appasp.sefaz.go.gov.br/Sintegra/Consulta/default.asp";

    @Override
    public SintegraServiceWorkerResponse consultar(String cnpj) throws Exception {

        Connection.Response response;

        Map<String, String> cookies;

        Document document;

        TrustUtil.setTrustAllCerts();

        response = Jsoup.connect(URL).execute();

        cookies = response.cookies();

        response = Jsoup.connect("http://appasp.sefaz.go.gov.br/Sintegra/Consulta/consultar.asp")
                .method(Connection.Method.POST)
                .cookies(cookies)
                .data("rTipoDoc", "2")
                .data("tDoc", cnpj)
                .data("tCNPJ", cnpj)
                .data("zion.SystemAction", "consultarSintegra()")
                .data("zion.FormElementPosted", "zionFormID_1")
                .data("zionPostMethod", "iframe")
                .data("zionRichValidator", "true")
                .execute();

        document = response.parse();

        String inscricaoEstadual = FormatadorString.removePontuacao(document.select("table tbody tr td div center table tbody tr td span.label_text").get(1).text());

        if (!StringUtils.isNumeric(inscricaoEstadual)) {
            throw new Exception("Inscricao not found");
        }

        return new SintegraServiceWorkerResponse(document, ResultScraping.LOCALIZADO, inscricaoEstadual);
    }

    @Override
    public String resolveCaptcha() {
        return null;
    }

}