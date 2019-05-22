package br.com.cnpjwebscraping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CnpjwebscrapingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CnpjwebscrapingApplication.class, args);
	}

}
