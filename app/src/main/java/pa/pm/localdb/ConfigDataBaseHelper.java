package pa.pm.localdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import pa.pm.cartaoprograma2.MarkerRide;

public class ConfigDataBaseHelper extends SQLiteOpenHelper {

    // database version
    private static final int database_VERSION = 1;
    // database name
    private static String DB_NAME = "cartaoprograma";
    private static final String CONFIG_TABLE = "configcores";
    private static final String MARKER_RIDE_TABLE = "markerride";

    //Configuração de cores da aplicação
    private static final String KEY_ID_CONFIG = "idconfig";
    private static final String KEY_COLOR_LINHA_DE_ROTA = "colorlinhadarota";
    private static final String KEY_COLOR_AREA_DE_ATUACAO = "colorareadeatuacao";
    private static final String KEY_COLOR_MANCHA = "colormancha";
    private static final String KEY_COLOR_LOCALIZACAO_ATUAL = "colorlocalizacaoatual";

    //percurso percorrido da aplicação
    private static final String KEY_ID_MARKER_RIDE = "idMarkeRride";
    private static final String KEY_ID_CARD = "idCard";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";


    private final String CREATE_CONFIG_TABLE = "CREATE TABLE IF NOT EXISTS " + CONFIG_TABLE + "("
            + KEY_ID_CONFIG + " INTEGER PRIMARY KEY,"
            + KEY_COLOR_LINHA_DE_ROTA + " TEXT," + KEY_COLOR_AREA_DE_ATUACAO
            + " TEXT," + KEY_COLOR_MANCHA + " TEXT,"
            + KEY_COLOR_LOCALIZACAO_ATUAL + " TEXT" + ");";

    private final String INSERT_CONFIG_TABLE = "INSERT INTO " + CONFIG_TABLE
            + " VALUES (0, 'GREEN', 'GREEN', 'RED', 'BLUE');";


    private final String CREATE_MARKER_RIDE_TABLE = "CREATE TABLE IF NOT EXISTS " + MARKER_RIDE_TABLE + "("
            + KEY_ID_MARKER_RIDE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_ID_CARD + " INTEGER NOT NULL,"
            + KEY_LAT + " REAL NOT NULL,"
            + KEY_LNG + " REAL NOT NULL"+ ");";

    public ConfigDataBaseHelper(Context context) {
        super(context, DB_NAME, null, database_VERSION);
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONFIG_TABLE);
        db.execSQL(INSERT_CONFIG_TABLE);
        db.execSQL(CREATE_MARKER_RIDE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + CONFIG_TABLE);
        // Create tables again
        onCreate(db);
    }


    public void updateConfig(String colorlinhadarota, String colorareadeatuacao, String colormancha,
                             String colorlocalizacaoatual) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COLOR_LINHA_DE_ROTA, colorlinhadarota);
        values.put(KEY_COLOR_AREA_DE_ATUACAO, colorareadeatuacao);
        values.put(KEY_COLOR_MANCHA, colormancha);
        values.put(KEY_COLOR_LOCALIZACAO_ATUAL, colorlocalizacaoatual);
        db.update(CONFIG_TABLE, values, "idconfig=0", null);
        db.close(); // Closing database connection
    }


    public String getColorConfig(String itemConfig){
        String cor = "";
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = db.rawQuery("SELECT " + itemConfig + " FROM " + DB_CONFIG_TABLE + " WHERE idconfig=0", null);
        Cursor cursor = db.query(true, CONFIG_TABLE, new String[]{itemConfig}, "idconfig=?", new String[]{"0"}, null, null, null, null);
        if (cursor.moveToFirst()) {
            cor = cursor.getString(0);
        }
        cursor.close();
        db.close();

        return cor;
    }


    public void addMarkerRide(String idCard, Double lat, Double lng) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID_CARD, Integer.parseInt(idCard));
        values.put(KEY_LAT, lat);
        values.put(KEY_LNG, lng);

        System.out.println("RIDE MARKER: "+ idCard +" ="+ lat +", "+ lng);

        db.insert(MARKER_RIDE_TABLE, null, values);
        db.close(); // Closing database connection
    }


    public List<MarkerRide> getAllMarkersRide() {
        List<MarkerRide> markerRideList = new ArrayList<MarkerRide>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MARKER_RIDE_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MarkerRide markerRide = new MarkerRide();
                markerRide.setIdCard(cursor.getInt(1));
                markerRide.setLat(cursor.getDouble(2));
                markerRide.setLng(cursor.getDouble(3));
                // Adding markerRide to list
                markerRideList.add(markerRide);
            } while (cursor.moveToNext());
        }

        // return markerRide list
        return markerRideList;
    }


    public MarkerRide getLastMarkerRide() {
        MarkerRide markerRide = new MarkerRide();
        // Select All Query
        String selectQuery = "SELECT TOP 1 * FROM "+ MARKER_RIDE_TABLE +" ORDER BY "+ KEY_ID_MARKER_RIDE +" DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                markerRide.setIdCard(cursor.getInt(1));
                markerRide.setLat(cursor.getDouble(2));
                markerRide.setLng(cursor.getDouble(3));
            } while (cursor.moveToNext());
        }

        // return markerRide list
        return markerRide;
    }
}