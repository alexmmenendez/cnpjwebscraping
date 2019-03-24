package br.com.cnpjwebscraping.solver.request;

public class ImageToTextTask {

	private String type = "ImageToTextTask";
	
	private String body;
	
	private Boolean phrase = false;
	
	private Boolean Case  = false;
	
	private Integer numeric  = 0;
	
	private Boolean math = false;
	
	private Integer minLength = 0;
	
	private Integer maxLength = 0;
	
	public ImageToTextTask(String body) {
		this.body = body;
	}

	public ImageToTextTask() {
	}
	
	public ImageToTextTask(Boolean math, Integer numeric){
		this.math = math;
		this.numeric = numeric;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Boolean getPhrase() {
		return phrase;
	}

	public void setPhrase(Boolean phrase) {
		this.phrase = phrase;
	}

	public Boolean getCase() {
		return Case;
	}

	public void setCase(Boolean case1) {
		Case = case1;
	}

	public Integer getNumeric() {
		return numeric;
	}

	public void setNumeric(Integer numeric) {
		this.numeric = numeric;
	}

	public Boolean getMath() {
		return math;
	}

	public void setMath(Boolean math) {
		this.math = math;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	};
	
	}
