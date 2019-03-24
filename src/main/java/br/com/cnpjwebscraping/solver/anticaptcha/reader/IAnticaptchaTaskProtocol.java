package br.com.cnpjwebscraping.solver.anticaptcha.reader;

import org.json.JSONObject;

public interface IAnticaptchaTaskProtocol {
    JSONObject getPostData();
    String getTaskSolution();
}
