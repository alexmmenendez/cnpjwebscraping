package br.com.cnpjwebscraping.service.worker.sintegra;

import br.com.cnpjwebscraping.hardcoded.ResultScraping;
import br.com.cnpjwebscraping.service.worker.sintegra.response.SintegraServiceWorkerResponse;
import br.com.cnpjwebscraping.solver.anticaptcha.Anticaptcha;
import br.com.cnpjwebscraping.solver.anticaptcha.exception.AnticaptchaException;
import br.com.cnpjwebscraping.solver.deathbycaptchav2.DeathbycaptchaV2;
import br.com.cnpjwebscraping.solver.deathbycaptchav2.exception.DeathbycaptchaException;
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
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class APSintegraServiceWorker implements SintegraServiceWorker {

    private static final String URL = "http://www.sefaz.ap.gov.br/sate/seg/SEGf_AcessarFuncao.jsp?cdFuncao=ARR_305";

    private Connection.Response response;

    private Map<String, String> cookies;

    @Autowired
    private DeathbycaptchaV2 deathbycaptchaV2;

    @Override
    public SintegraServiceWorkerResponse consultar(String cnpj) throws Exception {

        TrustUtil.setTrustAllCerts();

        response = Jsoup.connect(URL).timeout(60000).execute();

        cookies = response.cookies();

        Jsoup.connect("http://www.sefaz.ap.gov.br/sate/arr/ARRf_DarInternetEmitir.do")
                .method(Connection.Method.POST)
                .cookies(cookies)
                .timeout(60000)
                .data("hidAcao", "obterTipoContribuinte")
                .data("hidHistorico", "-1")
                .data("cmbTpDocumento", "2")
                .execute();

        response = Jsoup.connect("http://www.sefaz.ap.gov.br/sate/arr/ARRf_DarInternetEmitir.do")
                .method(Connection.Method.POST)
                .cookies(cookies)
                .timeout(60000)
                .data("hidAcao", "obterDadosContribuinte")
                .data("hidHistorico", "-1")
                .data("cmbTpDocumento", "2")
                .data("edtNrDocumento", cnpj)
                .data("edtCaptchaResponse", resolveCaptcha())
                .execute();

        Document document = response.parse();

        /*String inscricaoEstadual = FormatadorString.removePontuacao(document.select("#conteudo b").get(1).text());

        if (!StringUtils.isNumeric(inscricaoEstadual)) {
            throw new Exception("Inscricao not found");
        }*/

        return new SintegraServiceWorkerResponse(document, ResultScraping.LOCALIZADO, "");
    }

    @Override
    public String resolveCaptcha() throws DeathbycaptchaException, IOException {

        response = Jsoup.connect("http://www.sefaz.ap.gov.br/sate/servicos/JCaptcha")
                .cookies(cookies)
                .ignoreContentType(true)
                .execute();

        File file = new File("/tmp/captcha_init" + UUID.randomUUID());

        FileUtils.writeByteArrayToFile(file, response.bodyAsBytes());

        String result = new DeathbycaptchaV2().solveImageCaptcha(new TextCaptchaRequest(file)).getValue();

        FileUtils.deleteQuietly(file);

        return result;
    }

    public static void main(String[] args) throws Exception {

        SintegraServiceWorkerResponse response = new APSintegraServiceWorker().consultar("05.995.840/0001-55");

        System.out.println(response.getDocument().html());

        System.out.println(response.getInscricaoEstadual());
    }

}
