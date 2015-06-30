package pa.pm.localdb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import pa.pm.cartaoprograma2.LoginActivity;
import pa.pm.cartaoprograma2.MainActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {
	private static String TAG = "DataBaseHelper";
	private static String DB_PATH = "";
	private static String DB_NAME = "LOGSX";
	private static final String DB_TABLE = "LOGS";
	private SQLiteDatabase mDataBase;
	private final Context mContext;

	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, 1);// 1? its Database Version
		if (android.os.Build.VERSION.SDK_INT >= 17) {
			DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
		} else {
			DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
		}
		this.mContext = context;
	}

	private static final String KEY_ID = "id";
	private static final String KEY_DATA = "data";
	private static final String KEY_HORA = "hora";
	private static final String KEY_CODIGO = "codigo";
	private static final String KEY_IDUSER = "idusuario";
	private static final String KEY_LAT = "lat";
	private static final String KEY_LNG = "lng";
	private static final String KEY_IP = "ip";
	private static final String KEY_CREATED_AT = "created_at";

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + DB_TABLE + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY," + KEY_DATA + " TEXT," + KEY_HORA
				+ " TEXT," + KEY_CODIGO + " TEXT," + KEY_IDUSER + " INTEGER,"
				+ KEY_LAT + " REAL," + KEY_LNG + " REAL," + KEY_IP + " TEXT"
				+ ")";
		// + KEY_CREATED_AT + " TEXT" + ")";
		db.execSQL(CREATE_LOGIN_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

		// Create tables again
		onCreate(db);
	}

	public void addLog(String data, String hora, String codigo, int idusuario,
			Double location1, Double location2, String ip) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_DATA, data);
		values.put(KEY_HORA, hora);
		values.put(KEY_CODIGO, codigo);
		values.put(KEY_IDUSER, idusuario);
		values.put(KEY_LAT, location1);
		values.put(KEY_LNG, location2);
		values.put(KEY_IP, ip);
		db.insert(DB_TABLE, null, values);
		db.close(); // Closing database connection
	}

	public void createDataBase() throws IOException {
		// If database not exists copy it from the assets

		boolean mDataBaseExist = checkDataBase();
		if (!mDataBaseExist) {
			this.getReadableDatabase();
			this.close();
			try {
				// Copy the database from assests
				copyDataBase();
				Log.e(TAG, "createDatabase database created");
			} catch (IOException mIOException) {
				throw new Error("ErrorCopyingDataBase");
			}
		}
	}

	// Check that the database exists here: /data/data/your package/databases/Da
	// Name
	private boolean checkDataBase() {
		File dbFile = new File(DB_PATH + DB_NAME);
		// Log.v("dbFile", dbFile + "   "+ dbFile.exists());
		return dbFile.exists();
	}

	// Copy the database from assets
	private void copyDataBase() throws IOException {
		InputStream mInput = mContext.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream mOutput = new FileOutputStream(outFileName);
		byte[] mBuffer = new byte[1024];
		int mLength;
		while ((mLength = mInput.read(mBuffer)) > 0) {
			mOutput.write(mBuffer, 0, mLength);
		}
		mOutput.flush();
		mOutput.close();
		mInput.close();
	}

	// Open the database, so we can query it
	public boolean openDataBase() throws SQLException {
		String mPath = DB_PATH + DB_NAME;
		// Log.v("mPath", mPath);
		mDataBase = SQLiteDatabase.openDatabase(mPath, null,
				SQLiteDatabase.CREATE_IF_NECESSARY);
		// mDataBase = SQLiteDatabase.openDatabase(mPath, null,
		// SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		return mDataBase != null;
	}

	@Override
	public synchronized void close() {
		if (mDataBase != null)
			mDataBase.close();
		super.close();
	}

	public void resetTables() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(DB_TABLE, null, null);
		db.close();
	}

	public int getRowCount() {
		String countQuery = "SELECT  * FROM " + DB_TABLE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();

		// return row count
		return rowCount;
	}

	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + DB_TABLE;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			while (!cursor.isLast()) {

				user.put("data", cursor.getString(1));
				user.put("hora", cursor.getString(2));
				user.put("codigo", cursor.getString(3));
				user.put("idusuario", cursor.getString(4));
				user.put("location", cursor.getString(5));
				user.put("ip", cursor.getString(6));
				String g = user.toString();
				System.out.println("Db: " + g);
				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();
		// return user
		return user;
	}

	String d;
	String h;
	String c;
	String i;
	String la;
	String ln;
	String a;
	int x;
	int total;

	public void send() {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + DB_TABLE;

		SQLiteDatabase db = this.getReadableDatabase();
		final Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.moveToFirst();
		total = cursor.getCount();
		System.out.println("Cursor: " + total);
		if (cursor.getCount() > 0) {
			while (total > cursor.getPosition()) {
				x = 0;
				while (x < 1) {
					try {

						try {
							d = user.put("data", cursor.getString(1));
							h = user.put("hora", cursor.getString(2));
							c = user.put("codigo", cursor.getString(3));
							i = user.put("idusuario", cursor.getString(4));
							la = user.put("lat", cursor.getString(5));
							ln = user.put("lng", cursor.getString(6));
							a = user.put("ip", cursor.getString(7));
						} catch (Exception e) {
							cursor.moveToNext();
							x = 1;

						}
						Thread.sleep(100);
						new Thread(new Runnable()

						{
							public void run() {

								try {

									HttpClient httpclient = new DefaultHttpClient();
									HttpPost httppost = new HttpPost(
											"http://productiveinc.com/xmlRecebeLog.php?idCard="
													+ LoginActivity.idCard
													+ "&lat=" + la + "&lng="
													+ ln + "&data=" + d
													+ "&hora=" + h + "&ip=" + a);
									HttpResponse response = httpclient
											.execute(httppost);
									HttpEntity entity = response.getEntity();
									is = entity.getContent();
									System.out.println("Db: " + httppost);
									Log.e("pass 1", "connection success ");
									cursor.moveToNext();
									System.out.println("Cursor: "
											+ cursor.getPosition());
									x = 1;
								} catch (Exception e) {
									Log.e("Fail 1", e.toString());
									x = 0;

								}
							}
						}).start();
						//

					} catch (InterruptedException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
					continue;
				}
			}
		}
		cursor.close();
		db.close();
		MainActivity.clean();

	}

	InputStream is = null;
	int id;
	double l1, l2;

	public void addone(String data, String hora, String codigo, int idusuario,
			Double location1, Double location2, String ip) {
		d = data;
		h = hora;
		c = codigo;
		i = String.valueOf(idusuario);
		id = idusuario;
		l1 = location1;
		l2 = location2;
		la = location1.toString();
		;
		ln = location2.toString();
		;
		a = ip;
		addLog(d, h, c, id, l1, l2, a);
	}
}