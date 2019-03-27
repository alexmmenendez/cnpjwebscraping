package br.com.cnpjwebscraping.controller;

import br.com.cnpjwebscraping.domain.Consulta;
import br.com.cnpjwebscraping.domain.Empresa;
import br.com.cnpjwebscraping.hardcoded.ConsultaStatus;
import br.com.cnpjwebscraping.hardcoded.ResponseError;
import br.com.cnpjwebscraping.input.wrapper.ConsultaInputWrapper;
import br.com.cnpjwebscraping.output.ResponseErrorOutput;
import br.com.cnpjwebscraping.service.domain.ConsultaService;
import br.com.cnpjwebscraping.service.domain.EmpresaService;
import br.com.cnpjwebscraping.util.FormatadorString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.tree.FormalTypeParameter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/cnpj/consulta")
public class ConsultaAPI {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private ConsultaService consultaService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> nova(@Valid @RequestBody ConsultaInputWrapper consultaInputWrapper, BindingResult bindingResult, HttpServletRequest request) {

        //Verifica erros do binding
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new ResponseErrorOutput(ResponseError.PARAMETER_MISSING, bindingResult), HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok().body(String.class);
        }

/*        Empresa empresa = new Empresa();

        empresa.setCnpj(FormatadorString.removePontuacao(consultaInputWrapper.getEmpresaInput().getCnpj()));

        empresa = empresaService.salvar(empresa);

        Consulta consulta = new Consulta();

        consulta.setDataAbertura(new Date());
        consulta.setStatus(ConsultaStatus.NOVA);
        consulta.setEmpresa(empresa);*/
    }

}
