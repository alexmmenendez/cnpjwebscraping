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
public class TRT11ServiceWorker implements TRTServiceWorker {

    private static final String URL = "https://certtrab.trt11.jus.br/ceat/emiteCertidao.xhtml";

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
                    .data("javax.faces.source", "tipoBusca:1")
                    .data("javax.faces.partial.execute", "tipoBusca")
                    .data("javax.faces.partial.render", "j_idt12")
                    .data("javax.faces.behavior.event", "change")
                    .data("javax.faces.partial.event", "change")
                    .data("j_idt12", "j_idt12")
                    .data("tipoBusca", "cnpj")
                    .data("javax.faces.ViewState", viewState)
                    .execute();

            document = (Jsoup.connect(URL)
                    .cookies(cookies)
                    .data("javax.faces.partial.ajax", "true")
                    .data("javax.faces.source", "idCNPJ")
                    .data("javax.faces.partial.execute", "idCNPJ")
                    .data("javax.faces.partial.render", "cpfCNPJNome idConsta idBotoes messages")
                    .data("javax.faces.behavior.event", "blur")
                    .data("javax.faces.partial.event", "blur")
                    .data("j_idt12", "j_idt12")
                    .data("tipoBusca", "cnpj")
                    .data("idCNPJ", cpfCnpj)
                    .data("javax.faces.ViewState", viewState)
                    .execute()).parse();

            nome = document.select("update[id=cpfCNPJNome]").html();

            nome = StringUtils.substringBetween(nome, ">", "</span>");

            nome = nome.trim();

        } else {


            document = (Jsoup.connect(URL)
                    .cookies(cookies)
                    .data("javax.faces.partial.ajax", "true")
                    .data("javax.faces.source", "idCpf")
                    .data("javax.faces.partial.execute", "idCpf")
                    .data("javax.faces.partial.render", "cpfCNPJNome idConsta idBotoes messages")
                    .data("javax.faces.behavior.event", "blur")
                    .data("javax.faces.partial.event", "blur")
                    .data("j_idt12", "j_idt12")
                    .data("tipoBusca", "cpf")
                    .data("idCpf", cpfCnpj)
                    .data("javax.faces.ViewState", viewState)
                    .execute()).parse();

            nome = document.select("update[id=cpfCNPJNome]").html();

            nome = StringUtils.substringBetween(nome, ">", "</span>");

            nome = nome.trim();

        }

        return nome;
    }
}