package br.com.cnpjwebscraping.service.consulta;

import br.com.cnpjwebscraping.logger.DebugHelper;
import br.com.cnpjwebscraping.util.SpringBeanUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TRTServiceWorkerManager {

    public String consultar(String cpfCnpj) throws Exception {

        List<String> listWorkers = new ArrayList<String>() {
            {
                add(SimpleTRT02ServiceWorker.class.getName());
                add(SimpleTRT03ServiceWorker.class.getName());
                add(SimpleTRT11ServiceWorker.class.getName());
                add(SimpleTRT12ServiceWorker.class.getName());
                add(SimpleTRT20ServiceWorker.class.getName());
                add(SimpleTRT24ServiceWorker.class.getName());
            }
        };

        String result;

        do {

            int randomIndex = new Random().nextInt(listWorkers.size());

            String workerName = listWorkers.get(randomIndex);

            listWorkers.remove(randomIndex);

            TRTServiceWorker worker = null;

            try {
                Class<?> clazz = Class.forName(workerName);

                worker = (TRTServiceWorker) SpringBeanUtil.getBean(clazz);

                DebugHelper.out("Consultando o cpf/cnpj: " + cpfCnpj + " pelo " + worker.getClass().getSimpleName() + "...", DebugHelper.Type.INFO);

                String nome = worker.consultaNomeCompletoRazaoSocialPeloCPFCNPJ(cpfCnpj);

                result = new JSONObject().put("nome", nome).put("sucesso", true).put("mensagem", "").toString();

                DebugHelper.out("Resultado da consulta do " + worker.getClass().getSimpleName() + ": " + result, DebugHelper.Type.SUCCESS);

                return result;

            } catch (Exception e) {
                DebugHelper.out("Falha na consulta do " + cpfCnpj + " pelo " + worker.getClass().getSimpleName() + "...", DebugHelper.Type.ERROR);

                if (listWorkers.isEmpty()) {
                    throw e;
                }
            }

        } while (true);
    }
}
