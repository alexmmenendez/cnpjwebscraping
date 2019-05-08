package br.com.cnpjwebscraping.job;

import br.com.cnpjwebscraping.domain.Cidade;
import br.com.cnpjwebscraping.domain.Consulta;
import br.com.cnpjwebscraping.domain.Empresa;
import br.com.cnpjwebscraping.hardcoded.CNPJDados;
import br.com.cnpjwebscraping.hardcoded.ConsultaStatus;
import br.com.cnpjwebscraping.logger.DebugHelper;
import br.com.cnpjwebscraping.service.domain.CidadeService;
import br.com.cnpjwebscraping.service.domain.ConsultaService;
import br.com.cnpjwebscraping.service.domain.EmpresaService;
import br.com.cnpjwebscraping.service.worker.receitafederal.CNPJServiceWorker;
import br.com.cnpjwebscraping.service.worker.receitafederal.ServiceWorkerResponse;
import br.com.cnpjwebscraping.service.worker.sintegra.SintegraServiceWorker;
import br.com.cnpjwebscraping.service.worker.sintegra.factory.SintegraServiceWorkerFactory;
import br.com.cnpjwebscraping.service.worker.sintegra.response.SintegraServiceWorkerResponse;
import br.com.cnpjwebscraping.util.FormatadorString;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ScheduledTaks {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private CNPJServiceWorker cnpjServiceWorker;

    @Autowired
    private CidadeService cidadeService;

/*    @Scheduled(cron = "0 0/2 * * * ?")
    public void jobConsultaSintegra() {
        List<Empresa> empresas = empresaService.buscarEmpresasSemInscricaoEstadual();

        for (Empresa empresa : empresas) {
            SintegraServiceWorker worker = SintegraServiceWorkerFactory.identificarWorker(empresa);

            if (worker != null) {
                System.out.println(worker.getClass().getSimpleName());

                try {
                    SintegraServiceWorkerResponse response = worker.consultar(empresa.getCnpj());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

    @Scheduled(cron = "0 0/2 * * * ?")
    public void jobConsulta() {
        List<Consulta> consultas = consultaService.buscarPorStatusEm(Arrays.asList(ConsultaStatus.NOVA, ConsultaStatus.FALHA));

        ServiceWorkerResponse serviceWorkerResponse;

        for (Consulta consulta: consultas) {

            consulta.setStatus(ConsultaStatus.CONSULTANDO_RECEITA_FEDERAL);

            consultaService.salvar(consulta);
        }

        for (Consulta consulta : consultas) {
            try {
                serviceWorkerResponse = cnpjServiceWorker.consultar(consulta);

                Empresa empresa = consulta.getEmpresa();

                setDados(empresa, serviceWorkerResponse.getDocument());

                empresaService.salvar(empresa);

                consulta.setDataFinalizacao(new Date());

                consulta.setStatus(ConsultaStatus.CONCLUIDO);

                consultaService.salvar(consulta);
            } catch (Exception e) {
                e.printStackTrace();

                consulta.setStatus(ConsultaStatus.FALHA);

                consultaService.salvar(consulta);
            }
        }

    }

    public void setDados(Empresa empresa, Document document) throws ParseException {

        Map<CNPJDados, String> dados = new HashMap<>();

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
