package br.com.cnpjwebscraping.repository;

import br.com.cnpjwebscraping.domain.LogConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogConsultaRepository extends JpaRepository<LogConsulta, Long> {
}
