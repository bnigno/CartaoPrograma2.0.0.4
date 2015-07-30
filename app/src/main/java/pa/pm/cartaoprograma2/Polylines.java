package pa.pm.cartaoprograma2;

import java.util.ArrayList;

import android.graphics.Color;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;



public class Polylines{
	private String name;

	public static GoogleMap map;

	public Polylines(String name) {
		set(name);
	}

	public Polylines() {
		// TODO Auto-generated constructor stub
	}

	public synchronized void set(String name) {
		if (name==null) throw new NullPointerException();

		this.name = name;

	}

	public synchronized String getName(){
		return this.name;
	}

	static ArrayList<LatLng> points = new ArrayList<LatLng>();
	static PolylineOptions lineOptions =  new PolylineOptions();
	static Polyline polyline;

	public static void addpolylines(final GoogleMap myMap,LatLng Location) {
		points.add(Location);

	}

	public static void addall(final GoogleMap myMap, String cor){

		map = myMap;//será utilizado para mudança de cor

		polyline = myMap.addPolyline(lineOptions
			.addAll(points)
			.color(Color.parseColor(cor)));
	}

	public static void addColor(String c){// é utilizada em Config na função "salvar"
		polyline = map.addPolyline(lineOptions
				.color(Color.parseColor(c)));
	}
}