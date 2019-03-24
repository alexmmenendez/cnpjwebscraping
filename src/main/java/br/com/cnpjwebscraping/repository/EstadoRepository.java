package br.com.cnpjwebscraping.repository;

import br.com.cnpjwebscraping.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

	Optional<Estado> findOneByUfIgnoreCase(String uf);

	Optional<Estado> findOneByUfIgnoreCaseOrUrlIgnoreCase(String uf, String string);

}
