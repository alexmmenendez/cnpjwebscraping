package br.com.cnpjwebscraping.solver.anticaptcha.api;

import br.com.cnpjwebscraping.solver.anticaptcha.api.response.TaskResultResponse;
import br.com.cnpjwebscraping.solver.anticaptcha.helper.DebugHelper;
import br.com.cnpjwebscraping.solver.anticaptcha.reader.AnticaptchaBase;
import br.com.cnpjwebscraping.solver.anticaptcha.reader.IAnticaptchaTaskProtocol;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class NoCaptchaProxyless extends AnticaptchaBase implements IAnticaptchaTaskProtocol {
    private URL WebsiteUrl;
    private String WebsiteKey;
    private String WebsiteSToken;

    public void setWebsiteUrl(URL websiteUrl) {
        WebsiteUrl = websiteUrl;
    }

    public void setWebsiteKey(String websiteKey) {
        WebsiteKey = websiteKey;
    }

    public void setWebsiteSToken(String websiteSToken) {
        WebsiteSToken = websiteSToken;
    }

    @Override
    public JSONObject getPostData() {
        JSONObject postData = new JSONObject();

        try {
            postData.put("type", "NoCaptchaTaskProxyless");
            postData.put("websiteURL", WebsiteUrl.toString());
            postData.put("websiteKey", WebsiteKey);
            postData.put("websiteSToken", WebsiteSToken);
        } catch (JSONException e) {
            DebugHelper.out("JSON compilation error: " + e.getMessage(), DebugHelper.Type.ERROR);

            return null;
        }

        return postData;
    }

    public TaskResultResponse.SolutionData getTaskSolution() {
        return taskInfo.getSolution();
    }
}
