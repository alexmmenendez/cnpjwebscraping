package br.com.cnpjwebscraping.service.domain;

import br.com.cnpjwebscraping.domain.EmpresaScraping;
import br.com.cnpjwebscraping.repository.EmpresaScrapingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaScrapingService {

    @Autowired
    private EmpresaScrapingRepository EmpresaScrapingRepository;

    public EmpresaScraping salvar(EmpresaScraping empresa) {
        return EmpresaScrapingRepository.save(empresa);
    }

}
