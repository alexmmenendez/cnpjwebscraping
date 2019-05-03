package br.com.cnpjwebscraping.api;

import br.com.cnpjwebscraping.hardcoded.ResponseError;
import br.com.cnpjwebscraping.output.ResponseErrorOutput;
import br.com.cnpjwebscraping.solver.anticaptcha.properties.AnticaptchaProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/cnpjwebscraping/config")
public class ConfigAPI {

    @Autowired
    private AnticaptchaProperties properties;

    @PostMapping(value = "/anticaptcha", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> nova(@Valid String clientKey) {

        if (!StringUtils.isNotBlank(clientKey)) {
            return new ResponseEntity<>(new ResponseErrorOutput(ResponseError.INVALID_FIELD_VALUE, "clientKey"), HttpStatus.OK);
        }

        properties.setClientKey(clientKey);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
