package br.com.cnpjwebscraping.service.worker.sintegra;

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
public class SPSintegraServiceWorker implements SintegraServiceWorker {

    private static final String URL = "https://www.cadesp.fazenda.sp.gov.br/(S(3qljrb22dfakw2vvodzgzpqy))/Pages/Cadastro/Consultas/ConsultaPublica/ConsultaPublica.aspx";

    private Connection.Response response;

    private Map<String, String> cookies;

    @Autowired
    private DeathbycaptchaV2 deathbycaptchaV2;

    @Override
    public SintegraServiceWorkerResponse consultar(String cnpj) throws Exception {

        TrustUtil.setTrustAllCerts();

        response = Jsoup.connect(URL).timeout(60000).execute();

        cookies = response.cookies();

        Document document = response.parse();

        response = Jsoup.connect(response.url().toString())
                .method(Connection.Method.POST)
                .cookies(cookies)
                .timeout(60000)
                .data("ctl00_conteudoPaginaPlaceHolder_filtroTabContainer_ClientState", document.select("[name=ctl00_conteudoPaginaPlaceHolder_filtroTabContainer_ClientState]").val())
                .data("__EVENTTARGET", "ctl00$conteudoPaginaPlaceHolder$filtroTabContainer$filtroEmitirCertidaoTabPanel$tipoFiltroDropDownList")
                .data("__VIEWSTATE", document.select("[name=__VIEWSTATE]").val())
                .data("__VIEWSTATEGENERATOR", document.select("[name=__VIEWSTATEGENERATOR]").val())
                .data("__EVENTVALIDATION", document.select("[name=__EVENTVALIDATION]").val())
                .data("ctl00$conteudoPaginaPlaceHolder$filtroTabContainer$filtroEmitirCertidaoTabPanel$tipoFiltroDropDownList", "1")
                .execute();

        document = response.parse();

        response = Jsoup.connect(response.url().toString())
                .method(Connection.Method.POST)
                .cookies(cookies)
                .timeout(60000)
                .data("ctl00_conteudoPaginaPlaceHolder_filtroTabContainer_ClientState", document.select("[name=ctl00_conteudoPaginaPlaceHolder_filtroTabContainer_ClientState]").val())
                .data("__VIEWSTATE", document.select("[name=__VIEWSTATE]").val())
                .data("__VIEWSTATEGENERATOR", document.select("[name=__VIEWSTATEGENERATOR]").val())
                .data("__EVENTVALIDATION", document.select("[name=__EVENTVALIDATION]").val())
                .data("ctl00$conteudoPaginaPlaceHolder$filtroTabContainer$filtroEmitirCertidaoTabPanel$tipoFiltroDropDownList", "1")
                .data("ctl00$conteudoPaginaPlaceHolder$filtroTabContainer$filtroEmitirCertidaoTabPanel$valorFiltroTextBox", cnpj)
                .data("ctl00$conteudoPaginaPlaceHolder$filtroTabContainer$filtroEmitirCertidaoTabPanel$imagemDinamicaTextBox", resolveCaptcha())
                .data("ctl00$conteudoPaginaPlaceHolder$filtroTabContainer$filtroEmitirCertidaoTabPanel$consultaPublicaButton", "Consultar")
                .execute();

        document = response.parse();

        String inscricaoEstadual = FormatadorString.removePontuacao(document.select(".dadoDetalhe").first().text());

        if (!StringUtils.isNumeric(inscricaoEstadual)) {
            throw new Exception("Inscricao not found");
        }

        return new SintegraServiceWorkerResponse(document, inscricaoEstadual);
    }

    @Override
    public String resolveCaptcha() throws Exception {

        String srcImage = StringUtils.remove(response.url().toString(), "/Pages/Cadastro/Consultas/ConsultaPublica/ConsultaPublica.aspx");

        srcImage = srcImage + "/imagemDinamica.aspx";

        response = Jsoup.connect(srcImage)
                .cookies(cookies)
                .ignoreContentType(true)
                .execute();

        File file = new File("/tmp/captcha_init" + UUID.randomUUID());

        FileUtils.writeByteArrayToFile(file, response.bodyAsBytes());

        String result = deathbycaptchaV2.solveImageCaptcha(new TextCaptchaRequest(file)).getValue().toLowerCase();

        FileUtils.deleteQuietly(file);

        return result;
    }

    public static void main(String[] args) throws Exception {

        SintegraServiceWorkerResponse response = new SPSintegraServiceWorker().consultar("01131570000183");

        System.out.println(response.getDocument().html());

        System.out.println(response.getInscricaoEstadual());
    }

}
