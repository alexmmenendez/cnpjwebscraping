package br.com.cnpjwebscraping.job;

import br.com.cnpjwebscraping.domain.Cidade;
import br.com.cnpjwebscraping.domain.Empresa;
import br.com.cnpjwebscraping.hardcoded.CNPJDados;
import br.com.cnpjwebscraping.hardcoded.ConsultaStatus;
import br.com.cnpjwebscraping.logger.DebugHelper;
import br.com.cnpjwebscraping.service.domain.CidadeService;
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

import java.util.*;

@Component
public class ScheduledTaks {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private CNPJServiceWorker cnpjServiceWorker;

    @Autowired
    private CidadeService cidadeService;

    @Scheduled(cron = "0 0/2 * * * ?")
    public void jobConsultaSintegra() {
        List<Empresa> empresas = empresaService.buscarPorStatusEm(Arrays.asList(ConsultaStatus.CONCLUIDA_RECEITA_FEDERAL, ConsultaStatus.FALHA_CONSULTA_SINTEGRA));

        for (Empresa empresa: empresas) {

            empresa.setStatus(ConsultaStatus.CONSULTANDO_SINTEGRA);

            empresaService.salvar(empresa);
        }

        for (Empresa empresa : empresas) {

            try {
                SintegraServiceWorker worker = SintegraServiceWorkerFactory.identificarWorker(empresa);

                if (worker != null) {
                    System.out.println(worker.getClass().getSimpleName());

                    SintegraServiceWorkerResponse response = worker.consultar(empresa.getCnpj());

                    String inscricaoEstadual = response.getInscricaoEstadual();

                    empresa.setInscricaoEstadual(inscricaoEstadual);

                    empresa.setStatus(ConsultaStatus.CONCLUIDA_SINTEGRA);

                    empresaService.salvar(empresa);

                }

            } catch (Exception e) {
                e.printStackTrace();

                empresa.setStatus(ConsultaStatus.FALHA_CONSULTA_SINTEGRA);

                empresaService.salvar(empresa);
            }
        }
    }

    @Scheduled(cron = "0 0/2 * * * ?")
    public void jobConsulta() {
        List<Empresa> empresas = empresaService.buscarPorStatusEm(Arrays.asList(ConsultaStatus.NOVA, ConsultaStatus.FALHA_CONSULTA_RECEITA_FEDERAL));

        ServiceWorkerResponse serviceWorkerResponse;

        for (Empresa empresa: empresas) {

            empresa.setStatus(ConsultaStatus.CONSULTANDO_RECEITA_FEDERAL);

            empresaService.salvar(empresa);
        }

        for (Empresa empresa : empresas) {
            try {
                serviceWorkerResponse = cnpjServiceWorker.consultar(empresa);

                empresaService.setDados(empresa, serviceWorkerResponse.getDocument());

                empresa.setConsultaDataFinalizacao(new Date());

                empresa.setStatus(ConsultaStatus.CONCLUIDA_RECEITA_FEDERAL);

                empresaService.salvar(empresa);
            } catch (Exception e) {
                e.printStackTrace();

                empresa.setStatus(ConsultaStatus.FALHA_CONSULTA_RECEITA_FEDERAL);

                empresaService.salvar(empresa);
            }
        }
    }
}
