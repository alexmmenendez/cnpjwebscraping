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
public class PBSintegraServiceWorker implements SintegraServiceWorker {

    private static final String URL = "https://www4.receita.pb.gov.br/sintegra/SINf_ConsultaSintegra.jsp";

    private Document document;

    @Autowired
    private Anticaptcha anticaptcha;

    @Override
    public SintegraServiceWorkerResponse consultar(String cnpj) throws Exception {

        Connection.Response response;

        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv2Hello");

        response = Jsoup.connect(URL).execute();

        Map<String, String> cookies = response.cookies();

        document = response.parse();

        response = Jsoup.connect("https://www4.receita.pb.gov.br/sintegra/SINf_ConsultaSintegra")
                .method(Connection.Method.POST)
                .cookies(cookies)
                .timeout(60000)
                .data("nrDocumento", cnpj)
                .data("tipoDoc", "CNPJ")
                .data("tpDocumento", "2")
                .data("g-recaptcha-response", resolveCaptcha())
                .execute();

        Document document = response.parse();

        String inscricaoEstadual = FormatadorString.removePontuacao(document.select("#conteudo b").get(1).text());

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

    public static void main(String[] args) throws Exception {

        SintegraServiceWorkerResponse response = new PBSintegraServiceWorker().consultar("07526557001343");

        System.out.println(response.getDocument().html());

        System.out.println(response.getInscricaoEstadual());
    }

}
