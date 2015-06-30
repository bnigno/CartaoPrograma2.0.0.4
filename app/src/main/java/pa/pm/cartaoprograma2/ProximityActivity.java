package pa.pm.cartaoprograma2;

import android.app.Activity;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

public class ProximityActivity extends Activity {

	String notificationTitle;
	String notificationContent;
	String tickerMessage;
	static boolean prx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		View wantedView = MainActivity.listView.getChildAt(0);
		boolean proximity_entering = getIntent().getBooleanExtra(
				LocationManager.KEY_PROXIMITY_ENTERING, true);
		prx = false;
		if (proximity_entering) {

			wantedView.setBackgroundColor(Color.GREEN);
			prx = true;

		} else {

		}

		finish();
	}
}