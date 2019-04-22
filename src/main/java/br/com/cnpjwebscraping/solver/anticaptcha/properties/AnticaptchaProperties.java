package br.com.cnpjwebscraping.solver.anticaptcha.properties;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "singleton")
public class AnticaptchaProperties {

    private String clientKey = "";

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }
}
