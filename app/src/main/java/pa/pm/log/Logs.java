package pa.pm.log;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import pa.pm.cartaoprograma2.MainActivity;

import android.os.Environment;
import android.text.format.Time;

public class Logs {
	String data, hora, acao, codigo, ip;
	int idusuario;
	double location;
	MCrypt mcr;

	public String Hora() {
		MCrypt mcrypt = new MCrypt();

		Time now = new Time();
		now.setToNow();
		// hora=now.format("%d-%m-%Y %H:%M:%S");
		hora = now.format("%H:%M:%S");
		/* Encrypt */
		try {
			// hora = MCrypt.bytesToHex( mcrypt.encrypt(hora) );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println ("Hora: " +hora);
		return hora;
	}

	public String Data() {
		MCrypt mcrypt = new MCrypt();
		Time now = new Time();
		now.setToNow();
		data = now.format("%Y-%m-%d");
		try {
			// data = MCrypt.bytesToHex( mcrypt.encrypt(data) );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println ("Data: " +data);
		return data;
	}

	public int Usuario() {
		MCrypt mcrypt = new MCrypt();
		idusuario = 1;
		try {
			// idusuario = MCrypt.bytesToHex( mcrypt.encrypt(idusuario) );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idusuario;
	}

	public double LocationLat() {
		MCrypt mcrypt = new MCrypt();
		location = MainActivity.lastLocationLat();

		try {
			// location = MCrypt.bytesToHex( mcrypt.encrypt(location) );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return location;
	}

	public double LocationLng() {
		MCrypt mcrypt = new MCrypt();
		location = MainActivity.lastLocationLng();

		try {
			// location = MCrypt.bytesToHex( mcrypt.encrypt(location) );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return location;
	}

	float bn;

	public String Acao(int type) {
		MCrypt mcrypt = new MCrypt();
		switch (type) {
		case 12:
			acao = "INICIOU O APLICATIVO";

			break;
		case 1:
			acao = "LOGOU NO SISTEMA";
			break;
		case 2:
			acao = "errou o login";
			break;
		case 3:
			acao = "acertou o login";
			break;
		case 4:
			acao = "carregou o programa";
			break;
		case 5:
			acao = "mudou o perfil de bateria";
			break;
		case 6:
			acao = "ligou o gps";
			break;
		case 7:
			acao = "tempo de uso da bateria";
			break;
		case 8:
			acao = "ligou o aparelho em um carregador";
			break;
		case 9:
			acao = "carregou a rota";
			break;
		case 10:
			acao = "passou pelo ponto predefinido da rota";
			break;
		case 11:
			acao = "iniciou apertou o botão de emergência";
			break;
		}
		acao = acao.replaceAll("\\s+", "_");
		try {
			// acao = MCrypt.bytesToHex( mcrypt.encrypt(acao) );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return acao;
	}

	public String Codigo(int type) {
		MCrypt mcrypt = new MCrypt();
		switch (type) {

		case 1:
			codigo = "1";
			break;
		case 2:
			codigo = "2";
			break;
		case 3:
			codigo = "3";
			break;
		case 4:
			codigo = "4";
			break;
		case 5:
			codigo = "5";
			break;
		case 6:
			codigo = "6";
			break;
		case 7:
			codigo = "7";
			break;
		case 8:
			codigo = "8";
			break;
		case 9:
			codigo = "9";
			break;
		case 10:
			codigo = "10";
			break;
		case 11:
			codigo = "11";
			break;
		case 12:
			codigo = "12";
			break;
		}
		try {
			// codigo = MCrypt.bytesToHex( mcrypt.encrypt(codigo) );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return codigo;
	}

	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& inetAddress instanceof Inet4Address) {
						return inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public void delete() {
		File folder = new File(Environment.getExternalStorageDirectory()
				.toString() + "/cartaoprograma");
		for (File f : folder.listFiles()) {

			if (f.isFile()) {
				f.delete();
			}
		}
	}
}