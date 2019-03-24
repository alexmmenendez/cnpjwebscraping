package br.com.cnpjwebscraping.solver.anticaptcha.reader;

import br.com.cnpjwebscraping.solver.anticaptcha.api.NoCaptcha;
import br.com.cnpjwebscraping.solver.anticaptcha.api.NoCaptchaProxyless;
import br.com.cnpjwebscraping.solver.anticaptcha.helper.DebugHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
	
	public static void main(String[] args) throws InterruptedException, IOException {
		exampleNoCaptchaProxyless();
		exampleNoCaptcha();
	}
	
	private static void exampleNoCaptchaProxyless() throws InterruptedException, IOException {
		DebugHelper.setVerboseMode(true);
		
		NoCaptchaProxyless api = new NoCaptchaProxyless();
		api.setClientKey("");
		api.setWebsiteUrl(new URL("http://http.myjino.ru/recaptcha/test-get.php"));
		api.setWebsiteKey("6LfxbR0TAAAAABri9WZD80U8fbay0QU2eLtn5Htl");
		
		if (!api.createTask()) {
			DebugHelper.out("API v2 send failed. " + api.getErrorMessage(), DebugHelper.Type.ERROR);
		} else if (!api.waitForResult()) {
			DebugHelper.out("Could not solve the captcha.", DebugHelper.Type.ERROR);
		} else {
			DebugHelper.out("Result: " + api.getTaskSolution(), DebugHelper.Type.SUCCESS);
		}
		
		String url = "http://http.myjino.ru/recaptcha/test-get.php?text=Doug&g-recaptcha-response=" + api.getTaskSolution();
		
		Document document = Jsoup.connect(url).get();
		
		System.out.println(document);
		
	}

	private static void exampleNoCaptcha() throws MalformedURLException, InterruptedException {
		DebugHelper.setVerboseMode(true);
		
		NoCaptcha api = new NoCaptcha();
		api.setClientKey("");
		api.setWebsiteUrl(new URL("http://http.myjino.ru/recaptcha/test-get.php"));
		api.setWebsiteKey("6Lc_aCMTAAAAABx7u2W0WPXnVbI_v6ZdbM6rYf16");
		api.setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 " + "(KHTML, like Gecko) Chrome/52.0.2743.116");
		
		// proxy access parameters
		api.setProxyType(NoCaptcha.ProxyTypeOption.HTTP);
		api.setProxyAddress("xx.xxx.xx.xx");
		api.setProxyPort(8282);
		api.setProxyLogin("login");
		api.setProxyPassword("password");
		
		if (!api.createTask()) {
			DebugHelper.out("API v2 send failed. " + api.getErrorMessage(), DebugHelper.Type.ERROR);
		} else if (!api.waitForResult()) {
			DebugHelper.out("Could not solve the captcha.", DebugHelper.Type.ERROR);
		} else {
			DebugHelper.out("Result: " + api.getTaskSolution(), DebugHelper.Type.SUCCESS);
		}
	}
}
