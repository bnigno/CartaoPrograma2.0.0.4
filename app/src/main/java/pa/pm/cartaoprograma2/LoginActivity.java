package pa.pm.cartaoprograma2;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import pa.pm.localdb.ConfigDataBaseHelper;
import pa.pm.log.CreateFolders;
import pa.pm.log.Logs;
import pa.pm.localdb.DataBaseHelper;
import pa.pm.localdb.XMLParser;

import com.google.android.gms.common.GooglePlayServicesUtil;

import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

public class LoginActivity extends Activity implements OnInitListener {
	AlertDialog.Builder dialog;
	CreateFolders cf;
	LocationManager locationManager;
	private ProgressDialog carregando;
	public static final int DELAYED_RESPONSE = 3000;
	private Handler handler = new Handler();
	String data, hora, acao, location, codigo, usuario;
	int idusuario;
	Logs Logs;
	DataBaseHelper db;
	int t;
	String url1;
	public static String idCard;
	public static String cardDescription;
	public static String routeDescription;
	public static String areaDescription;
	public static String error = "";
	public static String msg_error;
	public static String idMap;
	public static String latcenter;
	public static String lngcenter;
	String error2 = "2";
	Editor edit;
	ToggleButton mToggleButton;
	boolean isLock = false;
	SharedPreferences userDetails;
	String shortcutUri;
	static String start;
	static String finish;
	String Uname;
	String pass;


	@Override
	public void onResume() {
		super.onResume();
		checkConnectionStatus();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		idCard = "b";
		Logs = new Logs();
		cf = new CreateFolders();
		cf.createFolder();
		cf.delete();
		cf.createFolder();
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		userDetails = this.getSharedPreferences("userdetails", MODE_PRIVATE);
		edit = userDetails.edit();
		check();
		File folder = new File(Environment.getExternalStorageDirectory()
				.toString() + "/cartaoprograma");
		folder.mkdirs();
		String DATABASE_NAME = "LOGSX";
		this.deleteDatabase(DATABASE_NAME);
		db = new DataBaseHelper(getApplicationContext());
		db.resetTables();
		Button signIn;
		final EditText userName = (EditText) findViewById(R.id.usernameEditText);
		userName.setEnabled(true);
		final EditText password = (EditText) findViewById(R.id.passwordEditText);
		password.setEnabled(true);
		signIn = (Button) findViewById(R.id.btnLogin);
		signIn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final EditText usrnm = (EditText) findViewById(R.id.usernameEditText);
				final EditText pswd = (EditText) findViewById(R.id.passwordEditText);
				carregando = new ProgressDialog(LoginActivity.this,
						R.style.MyTheme);
				ConnectionDetector cd = new ConnectionDetector(
						getApplicationContext());
				Boolean isInternetPresent = cd.isConnectingToInternet();

				if (isInternetPresent) {
					carregando.setTitle("Autenticando");
					carregando.setMessage("Aguarde...");
					carregando.show();
					if (usrnm.getText().toString().isEmpty()
							|| pswd.getText().toString().isEmpty()) {
						carregando.setTitle("Falha no login");
						carregando.setMessage("Preencha CPF e Senha");
						carregando.show();

						handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								carregando.cancel();
							}
						}, 2000);

					} else {
						 Uname = usrnm.getText().toString();
						 pass = pswd.getText().toString();
						url1 = EnderecoServidor.OBTER_CP
								+ Uname.replaceAll(" ", "")
								+ "&pass="
								+ pass.replaceAll(" ", "");
						Card.getCard(url1);
						handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								//
								if(Uname.equals("local")&&pass.equals("local"))
								{
									idCard = "1"; // =teste
									//start = "1";
									//finish = "1";
									idMap = "1";
									latcenter = "-1.457728";
									lngcenter = "-48.490252";
									cardDescription = "Descrição";
									routeDescription = "Rota preventiva nas avenidas e transversais";
									areaDescription = "Proximidades do Cemitério de Santa Izabel";
									}
								//
								if (idCard.equals("")) {// !=null) {
									carregando.setTitle("Falha no login");
									carregando.setMessage(msg_error);
									carregando.show();

									handler.postDelayed(new Runnable() {
										@Override
										public void run() {
											carregando.cancel();
										}
									}, 2000);

								}

								else {
									System.out.println("errox: "
											+ error.length());
									handler.postDelayed(new Runnable() {
										@Override
										public void run() {
											if (cardDescription != null) {
												if (cardDescription.length() > 2) {
													long tm = difTempo();
													/*Deve ficar assim ---> if (tm <= 0) {*/
													
													if (tm >= 0) {
														carregando.cancel();
														logx(1);
														logx(3);
														finish();
														iniciarActivity();
													} else {
														carregando
																.setTitle("Indisponivel");
														carregando
																.setMessage("Sem cartao programa disponivel");
														carregando.show();

														handler.postDelayed(
																new Runnable() {
																	@Override
																	public void run() {
																		carregando
																				.cancel();
																	}
																}, 2000);
													}
												}
											} else {

												carregando
														.setTitle("Falha no login");
												carregando
														.setMessage("Tente novamente");
												handler.postDelayed(
														new Runnable() {
															@Override
															public void run() {
																carregando
																		.cancel();
															}
														}, 2000);
												// }
											}
										}
									}, 4000);
								}

							}
						}, DELAYED_RESPONSE);
					}

				} else {
					carregando.setTitle("Sem conexao");
					carregando.setMessage("Verifique a conexao com a internet");
					carregando.show();

					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							carregando.cancel();
						}
					}, 2000);

				}
			}
		});

	}

	void iniciarActivity() {

		startActivity(new Intent(this, MainActivity.class));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;
	}

	void checkConnectionStatus() {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;
		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;

		}
		if (haveConnectedWifi == false && haveConnectedMobile == false) {
			buildAlertMessageNoNetwork();

		}
		if (haveConnectedWifi == true) {
			checkGps();
		}
	}

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub

	}

	public void checkGps() {
		final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			buildAlertMessageNoGps();
		} else {
			logx(6);
		}

	}

	private void buildAlertMessageNoGps() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"O GPS parece estar desativado, deseja ir as configurações para ativá-lo?")
				.setCancelable(false)
				.setPositiveButton("Sim",
						new DialogInterface.OnClickListener() {
							public void onClick(
									@SuppressWarnings("unused") final DialogInterface dialog,
									@SuppressWarnings("unused") final int id) {
								startActivity(new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
							}
						})
				.setNegativeButton("Nao",
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog,
									@SuppressWarnings("unused") final int id) {
								dialog.cancel();

							}
						});
		final AlertDialog alert = builder.create();
		alert.show();
	}

	private void buildAlertMessageNoNetwork() {

		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Não foi possível se conectar a rede")
				.setCancelable(false)

				.setPositiveButton("Verificar em configurações",
						new DialogInterface.OnClickListener() {
							public void onClick(
									@SuppressWarnings("unused") final DialogInterface dialog,
									@SuppressWarnings("unused") final int id) {
								startActivity(new Intent(
										android.provider.Settings.ACTION_WIRELESS_SETTINGS));
							}
						})
				.setNegativeButton("Tentar novamente",
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog,
												@SuppressWarnings("unused") final int id) {

								dialog.cancel();

							}
						});
		final AlertDialog alert = builder.create();
		alert.show();
	}

	public void Legal(View view) {
		String LicenseInfo = GooglePlayServicesUtil
				.getOpenSourceSoftwareLicenseInfo(getApplicationContext());
		AlertDialog.Builder LicenseDialog = new AlertDialog.Builder(
				LoginActivity.this);
		LicenseDialog.setTitle("Legal Notices");
		LicenseDialog.setMessage(LicenseInfo);
		LicenseDialog.show();
	}

	public void Sobre(View view) {
        String LicenseInfo = "Versão 2.0.0.4 Desenvolvido por:\nBruno Lima\nFelipe Nazareth\nIuri Raiol\nLourdilene Silva\nLuiz Wagner\nTiago Antero" +
                "\n\nVersão 2.0.0.5 Desenvolvido por:\nAdailton Lima \nDiogo Ferreira \nFelipe Benigno \nKaique Cruz \nPedro Gomes \nRodrigo Quites \nWeslley Sammyr";
		AlertDialog.Builder LicenseDialog = new AlertDialog.Builder(
				LoginActivity.this);
		LicenseDialog.setTitle("Sobre");
		LicenseDialog.setMessage(LicenseInfo);
		LicenseDialog.show();
	}

	public void logx(int f) {
		data = Logs.Data();
		hora = Logs.Hora();
		acao = Logs.Acao(f);
		codigo = Logs.Codigo(f);
		idusuario = Logs.Usuario();
	}

	public void clean() {

		CreateFolders cf;
		cf = new CreateFolders();
		cf.createFolder();
		cf.delete();
		cf.createFolder();
		String DATABASE_NAME = "LOGSX";
		this.deleteDatabase(DATABASE_NAME);
	}

	public void check() {

		PackageManager pm = this.getPackageManager();

		PackageInfo info = new PackageInfo();
		try {
			info = pm.getPackageInfo(this.getPackageName(),
					PackageManager.GET_SIGNATURES);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Object inf = "3082030d308201f5a00302010202044f0c71c3300d06092a864886f70d01010b05003037310b30090603550406130255533110300e060355040a1307416e64726f6964311630140603550403130d416e64726f6964204465627567301e170d3134303432313035313034395a170d3434303431333035313034395a3037310b30090603550406130255533110300e060355040a1307416e64726f6964311630140603550403130d416e64726f696420446562756730820122300d06092a864886f70d01010105000382010f003082010a0282010100a1e62c360c65cd1135995f9ec49e9de5b05ed84ea3cbb989b31878c7baef61dc891d241cbb9e5ba80f1981d3d8f66e1973fc2f138f3a58910da52239f7ca7f5b71a2257881b63ff0bf47a8f1cc95d11d05530ea5cc588a0b88b342c11d8d5472ace1365c57cc94eb5ddc7133b91134e1e2647201dd56b3bb4aee5f508f969a8073f2306fd2da37d03c37535f6aa7287dbaea79189613607125fb818cf9a024c36c90d1791485f6d1929da52bf52e1727259b0ece0a4ff26e8c2cf1c114c7a88b5544f17e64f28faf6f02353affd9ad3d3aefd59ca75861b66537302702f6b811a83592b9577822c40f45ab8cac09fce880aceb68b1dda26e3a7c9812a23877db0203010001a321301f301d0603551d0e041604142046f2d34c16c61acbb1957e39542612b9058721300d06092a864886f70d01010b050003820101008f4545c7d26d6f6539070f26922c06f376019236e559d33d436e5fbb03c2516d27c82a3b6a75283cef00e9869a56d98edc65e0a8a5e1712d7f936e65297a218c55fb4db85ebd3c66ede1baaca6cdf56c21e78154f40864a41a9b941189fc80bf312357b0f7ab88299dd2f202518663f6dada7e002b161a46ddcf03cf8e64375ef01ffd099f9e3df5ac7141e195a62f4613967ee52edfd10aa0bcaff2372ada29ff0e87e9a93af98176bf00ecbc513b858f9e3cd234e4311ede128ac4d5894d12842c37819cb6c07fbf2402f02aacf69f305f42449148b8f1d96b77c49231f6c8caf6459e208534c850f49c34ea6b9163c8743be67e0e5ad9a7746513b42ba5db";
		// System.out.println ("signature is OKx "+info.signatures[ 0
		// ].toCharsString() );
		/*
		 * if ( info.signatures[ 0 ].toCharsString().equals( inf) ) {
		 * //signature is OK System.out.println ("signature is OKx " );
		 * 
		 * }else{ Uri packageURI =
		 * Uri.parse("package:"+LoginActivity.class.getPackage().getName());
		 * Intent uninstallIntent = new Intent(Intent.ACTION_DELETE,
		 * packageURI); startActivity(uninstallIntent);
		 * 
		 * }
		 */
		/*
		 * boolean flag=true; while(!info.signatures[ 0
		 * ].toCharsString().equals( inf)){
		 * 
		 * // if(flag){ // flag=false; Uri packageURI =
		 * Uri.parse("package:"+MainActivity.class.getPackage().getName());
		 * Intent uninstallIntent = new Intent(Intent.ACTION_DELETE,
		 * packageURI); startActivity(uninstallIntent);
		 * 
		 * 
		 * // } }
		 */
	}

	public long difTempo() {
		/*
		 * String dateStart = LoginActivity.start; String dateStop =Logs.Data()+
		 * " "+Logs.Hora();
		 */

		String dateStart = Logs.Data() + " " + Logs.Hora();
		String dateStop = LoginActivity.start;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);

			// in milliseconds
			long diff = d2.getTime() - d1.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			long ti = diff / 1000;
			System.out.print(diffDays + " days, ");
			System.out.print(diffHours + " hours, ");
			System.out.print(diffMinutes + " minutes, ");
			System.out.print(diffSeconds + " seconds.");
			return ti;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 99999999;
	}

	//
	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this, R.style.MyTheme2)
				.setTitle("Sair")
				.setMessage("Deseja sair?")
				.setPositiveButton("Sim",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
								dialog.cancel();
							}

						})
				.setNegativeButton("Não",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}

						}).show();
	}


}

