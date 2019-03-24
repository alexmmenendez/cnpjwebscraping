package br.com.cnpjwebscraping.solver.anticaptcha;

import br.com.cnpjwebscraping.solver.request.ReCaptchaRequest;
import br.com.cnpjwebscraping.solver.request.TextCaptchaRequest;

public interface CaptchaSolver {
	
	public CaptchaProcessed solve(TextCaptchaRequest textCaptchaRequest);
	
	public CaptchaProcessed solve(ReCaptchaRequest reCaptchaRequest);
	
}
