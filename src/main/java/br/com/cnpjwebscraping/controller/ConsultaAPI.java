package br.com.cnpjwebscraping.controller;

import br.com.cnpjwebscraping.domain.Cidade;
import br.com.cnpjwebscraping.domain.Consulta;
import br.com.cnpjwebscraping.domain.Empresa;
import br.com.cnpjwebscraping.hardcoded.ConsultaStatus;
import br.com.cnpjwebscraping.hardcoded.ResponseError;
import br.com.cnpjwebscraping.input.wrapper.ConsultaInputWrapper;
import br.com.cnpjwebscraping.output.ResponseErrorOutput;
import br.com.cnpjwebscraping.output.wrapper.ConsultaOutputWrapper;
import br.com.cnpjwebscraping.service.domain.CidadeService;
import br.com.cnpjwebscraping.service.domain.ConsultaService;
import br.com.cnpjwebscraping.service.domain.EmpresaService;
import br.com.cnpjwebscraping.util.FormatadorString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/cnpjwebscraping/consulta")
public class ConsultaAPI {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private CidadeService cidadeService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> nova(@Valid @RequestBody ConsultaInputWrapper consultaInputWrapper, BindingResult bindingResult, HttpServletRequest request) {

        //Verifica erros do binding
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new ResponseErrorOutput(ResponseError.PARAMETER_MISSING, bindingResult), HttpStatus.BAD_REQUEST);
        }

        Cidade cidade = cidadeService.buscarPelaUrl(consultaInputWrapper.getEmpresa().getUrlCidade());

        String cnpj = FormatadorString.removePontuacao((consultaInputWrapper.getEmpresa().getCnpj()));

        Empresa empresa = empresaService.buscarPorCNPJ(cnpj);

        if (empresa == null) {
            empresa.setCnpj(cnpj);

            empresa.setCidade(cidade);

            try {
                empresa = empresaService.salvar(empresa);
            } catch (DataIntegrityViolationException e) {
                e.printStackTrace();
            }
        }

        Consulta consulta = new Consulta();

        consulta.setDataAbertura(new Date());
        consulta.setStatus(ConsultaStatus.NOVA);
        consulta.setEmpresa(empresa);

        consulta = consultaService.salvar(consulta);

        return ResponseEntity.accepted().body(new ConsultaOutputWrapper(consulta));

    }

    @GetMapping("/{ticket}")
    public ResponseEntity<?> getEmpresaPeloTicket(@PathVariable("ticket") String ticket, HttpServletRequest request) {

        Consulta consulta = consultaService.buscarPeloTicket(ticket);

        if (consulta == null) {
            return new ResponseEntity<>(new ResponseErrorOutput(ResponseError.NOT_FOUND), HttpStatus.OK);
        }

        return ResponseEntity.ok().body(new ConsultaOutputWrapper(consulta));

    }

}
