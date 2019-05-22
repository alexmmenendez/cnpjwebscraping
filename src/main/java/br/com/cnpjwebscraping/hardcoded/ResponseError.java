package br.com.cnpjwebscraping.hardcoded;

public enum ResponseError {
	
	UNAUTHORIZED("Invalid token.", "1"),
	FORBIDDEN("Access denied.", "2"),
	NOT_NUMERIC("Value not numeric.", "3"),
	NOT_FOUND("Not found.", "4"),
	FILE_NOT_AVAILABLE("Could not retrieve file from server.", "5"),
	FIELD_MISSING("Field(s) missing", "6"),
	OBJECT_NOT_EXIST("Combination of requested parameters does not match an existing worker.", "7"),
	PARAMETER_MISSING("Parameter(s) missing", "8"),
	INVALID_FIELD_VALUE("Invalid field value", "9"),
	DOCUMENT_INACTIVE("Worker inativo", "10");
	
	private String message;
	private String code;
	
	private ResponseError(String message, String code) {
		this.setMessage(message);
		this.setCode(code);
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
}
