package br.com.cnpjwebscraping.service.worker.sintegra;

import br.com.cnpjwebscraping.hardcoded.ResultScraping;
import br.com.cnpjwebscraping.service.worker.sintegra.response.SintegraServiceWorkerResponse;
import br.com.cnpjwebscraping.solver.anticaptcha.Anticaptcha;
import br.com.cnpjwebscraping.solver.deathbycaptchav2.DeathbycaptchaV2;
import br.com.cnpjwebscraping.solver.request.ReCaptchaRequest;
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
public class MTSintegraServiceWorker implements SintegraServiceWorker {

    private static final String URL = "https://www.sefaz.mt.gov.br/sid/consulta/infocadastral/consultar/publica";

    private Connection.Response response;

    private Map<String, String> cookies;

    @Autowired
    private DeathbycaptchaV2 deathbycaptchaV2;

    @Override
    public SintegraServiceWorkerResponse consultar(String cnpj) throws Exception {

        TrustUtil.setTrustAllCerts();

        cookies = Jsoup.connect(URL).execute().cookies();

        response = Jsoup.connect(URL)
                .method(Connection.Method.POST)
                .cookies(cookies)
                .data("opcao", "2")
                .data("numero", cnpj)
                .data("captchaDigitado", resolveCaptcha())
                .data("pagn", "resultado")
                .data("captcha", "telaComCaptcha")
                .execute();

        Document document = response.parse();

        String inscricaoEstadual = FormatadorString.removePontuacao(document.select("body table tbody tr td.info").get(1).text());

        if (!StringUtils.isNumeric(inscricaoEstadual)) {
            throw new Exception("Inscricao not found");
        }

        return new SintegraServiceWorkerResponse(document, ResultScraping.LOCALIZADO, inscricaoEstadual);
    }

    @Override
    public String resolveCaptcha() throws Exception {

        response = Jsoup.connect("https://www.sefaz.mt.gov.br/sid/consulta/geradorcaracteres")
                .cookies(cookies)
                .ignoreContentType(true)
                .execute();

        File file = new File("/tmp/captcha_init" + UUID.randomUUID());

        FileUtils.writeByteArrayToFile(file, response.bodyAsBytes());

        String result = deathbycaptchaV2.solveImageCaptcha(new TextCaptchaRequest(file)).getValue();

        FileUtils.deleteQuietly(file);

        return result;
    }

    public static void main(String[] args) throws Exception {
        SintegraServiceWorkerResponse response = new MTSintegraServiceWorker().consultar("07526557001939");

        System.out.println(response.getDocument().html());

        System.out.println(response.getInscricaoEstadual());
    }

}