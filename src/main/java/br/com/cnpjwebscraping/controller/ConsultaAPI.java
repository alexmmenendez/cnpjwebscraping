package br.com.cnpjwebscraping.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/cnpj/consulta", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ConsultaAPI {



}
