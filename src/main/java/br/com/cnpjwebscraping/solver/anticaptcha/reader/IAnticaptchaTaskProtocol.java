package br.com.cnpjwebscraping.solver.anticaptcha.reader;

import br.com.cnpjwebscraping.solver.anticaptcha.api.response.TaskResultResponse;
import org.json.JSONObject;

public interface IAnticaptchaTaskProtocol {
    JSONObject getPostData();
    TaskResultResponse.SolutionData getTaskSolution();
}
