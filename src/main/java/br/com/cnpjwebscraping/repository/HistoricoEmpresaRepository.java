package br.com.cnpjwebscraping.repository;

import br.com.cnpjwebscraping.domain.Consulta;
import br.com.cnpjwebscraping.domain.HistoricoEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoEmpresaRepository extends JpaRepository<HistoricoEmpresa, Long> {
}
