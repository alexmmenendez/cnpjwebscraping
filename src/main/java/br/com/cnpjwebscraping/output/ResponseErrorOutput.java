package br.com.cnpjwebscraping.output;

import br.com.cnpjwebscraping.hardcoded.ResponseError;
import org.springframework.validation.BindingResult;

public class ResponseErrorOutput {
	
	private ErrorOutput error;
	
	public ResponseErrorOutput(ResponseError responseError) {
		this.setError(new ErrorOutput(responseError));
	}
	
	public ResponseErrorOutput(ResponseError responseError, String parameter) {
		this.setError(new ErrorOutput(responseError, parameter));
	}
	
	public ResponseErrorOutput(ResponseError responseError, BindingResult bindingResult) {
		this.setError(new ErrorOutput(responseError, bindingResult));
	}
	
	public ErrorOutput getError() {
		return error;
	}
	
	public void setError(ErrorOutput error) {
		this.error = error;
	}
}
