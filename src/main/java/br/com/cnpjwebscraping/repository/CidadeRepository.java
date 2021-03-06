package br.com.cnpjwebscraping.repository;

import br.com.cnpjwebscraping.domain.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

    Cidade findByUrl(String url);
}
