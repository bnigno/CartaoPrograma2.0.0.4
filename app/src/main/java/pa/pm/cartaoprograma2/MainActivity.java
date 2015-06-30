package pa.pm.cartaoprograma2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import pa.pm.log.CreateFolders;
import pa.pm.log.Logs;
import pa.pm.localdb.DataBaseHelper;
import pa.pm.localdb.XMLParser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity implements LocationSource,
		LocationListener, OnItemClickListener {

	final int RQS_GooglePlayServices = 1;
	private GoogleMap myMap;
	TextView tvLocInfo;
	Logs Logs;
	String hora, acao, codigo, usuario;
	public static LocationManager myLocationManager = null;
	OnLocationChangedListener myLocationListener = null;
	Criteria myCriteria;
	public static int TIMETOEXIT;
	public static final int TIMETOEXIT2 = 5000;
	public static String DESCRIPTION;
	public static String textlistx;
	String data, location;

	int idusuario;
	CreateFolders cf;
	TextView text;
	TextView end;
	int t;
	static DataBaseHelper db;
	Button pf;
	Button config;
	Button ocorrencia;
	ImageView img;
	private Handler handler = new Handler();
	static ListView listView;
	private ListView listView2;
	static AdapterListView adapterListView;
	static AdapterListView adapterListView2;
	public static ArrayList<ItemListView> itens;
	public static ArrayList<ItemListView> itens2;
	private Spinner spinner1;
	String manchax;
	ItemListView item1;
	String nitem;
	String horaitem;
	String descitem;
	String textlist;
	String ip;
	double latx, lngx;
	static int linewidth = 7;
	double profile;
	String perfil;
	String bestProvider;
	Boolean ativo0 = false;
	Boolean ativo1 = false;
	Boolean ativo2 = false;
	Boolean emocorrencia;
	LatLng FIRSTLOCATION;
	LatLng PREVIOUSLOCATION;
	static LatLng LASTLOCATION;
	public ProgressDialog carregando;
	public ProgressDialog carregando2;
	int num = 0;
	TextView SemDados;
	int i = 0;
	static String indisponivel = "INDISPONIVEL";
	static public Intent proximityIntent;
	static PendingIntent pendingIntent;
	double accuracy;
	float[] results = null;
	Boolean prx = true;
	double fx;
	double f;
	LatLng LASTMARKER;
	Marker marker1;
	double lat;
	double lng;
	Polygon polygontodas;
	Polygon polygonsequestro;
	Polygon polygonfurto;
	Polygon polygonassalto;
	Polygon polygontrafico;
	Marker marker2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);

		proximityIntent = new Intent("pa.pm.cartaoprograma2.proximity");
		pendingIntent = PendingIntent.getActivity(getBaseContext(), 0,
				proximityIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
		runOnUiThread(new Runnable() {
			public void run() {

				iniciar();
			}
		});

	}

	public void addItemsOnSpinner1() {

		spinner1 = (Spinner) findViewById(R.id.spinner1);
		List<String> list = new ArrayList<String>();
		list.add("OCULTAR");
		list.add("TODAS");
		list.add("SEQUESTRO");
		list.add("FURTO");
		list.add("ASSALTO");
		list.add("TRAFICO");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(dataAdapter);

		spinner1.setBackgroundColor(Color.WHITE);

	}

	public void addListenerOnButton() {

		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				manchax = spinner1.getSelectedItem().toString();
				Log.e("Selected item : ", manchax);
				if (mp == true) {
					mancha(manchax);
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

	}

	public void mancha(String manchax) {

		if (manchax.equals("OCULTAR")) {
			if (polygontodas != null) {
				polygontodas.remove();
			}
			if (polygonsequestro != null) {
				polygonsequestro.remove();
			}
			if (polygonfurto != null) {
				polygonfurto.remove();
			}
			if (polygonassalto != null) {
				polygonassalto.remove();
			}
			if (polygontrafico != null) {
				polygontrafico.remove();
			}
			if (marker2 != null) {
				marker2.remove();
			}
		}
		if (manchax.equals("TODAS")) {
			System.out.println("Key0000: ");
			Polygons.getPolygons(myMap);
			if (polygonfurto != null) {
				polygonfurto.remove();
			}
			if (polygonsequestro != null) {
				polygonsequestro.remove();
			}
			if (polygonassalto != null) {
				polygonassalto.remove();
			}
			if (polygontrafico != null) {
				polygontrafico.remove();
			}

		}
		if (manchax.equals("SEQUESTRO")) {
			if (polygontodas != null) {
				polygontodas.remove();
			}
			if (polygonfurto != null) {
				polygonfurto.remove();
			}
			if (polygonassalto != null) {
				polygonassalto.remove();
			}
			if (polygontrafico != null) {
				polygontrafico.remove();
			}

			polygonsequestro = myMap.addPolygon(new PolygonOptions().add(
					new LatLng(-1.474531783, -48.45607194), LASTLOCATION,
					new LatLng(3, 5), new LatLng(0, 0)).strokeColor(
					Color.parseColor(Config.colorMancha)));
			polygonsequestro.setFillColor(Color.parseColor(Config.colorMancha));
		}
		if (manchax.equals("FURTO")) {
			if (polygontodas != null) {
				polygontodas.remove();
			}
			if (polygonsequestro != null) {
				polygonsequestro.remove();
			}

			if (polygonassalto != null) {
				polygonassalto.remove();
			}
			if (polygontrafico != null) {
				polygontrafico.remove();
			}
			polygonfurto = myMap.addPolygon(new PolygonOptions()
					.add(new LatLng(-1.474531783, -48.45607194), LASTLOCATION,
							new LatLng(3, 5), new LatLng(0, 0))
					.strokeColor(Color.parseColor(Config.colorMancha))
					.fillColor(Color.parseColor(Config.colorMancha)));

		}
		if (manchax.equals("TRAFICO")) {
			if (polygontodas != null) {
				polygontodas.remove();
			}
			if (polygonsequestro != null) {
				polygonsequestro.remove();
			}
			if (polygonfurto != null) {
				polygonfurto.remove();
			}
			if (polygonassalto != null) {
				polygonassalto.remove();
			}

			polygontrafico = myMap.addPolygon(new PolygonOptions()
					.add(new LatLng(-1.474531783, -48.45607194),
							new LatLng(0, 5), new LatLng(3, 5),
							new LatLng(0, 0))
					.strokeColor(Color.parseColor(Config.colorMancha))
					.fillColor(Color.parseColor(Config.colorMancha)));

		}

	}

	public void createListView() {
		Boolean isInternetPresent;
		System.out.println("Aqui: ");
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {
			itens = new ArrayList<ItemListView>();
			final Markers marker = new Markers();
			marker.getMarkers(myMap);
			adapterListView = new AdapterListView(this, itens);
			int l = adapterListView.getCount();
			System.out.println("Size: " + l);
			listView.setAdapter(adapterListView);
			listView.setCacheColorHint(Color.TRANSPARENT);

		} else {
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					createListView();
				}
			}, 10000);

		}
	}

	private void createListView2() {
		itens2 = new ArrayList<ItemListView>();
		adapterListView2 = new AdapterListView(this, itens2);
		listView2.setAdapter(adapterListView2);
		listView2.setCacheColorHint(Color.BLUE);
	}

	public void onItemClick(AdapterView<?> arg0, final View arg1, int arg2,
			long arg3) {
		try {

			difd();
			ItemListView item = adapterListView2.getItem(arg2);
			arg1.setBackgroundColor(Color.RED);
			String alerta = item.getTexto2();
			String tittle = item.getTexto();
			new AlertDialog.Builder(this, R.style.MyTheme2)
					.setTitle(tittle)
					.setMessage(alerta)
					.setPositiveButton(android.R.string.yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									arg1.setBackgroundColor(Color.WHITE);
									dialog.cancel();
								}
							}).show();
		} catch (NullPointerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void logx(int f, boolean h) {
		if (h) {
			System.out.println("Log ativo ");
			data = Logs.Data();
			hora = Logs.Hora();
			ip = pa.pm.log.Logs.getLocalIpAddress();
			codigo = Logs.Codigo(f);
			idusuario = Logs.Usuario();
			latx = Logs.LocationLat();
			lngx = Logs.LocationLng();
			db.addone(data, hora, codigo, idusuario, latx, lngx, ip);
		}

	}

	void restart() {
		startActivity(new Intent(this, LoginActivity.class));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();

		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());

		if (resultCode == ConnectionResult.SUCCESS) {
			myLocationManager.requestLocationUpdates(0L, // minTime
					0.0f, // minDistance
					myCriteria, // criteria
					this, // listener
					null); // looper

			myMap.setLocationSource(this);

			int cor = transparent(Config.colorMancha);
			if (polygontodas != null) {
				polygontodas.setFillColor(cor);
				polygontodas.setStrokeColor(cor);
			}
			if (polygonfurto != null) {
				polygonfurto.setFillColor(cor);
				polygonfurto.setStrokeColor(cor);
			}
			if (polygonassalto != null) {
				polygonassalto.setFillColor(cor);
				polygonassalto.setStrokeColor(cor);
			}
			if (polygontrafico != null) {
				polygontrafico.setFillColor(cor);
				polygontrafico.setStrokeColor(cor);
			}
			if (polygonsequestro != null) {
				polygonsequestro.setFillColor(cor);
				polygonsequestro.setStrokeColor(cor);
			}

		} else {
			GooglePlayServicesUtil.getErrorDialog(resultCode, this,
					RQS_GooglePlayServices);
		}

	}

	public int transparent(String col) {
		int cor = 0;
		if (col.equals("BLUE")) {
			cor = 0x7F0000FF;
		}
		if (col.equals("RED")) {
			cor = 0x7FFF0000;
		}
		if (col.equals("GREEN")) {
			cor = 0x7F00FF00;
		}
		if (col.equals("YELLOW")) {
			cor = 0x7FFFFF00;
		}
		return cor;
	}

	@Override
	protected void onPause() {
		myMap.setLocationSource(null);
		myLocationManager.removeUpdates(this);
		super.onPause();
	}

	@Override
	public void activate(OnLocationChangedListener listener) {
		myLocationListener = listener;
	}

	@Override
	public void deactivate() {
		myLocationListener = null;
	}

	@Override
	public void onLocationChanged(Location location) {
		accuracy = location.getAccuracy();
		float[] results = new float[3];
		if (marker1 != null) {
			marker1.remove();
		}

		if (myLocationListener != null) {

			if (itens.size() > 0) {

				item1 = itens.get(0);
				f = item1.getLat();
				fx = item1.getLng();
				LASTMARKER = new LatLng(f, fx);
				if (prx) {
					myLocationManager.addProximityAlert(f, fx, 1000, -1,
							pendingIntent);
					prx = false;
					System.out.println("First: ALERTA ADICIONADO");
					System.out.println("First: ALERTA ADICIONADO" + f + fx);
				}
				System.out
						.println("First: ALERTA PRX " + ProximityActivity.prx);
				if (ProximityActivity.prx) {
					System.out.println("First: ALERTA ATIVADO");
					Location.distanceBetween(location.getLatitude(),
							location.getLongitude(), f, fx, results);
					System.out.println("First3" + results[0]);

					if (results[0] > 50) {
						itens.remove(0);
						adapterListView.notifyDataSetChanged();
						ProximityActivity.prx = false;
						prx = true;
						System.out.println("Removido: " + prx);
						myLocationManager.removeProximityAlert(pendingIntent);
						if (itens.size() == 0) {
							end = (TextView) findViewById(R.id.end);
							end.bringToFront();
							end.setTextSize(20);
							end.setText("FIM\nPONTOS\nBASE");
							end.setBackgroundColor(Color.WHITE);
						}
					}
				}
			}
			myLocationListener.onLocationChanged(location);
			lat = location.getLatitude();
			lng = location.getLongitude();
			LatLng latlng = new LatLng(lat, lng);

			if (i == 0) {
				FIRSTLOCATION = latlng;
				rota(FIRSTLOCATION, FIRSTLOCATION);
				i = 2;

			}
			if (i == 3) {
				i = 4;
				myMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
					public void onMapLoaded() {
						mp = true;
					}
				});
			}
			if (i == 4) {
				PREVIOUSLOCATION = LASTLOCATION;
				String previous = "Previous" + PREVIOUSLOCATION.toString();
				;
				Log.d("Background Task", previous);
			}
			LASTLOCATION = latlng;
			String previous = "Last" + LASTLOCATION.toString();
			Bitmap.Config conf = Bitmap.Config.ARGB_8888;
			Bitmap bmp = Bitmap.createBitmap(40, 30, conf);
			Canvas canvas = new Canvas(bmp);
			Paint p = new Paint();
			p.setStyle(Style.FILL);
			canvas.drawColor(Color.parseColor(Config.colorLocalizacaoAtual));
			canvas.drawCircle(60, 50, 25, p);
			marker1 = myMap.addMarker(new MarkerOptions()
					.position(LASTLOCATION).title(descricaocartao)
					.snippet(descricaorota + descricaoarea)
					.icon(BitmapDescriptorFactory.fromBitmap(bmp)));
			caminhoPercorrido();
			logx(10, ocx);
			check();
		}

	}

	public static double lastLocationLat() {
		if (LASTLOCATION != null) {
			double n = LASTLOCATION.latitude;
			return n;
		} else
			return 0;
	}

	public static double lastLocationLng() {
		if (LASTLOCATION != null) {
			double n = LASTLOCATION.longitude;
			return n;
		} else
			return 0;
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	Circle myCircle;
	LatLng origin;
	LatLng dest;

	private Boolean rota(LatLng l1, LatLng l2) {
		origin = l1;
		dest = l2;

		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		Boolean isInternetPresent;
		System.out.println("Aqui: ");
		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {
			if (mp == true) {

				String url = getDirectionsUrl(origin, dest);

				DownloadTask downloadTask = new DownloadTask();
				if (url != null) {
					downloadTask.execute(url);
				}
				return true;
			} else {
				return false;
			}
		} else {
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					rota(origin, dest);

				}
			}, 10000);
			return false;
		}

	}

	public void rota2(LatLng l1, LatLng l2) {
		LatLng origin2 = l1;
		LatLng dest2 = l2;
		rota(origin2, dest2);
	}

	private void caminhoPercorrido() {

		if (i == 2) {
			myMap.addPolyline((new PolylineOptions())
					.add(FIRSTLOCATION, LASTLOCATION).width(linewidth)
					.color(Color.parseColor(Config.colorCaminhoPercorrido))
					.geodesic(true));
			myMap.moveCamera(CameraUpdateFactory
					.newLatLngZoom(LASTLOCATION, 15));

			pointsrota.add(LASTLOCATION);
			i = 3;
		}
		if (i == 4) {
			myMap.addPolyline((new PolylineOptions())
					.add(PREVIOUSLOCATION, LASTLOCATION).width(linewidth)
					.color(Color.parseColor(Config.colorCaminhoPercorrido))
					.geodesic(true));

			if (mp) {
				System.out.println("Carregado");
				myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
						LASTLOCATION, 15));
			}
			pointsrota.add(LASTLOCATION);

		}

	}

	Boolean mp;
	Boolean imagemap;

	public void callAsynchronousTask(boolean b) {
		System.out.println("qui" + isRunning(MainActivity.this));
		if (isRunning(MainActivity.this) == false) {
			clean();

		}

		final Handler handler = new Handler();
		Timer timer = new Timer();

		TimerTask doAsynchronousTask = new TimerTask() {
			@Override
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						if (stop == 1) {
							System.out.println("=============zzz===");
						}
						if (stop == 0) {
							getBatteryLevel();
							try {

								ConnectionDetector cd = new ConnectionDetector(
										getApplicationContext());
								SemDados = (TextView) findViewById(R.id.Dados);
								Boolean isInternetPresent = cd
										.isConnectingToInternet(); // true or
								// false
								if (num == 0) {
									if (isInternetPresent == false) {
										num = 1;
										Thread t = new Thread("Thread1") {
											@Override
											public void run() {

												runOnUiThread(new Runnable() {
													public void run() {
														ativo0 = false;
														ativo1 = false;
														ativo2 = false;
														/*
														 * File image = new
														 * File(
														 * "/mnt/sdcard/cartaoprograma/map.png"
														 * );
														 * img.setImageBitmap(
														 * BitmapFactory
														 * .decodeFile
														 * (image.getAbsolutePath
														 * ()));
														 */
														SemDados.setBackgroundResource(R.drawable.nonetwork);
													}
												});
											}
										};
										t.start();
									}
								}

								if (num == 1) {
									if (isInternetPresent == true) {
										num = 0;
										SemDados.setBackgroundResource(0);
										img.setImageBitmap(null);
										// R.drawable.networkok);

									}
								}

							} catch (Exception e) {
								// TODO Auto-generated catch block
							}
						}
					}
				});
			}
		};
		timer.schedule(doAsynchronousTask, 0, 10000);
	}

	public void Createimagemap() {
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		Boolean isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {
			if (imagemap == false) {
				imagemap = true;
				myMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
					public void onMapLoaded() {
						myMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
							public void onSnapshotReady(Bitmap bitmap) {
								OutputStream out = null;
								try {
									out = new FileOutputStream(
											"/mnt/sdcard/cartaoprograma/map.png");
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								bitmap.compress(Bitmap.CompressFormat.PNG, 90,
										out);
							}
						});
					}
				});
			}
		}
	}

	//

	public void callAsynchronousTask2(boolean b) {

		final Handler handler = new Handler();
		Timer timer = new Timer();
		if (b) {
			TimerTask doAsynchronousTask = new TimerTask() {
				@Override
				public void run() {
					handler.post(new Runnable() {
						public void run() {
							if (stop == 1) {
								System.out.println("=============xx===");
							}
							if (stop == 0) {
								try {

									Thread t = new Thread("Thread1") {
										@Override
										public void run() {

											runOnUiThread(new Runnable() {
												public void run() {
													// caminhoPercorrido();
													logx(10, ocx);
													check();
													// getBatteryLevel();
												}

											});

										}
									};
									t.start();

								} catch (Exception e) {
									// TODO Auto-generated catch block
								}
							}
						}
					});
				}
			};
			timer.schedule(doAsynchronousTask, 0, 5000);
		} else {

			timer.cancel();
			timer.purge();
			timer = null;
			System.out.println("=============timer is ===" + timer);
			return;
		}
	}

	public void callAsynchronousTask3(boolean b) {
		final Handler handler = new Handler();
		Timer timer = new Timer();
		if (b) {
			TimerTask doAsynchronousTask = new TimerTask() {
				@Override
				public void run() {
					handler.post(new Runnable() {
						public void run() {
							if (stop == 1) {
								System.out.println("=============yy===");
							}
							if (stop == 0) {
								try {

									Thread t = new Thread("Thread1") {
										@Override
										public void run() {

											runOnUiThread(new Runnable() {
												public void run() {
													atualizarNotificacao();

												}

											});

										}
									};
									t.start();

								} catch (Exception e) {
									// TODO Auto-generated catch block
								}
							}
						}
					});
				}
			};
			timer.schedule(doAsynchronousTask, 0, 30000);
		} else {

			timer.cancel();
			timer.purge();
			timer = null;
			System.out.println("=============timer is ===" + timer);
			return;
		}
	}

	// /
	/*
	 * O aplicativo deverá enviar notificações a respeito de: Alerta de
	 * suspeitos na área, com um retrato falado. Alerta de roubo de veículos,
	 * com a descrição do veículo em questão.
	 */
	private static final String KEY_NOTIFICATION = "notification";
	private static final String KEY_id = "id";
	private static final String KEY_title = "title";
	private static final String KEY_description = "description";
	private static final String KEY_created = "created";
	private static final String KEY_modified = "modified";
	private static final String KEY_created_by = "created_by";
	private static final String KEY_ntDescription = "ntDescription";
	public int idLast;
	NodeList nl;
	int notf=0;

	public void atualizarNotificacao() {
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		Boolean isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {
			System.out.println("Notificacao: ");
			new Thread(new Runnable()

			{
				public void run() {
					if (LoginActivity.idCard.equals("test")) {
						notf++;
						String title = "Title:Notificacao"+notf;
						String description = "Desc:Notificacao"+notf;
						String created = "Data"+notf;
						String modified ="Notificacao"+notf;
						String created_by = "Notificacao"+notf;
						String ntDescription = "Nt:Notificacao";
							item1 = new ItemListView(title, 0);
							item1.setTexto(ntDescription+notf);
							item1.setTexto2(description + "\n"
									+ created);
							itens2.add(0, item1);
							System.out.println("Notificacaox: "
									+ item1.getTexto2());
							notification(title, ntDescription);
							runOnUiThread(new Runnable() {
								public void run() {
									adapterListView
											.notifyDataSetChanged();
								}
							});



					}
					if (!LoginActivity.idCard.equals("test")) {
					String url1 = "http://productiveinc.com/xmlObterNotificacoes.php?idCard="
							+ LoginActivity.idMap + "&idLast=" + idLast;
					ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

					XMLParser parser = new XMLParser();
					String xml = parser.getXmlFromUrl(url1); // getting XML
					if (xml == null) {

					} else {
						Document doc = parser.getDomElement(xml); // getting DOM
						// element

						try {
							nl = doc.getElementsByTagName(KEY_NOTIFICATION);
						} catch (NullPointerException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();

						}
						if (nl != null) {
							for (int i = 0; i < nl.getLength(); i++) {

								HashMap<String, String> map = new HashMap<String, String>();
								Element e = (Element) nl.item(i);
								map.put(KEY_id, parser.getValue(e, KEY_id));
								map.put(KEY_title,
										parser.getValue(e, KEY_title));
								map.put(KEY_description,
										parser.getValue(e, KEY_description));
								map.put(KEY_created,
										parser.getValue(e, KEY_created));
								map.put(KEY_modified,
										parser.getValue(e, KEY_modified));
								map.put(KEY_created_by,
										parser.getValue(e, KEY_created_by));
								map.put(KEY_ntDescription,
										parser.getValue(e, KEY_ntDescription));
								if (idLast < Integer.parseInt(map.put(KEY_id,
										parser.getValue(e, KEY_id)))) {
									idLast = Integer.parseInt(map.put(KEY_id,
											parser.getValue(e, KEY_id)));
								}
								String title = map.put(KEY_title,
										parser.getValue(e, KEY_title));
								String description = map.put(KEY_description,
										parser.getValue(e, KEY_description));
								String created = map.put(KEY_created,
										parser.getValue(e, KEY_created));
								String modified = map.put(KEY_modified,
										parser.getValue(e, KEY_modified));
								String created_by = map.put(KEY_created_by,
										parser.getValue(e, KEY_created_by));
								String ntDescription = map.put(
										KEY_ntDescription,
										parser.getValue(e, KEY_ntDescription));
								System.out.println("Notificacaox: "
										+ description + idLast);
								if (title.equals("")) {
								} else {
									item1 = new ItemListView(title, 0);
									item1.setTexto(ntDescription);
									item1.setTexto2(description + "\n"
											+ created);
									itens2.add(0, item1);
									System.out.println("Motificacaox: "
											+ item1.getTexto2());
									notification(title, ntDescription);
									runOnUiThread(new Runnable() {
										public void run() {
											adapterListView
													.notifyDataSetChanged();
										}
									});

								}
							}

						}
					}
				}}
			}).start();
		}
	}

	private String getDirectionsUrl(LatLng origin, LatLng dest) {
		if (origin != null && dest != null) {
			String str_origin = "origin=" + origin.latitude + ","
					+ origin.longitude;
			String str_dest = "destination=" + dest.latitude + ","
					+ dest.longitude;
			String sensor = "sensor=false";
			String parameters = str_origin + "&" + str_dest + "&" + sensor;
			String output = "json";

			String url = "https://maps.googleapis.com/maps/api/directions/"
					+ output + "?" + parameters;

			return url;
		} else
			return null;
	}

	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			urlConnection = (HttpURLConnection) url.openConnection();

			urlConnection.connect();

			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception download url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	private class DownloadTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... url) {

			String data = "";

			try {

				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			ParserTask parserTask = new ParserTask();

			parserTask.execute(result);
		}
	}

	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {

			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				DirectionsJSONParser parser = new DirectionsJSONParser();

				routes = parser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {
			ArrayList<LatLng> points = null;
			PolylineOptions lineOptions = null;
			MarkerOptions markerOptions = new MarkerOptions();

			for (int i = 0; i < result.size(); i++) {
				points = new ArrayList<LatLng>();
				lineOptions = new PolylineOptions();

				List<HashMap<String, String>> path = result.get(i);

				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				lineOptions.addAll(points);
				lineOptions.width(linewidth);
				lineOptions.color(Color.parseColor(Config.colorLinhaDaRota));
			}

			myMap.addPolyline(lineOptions);
		}
	}

	public void getBatteryLevel() {
		Intent batteryIntent = registerReceiver(null, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));
		int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
		float bnow = ((float) level / (float) scale) * 100.0f;
		String bn = String.valueOf(bnow);
		System.out.println("Bateria: " + bn);
		Log.d("Bateria", bn);
		if (bnow > 70) {
			profile = 0;
			batteryProfile();
		}
		if ((bnow < 70) && (bnow > 40)) {
			profile = 1;
			batteryProfile();
		}
		if (bnow < 40) {
			profile = 2;
			batteryProfile();
		}

	}

	public void batteryProfile() {
		if (profile == 0) {
			if (ativo0 == false) {

				perfil = "Alta-Precisão";
				ativo0 = true;
				ativo1 = false;
				ativo2 = false;
				System.out.println("Profile: " + "0");
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				setMobileDataEnabled(this, true);
				lp.screenBrightness = 1.0f;
				myCriteria.setAccuracy(Criteria.ACCURACY_FINE);
				WifiManager wifiManager = (WifiManager) this
						.getSystemService(Context.WIFI_SERVICE);
				wifiManager.setWifiEnabled(true);
				getWindow().setAttributes(lp);

				Log.d("Bateria", perfil);
			}

		}
		if (profile == 1) {
			if (ativo1 == false) {
				ativo0 = false;
				ativo1 = true;
				ativo2 = false;
				perfil = "Geral";
				System.out.println("Profile: " + "1");
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				setMobileDataEnabled(this, true);
				lp.screenBrightness = 0.8f;
				myCriteria.setAccuracy(Criteria.ACCURACY_COARSE);
				WifiManager wifiManager = (WifiManager) this
						.getSystemService(Context.WIFI_SERVICE);
				wifiManager.setWifiEnabled(true);
				getWindow().setAttributes(lp);
			}

		}
		if (profile == 2) {
			if (ativo2 == false) {
				ativo0 = false;
				ativo1 = false;
				ativo2 = true;
				perfil = "Econômico";
				System.out.println("Profile: " + "2");
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.screenBrightness = 0.5f;
				myCriteria.setAccuracy(Criteria.POWER_LOW);
				WifiManager wifiManager = (WifiManager) this
						.getSystemService(Context.WIFI_SERVICE);
				wifiManager.setWifiEnabled(false);
				setMobileDataEnabled(this, true);
				getWindow().setAttributes(lp);
			}

		}
		if (profile == 3) {
			perfil = "Alternativo";
			System.out.println("Profile: " + "3");
			setMobileDataEnabled(this, false);
			WifiManager wifiManager = (WifiManager) this
					.getSystemService(Context.WIFI_SERVICE);
			wifiManager.setWifiEnabled(false);
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			lp.screenBrightness = 0.5f;

			myCriteria.setAccuracy(Criteria.POWER_MEDIUM);

			profile = -1;
		}
	}

	Boolean ajuda;

	public Boolean Ajuda(View v) {
		if (!ajuda) {
			aj.setBackgroundColor(Color.RED);
			lWebView.clearView();
			lWebView.setVisibility(View.VISIBLE);
			lWebView.loadUrl("file:///android_asset/target/target.html");
			int ZOOM_LEVEL = 100;
			lWebView.setInitialScale(ZOOM_LEVEL);
			ajuda = true;
			return false;
		}
		if (ajuda) {
			lWebView.clearView();
			lWebView.setVisibility(View.GONE);
			aj.setBackgroundColor(Color.parseColor("#428BCA"));
			ajuda = false;
			return false;
		}
		return false;

	}

	WebView lWebView;

	public void Manch(View v) {
		spinner1.performClick();

	}

	private void setMobileDataEnabled(Context context, boolean enabled) {
		final ConnectivityManager conman = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		Class conmanClass = null;
		try {
			conmanClass = Class.forName(conman.getClass().getName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Field connectivityManagerField = null;
		try {
			connectivityManagerField = conmanClass.getDeclaredField("mService");
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connectivityManagerField.setAccessible(true);
		Object connectivityManager = null;
		try {
			connectivityManager = connectivityManagerField.get(conman);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Class connectivityManagerClass = null;
		try {
			connectivityManagerClass = Class.forName(connectivityManager
					.getClass().getName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Method setMobileDataEnabledMethod = null;
		try {
			setMobileDataEnabledMethod = connectivityManagerClass
					.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setMobileDataEnabledMethod.setAccessible(true);

		try {
			setMobileDataEnabledMethod.invoke(connectivityManager, enabled);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initLocation(Location location) {

		LatLng locLatLng = new LatLng(
				Double.parseDouble(LoginActivity.latcenter),
				Double.parseDouble(LoginActivity.lngcenter));

		myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locLatLng, 15));
	}

	boolean ocx;

	public boolean Ocorrencia(View v) {

		TextView t = (TextView) findViewById(R.id.my_text_view);
		if (emocorrencia == false) {
			emocorrencia = true;
			ocorrencia.setBackgroundColor(Color.RED);
			t.setGravity(Gravity.CENTER);
			t.setText("EM OCORRENCIA");
			t.setTextColor(Color.RED);
			t.setTextSize(30);
			emOcorrencia(LoginActivity.idCard, lat, lng, Logs.Data(),
					Logs.Hora(), 1);
			logx(4, ocx);
			ocx = false;
			return true;
			/*
			 * O Aplicativo terá a opção chamada Em Ocorrência, sendo acionada
			 * sempre que a guarnição precisar abandonar a rota temporariamente.
			 * A Aplicação cessará o registro de movimentação no mapa e guardará
			 * o ponto no mapa onde esta opção fo ativada. A Aplicação deverá
			 * notificar o CIOPE sobre a guarnição na condição de atendimento de
			 * determinada ocorrência
			 */
		}
		if (emocorrencia == true) {
			/*
			 * A Opção Ocorrência Atendida, notificará o CIOPE do retorno da
			 * guarnição para a ronda e adequará no mapa o novo ponto de partida
			 * para a guarnição.
			 */
			ocx = true;
			emOcorrencia(LoginActivity.idCard, lat, lng, Logs.Data(),
					Logs.Hora(), 0);
			logx(4, ocx);

			emocorrencia = false;
			t.setGravity(Gravity.CENTER);
			t.setText("");
			ocorrencia.setBackgroundColor(Color.parseColor("#428BCA"));
			rota(LASTLOCATION, LASTMARKER);
			return false;
		}
		return true;
	}

	public void circle() {
		System.out.println("Color change" + Config.colorLocalizacaoAtual);
		if (myCircle == null) {
			CircleOptions circleOptions = new CircleOptions()
					.center(LASTLOCATION)
					// set center
					.radius(accuracy)
					// set radius in meters
					.fillColor(Color.parseColor(Config.colorLocalizacaoAtual))
					.strokeColor(Color.BLACK).strokeWidth(1);

			myCircle = myMap.addCircle(circleOptions);
		} else {
			myCircle.setCenter(LASTLOCATION);
			myCircle.setRadius(accuracy);
			myCircle.setFillColor(Color
					.parseColor(Config.colorLocalizacaoAtual));
		}
	}

	String descricaocartao;
	String descricaoarea;
	String descricaorota;
	String idrota;
	String bemvindo;
	String start, finish;

	public void descInicial() {

		carregando2 = new ProgressDialog(MainActivity.this, R.style.MyTheme);
		descricaocartao = "Descrição do cartão: "
				+ LoginActivity.cardDescription + "\n\n";
		descricaorota = "Descrição da rota: " + LoginActivity.routeDescription
				+ "\n\n";
		descricaoarea = "Descrição da área: " + LoginActivity.areaDescription
				+ "\n\n";
		bemvindo = "\n\n" + descricaocartao + descricaorota + descricaoarea;
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {

		new AlertDialog.Builder(MainActivity.this, R.style.MyTheme2)
				.setTitle("Bem vindo ao Aplicativo do Cartão Programa")
				.setMessage(bemvindo)
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
								callAsynchronousTask3(true);
							}
						}).show();
		createListView();
	}
}, 2000);


	}

	ArrayList<LatLng> pointsrota;
	Button aj;
	int stop;

	public void iniciar() {
		stop = 0;
		Logs = new Logs();
		TIMETOEXIT = difd();

		mp = false;
		idLast = 0;
		Config.colorCaminhoPercorrido = "BLUE";
		Config.colorLinhaDaRota = "GREEN";
		Config.colorAreaDeAtuacao = "GREEN";
		Config.colorMancha = "RED";
		Config.colorLocalizacaoAtual = "BLUE";
		pointsrota = new ArrayList<LatLng>();
		ocx = true;
		imagemap = false;
		tvLocInfo = (TextView) findViewById(R.id.locinfo);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		profile = 0;
		emocorrencia = false;
		FragmentManager myFragmentManager = getFragmentManager();
		MapFragment myMapFragment = (MapFragment) myFragmentManager
				.findFragmentById(R.id.map);
		myMap = myMapFragment.getMap();
		Calendar c = Calendar.getInstance();
		int minutes = c.get(Calendar.MINUTE);
		String min = "tempo=" + minutes;
		Log.d("Background Task", min);
		myMap.setMyLocationEnabled(true);
		myMap.getUiSettings().setAllGesturesEnabled(true);
		myMap.getUiSettings().setZoomGesturesEnabled(true);
		myMap.getUiSettings().setZoomControlsEnabled(false);
		myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		config = (Button) findViewById(R.id.config);
		ocorrencia = (Button) findViewById(R.id.ocorrencia);
		Button mac = (Button) findViewById(R.id.mancha);
		aj = (Button) findViewById(R.id.help);
		config.setBackgroundColor(Color.parseColor("#428BCA"));
		mac.setBackgroundColor(Color.parseColor("#428BCA"));
		ocorrencia.setBackgroundColor(Color.parseColor("#428BCA"));
		aj.setBackgroundColor(Color.parseColor("#428BCA"));

		startService(new Intent(this, SendService.class));
		config.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(),
						pa.pm.cartaoprograma2.Config.class);
				startActivityForResult(i, 1);

			}
		});

		db = new DataBaseHelper(getApplicationContext());
		logx(4, ocx);
		myCriteria = new Criteria();
		batteryProfile();
		myLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		listView = (ListView) findViewById(R.id.list);
		listView2 = (ListView) findViewById(R.id.list2);
		listView2.setOnItemClickListener(this);
		createListView();
		createListView2();
		addItemsOnSpinner1();
		addListenerOnButton();
		descInicial();
		lWebView = (WebView) findViewById(R.id.webView);
		lWebView.clearView();
		lWebView.setVisibility(View.GONE);
		ajuda = false;
		img = (ImageView) findViewById(R.id.image);
		Location loc = new Location("dummyprovider");
		//
		if(LoginActivity.idCard.equals("local")){initLocation(loc);}
		else{
			myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-1.45684039, -48.48398566),15));

		}
		//
		myMap.setInfoWindowAdapter(new InfoWindowAdapter() {

			@Override
			public View getInfoWindow(Marker arg0) {
				return null;
			}

			@Override
			public View getInfoContents(Marker arg0) {
				View v = getLayoutInflater().inflate(
						R.layout.info_window_layout, null);

				TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
				TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
				String markername = arg0.getTitle();
				String markerdesc = arg0.getSnippet();
				tvLat.setText(markername);
				tvLat.setTextColor(Color.BLACK);
				tvLng.setText(markerdesc);
				tvLng.setTextColor(Color.BLACK);
				return v;

			}
		});

		cf = new CreateFolders();

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Createimagemap();
				callAsynchronousTask(true);

			}
		}, 40000);
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				profile = 1;
				batteryProfile();
				cf.delete();
				stop = 1;
				callAsynchronousTask(false);
				callAsynchronousTask2(false);
				callAsynchronousTask3(false);
				ConnectionDetector cd = new ConnectionDetector(
						getApplicationContext());
				Boolean isInternetPresent = cd.isConnectingToInternet();

				while (isInternetPresent == false) {
					isInternetPresent = cd.isConnectingToInternet();

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					continue;
				}
				finish();

			}
		}, TIMETOEXIT);

	}

	public boolean isRunning(Context ctx) {
		ActivityManager activityManager = (ActivityManager) ctx
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasks = activityManager
				.getRunningTasks(Integer.MAX_VALUE);

		for (RunningTaskInfo task : tasks) {
			if (ctx.getPackageName().equalsIgnoreCase(
					task.baseActivity.getPackageName()))
				return true;
		}

		return false;
	}

	/*
	 * O aplicativo se auto-verificará quanto a integridade de seu código fonte,
	 * na identificação de qualquer alteração, a aplicação suspenderá o uso das
	 * funcionalidades relacionadas ao cartão programa presentes no aparelho,
	 * devendo-se desta forma, reinstalar o aplicativo.
	 */
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

	static NotificationManager notificationManager;

	public void notification(String title, String message) {
		notificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Uri soundUri = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				getApplicationContext()).setSmallIcon(R.drawable.pmicon2)
				.setContentTitle(title).setContentText(message)
				.setSound(soundUri); // This sets the sound to play

		notificationManager.notify(0, mBuilder.build());

	}

	public static void clean() {
		if (notificationManager != null) {
			notificationManager.cancelAll();
		}
		CreateFolders cf;
		cf = new CreateFolders();
		cf.createFolder();
		cf.delete();
		cf.createFolder();

	}

	private static final String KEY_CARD = "ocorrencia";

	public void emOcorrencia(final String idx, final double ltx,
			final double lnx, final String d, final String h, final int ok) {
		new Thread(new Runnable()

		{
			public void run() {
				String url1 = "http://productiveinc.com/xmlInformarOcorrencia.php?idCard="
						+ idx
						+ "&lat="
						+ ltx
						+ "&lng="
						+ lnx
						+ "&data="
						+ d
						+ "&hora=" + h + "&ok=" + ok;
				ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
				XMLParser parser = new XMLParser();
				String xml = parser.getXmlFromUrl(url1); // getting XML
				if (xml == null) {
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							emOcorrencia(idx, ltx, lnx, d, h, ok);

						}
					}, 10000);
				} else {
					try {
						Document doc = parser.getDomElement(xml); // getting DOM

						NodeList nlx = doc.getElementsByTagName(KEY_CARD);
						System.out.println("Lenght: " + nl.getLength());
						if (nlx.getLength() < 1) {
							handler.postDelayed(new Runnable() {
								@Override
								public void run() {
									emOcorrencia(idx, ltx, lnx, d, h, ok);

								}
							}, 10000);
						}
						//
					} catch (NullPointerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								emOcorrencia(idx, ltx, lnx, d, h, ok);

							}
						}, 10000);
					}
				}
			}
		}).start();
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this, R.style.MyTheme2)
				.setTitle("Sair")
				.setMessage("Deseja sair?")
				.setPositiveButton("Sim",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								profile = 1;
								batteryProfile();
								cf.delete();
								stop = 1;
								callAsynchronousTask(false);
								callAsynchronousTask2(false);
								callAsynchronousTask3(false);
								ConnectionDetector cd = new ConnectionDetector(
										getApplicationContext());
								Boolean isInternetPresent = cd
										.isConnectingToInternet(); // true

								while (isInternetPresent == false) {
									isInternetPresent = cd
											.isConnectingToInternet();
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									continue;
								}
								ex = 1;
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

	public static int ex = 0;

	public int difd() {
		System.out.print("days, ");
		/*
		 * String dateStart = Logs.Data()+ " "+Logs.Hora(); String dateStop
		 * =LoginActivity.finish;
		 */
		String dateStart = LoginActivity.finish;
		String dateStop = Logs.Data() + " " + Logs.Hora();
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
			int ti = (int) diff;
			System.out.print(diffDays + " days, \n");
			System.out.print(diffHours + " hours, ");
			System.out.print(diffMinutes + " minutes, ");
			System.out.print(diffSeconds + " seconds.");
			System.out.print("ti:" + ti);
			return ti;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 999999;

	}

}

