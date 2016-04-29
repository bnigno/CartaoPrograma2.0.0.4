package pa.pm.cartaoprograma2;

public class EnderecoServidor {

    private static String ip = "http://192.168.0.101:80/";

    static String OBTER_NOTIFICACAO = ip+"xmlObterNotificacoes.php?idCard="; // GET

    static String INFORMAR_OCORRENCIA = ip+"xmlInformarOcorrencia.php?idCard=";  // GET

    static String OBTER_CP = ip+"xmlObterCP.php?cpf=";  // GET

    static String OBTER_PONTOS = ip+"xmlObterPontos.php?idCard=";  // GET

    static String OBTER_MANCHA = ip+"xmlObterMancha.php?type=2&dataStart=2014-02-01&dataFinish=2014-06-01";  // GET

    public static String RECEBER_LOG = ip+"xmlRecebeLog.php?idCard=";

    public static String ENVIAR_ROTA_PERCORRIDA = ip+"xmlEnviarRotaPercorrida.php";  // POST
}
