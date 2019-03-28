package br.com.cnpjwebscraping.job;

import br.com.cnpjwebscraping.domain.Consulta;
import br.com.cnpjwebscraping.domain.Empresa;
import br.com.cnpjwebscraping.domain.HistoricoEmpresa;
import br.com.cnpjwebscraping.hardcoded.ConsultaStatus;
import br.com.cnpjwebscraping.service.domain.ConsultaService;
import br.com.cnpjwebscraping.service.domain.EmpresaService;
import br.com.cnpjwebscraping.service.domain.HistoricoEmpresaService;
import br.com.cnpjwebscraping.service.worker.CNPJServiceWorker;
import br.com.cnpjwebscraping.service.worker.ServiceWorkerResponse;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTaks {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private HistoricoEmpresaService historicoEmpresaService;

    @Autowired
    private CNPJServiceWorker cnpjServiceWorker;

    @Scheduled(cron = "0 0/2 * * * ?")
    public void jobConsulta() throws Exception {
        List<Consulta> consultas = consultaService.buscarPorStatus(ConsultaStatus.NOVA);

        System.out.println("Passou - " + new Date());

        ServiceWorkerResponse serviceWorkerResponse;

        for (Consulta consulta: consultas) {

            consulta.setStatus(ConsultaStatus.PROCESSANDO);

            consulta = consultaService.salvar(consulta);
        }

        for (Consulta consulta : consultas) {
            try {
                serviceWorkerResponse = cnpjServiceWorker.consultar(consulta);

                Document document = serviceWorkerResponse.getDocument();

                HistoricoEmpresa historicoEmpresa = consulta.getHistorico();

                Empresa empresa = historicoEmpresa.getEmpresa();

                empresa = setDados(empresa, document);

                empresa = empresaService.salvar(empresa);

                historicoEmpresa.setDados(empresa);

                historicoEmpresa = historicoEmpresaService.salvar(historicoEmpresa);

                consulta.setHistorico(historicoEmpresa);

                consulta.setDataFinalizacao(new Date());

                consulta.setStatus(ConsultaStatus.CONCLUIDO);

                consultaService.salvar(consulta);
            } catch (Exception e) {
                e.printStackTrace();

                consulta.setDataFinalizacao(new Date());

                consulta.setStatus(ConsultaStatus.FALHA);

                consultaService.salvar(consulta);
            }
        }

    }

    private Empresa setDados(Empresa empresa, Document document) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Elements elements = document.select("b");

        empresa.setParentesco(elements.get(3).html());

        empresa.setDataAbertura(formatter.parse(elements.get(5).html()));

        empresa.setRazaoSocial(elements.get(6).html());

        empresa.setPorte(elements.get(8).html());

        empresa.setLogradouro(elements.get(12).html());

        empresa.setNumeroLogradouro(elements.get(13).html());

        empresa.setComplementoLogradouro(elements.get(14).html());

        empresa.setCep(elements.get(15).html());

        empresa.setBairro(elements.get(16).html());

        empresa.setSituacaoCadastral(elements.get(23).html());

        return empresa;
    }

}
