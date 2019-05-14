package br.com.cnpjwebscraping.solver.deathbycaptchav2;

import br.com.cnpjwebscraping.hardcoded.ParametroConfiguracao;
import br.com.cnpjwebscraping.logger.DebugHelper;
import br.com.cnpjwebscraping.service.domain.ConfiguracaoService;
import br.com.cnpjwebscraping.solver.CaptchaProcessed;
import br.com.cnpjwebscraping.solver.CaptchaSolver;
import br.com.cnpjwebscraping.solver.deathbycaptchav2.client.Captcha;
import br.com.cnpjwebscraping.solver.deathbycaptchav2.client.HttpClient;
import br.com.cnpjwebscraping.solver.deathbycaptchav2.exception.DeathbycaptchaException;
import br.com.cnpjwebscraping.solver.deathbycaptchav2.client.Client;
import br.com.cnpjwebscraping.solver.request.ReCaptchaRequest;
import br.com.cnpjwebscraping.solver.request.TextCaptchaRequest;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DeathbycaptchaV2 implements CaptchaSolver {

	@Autowired
	private ConfiguracaoService configuracaoService;

	@Override
	public CaptchaProcessed solveImageCaptcha(TextCaptchaRequest textCaptchaRequest) throws DeathbycaptchaException {
		DebugHelper.out("Trying to solve the captcha by DeathByCaptcha", DebugHelper.Type.INFO);

		CaptchaProcessed captchaProcessed = new CaptchaProcessed();
/*

		String login = configuracaoService.buscarPorParametro(ParametroConfiguracao.DEATHBYCAPTCHA_LOGIN).getValor();

		String password = configuracaoService.buscarPorParametro(ParametroConfiguracao.DEATHBYCAPTCHA_PASSWORD).getValor();
*/

		Client client = new HttpClient("lopesprodrigo", "xsw2XSW@");
		client.isVerbose = true;

		try {
			try {
				DebugHelper.out("DeathByCaptcha balance: " + client.getBalance() + " US cents", DebugHelper.Type.INFO);

			} catch (Exception e) {
				DebugHelper.out("Failed fetching balance: " + e.toString(), DebugHelper.Type.ERROR);

				throw new DeathbycaptchaException(e);
			}

			Captcha captcha;
			try {
				captcha = client.decode(textCaptchaRequest.getFile(), 120);

			} catch (Exception e) {
				DebugHelper.out("Failed uploading Recaptcha", DebugHelper.Type.ERROR);

				throw new DeathbycaptchaException(e);
			}

			if (null != captcha) {
				DebugHelper.out("Captcha solved: " + captcha.text, DebugHelper.Type.SUCCESS);

				captchaProcessed.setSolved(true);
				captchaProcessed.setValue(captcha.text);

				return captchaProcessed;

			} else {
				throw new DeathbycaptchaException("Captcha solver failed");
			}

		} catch (Exception e) {
			DebugHelper.out("Captcha solver failed", DebugHelper.Type.ERROR);

			throw new DeathbycaptchaException(e);
		} finally {
			FileUtils.deleteQuietly(textCaptchaRequest.getFile());
		}
	}

	@Override
	public CaptchaProcessed solveRecaptcha(ReCaptchaRequest reCaptchaRequest) throws DeathbycaptchaException {

		DebugHelper.out("Trying to solve the Recaptcha by DeathByCaptcha", DebugHelper.Type.INFO);

		CaptchaProcessed captchaProcessed = new CaptchaProcessed();

		Client client = new HttpClient("lopesprodrigo", "xsw2XSW@");
		client.isVerbose = true;

		try {
			try {
				DebugHelper.out("DeathByCaptcha balance: " + client.getBalance() + " US cents", DebugHelper.Type.INFO);

			} catch (IOException e) {
				DebugHelper.out("Failed fetching balance: " + e.toString(), DebugHelper.Type.ERROR);

				throw new DeathbycaptchaException(e);
			}

			Captcha captcha;
			try {
				captcha = client.decode(null, null, reCaptchaRequest.getGoogleKey(), reCaptchaRequest.getPageUrl());

			} catch (InterruptedException | IOException e) {
				DebugHelper.out("Failed uploading Recaptcha", DebugHelper.Type.ERROR);

				throw new DeathbycaptchaException(e);
			}

			if (null != captcha) {

				DebugHelper.out("Recaptcha solved: " + captcha.text, DebugHelper.Type.SUCCESS);

				captchaProcessed.setSolved(true);
				captchaProcessed.setValue(captcha.text);

				return captchaProcessed;

			} else {
				throw new DeathbycaptchaException("Recaptcha solver failed");
			}

		} catch (Exception e) {
			DebugHelper.out("Recaptcha solver failed", DebugHelper.Type.ERROR);

			throw new DeathbycaptchaException(e);
		}

	}
	
}