package br.com.cnpjwebscraping.service.worker.sintegra;

import br.com.cnpjwebscraping.hardcoded.ResultScraping;
import br.com.cnpjwebscraping.service.worker.sintegra.response.SintegraServiceWorkerResponse;
import br.com.cnpjwebscraping.solver.deathbycaptchav2.DeathbycaptchaV2;
import br.com.cnpjwebscraping.solver.request.TextCaptchaRequest;
import br.com.cnpjwebscraping.util.FormatadorString;
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
public class RJSintegraServiceWorker implements SintegraServiceWorker {

    private static final String URL = "http://www4.fazenda.rj.gov.br/sincad-web/index.jsf";

    private Connection.Response response;

    private Map<String, String> cookies;

    @Autowired
    private DeathbycaptchaV2 deathbycaptchaV2;

    @Override
    public SintegraServiceWorkerResponse consultar(String cnpj) throws Exception {

        TrustUtil.setTrustAllCerts();

        int cont = 0;

        Document document = null;

        do {

            cont++;

            if (cont > 5) {
                return new SintegraServiceWorkerResponse(document, ResultScraping.NAO_POSSUI, null);
            }

            try {

                response = Jsoup.connect(URL).execute();

                cookies = response.cookies();

                document = response.parse();

                String viewState = document.select("[name=javax.faces.ViewState]").val();

                response = Jsoup.connect(URL)
                        .method(Connection.Method.POST)
                        .timeout(60000)
                        .cookies(cookies)
                        .data("formulario", "formulario")
                        .data("javax.faces.ViewState", viewState)
                        .data("formulario:txtCNPJ", "")
                        .data("formulario:txtCPF","")
                        .data("formulario:txtInsEst","")
                        .data("formulario:cap", "")
                        .data("javax.faces.source", "formulario:txtCNPJ")
                        .data("javax.faces.partial.event", "change")
                        .data("javax.faces.partial.execute", "formulario:txtCNPJ @component")
                        .data("javax.faces.partial.render", "@component")
                        .data("javax.faces.behavior.event", "valueChange")
                        .data("org.richfaces.ajax.component", "formulario:txtCNPJ")
                        .data("AJAX:EVENTS_COUNT", "1")
                        .data("javax.faces.partial.ajax", "true")
                        .execute();

                response = Jsoup.connect(URL)
                        .method(Connection.Method.POST)
                        .timeout(60000)
                        .cookies(cookies)
                        .data("formulario", "formulario")
                        .data("javax.faces.ViewState", viewState)
                        .data("formulario:txtCNPJ", cnpj)
                        .data("formulario:txtCPF","")
                        .data("formulario:txtInsEst","")
                        .data("formulario:cap", resolveCaptcha())
                        .data("formulario:btnPesquisar", "formulario:btnPesquisar")
                        .data("skipValidation", "false")
                        .execute();

                document = response.parse();

                String inscricaoEstadual = FormatadorString.removePontuacao(document.select("#main table[id=formulario:tabelaIE] td.rf-dt-c").get(3).text());

                return new SintegraServiceWorkerResponse(document, ResultScraping.LOCALIZADO, inscricaoEstadual);
            } catch (Exception e) {
                cont++;
            }

        } while (true);

    }

    @Override
    public String resolveCaptcha() throws Exception {

        response = Jsoup.connect("http://www4.fazenda.rj.gov.br/sincad-web/captchaServlet")
                .cookies(cookies)
                .ignoreContentType(true)
                .execute();

        File file = new File("/tmp/captcha_init" + UUID.randomUUID());

        FileUtils.writeByteArrayToFile(file, response.bodyAsBytes());

        String result = deathbycaptchaV2.solveImageCaptcha(new TextCaptchaRequest(file)).getValue();

        FileUtils.deleteQuietly(file);

        return result;
    }

    public void setDeathbycaptchaV2(DeathbycaptchaV2 deathbycaptchaV2) {
        this.deathbycaptchaV2 = deathbycaptchaV2;
    }

}