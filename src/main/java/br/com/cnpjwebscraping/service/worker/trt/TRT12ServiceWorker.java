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
public class TRT12ServiceWorker implements TRTServiceWorker {

    private static final String URL = "https://pje.trt12.jus.br/certidao/feitosTrabalhistas/aba1.emissao.htm";

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

            Jsoup.connect(URL)
                    .cookies(cookies)
                    .data("javax.faces.partial.ajax", "true")
                    .data("javax.faces.source", "form:j_idt17")
                    .data("javax.faces.partial.execute", "form:j_idt17")
                    .data("javax.faces.partial.render", "form:tableDocumentos form:nomeConsulta form:painelNomeInformado form:textoVariacao")
                    .data("javax.faces.behavior.event", "change")
                    .data("javax.faces.partial.event", "change")
                    .data("form", "form")
                    .data("form:j_idt17", "J")
                    .data("javax.faces.ViewState", viewState)
                    .execute().parse();

            document = Jsoup.connect(URL)
                    .cookies(cookies)
                    .data("javax.faces.partial.ajax", "true")
                    .data("javax.faces.source", "form:inputCNPJ")
                    .data("javax.faces.partial.execute", "form:inputCNPJ")
                    .data("javax.faces.partial.render", "form:nomeConsulta form:nomeReceitaCPF form:nomeReceitaCNPJ")
                    .data("javax.faces.behavior.event", "change")
                    .data("javax.faces.partial.event", "change")
                    .data("form", "form")
                    .data("form:j_idt17", "J")
                    .data("form:inputCNPJ", cpfCnpj)
                    .data("javax.faces.ViewState", viewState)
                    .execute().parse();

            nome = document.select("update[id=form:nomeReceitaCNPJ]").html();

            nome = StringUtils.substringBetween(nome, "value=\"", "\"");

            nome = nome.trim();

        } else {

            document = Jsoup.connect(URL)
                    .cookies(cookies)
                    .data("javax.faces.partial.ajax", "true")
                    .data("javax.faces.source", "form:inputCPF")
                    .data("javax.faces.partial.execute", "form:inputCPF")
                    .data("javax.faces.partial.render", "form:nomeConsulta form:nomeReceitaCPF form:nomeReceitaCNPJ")
                    .data("javax.faces.behavior.event", "change")
                    .data("javax.faces.partial.event", "change")
                    .data("form", "form")
                    .data("form:j_idt17", "F")
                    .data("form:inputCPF", cpfCnpj)
                    .data("javax.faces.ViewState", viewState)
                    .execute().parse();

            nome = document.select("update[id=form:nomeReceitaCPF]").html();

            nome = StringUtils.substringBetween(nome, "value=\"", "\"");

            nome = nome.trim();
        }

        return nome;
    }
}