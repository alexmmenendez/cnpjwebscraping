package br.com.cnpjwebscraping.solver.request;

public class ImageCaptchaToTextRequest {
	
	private String clientKey;
	
	private ImageToTextTask task;

	public ImageCaptchaToTextRequest(String key) {
		this.clientKey = key;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

	public ImageToTextTask getTask() {
		return task;
	}

	public void setTask(ImageToTextTask task) {
		this.task = task;
	}
	
	
}
