package br.com.cnpjwebscraping.solver.anticaptcha;

import br.com.cnpjwebscraping.solver.anticaptcha.api.NoCaptchaProxyless;
import br.com.cnpjwebscraping.solver.anticaptcha.helper.DebugHelper;
import br.com.cnpjwebscraping.solver.anticaptcha.properties.AnticaptchaProperties;
import br.com.cnpjwebscraping.solver.request.ReCaptchaRequest;
import br.com.cnpjwebscraping.solver.request.TextCaptchaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class Anticaptcha implements CaptchaSolver {

	@Autowired
	private AnticaptchaProperties properties;

	@Override
	public CaptchaProcessed solve(TextCaptchaRequest textCaptchaRequest) {
		return new CaptchaProcessed();
	}

	@Override
	public CaptchaProcessed solve(ReCaptchaRequest reCaptchaRequest) {
		DebugHelper.setVerboseMode(true);
		
		/* 
		 * Em analise de uso
		 * 
		 * NoCaptcha api = new NoCaptcha();
		 * 
		 */
		NoCaptchaProxyless api = new NoCaptchaProxyless();
		try {
			//api.setClientKey(properties.getClientKey());
			api.setClientKey("");
			api.setWebsiteUrl(new URL(reCaptchaRequest.getPageUrl()));
			api.setWebsiteKey(reCaptchaRequest.getGoogleKey());
			
			/* 
			 * Em analise de uso
			 * 
			 * api.setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116");
			 * 
			 * if (proxyAtivado) {
			 * api.setProxyType(NoCaptcha.ProxyTypeOption.HTTP);
			 * api.setProxyAddress("xx.xxx.xx.xx");
			 * api.setProxyPort(8282);
			 * api.setProxyLogin("login");
			 * api.setProxyPassword("password");
			 * 
			}*/
			
			if (!api.createTask()) {
				DebugHelper.out("API v2 send failed. " + api.getErrorMessage(), DebugHelper.Type.ERROR);
				
				return new CaptchaProcessed();
			} else if (!api.waitForResult()) {
				DebugHelper.out("Could not solve the captcha.", DebugHelper.Type.ERROR);
				
				return new CaptchaProcessed();
			} else {
				DebugHelper.out("Result: " + api.getTaskSolution(), DebugHelper.Type.SUCCESS);
				
				return new CaptchaProcessed(api.getTaskSolution(), true);
			}
		} catch (Exception e) {
			//Log4JLogger.info(e.getMessage(), this.getClass());
			return new CaptchaProcessed();
		}
	}
}
