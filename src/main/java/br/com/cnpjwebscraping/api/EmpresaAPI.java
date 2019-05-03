package br.com.cnpjwebscraping.api;

import br.com.cnpjwebscraping.domain.Empresa;
import br.com.cnpjwebscraping.hardcoded.ResponseError;
import br.com.cnpjwebscraping.output.ResponseErrorOutput;
import br.com.cnpjwebscraping.output.wrapper.EmpresaOutputWrapper;
import br.com.cnpjwebscraping.service.domain.EmpresaService;
import br.com.cnpjwebscraping.util.FormatadorString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/cnpjwebscraping/cnpj")
public class EmpresaAPI {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/{cnpj}")
    public ResponseEntity<?> getEmpresaPeloTicket(@PathVariable("cnpj") String cnpj, HttpServletRequest request) {

        Empresa empresa = empresaService.buscarPorCNPJ(FormatadorString.removePontuacao(cnpj));

        if (empresa == null) {
            return new ResponseEntity<>(new ResponseErrorOutput(ResponseError.NOT_FOUND), HttpStatus.OK);
        }

        return ResponseEntity.ok().body(new EmpresaOutputWrapper(empresa));
    }
}
