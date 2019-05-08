package br.com.cnpjwebscraping.service.domain;

import br.com.cnpjwebscraping.domain.Cidade;
import br.com.cnpjwebscraping.domain.Estado;
import br.com.cnpjwebscraping.repository.CidadeRepository;
import br.com.cnpjwebscraping.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado buscarPelaUF(String uf) {
        return estadoRepository.findOneByUf(uf);
    }

}
