package pa.pm.cartaoprograma2;

public class EnderecoServidor {

    private static String ip = "http://192.168.0.107:80/";

    static String OBTER_NOTIFICACAO = ip+"xmlObterNotificacoes.php?idCard=";

    static String INFORMAR_OCORRENCIA = ip+"xmlInformarOcorrencia.php?idCard=";

    static String OBTER_CP = ip+"xmlObterCP.php?cpf=";

    static String OBTER_PONTOS = ip+"xmlObterPontos.php?idCard=";

    static String OBTER_MANCHA = ip+"xmlObterMancha.php?type=2&dataStart=2014-02-01&dataFinish=2014-06-01";

    public static String RECEBER_LOG = ip+"xmlRecebeLog.php?idCard=";
}
