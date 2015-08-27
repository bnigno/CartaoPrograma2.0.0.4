package pa.pm.cartaoprograma2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings.Secure;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

public class CheckinService extends Service {

    private GPSTrackerAux gps;
    private static int checkInId;

    private static String androidId;
    private static boolean ativo = false;// flag

    private static Thread threadChekin;

    @Override
    public void onCreate() {
        Log.e("P29", "onCreate");
        setGps(new GPSTrackerAux(MainActivity.instancia));
        setCheckInId(0);
        setAndroidId(getAndroidDeviceId());
    }

    private String getAndroidDeviceId() {
        setAndroidId((Secure.getString(getBaseContext().getContentResolver(), Secure.ANDROID_ID)));
        return getAndroidId();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("P29", "onStartCommand");
        setAtivo(true);
        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        setCheckInId(0);
        setAtivo(false);
        getThreadChekin().interrupt();
        setThreadChekin(null);
    }

    public static int getCheckInId() {
        return checkInId;
    }

    public static void setCheckInId(int checkInId) {
        CheckinService.checkInId = checkInId;
    }

    public static boolean isAtivo() {
        return ativo;
    }

    public static void setAtivo(boolean ativo) {
        CheckinService.ativo = ativo;
    }

    private GPSTrackerAux getGps() {
        return gps;
    }

    private void setGps(GPSTrackerAux gps) {
        this.gps = gps;
    }

    private static String getAndroidId() {
        return androidId;
    }

    private static void setAndroidId(String androidId) {
        CheckinService.androidId = androidId;
    }

    private static Thread getThreadChekin() {
        return threadChekin;
    }

    private static void setThreadChekin(Thread threadChekin) {
        CheckinService.threadChekin = threadChekin;
    }

}
