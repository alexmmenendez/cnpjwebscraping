package br.com.cnpjwebscraping.service.domain;


import br.com.cnpjwebscraping.domain.Consulta;
import br.com.cnpjwebscraping.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    public Consulta salvar (Consulta consulta) {
        return consultaRepository.save(consulta);
    }

}
