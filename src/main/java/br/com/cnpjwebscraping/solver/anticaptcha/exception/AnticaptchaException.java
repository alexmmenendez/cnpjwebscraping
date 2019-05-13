package br.com.cnpjwebscraping.solver.anticaptcha.exception;

public class AnticaptchaException extends Exception {

    private static final long serialVersionUID = 1L;

    public AnticaptchaException(String message) {
        super(message);
    }

    public AnticaptchaException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public AnticaptchaException(Throwable throwable) {
        super(throwable);
    }
}
