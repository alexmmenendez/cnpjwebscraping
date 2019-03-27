package br.com.cnpjwebscraping;

import br.com.cnpjwebscraping.domain.Empresa;
import br.com.cnpjwebscraping.solver.anticaptcha.Anticaptcha;
import br.com.cnpjwebscraping.solver.request.ReCaptchaRequest;
import br.com.cnpjwebscraping.util.TrustUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class Lab {

	public static void main(String[] args) throws Exception {

		Empresa empresa = setDados(consultaCnpj());

		System.out.println(empresa.toString());

	}

	private static Document consultaCnpj() throws Exception {

		String URL_BASE = "https://www.receita.fazenda.gov.br/PessoaJuridica/CNPJ/cnpjreva/";

		TrustUtil.setTrustAllCerts();

		Connection.Response response =
				Jsoup.connect(URL_BASE + "Cnpjreva_Solicitacao2.asp")
						.execute();

		Map<String, String> cookies = response.cookies();

		String googleKey = response.parse().select(".g-recaptcha").attr("data-sitekey");
		String recaptcha = new Anticaptcha().solve(new ReCaptchaRequest(googleKey, URL_BASE + "Cnpjreva_Solicitacao2.asp")).getValue();

		Map<String, String> data = new HashMap<>();
		data.put("origem", "comprovante");
		data.put("cnpj", "62232889001838");
		data.put("g-recaptcha-response", recaptcha);
		data.put("submit1", "Consultar");
		data.put("search_type", "cnpj");

		response = Jsoup.connect(URL_BASE + "valida_recaptcha.asp")
				.cookies(cookies)
				.data(data)
				.method(Connection.Method.POST)
				.followRedirects(false)
				.execute();

		String location = response.header("Location");

		response = Jsoup.connect(URL_BASE + location)
				.cookies(cookies)
				.method(Connection.Method.GET)
				.followRedirects(false)
				.execute();

		location = response.header("Location");

		response = Jsoup.connect(URL_BASE + location)
				.cookies(cookies)
				.method(Connection.Method.GET)
				.followRedirects(false)
				.execute();

		location = response.header("Location");

		Document document = Jsoup.parse((response = Jsoup
				.connect(URL_BASE + location)
				.method(Connection.Method.GET)
				.cookies(cookies)
				.execute()).bodyStream(), "ISO-8859-1", "");

		document = Jsoup.parse(document.select(".conteudo").html());
		document.select("img").first().attr("src", "https://www.receita.fazenda.gov.br/PessoaJuridica/CNPJ/cnpjreva/images/brasao2.gif");

		return document;
	}


	private static Empresa setDados(Document document) {
		Empresa empresa = new Empresa();

		Elements elements = document.select("b");

		empresa.setParentesco(elements.get(3).html());

		empresa.setRazaoSocial(elements.get(6).html());

		return empresa;
	}

}