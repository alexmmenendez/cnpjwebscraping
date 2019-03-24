package br.com.cnpjwebscraping.solver.request;

public class ReCaptchaRequest {
	
	private String googleKey;
	private String pageUrl;
	
	public ReCaptchaRequest() {
		
	}
	
	public ReCaptchaRequest(String googleKey, String pageUrl) {
		this.googleKey = googleKey;
		this.pageUrl = pageUrl;
	}

	public String getGoogleKey() {
		return googleKey;
	}

	public void setGoogleKey(String googleKey) {
		this.googleKey = googleKey;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	
	
}