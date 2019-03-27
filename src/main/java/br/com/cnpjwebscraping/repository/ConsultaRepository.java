package br.com.cnpjwebscraping.repository;

import br.com.cnpjwebscraping.domain.Consulta;
import br.com.cnpjwebscraping.hardcoded.ConsultaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    List<Consulta> findAllByStatus(ConsultaStatus status);

    Consulta findOneByTicket(UUID ticket);

}
