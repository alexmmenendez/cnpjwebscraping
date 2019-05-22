package br.com.cnpjwebscraping.solver;

import br.com.cnpjwebscraping.solver.request.ReCaptchaRequest;
import br.com.cnpjwebscraping.solver.request.TextCaptchaRequest;

public interface CaptchaSolver {
	
	public CaptchaProcessed solveImageCaptcha(TextCaptchaRequest textCaptchaRequest) throws Exception;
	
	public CaptchaProcessed solveRecaptcha(ReCaptchaRequest reCaptchaRequest) throws Exception;
	
}
