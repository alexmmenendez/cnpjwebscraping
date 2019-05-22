package br.com.cnpjwebscraping.service.worker.trt;

import br.com.cnpjwebscraping.util.FormatadorString;
import br.com.cnpjwebscraping.util.TrustUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TRT24ServiceWorker implements TRTServiceWorker {

    private static final String URL = "http://sgrh.trt24.jus.br/certidao/pages/certidao/index.xhtml?windowId=115";

    @Override
    public String consultaNomeCompletoRazaoSocialPeloCPFCNPJ(String cpfCnpj) throws Exception {

        TrustUtil.setTrustAllCerts();

        Connection.Response response = Jsoup.connect(URL).execute();

        Map<String, String> cookies = response.cookies();

        Document document = response.parse();

        String viewState = document.select("[name=javax.faces.ViewState]").val();

        cpfCnpj = FormatadorString.removePontuacao(cpfCnpj);

        String nome;

        if (cpfCnpj.length() == 14) {

            document = Jsoup.connect(URL)
                    .cookies(cookies)
                    .data("javax.faces.partial.ajax", "true")
                    .data("javax.faces.source", "form:tvEmissaoCertidao:j_idt54")
                    .data("javax.faces.partial.execute", "form:tvEmissaoCertidao:j_idt54 form:tvEmissaoCertidao:itNrCpfCnpj")
                    .data("javax.faces.partial.render", "form:tvEmissaoCertidao:pgSolicitante form:ppMessages")
                    .data("form:tvEmissaoCertidao:j_idt54", "form:tvEmissaoCertidao:j_idt54")
                    .data("form:tvEmissaoCertidao:itNrCpfCnpj", cpfCnpj)
                    .data("javax.faces.ViewState", viewState)
                    .execute().parse();

            nome = document.select("update[id=form:tvEmissaoCertidao:pgSolicitante]").html();

            nome = StringUtils.substringBetween(nome, FormatadorString.formatarCnpj(cpfCnpj) + " - ", "</label>");

            nome = nome.trim();

        } else {

            Jsoup.connect(URL)
                    .cookies(cookies)
                    .data("javax.faces.partial.ajax", "true")
                    .data("javax.faces.source", "form:tvEmissaoCertidao:j_idt47")
                    .data("javax.faces.partial.execute", "form:tvEmissaoCertidao:j_idt47")
                    .data("javax.faces.partial.render", "form:tvEmissaoCertidao:pgSolicitante")
                    .data("javax.faces.behavior.event", "change")
                    .data("javax.faces.partial.event", "change")
                    .data("form", "form")
                    .data("form:tvEmissaoCertidao:j_idt47", "F")
                    .data("form:tvEmissaoCertidao:j_idt99_collapsed", "true")
                    .data("form:tvEmissaoCertidao:j_idt101_collapsed", "true")
                    .data("form:tvEmissaoCertidao:itNrCpfCnpj", cpfCnpj)
                    .data("form:tvEmissaoCertidao_activeIndex", "0")
                    .data("javax.faces.ViewState", viewState)
                    .execute().parse();

            document = Jsoup.connect(URL)
                    .cookies(cookies)
                    .data("javax.faces.partial.ajax", "true")
                    .data("javax.faces.source", "form:tvEmissaoCertidao:j_idt54")
                    .data("javax.faces.partial.execute", "form:tvEmissaoCertidao:j_idt54 form:tvEmissaoCertidao:itNrCpfCnpj")
                    .data("javax.faces.partial.render", "form:tvEmissaoCertidao:pgSolicitante form:ppMessages")
                    .data("form:tvEmissaoCertidao:j_idt54", "form:tvEmissaoCertidao:j_idt54")
                    .data("form:tvEmissaoCertidao:itNrCpfCnpj", cpfCnpj)
                    .data("javax.faces.ViewState", viewState)
                    .execute().parse();

            nome = document.select("update[id=form:tvEmissaoCertidao:pgSolicitante]").html();

            nome = StringUtils.substringBetween(nome, FormatadorString.formatarCPF(cpfCnpj) + " - ", "</label>");

            nome = nome.trim();
        }

        return nome;
    }
}