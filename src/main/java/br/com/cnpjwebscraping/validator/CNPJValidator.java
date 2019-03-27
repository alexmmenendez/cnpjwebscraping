package br.com.cnpjwebscraping.validator;

import br.com.cnpjwebscraping.util.DocumentoUtils;
import br.com.cnpjwebscraping.validator.annotation.CNPJ;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CNPJValidator implements ConstraintValidator<CNPJ, String> {

	@Override
	public void initialize(CNPJ constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return DocumentoUtils.isCNPJValid(value);
	}
}
