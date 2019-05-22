package br.com.cnpjwebscraping.service.worker.trt;

import br.com.cnpjwebscraping.util.FormatadorString;
import br.com.cnpjwebscraping.util.TrustUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class TRT20ServiceWorker implements TRTServiceWorker {

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

        return document.body().text().trim();
    }
}