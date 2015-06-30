package pa.pm.cartaoprograma2;

import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import pa.pm.localdb.XMLParser;

import android.os.Handler;

public class Card {
	private static final String KEY_CARD = "card";
	private static final String KEY_IDCARD = "idCard";
	private static final String KEY_IDMAP = "idMap";
	private static final String KEY_START = "start";
	private static final String KEY_FINISH = "finish";
	private static final String KEY_LATCENTER = "latCenter";
	private static final String KEY_LNGCENTER = "lngCenter";
	private static final String KEY_CARDDESCRIPTION = "cardDescription";
	private static final String KEY_ROUTEDESCRIPTION = "routeDescription";
	private static final String KEY_AREADESCRIPTION = "areaDescription";
	private static final String KEY_MSG = "msg_error";
	private static final String KEY_ERROR = "error";
	static NodeList nl;
	private static Handler handler = new Handler();
	static int x = 0;

	public static void getCard(final String url1) {
		new Thread(new Runnable()

		{
			public void run() {
				XMLParser parser = new XMLParser();
				String xml = null;
				x = 0;
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						x = 5;
					}
				}, 3000);
				while (x == 0) {
					xml = parser.getXmlFromUrl(url1); // getting XML
					if (xml != null) {
						x = 5;
						System.out.println("xml: " + xml);
					}
				}
				if (xml == null) {
				} else {
					Document doc = parser.getDomElement(xml); // getting DOM
																// element
					try {
						nl = doc.getElementsByTagName(KEY_CARD);
					} catch (NullPointerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					if (nl != null) {
						System.out.println("Notificacao: " + nl.getLength());
						for (int i = 0; i < nl.getLength(); i++) {
							System.out.println("xml: " + xml);
							HashMap<String, String> map = new HashMap<String, String>();
							Element e = (Element) nl.item(i);
							map.put(KEY_IDCARD, parser.getValue(e, KEY_IDCARD));
							map.put(KEY_START, parser.getValue(e, KEY_START));
							map.put(KEY_FINISH, parser.getValue(e, KEY_FINISH));
							map.put(KEY_IDMAP, parser.getValue(e, KEY_IDMAP));
							map.put(KEY_LATCENTER,
									parser.getValue(e, KEY_LATCENTER));
							map.put(KEY_LNGCENTER,
									parser.getValue(e, KEY_LNGCENTER));
							map.put(KEY_CARDDESCRIPTION,
									parser.getValue(e, KEY_CARDDESCRIPTION));
							map.put(KEY_ROUTEDESCRIPTION,
									parser.getValue(e, KEY_ROUTEDESCRIPTION));
							map.put(KEY_AREADESCRIPTION,
									parser.getValue(e, KEY_AREADESCRIPTION));
							map.put(KEY_ERROR, parser.getValue(e, KEY_ERROR));
							map.put(KEY_MSG, parser.getValue(e, KEY_MSG));

							LoginActivity.idCard = map.put(KEY_IDCARD,
									parser.getValue(e, KEY_IDCARD));
							System.out.println("Idcard: "
									+ LoginActivity.idCard);
							LoginActivity.start = map.put(KEY_START,
									parser.getValue(e, KEY_START));
							LoginActivity.finish = map.put(KEY_FINISH,
									parser.getValue(e, KEY_FINISH));
							LoginActivity.idMap = map.put(KEY_IDMAP,
									parser.getValue(e, KEY_IDMAP));
							LoginActivity.latcenter = map.put(KEY_LATCENTER,
									parser.getValue(e, KEY_LATCENTER));
							LoginActivity.lngcenter = map.put(KEY_LNGCENTER,
									parser.getValue(e, KEY_LNGCENTER));
							LoginActivity.cardDescription = map.put(
									KEY_CARDDESCRIPTION,
									parser.getValue(e, KEY_CARDDESCRIPTION));
							System.out.println("Size: " + LoginActivity.finish);
							LoginActivity.routeDescription = map.put(
									KEY_ROUTEDESCRIPTION,
									parser.getValue(e, KEY_ROUTEDESCRIPTION));
							LoginActivity.areaDescription = map.put(
									KEY_AREADESCRIPTION,
									parser.getValue(e, KEY_AREADESCRIPTION));
							LoginActivity.error = map.put(KEY_ERROR,
									parser.getValue(e, KEY_ERROR));
							LoginActivity.msg_error = map.put(KEY_MSG,
									parser.getValue(e, KEY_MSG));
							System.out.println("Size: " + LoginActivity.idMap
									+ LoginActivity.latcenter
									+ LoginActivity.lngcenter + "   "
									+ LoginActivity.idCard);

						}

					}
				}
			}
		}).start();
	}

}