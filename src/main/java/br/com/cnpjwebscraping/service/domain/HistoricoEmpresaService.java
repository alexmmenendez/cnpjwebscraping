package br.com.cnpjwebscraping.service.domain;

import br.com.cnpjwebscraping.domain.Empresa;
import br.com.cnpjwebscraping.domain.HistoricoEmpresa;
import br.com.cnpjwebscraping.repository.EmpresaRepository;
import br.com.cnpjwebscraping.repository.HistoricoEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoricoEmpresaService {

    @Autowired
    private HistoricoEmpresaRepository HistoricoEmpresaRepository;

    public HistoricoEmpresa salvar(HistoricoEmpresa empresa) {
        return HistoricoEmpresaRepository.save(empresa);
    }

}
