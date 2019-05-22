package br.com.cnpjwebscraping.solver.anticaptcha;

import br.com.cnpjwebscraping.hardcoded.ParametroConfiguracao;
import br.com.cnpjwebscraping.service.domain.ConfiguracaoService;
import br.com.cnpjwebscraping.solver.CaptchaProcessed;
import br.com.cnpjwebscraping.solver.CaptchaSolver;
import br.com.cnpjwebscraping.solver.anticaptcha.api.ImageToText;
import br.com.cnpjwebscraping.solver.anticaptcha.api.NoCaptchaProxyless;
import br.com.cnpjwebscraping.solver.anticaptcha.exception.AnticaptchaException;
import br.com.cnpjwebscraping.solver.anticaptcha.helper.DebugHelper;
import br.com.cnpjwebscraping.solver.request.ReCaptchaRequest;
import br.com.cnpjwebscraping.solver.request.TextCaptchaRequest;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class Anticaptcha implements CaptchaSolver {

	@Autowired
	private ConfiguracaoService configuracaoService;

	private String clientKey;

	@Override
	public CaptchaProcessed solveImageCaptcha(TextCaptchaRequest textCaptchaRequest) throws AnticaptchaException {
		DebugHelper.setVerboseMode(true);

		DebugHelper.out("Trying to solve the ImageCaptcha by Anticaptcha", DebugHelper.Type.INFO);

		ImageToText api = new ImageToText();

		try {

			clientKey = configuracaoService.buscarPorParametro(ParametroConfiguracao.ANTICAPTCHA_CLIENT_KEY).getValor();

			api.setClientKey(clientKey);
			api.setFilePath(textCaptchaRequest.getFile().getPath());

			if (!api.createTask()) {
				DebugHelper.out("API v2 send failed. " + api.getErrorMessage(), DebugHelper.Type.ERROR);

				throw new AnticaptchaException("Failed to solve the captcha.");

			} else if (!api.waitForResult()) {
				DebugHelper.out("Could not solve the captcha.", DebugHelper.Type.ERROR);

				throw new AnticaptchaException("Failed to solve the captcha.");

			} else {
				DebugHelper.out("Result: " + api.getTaskSolution().getText(), DebugHelper.Type.SUCCESS);

				return new CaptchaProcessed(api.getTaskSolution().getText(), true);
			}

		} catch (Exception e) {
			DebugHelper.out("Failed to solve the captcha.", DebugHelper.Type.ERROR);

			throw new AnticaptchaException("Failed to solve the captcha.", e);

		} finally {
			FileUtils.deleteQuietly(textCaptchaRequest.getFile());
		}
	}

	@Override
	public CaptchaProcessed solveRecaptcha(ReCaptchaRequest reCaptchaRequest) throws AnticaptchaException {
		DebugHelper.setVerboseMode(true);

		DebugHelper.out("Trying to solve the Recaptcha by Anticaptcha", DebugHelper.Type.INFO);

		NoCaptchaProxyless api = new NoCaptchaProxyless();
		try {

			clientKey = configuracaoService.buscarPorParametro(ParametroConfiguracao.ANTICAPTCHA_CLIENT_KEY).getValor();

			api.setClientKey(clientKey);
			api.setWebsiteUrl(new URL(reCaptchaRequest.getPageUrl()));
			api.setWebsiteKey(reCaptchaRequest.getGoogleKey());
			
			if (!api.createTask()) {
				DebugHelper.out("API v2 send failed. " + api.getErrorMessage(), DebugHelper.Type.ERROR);

				throw new AnticaptchaException("Failed to solve the captcha.");

			} else if (!api.waitForResult()) {
				DebugHelper.out("Could not solve the captcha.", DebugHelper.Type.ERROR);

				throw new AnticaptchaException("Failed to solve the captcha.");

			} else {
				DebugHelper.out("Result: " + api.getTaskSolution(), DebugHelper.Type.SUCCESS);
				
				return new CaptchaProcessed(api.getTaskSolution().getGRecaptchaResponse(), true);
			}
		} catch (Exception e) {
			DebugHelper.out("Failed to solve the captcha.", DebugHelper.Type.ERROR);

			throw new AnticaptchaException("Failed to solve the captcha.", e);
		}
	}
}
