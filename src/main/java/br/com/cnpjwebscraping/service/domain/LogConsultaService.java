package br.com.cnpjwebscraping.service.domain;

import br.com.cnpjwebscraping.domain.Cidade;
import br.com.cnpjwebscraping.domain.Empresa;
import br.com.cnpjwebscraping.domain.LogConsulta;
import br.com.cnpjwebscraping.repository.CidadeRepository;
import br.com.cnpjwebscraping.repository.LogConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogConsultaService {

    @Autowired
    private LogConsultaRepository logConsultaRepository;

    public void salvar(Empresa empresa) {
        logConsultaRepository.save(new LogConsulta(new Date(), empresa.getStatus(), empresa));
    }

}
