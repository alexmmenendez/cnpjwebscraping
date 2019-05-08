package br.com.cnpjwebscraping.service.domain;

import br.com.cnpjwebscraping.domain.Empresa;
import br.com.cnpjwebscraping.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public Empresa salvar(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public Empresa buscarPorCNPJ(String CNPJ) {
        return empresaRepository.findOneByCnpj(CNPJ);
    }

    public List<Empresa> buscarEmpresasSemInscricaoEstadual() {
        return empresaRepository.findAllByInscricaoEstadualIsNullAndCidadeIsNotNull();
    }


}
