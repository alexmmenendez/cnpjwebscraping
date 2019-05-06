package br.com.cnpjwebscraping.controller;

import br.com.cnpjwebscraping.util.FormatadorString;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
public class AppController {

    @GetMapping("/consulta")
    public String consulta(Model model) {

        return "consulta";
    }

    @PostMapping("/consulta")
    public String consultaPost(Model model, String cpfcnpj) {

        cpfcnpj = FormatadorString.removePontuacao(cpfcnpj);

        try {
            Connection.Response response =
                    Jsoup.connect("https://aplicacoes10.trtsp.jus.br/certidao_trabalhista_eletronica/public/index.php/index/nome-cpf")
                            .method(Connection.Method.POST)
                            .data("numero", cpfcnpj)
                            .timeout(60000)
                            .postDataCharset("UTF-8")
                            .ignoreContentType(true)
                            .execute()
                            .bufferUp();

            if (StringUtils.equals(response.contentType(), MediaType.APPLICATION_JSON_VALUE)) {

                JSONObject jsonObject = new JSONObject(response.parse().select("body").html().replace("&amp;", "&"));

                String nomeRazaoScial = jsonObject.getString("nome").trim();

                return nomeRazaoScial;
                // model.addAttribute("nomeRazaoSocial", nomeRazaoScial);

            } else {
                throw new Exception("Error");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "consulta";
        }

    }

}
