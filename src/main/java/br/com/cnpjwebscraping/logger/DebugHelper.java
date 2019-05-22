package br.com.cnpjwebscraping.logger;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DebugHelper {

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RESET = "\u001B[0m";

    public enum Type {
        ERROR,
        INFO,
        SUCCESS
    }

    public static void out(String message, Type type) {

        if (type.equals(Type.ERROR)) {
            System.out.println(ANSI_RED + new Date() + " [CRAWLER ERROR]  -  " + message);
        }
        else if (type.equals(Type.INFO)) {
            System.out.println(ANSI_YELLOW + new Date() + " [CRAWLER INFO]  -  " + message);
        }
        else if (type.equals(Type.SUCCESS)) {
            System.out.println(ANSI_GREEN + new Date() + " [CRAWLER SUCCESS]  -  " + message);
        }
    
        System.out.print(ANSI_RESET);
    }

    public static void out(String message, Type type, long solicitacaoId, Class clazz) {

        StringBuilder messageBuilder = new StringBuilder("");

        String finalMessage = "";

        if (type.equals(Type.ERROR)) {
            finalMessage = messageBuilder.append(ANSI_RED)
                    .append(new Date()).append(" [CRAWLER ERROR] #").append(solicitacaoId)
                    .append(" - @").append(clazz.getSimpleName()).append(": ").append(message).toString();

        } else if (type.equals(Type.INFO)) {
            finalMessage = messageBuilder.append(ANSI_YELLOW)
                    .append(new Date()).append(" [CRAWLER INFO] #").append(solicitacaoId)
                    .append(" - @").append(clazz.getSimpleName()).append(": ").append(message).toString();
        } else if (type.equals(Type.SUCCESS)) {
            finalMessage = messageBuilder.append(ANSI_GREEN)
                    .append(new Date()).append(" [CRAWLER SUCCESS] #").append(solicitacaoId)
                    .append(" - @").append(clazz.getSimpleName()).append(": ").append(message).toString();
        }

        System.out.println(finalMessage);
        System.out.print(ANSI_RESET);

    }
}
