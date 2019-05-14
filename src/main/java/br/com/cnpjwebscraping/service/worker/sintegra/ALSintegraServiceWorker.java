package br.com.cnpjwebscraping.service.worker.sintegra;

import br.com.cnpjwebscraping.hardcoded.ResultScraping;
import br.com.cnpjwebscraping.service.worker.sintegra.response.SintegraServiceWorkerResponse;
import br.com.cnpjwebscraping.util.TrustUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ALSintegraServiceWorker implements SintegraServiceWorker {

    @Override
    public SintegraServiceWorkerResponse consultar(String cnpj) throws Exception {

        TrustUtil.setTrustAllCerts();

        Connection.Response response = Jsoup.connect("http://sintegra.sefaz.al.gov.br/sfzsintegraapi/api/consultar/contribuinte?cacheBuster=" + new Date().getTime())
                .method(Connection.Method.POST)
                .ignoreContentType(true)
                .header("Content-Type", "application/json;charset=UTF-8")
                .requestBody(new JSONObject().put("caceal", "").put("cnpj", cnpj).toString())
                .execute();

        Document document = response.parse();

        JSONArray json = new JSONArray(document.body().html());

        if (json.isEmpty()) {
            return new SintegraServiceWorkerResponse(document, ResultScraping.NAO_POSSUI, null);
        }

        String inscricaoEstadual = json.getJSONObject(0).get("caceal").toString();

        if (!StringUtils.isNumeric(inscricaoEstadual)) {
            throw new Exception("Inscricao not found");
        }

        return new SintegraServiceWorkerResponse(document, ResultScraping.LOCALIZADO, inscricaoEstadual);

    }

    @Override
    public String resolveCaptcha() {
        return null;
    }

    public static void main(String[] args) throws Exception {
        SintegraServiceWorkerResponse response = new ALSintegraServiceWorker().consultar("56228356011338");

        System.out.println(response.getDocument().html());

        System.out.println(response.getInscricaoEstadual());
    }

}