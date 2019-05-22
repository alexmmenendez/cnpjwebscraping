package br.com.cnpjwebscraping.service.worker.sintegra.factory;

import br.com.cnpjwebscraping.domain.Empresa;
import br.com.cnpjwebscraping.logger.DebugHelper;
import br.com.cnpjwebscraping.service.worker.sintegra.SintegraServiceWorker;
import br.com.cnpjwebscraping.util.SpringBeanUtil;
import org.apache.commons.lang3.StringUtils;

public class SintegraServiceWorkerFactory {

    private static final String PREFFIX = "br.com.cnpjwebscraping.service.worker.sintegra.";
    private static final String SUFFIX = "SintegraServiceWorker";


    public static SintegraServiceWorker identificarWorker(Empresa empresa) throws Exception {

        if (empresa.getCidade() == null) {
            throw new Exception("Cidade not found");
        }

        String uf = empresa.getCidade().getEstado().getUf();

        String className = StringUtils.join(PREFFIX, uf, SUFFIX);

        try {

            Class<?> clazz = Class.forName(className);
            Object object = SpringBeanUtil.getBean(clazz);

            return (SintegraServiceWorker) object;

        } catch (ClassNotFoundException e) {
            DebugHelper.out(className + " não encontrado", DebugHelper.Type.ERROR);
        }

        return null;
    }


}
