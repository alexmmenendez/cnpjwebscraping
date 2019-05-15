package br.com.cnpjwebscraping.service.worker.trt;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class TRT02ServiceWorker implements TRTServiceWorker {

    @Override
    public String consultaNomeCompletoRazaoSocialPeloCPFCNPJ(String cpfCnpj) throws Exception {

        Connection.Response response =
                Jsoup.connect("https://aplicacoes10.trtsp.jus.br/certidao_trabalhista_eletronica/public/index.php/index/nome-cpf")
                        .method(Connection.Method.POST)
                        .data("numero", cpfCnpj)
                        .timeout(60000)
                        .postDataCharset("UTF-8")
                        .ignoreContentType(true)
                        .execute()
                        .bufferUp();

        if (StringUtils.equals(response.contentType(), MediaType.APPLICATION_JSON_VALUE)) {

            JSONObject jsonObject = new JSONObject(response.parse().select("body").html().replace("&amp;", "&"));

            return jsonObject.getString("nome");

        } else {
            throw new Exception("Error");
        }
    }
}
