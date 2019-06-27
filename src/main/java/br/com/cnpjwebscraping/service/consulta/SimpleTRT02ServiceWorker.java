package br.com.cnpjwebscraping.service.consulta;

import br.com.cnpjwebscraping.util.TrustUtil;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

@Service
public class SimpleTRT02ServiceWorker implements TRTServiceWorker {

    @Override
    public String consultaNomeCompletoRazaoSocialPeloCPFCNPJ(String cpfCnpj) throws Exception {

        TrustUtil.setTrustAllCerts();

        Connection.Response response =
                Jsoup.connect("https://aplicacoes10.trtsp.jus.br/certidao_trabalhista_eletronica/public/index.php/index/nome-cpf")
                        .method(Connection.Method.POST)
                        .data("numero", cpfCnpj)
                        .timeout(60000)
                        .postDataCharset("UTF-8")
                        .ignoreContentType(true)
                        .execute()
                        .bufferUp();

        JSONObject jsonObject = new JSONObject(response.parse().select("body").html().replace("&amp;", "&"));

        String result = jsonObject.getString("nome");

        if (result.equals("null")) {
            throw new Exception("Resultado é nulo");
        }

        return result;
    }
}