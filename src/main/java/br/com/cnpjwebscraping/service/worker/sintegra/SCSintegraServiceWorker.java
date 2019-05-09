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
            throw new Exception("Não é inscricao municipal");
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

        return new DeathbycaptchaV2().solveImageCaptcha(new TextCaptchaRequest(file)).getValue().toLowerCase();
    }

    public static void main(String[] args) throws Exception {

        Connection.Response response = Jsoup.connect(URL).execute();

        Map<String, String> cookies = response.cookies();

        Document document = response.parse();

        String urlImage = "http://sistemas3.sef.sc.gov.br/sintegra/";

        urlImage = urlImage + document.select("#UpdatePanel1").first().select("img").first().attr("src");

        response = Jsoup.connect(urlImage)
                .cookies(cookies)
                .ignoreContentType(true)
                .execute();

        File file = new File("/tmp/captcha_init" + UUID.randomUUID());

        FileUtils.writeByteArrayToFile(file, response.bodyAsBytes());

        String captcha = new DeathbycaptchaV2().solveImageCaptcha(new TextCaptchaRequest(file)).getValue().toLowerCase();

        response = Jsoup.connect(URL)
                .method(Connection.Method.POST)
                .data("__VIEWSTATE", document.select("[name=__VIEWSTATE]").val())
                .data("__VIEWSTATEGENERATOR", document.select("[name=__VIEWSTATEGENERATOR]").val())
                .data("__EVENTVALIDATION", document.select("[name=__EVENTVALIDATION]").val())
                .data("opt_pessoa", "2")
                .data("txt_CPFCNPJ", "07526557002900")
                .data("txtCodigoCaptcha", captcha)
                .data("btnEnviar", "Pesquisar")
                .cookies(cookies)
                .execute()
                .bufferUp();

        document = response.parse();

        Element table = document.select("form table font font font").first();

        String inscricaoMunicipal = table.html().trim();

        if (!StringUtils.isNumeric(inscricaoMunicipal)) {
            throw new Exception("Não é inscricao municipal");
        }


    }

}
