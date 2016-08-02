package pa.pm.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SyncService extends Service {
    private static final String TAG = "SyncService";

    private static SyncAdapter sSyncAdapter = null;
    private static final Object sSyncAdapterLock = new Object();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "SyncService created");
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = new SyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "SyncService destroyed");
    }


    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}