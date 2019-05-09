package br.com.cnpjwebscraping.api;

import br.com.cnpjwebscraping.domain.Empresa;
import br.com.cnpjwebscraping.hardcoded.ConsultaStatus;
import br.com.cnpjwebscraping.hardcoded.ResponseError;
import br.com.cnpjwebscraping.input.wrapper.ConsultaInputWrapper;
import br.com.cnpjwebscraping.output.ResponseErrorOutput;
import br.com.cnpjwebscraping.output.wrapper.ConsultaOutputWrapper;
import br.com.cnpjwebscraping.service.domain.CidadeService;
import br.com.cnpjwebscraping.service.domain.EmpresaService;
import br.com.cnpjwebscraping.service.worker.sintegra.SCSintegraServiceWorker;
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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/cnpjwebscraping/consulta")
public class ConsultaAPI {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private SCSintegraServiceWorker scSintegraServiceWorker;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> nova(@Valid @RequestBody ConsultaInputWrapper consultaInputWrapper, BindingResult bindingResult, HttpServletRequest request) {

        //Verifica erros do binding
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new ResponseErrorOutput(ResponseError.PARAMETER_MISSING, bindingResult), HttpStatus.BAD_REQUEST);
        }

        String cnpj = FormatadorString.removePontuacao((consultaInputWrapper.getEmpresa().getCnpj()));

        Empresa empresa = empresaService.buscarPorCNPJ(cnpj);

        if (empresa == null) {

            empresa = new Empresa();

            empresa.setConsultaDataCriacao(new Date());

            empresa.setCnpj(cnpj);

            empresa.setStatus(ConsultaStatus.NOVA);

            empresa = empresaService.salvar(empresa);

        }

        return ResponseEntity.accepted().body(new ConsultaOutputWrapper(empresa));

    }

    @PostMapping(value = "/cpf-cnpj", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> consultar(@Valid @RequestBody String cpfcnpj, Errors errors) {

        if (errors.hasErrors()) {

            return ResponseEntity.badRequest().body(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));

        }

        cpfcnpj = FormatadorString.removePontuacao(cpfcnpj);

        try {
            Connection.Response response =
                    Jsoup.connect("https://aplicacoes10.trtsp.jus.br/certidao_trabalhista_eletronica/public/index.php/index/nome-cpf")
                            .method(Connection.Method.POST)
                            .data("numero", cpfcnpj)
                            .timeout(60000)
                            .postDataCharset("UTF-8")
                            .ignoreContentType(true)
                            .execute()
                            .bufferUp();

            if (StringUtils.equals(response.contentType(), MediaType.APPLICATION_JSON_VALUE)) {

                JSONObject jsonObject = new JSONObject(response.parse().select("body").html().replace("&amp;", "&"));

                jsonObject.put("inscricaoEstadual", scSintegraServiceWorker.consultar(cpfcnpj).getInscricaoEstadual());

                return ResponseEntity.ok().body(jsonObject.toString());

            } else {
                throw new Exception("Error");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
