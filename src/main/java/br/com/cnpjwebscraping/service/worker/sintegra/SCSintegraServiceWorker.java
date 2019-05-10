package br.com.cnpjwebscraping.service.worker.sintegra;

import br.com.cnpjwebscraping.service.worker.sintegra.response.SintegraServiceWorkerResponse;
import br.com.cnpjwebscraping.solver.deathbycaptchav2.DeathbycaptchaV2;
import br.com.cnpjwebscraping.solver.request.TextCaptchaRequest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
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
public class SCSintegraServiceWorker implements SintegraServiceWorker {

    private static final String URL = "http://sistemas3.sef.sc.gov.br/sintegra/consulta_empresa_pesquisa.aspx";

    private Connection.Response response;

    private Map<String, String> cookies;

    private Document document;

    @Autowired
    private DeathbycaptchaV2 deathbycaptchaV2;

    @Override
    public SintegraServiceWorkerResponse consultar(String cnpj) throws Exception {

        response = Jsoup.connect(URL).execute();

        cookies = response.cookies();

        document = response.parse();

        String captcha = resolveCaptcha();

        response = Jsoup.connect(URL)
                .method(Connection.Method.POST)
                .data("__VIEWSTATE", document.select("[name=__VIEWSTATE]").val())
                .data("__VIEWSTATEGENERATOR", document.select("[name=__VIEWSTATEGENERATOR]").val())
                .data("__EVENTVALIDATION", document.select("[name=__EVENTVALIDATION]").val())
                .data("opt_pessoa", "2")
                .data("txt_CPFCNPJ", cnpj)
                .data("txtCodigoCaptcha", captcha)
                .data("btnEnviar", "Pesquisar")
                .cookies(cookies)
                .execute()
                .bufferUp();

        document = response.parse();

        if (StringUtils.containsIgnoreCase(document.html(), "Sequência de caracteres incorreta.")) {
            throw new Exception("Captcha failed");
        }

        if (StringUtils.containsIgnoreCase(document.html(), "CNPJ NÃO CADASTRADO NO CAD.ICMS PR")) {
            return new SintegraServiceWorkerResponse(document, "CNPJ NÃO CADASTRADO NO CAD.ICMS PR");
        }

        Element table = document.select("form table font font font").first();

        String inscricaoEstadual = table.html().trim();

        if (!StringUtils.isNumeric(inscricaoEstadual)) {
            return new SintegraServiceWorkerResponse(document, "Não possui.");
        }

        return new SintegraServiceWorkerResponse(document, inscricaoEstadual);
    }

    @Override
    public String resolveCaptcha() throws Exception {
        String urlImage = "http://sistemas3.sef.sc.gov.br/sintegra/";

        urlImage = urlImage + document.select("#UpdatePanel1").first().select("img").first().attr("src");

        response = Jsoup.connect(urlImage)
                .cookies(cookies)
                .ignoreContentType(true)
                .execute();

        File file = new File("/tmp/captcha_init" + UUID.randomUUID());

        FileUtils.writeByteArrayToFile(file, response.bodyAsBytes());

        return deathbycaptchaV2.solveImageCaptcha(new TextCaptchaRequest(file)).getValue().toLowerCase();
    }
}
