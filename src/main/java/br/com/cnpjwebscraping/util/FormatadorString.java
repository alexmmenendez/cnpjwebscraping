package br.com.cnpjwebscraping.util;

import br.com.caelum.stella.format.CNPJFormatter;
import br.com.caelum.stella.format.CPFFormatter;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatadorString {

	public static String formatarId(Long id) {
		return StringUtils.leftPad(String.valueOf(id), 6, "0");
	}

	public static String formatarParaMoedaEmReais(BigDecimal valor) {
		return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valor).replace(" ", "");
	}
	
	public static String normalizar(String str) {
		
		str = Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		str = StringUtils.replaceAll(str, "'", "");
		str = StringUtils.replaceAll(str, "-", "");
		str = StringUtils.replaceAll(str, " ", "");
		
		return str.toLowerCase().trim();
	}

	public static String removerAcentos(String frase) {
		
		frase = Normalizer.normalize(frase, Normalizer.Form.NFD);
		frase = StringUtils.replaceAll(frase, "[^\\p{ASCII}]", "");
		frase = StringUtils.replaceAll(frase, "[\\s]{2,}", " ");
		frase = StringUtils.removeAll(frase, "[^a-zA-Z0-9\\sàáãâèéêìíîòóõôùúûçÀÁÃÂÈÉÊÌÍÎÒÓÕÔÙÚÛÇ.,;:_+%$*)(&/-]+");
		frase = StringUtils.replace(frase, "\n", " ");
		frase = StringUtils.replace(frase, "\t", " ");
		
		frase = StringUtils.trim(frase);

		return frase;
	}

	public static String removerAcentosEspacos(String frase) {

		frase = Normalizer.normalize(frase, Normalizer.Form.NFD);
		frase = StringUtils.replaceAll(frase, "[^\\p{ASCII}]", "");
		frase = StringUtils.removeAll(frase, "[\\s]");
		frase = StringUtils.removeAll(frase, "[^a-zA-Z0-9\\sàáãâèéêìíîòóõôùúûçÀÁÃÂÈÉÊÌÍÎÒÓÕÔÙÚÛÇ.,;:_+%$*)(&/-]+");
		frase = StringUtils.removeAll(frase, "\n");
		frase = StringUtils.removeAll(frase, "\t");

		return frase;
	}

	public static String formatarCPF(String CPF) throws Exception {
		if(StringUtils.isBlank(CPF)) {
			throw new Exception("CPF nulo");
		}

		try {
			CPF = StringUtils.removeAll(CPF, "[^\\d]+");
			CPF = StringUtils.trim(CPF);
			return new CPFFormatter().format(CPF);
		} catch (Exception e) {
			throw new Exception("CPF Inválido");
		}
	}
	
	public static String removePontuacao(String texto) {
		texto = StringUtils.removeAll(texto, "[^a-zA-Z0-9]+");
		return texto;
	}

	public static String formatarCnpj(String CNPJ) throws Exception {

		if(StringUtils.isBlank(CNPJ)) {
			throw new Exception("CNPJ nulo");
		}

		try {
			CNPJ = StringUtils.removeAll(CNPJ, "[^\\d]+");
			return new CNPJFormatter().format(CNPJ);
		} catch (Exception e) {
			throw new Exception("CNPJ Inválido");
		}
	}
	
	public static String formatarNIRF(String nirf) throws Exception {
		if(StringUtils.isBlank(nirf)) {
			return nirf;
		}
		
		try {
			nirf = StringUtils.removeAll(nirf, "[^\\d]+");
			nirf = StringUtils.trim(nirf);
			if (!StringUtils.isNumeric(nirf) || nirf.length() != 8) {
				throw new Exception("NIRF Inválido");
			}
			
			Pattern pattern = Pattern.compile("(\\d)(\\d{3})(\\d{3})(\\d)");
			Matcher matcher = pattern.matcher(nirf);
			if (matcher.matches()) {
				nirf = matcher.replaceAll("$1.$2.$3-$4");
				return nirf;
			}
		} catch (Exception e) {
			return null;
		}
		
		throw new Exception("NIRF Inválido");
	}
	
	public static String encodeURIComponent(String texto, String charset) throws UnsupportedEncodingException {
		texto = URLEncoder.encode(texto, charset);
		texto = StringUtils.replace(texto, "+", "%20");
		return texto;
	}

	public static String formatarData(String dataString) throws Exception {
		DateTimeFormatter formatter;
		LocalDate data = null;
		if(StringUtils.isNotBlank(dataString)) {
			try {
				if(!StringUtils.contains(dataString, "/")) {
					formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
					data = LocalDate.parse(dataString,formatter);
					
					formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					dataString = formatter.format(data);
				}
				
			} catch (Exception e) {
				throw new Exception("Data Inválida");
			}
		} else {
			return "";
		}
		return dataString;
		
	}
	
}