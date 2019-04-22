package br.com.cnpjwebscraping.service.domain;


import br.com.cnpjwebscraping.domain.Consulta;
import br.com.cnpjwebscraping.hardcoded.ConsultaStatus;
import br.com.cnpjwebscraping.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    public Consulta salvar (Consulta consulta) {
        return consultaRepository.save(consulta);
    }

    public List<Consulta> buscarPorStatus(ConsultaStatus status) {
        return consultaRepository.findAllByStatus(status);
    }

    public List<Consulta> buscarPorStatusEm(List<ConsultaStatus> status) {
        return consultaRepository.findAllByStatusIn(status);
    }

    public Consulta buscarPeloTicket(String ticket) {
        return consultaRepository.findOneByTicket(UUID.fromString(ticket));
    }

}
