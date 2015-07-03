package pa.pm.cartaoprograma2;

import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import pa.pm.localdb.XMLParser;

import android.os.Handler;
import android.os.Looper;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Markers {
	public LatLng Location1;
	public LatLng Location2;
	public LatLng Location3;
	String desc = "Descrição ";
	private String name;
	public String f;
	public String fx;
	public String hx;
	double lt;
	double ln;
	String l;
	String de;
	NodeList nl;
	int h;
	int n;
	float m;
	int i;
	static final String KEY_ITEM = "point"; // parent node
	static final String KEY_DESCRIPTION = "description";
	static final String KEY_ARRIVAL = "arrival";
	static final String KEY_LEAVING = "leaving";
	static final String KEY_LAT = "lat";
	static final String KEY_LNG = "lng";
	static final String KEY_TYPE = "type";
	String DESCRIPTION;
	String ARRIVAL;
	String LEAVING;
	String LAT;
	String LNG;
	String TYPE;
	ItemListView item1;
	String textlist;
	

	private Handler handler = new Handler();

	public Markers(String name) {
		set(name);
	}

	public Markers() {
		// TODO Auto-generated constructor stub
	}

	public synchronized void set(String name) {
		if (name == null)
			throw new NullPointerException();

		this.name = name;

	}

	public synchronized String getName() {
		return this.name;
	}


	/*Adicionar marcadores ao mapa*/
	public void addMarkers(final GoogleMap myMap, LatLng Location,
			String title, String snippet, int d) {

		if (d == 0) {
			/*Se 0, adiciona o ponto como ponto base*/
			myMap.addMarker(new MarkerOptions().position(Location).title(title)
					.snippet(snippet)

					);
		}
		if (d == 1) {
			/*Se 0, adiciona o ponto como ponto global*/
			myMap.addMarker(new MarkerOptions()
			.position(Location)
			.title(title)
			.snippet(snippet)
			.icon(BitmapDescriptorFactory
					.fromResource(R.drawable.marker)));

		}

	}



	public void getMarkers(final GoogleMap myMap) {
		if (myMap != null) {
			new Thread(new Runnable()
			{
				public void run() {
					/* Teste dados locais */
					if (LoginActivity.idCard.equals("test")) {
						n = 0;
						m = -1;
						while (n < 8) {
							if (n > m) {
								m = n;
								System.out.println("MarkerLL: " + DESCRIPTION);
								System.out.println("Qui" + TYPE);
								testMarker(n);

								if (TYPE.equals("basePoint")) {

									String de = "\nDescrição:" + DESCRIPTION;
									f = de;
									String hi = "Hora inicial:" + ARRIVAL;
									String hf = "\nHora Final:" + LEAVING;
									hx = hi + hf;
									System.out.println("Markera: " + i + "   "
											+ DESCRIPTION);
									System.out.println("Markera: " + i + "   "
											+ ARRIVAL);
									System.out.println("Markera: " + i + "   "
											+ LEAVING);
									MainActivity.textlistx = "HI:" + ARRIVAL
											+ "\nHF:" + LEAVING + "\nDESC:"
											+ DESCRIPTION;
									lt = Double.parseDouble(LAT);
									ln = Double.parseDouble(LNG);
									Location1 = new LatLng(Double
											.parseDouble(LAT), Double
											.parseDouble(LNG));

									Handler handler = new Handler(Looper
											.getMainLooper());
									handler.post(new Runnable() {
										@Override
										public void run() {

											item1 = new ItemListView(
													MainActivity.textlistx, 0);
											item1.setLoc(lt, ln);
											MainActivity.itens.add(item1);
											MainActivity.adapterListView
											.notifyDataSetChanged();
											addMarkers(myMap, Location1, f, hx,
													0);
											n++;
											h++;

										}
									});
								}
								if (TYPE.equals("globalPoint")) {
									l = "Nome:\n" + DESCRIPTION;

									de = "Descrição:\n" + DESCRIPTION;

									Location2 = new LatLng(Double
											.parseDouble(LAT), Double
											.parseDouble(LNG));
									System.out.println("Marker: " + i + "   "
											+ Location2);
									Handler handler = new Handler(Looper
											.getMainLooper());
									handler.post(new Runnable() {
										@Override
										public void run() {
											addMarkers(myMap, Location2, l, de,
													1);

											n++;

										}
									});
								}
								if (TYPE.equals("routePoint")) {

									Location3 = new LatLng(Double
											.parseDouble(LAT), Double
											.parseDouble(LNG));

									Handler handler = new Handler(Looper
											.getMainLooper());
									handler.post(new Runnable() {
										@Override
										public void run() {
											Polylines.addpolylines(myMap,
													Location3);
											System.out.println("Markeru: " + i
													+ "   " + Location3);

											n++;


										}
									});
								}
							}
						}
						System.out.println("Fim");
						Handler handler = new Handler(Looper.getMainLooper());
						handler.post(new Runnable() {
							@Override
							public void run() {
								Polylines.addall(myMap);

							}
						});
					}

					/* Fim dados locais */
					if (!LoginActivity.idCard.equals("test")) {
						String url1 = "http://productiveinc.com/xmlObterPontos.php?idCard="
								+ LoginActivity.idCard;

						ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

						XMLParser parser = new XMLParser();
						String xml = parser.getXmlFromUrl(url1); // getting XML

						if (xml == null) {
							handler.postDelayed(new Runnable() {
								@Override
								public void run() {
									getMarkers(myMap);
								}
							}, 10000);
						} else {
							Document doc=null;
							try {
								doc = parser.getDomElement(xml); // getting
								// DOM
								// element
							} catch (Exception e) {
								// TODO Auto-generated catch block
							}
							
							try {
								nl = doc.getElementsByTagName(KEY_ITEM);
							} catch (NullPointerException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								handler.postDelayed(new Runnable() {
									@Override
									public void run() {
										getMarkers(myMap);

									}
								}, 10000);
							}
							if (nl != null) {
								System.out.println("MarkerLL: "
										+ nl.getLength());
								 for (int i = 0; i < nl.getLength(); i++) {
							//	i = 0;

							//	while (i < nl.getLength()) {
									if (i > m) {
										m = i;
										try {
											HashMap<String, String> map = new HashMap<String, String>();
											Element e = (Element) nl.item(i);

											map.put(KEY_DESCRIPTION, parser
													.getValue(e,
															KEY_DESCRIPTION));
											map.put(KEY_ARRIVAL, parser
													.getValue(e, KEY_ARRIVAL));
											map.put(KEY_LEAVING, parser
													.getValue(e, KEY_LEAVING));
											map.put(KEY_LAT,
													parser.getValue(e, KEY_LAT));
											map.put(KEY_LNG,
													parser.getValue(e, KEY_LNG));
											map.put(KEY_TYPE, parser.getValue(
													e, KEY_TYPE));

											DESCRIPTION = map.put(
													KEY_DESCRIPTION,
													parser.getValue(e,
															KEY_DESCRIPTION));
											ARRIVAL = map.put(KEY_ARRIVAL,
													parser.getValue(e,
															KEY_ARRIVAL));
											LEAVING = map.put(KEY_LEAVING,
													parser.getValue(e,
															KEY_LEAVING));
											LAT = map.put(KEY_LAT,
													parser.getValue(e, KEY_LAT));
											LNG = map.put(KEY_LNG,
													parser.getValue(e, KEY_LNG));
											TYPE = map.put(KEY_TYPE, parser
													.getValue(e, KEY_TYPE));
											System.out.println("MarkerLL: "
													+ DESCRIPTION);
											System.out.println("Qui" + TYPE);
											Thread.sleep(100);
											/*
											 * while(x<1){try {
											 * Thread.sleep(100);
											 */


											if (TYPE.equals("basePoint")) {

												String de = "\nDescrição:"
														+ DESCRIPTION;
												f = de;
												String hi = "Hora inicial:"
														+ ARRIVAL;
												String hf = "\nHora Final:"
														+ LEAVING;
												hx = hi + hf;
												System.out.println("Markera: "
														+ i + "   "
														+ DESCRIPTION);
												System.out.println("Markera: "
														+ i + "   " + ARRIVAL);
												System.out.println("Markera: "
														+ i + "   " + LEAVING);
												MainActivity.textlistx = "HI:"
														+ ARRIVAL + "\nHF:"
														+ LEAVING + "\nDESC:"
														+ DESCRIPTION;
												lt = Double.parseDouble(LAT);
												ln = Double.parseDouble(LNG);
												Location1 = new LatLng(Double
														.parseDouble(LAT),
														Double.parseDouble(LNG));

												Handler handler = new Handler(
														Looper.getMainLooper());
												handler.post(new Runnable() {
													@Override
													public void run() {

														item1 = new ItemListView(
																MainActivity.textlistx,
																0);
														item1.setLoc(lt, ln);
														MainActivity.itens
														.add(item1);
														MainActivity.adapterListView
														.notifyDataSetChanged();
														addMarkers(myMap,
																Location1, f,
																hx, 0);
														h++;
													}
												});
											}
											if (TYPE.equals("globalPoint")) {

												l = "Nome:\n" + DESCRIPTION;

												de = "Descrição:\n"
														+ DESCRIPTION;

												Location2 = new LatLng(Double
														.parseDouble(LAT),
														Double.parseDouble(LNG));
												System.out
												.println("Marker: " + i
														+ "   "
														+ Location2);
												Handler handler = new Handler(
														Looper.getMainLooper());
												handler.post(new Runnable() {
													@Override
													public void run() {
														addMarkers(myMap,
																Location2, l,
																de, 1);
													}
												});
											}
											if (TYPE.equals("routePoint")) {
												System.out.println("11º AAAAAAAAAAAAAAAAA: ");


												Location3 = new LatLng(Double
														.parseDouble(LAT),
														Double.parseDouble(LNG));

												Handler handler = new Handler(
														Looper.getMainLooper());
												handler.post(new Runnable() {
													@Override
													public void run() {
														Polylines.addpolylines(
																myMap,
																Location3);
														//System.out
														//.println("Markeru: " + i + "   " + Location3);
													}
												});
											}
										} catch (InterruptedException ex) {
											// TODO Auto-generated catch block
											ex.printStackTrace();
										}
										continue;
									}

								}
								Handler handler = new Handler(Looper
										.getMainLooper());
								handler.post(new Runnable() {
									@Override
									public void run() {
										Polylines.addall(myMap);

									}
								});
							}
						}
					}
				}
			}).start();

		}
	}

	//Dados locais
	public void testMarker(int n) {
		if (n == 0) {
			DESCRIPTION = "Posto de gasolina";
			ARRIVAL = "9:00";
			LEAVING = "9:20";
			LAT = "-1.457012";
			LNG = "-48.49048734";
			TYPE = "basePoint";
		}

		if (n == 1) {
			DESCRIPTION = "Posto de gasolina II";
			ARRIVAL = "9:40";
			LEAVING = "9:50";
			LAT = "-1.45647573";
			LNG = "-48.48937154";
			TYPE = "basePoint";
		}

		if (n == 2) {
			DESCRIPTION = "Posto I";
			ARRIVAL = "9:00";
			LEAVING = "9:20";
			LAT = "-1.45928577";
			LNG = "-48.48975778";
			TYPE = "globalPoint";
		}

		if (n == 3) {
			DESCRIPTION = "Posto II";
			ARRIVAL = "9:00";
			LEAVING = "9:20";
			LAT = "-1.45829904";
			LNG = "-48.4882772";
			TYPE = "globalPoint";
		}

		if (n == 4) {
			DESCRIPTION = "Posto de gasolina";
			ARRIVAL = "9:00";
			LEAVING = "9:20";
			LAT = "-1.4584492";
			LNG = "-48.48984361";
			TYPE = "routePoint";
		}

		if (n == 5) {
			DESCRIPTION = "Posto de gasolina";
			ARRIVAL = "9:00";
			LEAVING = "9:20";
			LAT = "-1.45729086";
			LNG = "-48.49063754";
			TYPE = "routePoint";
		}

		if (n == 6) {
			DESCRIPTION = "Posto de gasolina";
			ARRIVAL = "9:00";
			LEAVING = "9:20";
			LAT = "-1.45634702";
			LNG = "-48.48941445";
			TYPE = "routePoint";
		}

		if (n == 7) {
			DESCRIPTION = "Posto de gasolina";
			ARRIVAL = "9:00";
			LEAVING = "9:20";
			LAT = "-1.4584492";
			LNG = "-48.4878695";
			TYPE = "routePoint";
		}

	}

	//

}