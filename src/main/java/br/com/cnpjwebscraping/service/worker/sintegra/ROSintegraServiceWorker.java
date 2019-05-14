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
public class ROSintegraServiceWorker implements SintegraServiceWorker {

    private static final String URL = "https://portalcontribuinte.sefin.ro.gov.br/Publico/parametropublica.jsp";

    @Override
    public SintegraServiceWorkerResponse consultar(String cnpj) throws Exception {

        Connection.Response response;

        TrustUtil.setTrustAllCerts();

        Map<String, String> cookies = Jsoup.connect(URL).execute().cookies();

        response = Jsoup.connect("https://portalcontribuinte.sefin.ro.gov.br/Publico/consultapublica.jsp")
                .method(Connection.Method.POST)
                .cookies(cookies)
                .timeout(60000)
                .data("TipoDevedor", "3")
                .data("NuDevedor", cnpj)
                .data("B1", "Consultar Cadastro")
                .execute();

        Document document = response.parse();

        String inscricaoEstadual = FormatadorString.removePontuacao(document.select("#AutoNumber1 td[bgcolor=#FFFFFF]").get(1).text());

        if (!StringUtils.isNumeric(inscricaoEstadual)) {
            throw new Exception("Inscricao not found");
        }

        inscricaoEstadual = String.valueOf(Long.parseLong(inscricaoEstadual));

        return new SintegraServiceWorkerResponse(document, ResultScraping.LOCALIZADO, inscricaoEstadual);
    }

    @Override
    public String resolveCaptcha() {
        return null;
    }

    public static void main(String[] args) throws Exception {

        SintegraServiceWorkerResponse response = new ROSintegraServiceWorker().consultar("56228356009198");

        System.out.println(response.getDocument().html());

        System.out.println(response.getInscricaoEstadual());

    }

}
