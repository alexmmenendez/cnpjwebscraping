package br.com.cnpjwebscraping.repository;

import br.com.cnpjwebscraping.domain.Empresa;
import br.com.cnpjwebscraping.hardcoded.ConsultaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Empresa findOneByCnpj(String CNPJ);

    List<Empresa> findAllByInscricaoEstadualIsNullAndCidadeIsNotNull();

    List<Empresa> findAllByStatus(ConsultaStatus status);

    List<Empresa> findAllByStatusIn(List<ConsultaStatus> status);

}
