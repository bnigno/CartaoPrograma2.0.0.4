package pa.pm.localdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConfigDataBaseHelper extends SQLiteOpenHelper {

    // database version
    private static final int database_VERSION = 1;
    // database name
    private static String DB_NAME = "configcores";
    private static final String CONFIG_TABLE = "configcores";

    //Configuração de cores da aplicação
    private static final String KEY_ID_CONFIG = "idconfig";
    private static final String KEY_COLOR_LINHA_DE_ROTA = "colorlinhadarota";
    private static final String KEY_COLOR_AREA_DE_ATUACAO = "colorareadeatuacao";
    private static final String KEY_COLOR_MANCHA = "colormancha";
    private static final String KEY_COLOR_LOCALIZACAO_ATUAL = "colorlocalizacaoatual";


    private final String CREATE_CONFIG_TABLE = "CREATE TABLE " + CONFIG_TABLE + "("
            + KEY_ID_CONFIG + " INTEGER PRIMARY KEY,"
            + KEY_COLOR_LINHA_DE_ROTA + " TEXT," + KEY_COLOR_AREA_DE_ATUACAO
            + " TEXT," + KEY_COLOR_MANCHA + " TEXT,"
            + KEY_COLOR_LOCALIZACAO_ATUAL + " TEXT" + ");";

    private final String INSERT_CONFIG_TABLE = "INSERT INTO " + CONFIG_TABLE
            + " VALUES (0, 'GREEN', 'GREEN', 'RED', 'BLUE');";


    public ConfigDataBaseHelper(Context context) {
        super(context, DB_NAME, null, database_VERSION);
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONFIG_TABLE);
        db.execSQL(INSERT_CONFIG_TABLE);
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
            System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ " +cursor.getString(0));
            System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL " + cor);
        }
        cursor.close();
        db.close();

        return cor;
    }

}