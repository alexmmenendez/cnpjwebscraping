package br.com.cnpjwebscraping.service.consulta;

import br.com.cnpjwebscraping.util.FormatadorString;
import br.com.cnpjwebscraping.util.TrustUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class SimpleTRT20ServiceWorker implements TRTServiceWorker {

    private static final String URL = "https://www.trt20.jus.br/standalone/wsreceita.php";

    @Override
    public String consultaNomeCompletoRazaoSocialPeloCPFCNPJ(String cpfCnpj) throws Exception {

        TrustUtil.setTrustAllCerts();

        cpfCnpj = FormatadorString.removePontuacao(cpfCnpj);

        Document document =
                Jsoup.connect(URL)
                        .timeout(60000)
                        .data("cpf", cpfCnpj)
                        .execute().parse();

        String nome = document.body().text().trim();

        if (StringUtils.isBlank(nome)) {
            throw new Exception("Resultado da consulta é vazio");
        }

        return nome;
    }
}