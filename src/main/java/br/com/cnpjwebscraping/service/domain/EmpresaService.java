package br.com.cnpjwebscraping.service.domain;

import br.com.cnpjwebscraping.domain.Empresa;
import br.com.cnpjwebscraping.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository EmpresaRepository;

    public Empresa salvar (Empresa empresa) {
        return EmpresaRepository.save(empresa);
    }

}
