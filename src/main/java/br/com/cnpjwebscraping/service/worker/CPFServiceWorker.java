package br.com.cnpjwebscraping.service.worker;


import br.com.cnpjwebscraping.domain.Consulta;
import br.com.cnpjwebscraping.solver.anticaptcha.Anticaptcha;
import br.com.cnpjwebscraping.solver.request.ReCaptchaRequest;
import br.com.cnpjwebscraping.util.TrustUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CPFServiceWorker implements ServiceWorker {

    @Override
    public ServiceWorkerResponse consultar(Consulta consulta) throws Exception {
        TrustUtil.setTrustAllCerts();

        Connection.Response response =
                Jsoup.connect("")
                        .execute();


        Document document = response.parse();

        return new ServiceWorkerResponse(document);
    }


    public static void main(String[] args) throws Exception {
        Connection.Response response =
                Jsoup.connect("https://aplicacoes10.trtsp.jus.br/certidao_trabalhista_eletronica/public/index.php/index/nome-cpf")
                    .method(Connection.Method.POST)
                    .data("numero", "62232889001404")
                    .ignoreContentType(true)
                    .execute()
                    .bufferUp();

        if (StringUtils.equals(response.contentType(), MediaType.APPLICATION_JSON_VALUE)) {

            JSONObject jsonObject = new JSONObject(response.parse().select("body").html());

            if (jsonObject.getBoolean("sucesso")) {

                System.out.println(jsonObject.getString("nome"));

            } else {
                throw new Exception("Sucesso:false");
            }

        } else {
            throw new Exception("Error");
        }
    }

}
