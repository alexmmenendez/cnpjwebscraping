package br.com.cnpjwebscraping.service.domain;

import br.com.cnpjwebscraping.domain.Cidade;
import br.com.cnpjwebscraping.domain.Empresa;
import br.com.cnpjwebscraping.hardcoded.CNPJDados;
import br.com.cnpjwebscraping.hardcoded.ConsultaStatus;
import br.com.cnpjwebscraping.repository.EmpresaRepository;
import br.com.cnpjwebscraping.util.FormatadorString;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private LogConsultaService logService;

    public Empresa salvar(Empresa empresa) {

        empresa = empresaRepository.save(empresa);

        logService.salvar(empresa);

        return empresa;
    }

    public Empresa buscarPorCNPJ(String CNPJ) {
        return empresaRepository.findOneByCnpj(CNPJ);
    }

    public List<Empresa> buscarEmpresasSemInscricaoEstadual() {
        return empresaRepository.findAllByInscricaoEstadualIsNullAndCidadeIsNotNull();
    }

    public List<Empresa> buscarPorStatus(ConsultaStatus status) {
        return empresaRepository.findAllByStatus(status);
    }

    public List<Empresa> buscarPorStatusEm(List<ConsultaStatus> status) {
        return empresaRepository.findAllByStatusIn(status);
    }

    public void setDados(Empresa empresa, Document document) throws ParseException {

        Map<CNPJDados, String> dados = new HashMap<>();

        System.out.println(document.html());

        List<Element> elements = document.select("font");

        for (int i = 0; i < elements.size(); i++) {
            CNPJDados cnpjDados = CNPJDados.getEnum(elements.get(i).text());

            if (cnpjDados != null) {
                dados.put(cnpjDados, elements.get(i+1).text());
            }
        }

        empresa.setParentesco(document.select("b").get(3).text().trim());

        empresa.setBairro(dados.get(CNPJDados.BAIRRO_LOGRADOURO));

        empresa.setCep(dados.get(CNPJDados.CEP));

        empresa.setComplementoLogradouro(dados.get(CNPJDados.COMPLEMENTO_LOGRADOURO));

        empresa.setLogradouro(dados.get(CNPJDados.LOGRADOURO));

        empresa.setNumeroLogradouro(dados.get(CNPJDados.NUMERO_LOGRADOURO));

        empresa.setDataAbertura(new SimpleDateFormat("dd/MM/yyyy").parse(dados.get(CNPJDados.DATA_ABERTURA)));

        empresa.setSituacaoCadastral(dados.get(CNPJDados.SITUACAO_CADASTRAL));

        empresa.setRazaoSocial(dados.get(CNPJDados.NOME_EMPRESARIAL));

        String url = dados.get(CNPJDados.UF).toLowerCase().toLowerCase() + "-";

        url = url + StringUtils.replaceAll(FormatadorString.removerAcentos(dados.get(CNPJDados.MUNICIPIO)).toLowerCase().trim(), " ", "-");

        Cidade cidade = cidadeService.buscarPelaUrl(url);

        empresa.setCidade(cidade);

    }


}
