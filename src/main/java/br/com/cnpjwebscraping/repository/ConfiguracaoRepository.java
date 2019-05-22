package br.com.cnpjwebscraping.repository;

import br.com.cnpjwebscraping.domain.Configuracao;
import br.com.cnpjwebscraping.hardcoded.ParametroConfiguracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracaoRepository extends JpaRepository<Configuracao, Long> {

	Configuracao findOneByParametro(ParametroConfiguracao parametro);
	
}