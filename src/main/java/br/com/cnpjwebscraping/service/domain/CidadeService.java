package br.com.cnpjwebscraping.service.domain;

import br.com.cnpjwebscraping.domain.Cidade;
import br.com.cnpjwebscraping.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public Cidade buscarPelaUrl(String url) {
        return cidadeRepository.findByUrl(url);
    }

}
