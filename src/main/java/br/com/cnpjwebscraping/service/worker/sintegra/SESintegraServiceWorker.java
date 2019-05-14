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
public class SESintegraServiceWorker implements SintegraServiceWorker {

    private static final String URL = "https://security.sefaz.se.gov.br/SIC/sintegra/index.jsp";

    private static final String URL_BASE = "https://security.sefaz.se.gov.br/SIC/sintegra/";

    private Connection.Response response;

    private Map<String, String> cookies;

    private Document document;

    @Autowired
    private DeathbycaptchaV2 deathbycaptchaV2;

    @Override
    public SintegraServiceWorkerResponse consultar(String cnpj) throws Exception {

        TrustUtil.setTrustAllCerts();

        response = Jsoup.connect(URL).execute();

        cookies = response.cookies();

        document = response.parse();

        String captcha = resolveCaptcha();

        response = Jsoup.connect("https://security.sefaz.se.gov.br/SIC/sintegra/result.jsp")
                .data("AppName", document.select("[name=AppName]").val())
                .data("TransId", document.select("[name=TransId]").val())
                .data("cdCnpj", cnpj)
                .data("cdPessoaContribuinte", "")
                .data("dsImagem", captcha)
                .data("cdImagem", document.select("[name=cdImagem]").val())
                .method(Connection.Method.POST)
                .cookies(cookies)
                .execute();

        document = response.parse();

        String inscricaoEstadual = FormatadorString.removePontuacao(document.select("table tbody table font").get(4).html().trim());

        if (!StringUtils.isNumeric(inscricaoEstadual)) {
            throw new Exception("Inscricao not found");
        }

        return new SintegraServiceWorkerResponse(document, ResultScraping.LOCALIZADO, inscricaoEstadual);
    }

    @Override
    public String resolveCaptcha() throws Exception {

        String imageSrc = URL_BASE + document.select("form img").first().attr("src");

        response = Jsoup.connect(imageSrc)
                .cookies(cookies)
                .ignoreContentType(true)
                .execute();

        File file = new File("/tmp/captcha_init" + UUID.randomUUID());

        FileUtils.writeByteArrayToFile(file, response.bodyAsBytes());

        String result = deathbycaptchaV2.solveImageCaptcha(new TextCaptchaRequest(file)).getValue().toLowerCase();

        FileUtils.deleteQuietly(file);

        return result;
    }

}
