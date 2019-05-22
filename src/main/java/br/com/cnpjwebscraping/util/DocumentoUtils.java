package br.com.cnpjwebscraping.util;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class DocumentoUtils {

	public static boolean isCPFValid(String cpf) {
		try {
			cpf = FormatadorString.removePontuacao(cpf);
			CPFValidator cpfValidator = new CPFValidator();
			cpfValidator.assertValid(cpf);
			
			return true;
			
		} catch (InvalidStateException e) {
			return false;
		}
	}
	
	public static boolean isCNPJValid(String cnpj) {
		try {
			cnpj = FormatadorString.removePontuacao(cnpj);
			CNPJValidator cnpjValidator = new CNPJValidator();
			cnpjValidator.assertValid(cnpj);
			
			return true;
			
		} catch (InvalidStateException e) {
			return false;
		}
	}
}
