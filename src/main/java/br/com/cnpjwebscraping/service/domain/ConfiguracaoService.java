package br.com.cnpjwebscraping.service.domain;

import br.com.cnpjwebscraping.domain.Configuracao;
import br.com.cnpjwebscraping.hardcoded.ParametroConfiguracao;
import br.com.cnpjwebscraping.repository.ConfiguracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracaoService {

    @Autowired
    private ConfiguracaoRepository repository;

    public Configuracao buscarPorParametro(ParametroConfiguracao parametro) {
        return repository.findOneByParametro(parametro);
    }
}
