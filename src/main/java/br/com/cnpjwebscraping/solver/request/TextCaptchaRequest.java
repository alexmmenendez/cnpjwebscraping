package br.com.cnpjwebscraping.solver.request;

import java.io.File;

public class TextCaptchaRequest {
	
	private File file;
	private String instructions;
	
	public TextCaptchaRequest() {
		
	}
	
	public TextCaptchaRequest(String path) {
		this.file = new File(path);
	}
	
	public TextCaptchaRequest(String path, String instructions) {
		this.file = new File(path);
		this.instructions = instructions;
	}
	
	public TextCaptchaRequest(File file) {
		this.file = file;
	}
	
	public TextCaptchaRequest(File file, String instructions) {
		this.file = file;
		this.instructions = instructions;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TextCaptchaRequest [file=");
		builder.append(file);
		builder.append(", instructions=");
		builder.append(instructions);
		builder.append("]");
		return builder.toString();
	}
	
}