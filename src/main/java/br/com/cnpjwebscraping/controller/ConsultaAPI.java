package br.com.cnpjwebscraping.controller;

import br.com.cnpjwebscraping.domain.Consulta;
import br.com.cnpjwebscraping.domain.Empresa;
import br.com.cnpjwebscraping.domain.EmpresaScraping;
import br.com.cnpjwebscraping.hardcoded.ConsultaStatus;
import br.com.cnpjwebscraping.hardcoded.ResponseError;
import br.com.cnpjwebscraping.input.wrapper.ConsultaInputWrapper;
import br.com.cnpjwebscraping.output.ResponseErrorOutput;
import br.com.cnpjwebscraping.output.wrapper.ConsultaOutputWrapper;
import br.com.cnpjwebscraping.service.domain.CidadeService;
import br.com.cnpjwebscraping.service.domain.ConsultaService;
import br.com.cnpjwebscraping.service.domain.EmpresaService;
import br.com.cnpjwebscraping.service.domain.EmpresaScrapingService;
import br.com.cnpjwebscraping.util.FormatadorString;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
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
    private EmpresaScrapingService empresaScrapingService;

    @Autowired
    private CidadeService cidadeService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> nova(@Valid @RequestBody ConsultaInputWrapper consultaInputWrapper, BindingResult bindingResult, HttpServletRequest request) {

        //Verifica erros do binding
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new ResponseErrorOutput(ResponseError.PARAMETER_MISSING, bindingResult), HttpStatus.BAD_REQUEST);
        }

        String cnpj = FormatadorString.removePontuacao((consultaInputWrapper.getEmpresa().getCnpj()));

        Empresa empresa = empresaService.buscarPorCNPJ(cnpj);

        EmpresaScraping empresaScraping;

        if (empresa == null) {

            empresa = new Empresa();

            empresa.setCnpj(cnpj);

            empresa = empresaService.salvar(empresa);

            empresaScraping = new EmpresaScraping(empresa);

            empresaScraping = empresaScrapingService.salvar(empresaScraping);

        } else {
            empresaScraping = new EmpresaScraping(empresa);

            empresaScraping = empresaScrapingService.salvar(empresaScraping);
        }

        Consulta consulta = new Consulta();

        consulta.setDataAbertura(new Date());
        consulta.setStatus(ConsultaStatus.NOVA);
        consulta.setScraping(empresaScraping);

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

    @GetMapping(value = "/cpf-cnpj/{cnpj}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getNomeByCPF(@PathVariable("cnpj") String cnpj, HttpServletRequest request) {

        cnpj = FormatadorString.removePontuacao(cnpj);

        System.out.println("Requisição de: " + request.getRemoteHost() + " para o cnpj: " + cnpj);

        try {
            Connection.Response response =
                    Jsoup.connect("https://aplicacoes10.trtsp.jus.br/certidao_trabalhista_eletronica/public/index.php/index/nome-cpf")
                            .method(Connection.Method.POST)
                            .data("numero", cnpj)
                            .timeout(60000)
                            .postDataCharset("UTF-8")
                            .ignoreContentType(true)
                            .execute()
                            .bufferUp();

            if (StringUtils.equals(response.contentType(), MediaType.APPLICATION_JSON_VALUE)) {

                JSONObject jsonObject = new JSONObject(response.parse().select("body").html().replace("&amp;", "&"));

                return ResponseEntity.ok().body(jsonObject.toString());

            } else {
                throw new Exception("Error");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("error");
        }
    }

}
