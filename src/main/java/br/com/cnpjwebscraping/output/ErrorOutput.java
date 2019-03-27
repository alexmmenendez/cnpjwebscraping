package br.com.cnpjwebscraping.output;

import br.com.cnpjwebscraping.hardcoded.ResponseError;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorOutput {
	
	private Long code;
	private String message;
	private String parameter;
	private Map<String, String> fields = new HashMap<>();
	
	public ErrorOutput(ResponseError responseError) {
		this.code = Long.parseLong(responseError.getCode());
		this.message = responseError.getMessage();
	}
	
	public ErrorOutput(ResponseError responseError, String parameter) {
		this.code = Long.parseLong(responseError.getCode());
		this.message = responseError.getMessage();
		this.parameter = parameter;
	}
	
	public ErrorOutput(ResponseError responseError, BindingResult bindingResult) {
		this.code = Long.parseLong(responseError.getCode());
		this.message = responseError.getMessage();
		Map<String, String> erros = new HashMap<>();
		bindingResult.getAllErrors().forEach(
			error -> {
				if(error instanceof FieldError) {
					erros.put(((FieldError)error).getField(), error.getDefaultMessage());
				} else if(error instanceof ObjectError) {
					erros.put(error.getObjectName(), error.getDefaultMessage());
				}
			}
		);
		this.setFields(erros);
		
	}
	
	public Long getCode() {
		return code;
	}
	
	public void setCode(Long code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getParameter() {
		return parameter;
	}
	
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	public Map<String, String> getFields() {
		return fields;
	}
	
	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}
}
