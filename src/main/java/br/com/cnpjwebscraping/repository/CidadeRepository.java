package br.com.cnpjwebscraping.repository;

import br.com.cnpjwebscraping.domain.Cidade;
import br.com.cnpjwebscraping.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

    Optional<Cidade> findOneByEstadoAndNome(Estado estado, String nome);

    Optional<Cidade> findOneByUrlIgnoreCase(String url);

    Set<Cidade> findAllByEstado(Optional<Estado> estado);

}
