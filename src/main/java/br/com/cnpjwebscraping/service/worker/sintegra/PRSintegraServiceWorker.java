package br.com.cnpjwebscraping.service.worker.sintegra;

import br.com.cnpjwebscraping.service.worker.sintegra.response.SintegraServiceWorkerResponse;
import br.com.cnpjwebscraping.solver.deathbycaptchav2.DeathbycaptchaV2;
import br.com.cnpjwebscraping.solver.request.TextCaptchaRequest;
import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;
import java.util.UUID;

@Service
public class PRSintegraServiceWorker implements SintegraServiceWorker {

    private static final String URL = "http://www.sintegra.fazenda.pr.gov.br/sintegra/";

    @Override
    public SintegraServiceWorkerResponse consultar(String cnpj) throws Exception {

        return null;
    }

    @Override
    public String resolveCaptcha() throws Exception {
        return null;
    }

    //07.526.557/0058-45

    public static void main(String[] args) throws Exception {

        Connection.Response response = Jsoup.connect(URL).execute();

        response.headers().forEach((s, s2) -> System.out.println(s + " - " + s2));

        Map<String, String> cookies = response.cookies();

        String random = Double.toString(Math.random() * 2);

        response = Jsoup.connect("http://www.sintegra.fazenda.pr.gov.br/sintegra/captcha?" + random)
                .cookies(cookies)
                .header("P3P", "CP=\"NOI ADM DEV PSAi COM NAV OUR OTRo STP IND DEM\"")
                .ignoreContentType(true)
                .execute();

        response.headers().forEach((s, s2) -> System.out.println(s + " - " + s2));

        //cookies = response.cookies(); CAKEPHP=0d8628ef992afcd0de68304788b770ae; path=/sintegra
/*
        Set-Cookie
        CAKEPHP=deleted; expires=Mon, … 20:39:04 GMT; path=/sintegra
        Set-Cookie
        CAKEPHP=a4f1ff202332cdb6d59fcb04452b702f; path=/sintegra
        Set-Cookie
        CAKEPHP=0d8628ef992afcd0de68304788b770ae; path=/sintegra
        Set-Cookie
        CAKEPHP=a4f1ff202332cdb6d59fcb04452b702f; path=/sintegra*/

        File file = new File("/tmp/captcha_init" + UUID.randomUUID());

        FileUtils.writeByteArrayToFile(file, response.bodyAsBytes());

        String captcha = new DeathbycaptchaV2().solveImageCaptcha(new TextCaptchaRequest(file)).getValue().toLowerCase();

        response = Jsoup.connect("http://www.sintegra.fazenda.pr.gov.br/sintegra")
                .cookies(cookies)
                .header("P3P", "CP=\"NOI ADM DEV PSAi COM NAV OUR OTRo STP IND DEM\"")
                .data("_method", "POST")
                .data("data[Sintegra1][CodImage]", captcha)
                .data("data[Sintegra1][Cnpj]", "07526557005845")
                .data("empresa", "Consultar Empresa")
                .data("data[Sintegra1][Cadicms]", "")
                .data("data[Sintegra1][CadicmsProdutor]", "")
                .data("data[Sintegra1][CnpjCpfProdutor]", "")
                .method(Connection.Method.POST)
                .execute()
                .bufferUp();

        System.out.println(response.parse());

    }

}
