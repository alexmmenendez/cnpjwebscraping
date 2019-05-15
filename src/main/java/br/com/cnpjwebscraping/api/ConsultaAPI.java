package br.com.cnpjwebscraping.api;

import br.com.cnpjwebscraping.domain.Empresa;
import br.com.cnpjwebscraping.hardcoded.ConsultaStatus;
import br.com.cnpjwebscraping.hardcoded.ResponseError;
import br.com.cnpjwebscraping.input.wrapper.ConsultaInputWrapper;
import br.com.cnpjwebscraping.input.wrapper.SimpleConsultaInputWrapper;
import br.com.cnpjwebscraping.output.ResponseErrorOutput;
import br.com.cnpjwebscraping.output.wrapper.ConsultaOutputWrapper;
import br.com.cnpjwebscraping.service.domain.EmpresaService;
import br.com.cnpjwebscraping.service.worker.trt.TRT02ServiceWorker;
import br.com.cnpjwebscraping.util.FormatadorString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

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
    private TRT02ServiceWorker trt02ServiceWorker;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> nova(@Valid @RequestBody ConsultaInputWrapper consultaInputWrapper, BindingResult bindingResult) {

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

        return ResponseEntity.ok().body(new ConsultaOutputWrapper(empresa));

    }

    @PostMapping(value = "/cpf-cnpj", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> consultar(@RequestBody SimpleConsultaInputWrapper consulta) {

        String cpfcnpj = FormatadorString.removePontuacao(consulta.getCpfcnpj());

        if (cpfcnpj.length() == 14) {
            Empresa empresa = empresaService.buscarPorCNPJ(cpfcnpj);

            if (empresa == null) {

                empresa = new Empresa();

                empresa.setConsultaDataCriacao(new Date());

                empresa.setCnpj(cpfcnpj);

                empresa.setStatus(ConsultaStatus.NOVA);

                empresaService.salvar(empresa);

                try {
                    String nomeRazaoSocial = trt02ServiceWorker.consultaNomeCompletoRazaoSocialPeloCPFCNPJ(cpfcnpj);

                    empresa.setRazaoSocial(nomeRazaoSocial);

                    empresa = empresaService.salvar(empresa);

                    return ResponseEntity.ok().body(new ConsultaOutputWrapper(empresa));

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                return ResponseEntity.ok().body(new ConsultaOutputWrapper(empresa));
            }

        } else {

            try {
                String nome = trt02ServiceWorker.consultaNomeCompletoRazaoSocialPeloCPFCNPJ(cpfcnpj);

                Empresa empresa = new Empresa();
                empresa.setRazaoSocial(nome);

                return ResponseEntity.ok().body(new ConsultaOutputWrapper(empresa));

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body("error");
            }
        }
    }
}
