package pa.pm.cartaoprograma2;

import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import pa.pm.localdb.XMLParser;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class Polygons {
	public static LatLng Location;
	String desc = "Descrição ";
	private String name;
	public String f;
	public String fx;
	public String hx;
	double lt;
	double ln;

	public Polygons(String name) {
		set(name);
	}

	public Polygons() {
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

	int i;
	static final String KEY_ITEM = "pointsspot"; // parent node
	static final String KEY_POINT = "point";
	static final String KEY_LAT = "lat";
	static final String KEY_LNG = "lng";
	static Polygon polygon;
	private static Handler handler = new Handler();
	static PolygonOptions lineOptions = new PolygonOptions();
	static ArrayList<LatLng> points = new ArrayList<LatLng>();

	public static void addPolygons(LatLng Location) {
		points.add(Location);

	}

	public static void addall(final GoogleMap myMap) {
		try {
		polygon = myMap.addPolygon(new PolygonOptions().addAll(points)
				.strokeColor(Color.parseColor(Config.colorMancha)));
		polygon.setFillColor(Color.parseColor(Config.colorMancha));
		} catch (NullPointerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	static int x;
	static NodeList nl;

	public static void getPolygons(final GoogleMap myMap) {
		System.out.println("Key1111: ");
		if (myMap != null) {

			new Thread(new Runnable()

			{
				public void run() {

					String url1 = "http://productiveinc.com/xmlObterMancha.php?type=2&dataStart=2014-02-01&dataFinish=2014-06-01";

					ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
					System.out.println("Key: " + url1);
					XMLParser parser = new XMLParser();
					String xml = parser.getXmlFromUrl(url1); // getting XML
					if (xml == null) {
						handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								getPolygons(myMap);

							}
						}, 10000);
					} else {
						System.out.println("Key: " + xml.toString());
						Document doc = parser.getDomElement(xml); // getting DOM
																	// element

						try {
							nl = doc.getElementsByTagName(KEY_POINT);

							for (int i = 0; i < nl.getLength(); i++) {
								x = 0;
								while (x < 1) {
									try {
										HashMap<String, String> map = new HashMap<String, String>();
										Element e = (Element) nl.item(i);

										// map.put(KEY_POINTS,
										// parser.getValue(e, KEY_POINTS));
										map.put(KEY_LAT,
												parser.getValue(e, KEY_LAT));
										map.put(KEY_LNG,
												parser.getValue(e, KEY_LNG));
										Thread.sleep(100);

										// String
										// aJsonString=map.put(KEY_POINTS,
										// parser.getValue(e, KEY_POINTS));
										String aJsonString1 = map.put(KEY_LAT,
												parser.getValue(e, KEY_LAT));
										String aJsonString2 = map.put(KEY_LNG,
												parser.getValue(e, KEY_LNG));
										System.out.println("Key: "
												+ aJsonString1 + aJsonString2);
										Location = new LatLng(
												Double.parseDouble(aJsonString1),
												Double.parseDouble(aJsonString2));
										Handler handler = new Handler(Looper
												.getMainLooper());
										handler.post(new Runnable() {
											@Override
											public void run() {
												addPolygons(Location);
												x = 1;
												System.out
														.println("Keyx: " + x);

											}
										});
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
									addall(myMap);
									x = 1;

								}
							});
						} catch (NullPointerException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							handler.postDelayed(new Runnable() {
								@Override
								public void run() {
									getPolygons(myMap);

								}
							}, 10000);
						}
						//
					}
				}
			}).start();

		}
	}

	String DESCRIPTION;
	String ARRIVAL;
	String LEAVING;
	ItemListView item1;
	String textlist;
	int ix = 0;
}