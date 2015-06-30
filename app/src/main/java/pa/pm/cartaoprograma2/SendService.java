package pa.pm.cartaoprograma2;

import java.util.Timer;
import java.util.TimerTask;

import pa.pm.log.CreateFolders;
import pa.pm.localdb.DataBaseHelper;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

public class SendService extends Service {
	public SendService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
	}

	@Override
	public void onDestroy() {
	}

	private Handler handler = new Handler();
	static DataBaseHelper db;

	@Override
	public int onStartCommand(Intent intent, int flags, int startid) {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// MainActivity.notificationManager.cancelAll();
				db = new DataBaseHelper(getApplicationContext());
				db.send();
				clx();

			}
		}, MainActivity.TIMETOEXIT);
		final Handler handler = new Handler();
		Timer timer = new Timer();

		TimerTask doAsynchronousTask = new TimerTask() {

			@Override
			public void run() {
				if (MainActivity.ex == 1) {
					db = new DataBaseHelper(getApplicationContext());
					db.send();
					clx();
				}
			}
		};
		timer.schedule(doAsynchronousTask, 0, 10000);

		return startid;

	}

	private void clx() {
		String DATABASE_NAME = "LOGSX";
		this.deleteDatabase(DATABASE_NAME);
		CreateFolders cf;
		cf = new CreateFolders();
		cf.createFolder();
		cf.delete();
		cf.createFolder();
		this.stopSelf();
	}

}