package br.com.cnpjwebscraping.solver;

public class CaptchaProcessed {

	private String value;
	
	private boolean solved = false;
	
	public CaptchaProcessed() {
		
	}
	
	public CaptchaProcessed(String value, Boolean solved) {
		this.value = value;
		this.solved = solved;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isSolved() {
		return solved;
	}

	public void setSolved(boolean solved) {
		this.solved = solved;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CaptchaProcessed [value=");
		builder.append(value);
		builder.append(", solved=");
		builder.append(solved);
		builder.append("]");
		return builder.toString();
	}

}