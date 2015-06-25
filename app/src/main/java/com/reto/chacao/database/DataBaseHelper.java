package com.reto.chacao.database;

/**
 * Created by gustavo on 25/06/15.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.reto.chacao.model.Event;
import com.reto.chacao.model.New;

import java.util.ArrayList;
import java.util.List;


/*Para llamar al helper y usar las funciones se debe hacer de la siguiente manera
*
* DataBaseHelper dt =
            new DataBaseHelper(getBaseContext());
 LUEGO
 SQLiteDatabase db = dt.getWritableDatabase();

 ya en db podemos llamar a la funciones para hacer las consultas

 ejemplo en http://notasprogramacion.sodenet.es/crud-android-sqlite-bbdd/
* */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static int VERSION = 1;
    private static String NAME = "HipotecaDb";
    private static CursorFactory FACTORY = null;

    private static final String LOG = DataBaseHelper.class.getName();

    private static final String CREATE_EVENT = "CREATE TABLE event (" +
            "id INTEGER PRIMARY KEY," +
            "name TEXT," +
            "description TEXT," +
            "latitude FLOAT," +
            "longitude FLOAT," +
            "facebook TEXT," +
            "twitter TEXT," +
            "instagram TEXT)";
    private static final String CREATE_PHOTO = "CREATE TABLE photo (" +
            "id INTEGER PRIMARY KEY," +
            "id_type INTEGER," +
            "type TEXT," +
            "name TEXT," +
            "url TEXT)";
    private static final String CREATE_NEW = "CREATE TABLE new (" +
            "id INTEGER PRIMARY KEY," +
            "name TEXT," +
            "description TEXT)";
    private static final String CREATE_REPORT = "CREATE TABLE report (" +
            "id INTEGER PRIMARY KEY," +
            "text TEXT)";

    public DataBaseHelper(Context context) {
        super(context, NAME, FACTORY, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(CREATE_EVENT);
        db.execSQL(CREATE_NEW);
        db.execSQL(CREATE_PHOTO);
        db.execSQL(CREATE_REPORT);

        db.execSQL("INSERT INTO event (id,name,description) VALUES (1,'event1','descripcion 1')");
        db.execSQL("INSERT INTO event (id,name,description) VALUES (2,'event2','descripcion 2')");
        db.execSQL("INSERT INTO event (id,name,description) VALUES (3,'event3','descripcion 3')");

        db.execSQL("INSERT INTO new (id,name,description) VALUES (1,'new1','descripcion 1')");
        db.execSQL("INSERT INTO new (id,name,description) VALUES (2,'new2','descripcion 2')");
        db.execSQL("INSERT INTO new (id,name,description) VALUES (3,'new3','descripcion 3')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS event");
        db.execSQL("DROP TABLE IF EXISTS new");
        db.execSQL("DROP TABLE IF EXISTS photo");
        db.execSQL("DROP TABLE IF EXISTS report");

        //Se crea la nueva versión de la tabla
        onCreate(db);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    public Event get_event(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM event WHERE id =" + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Event ev = new Event();
        ev.setId(c.getInt(c.getColumnIndex("id")));
        ev.setName((c.getString(c.getColumnIndex("name"))));
        ev.setDescription((c.getString(c.getColumnIndex("description"))));
        ev.setLatitude((c.getFloat(c.getColumnIndex("latitude"))));
        ev.setLongitude((c.getFloat(c.getColumnIndex("longitude"))));
        ev.setFacebook((c.getString(c.getColumnIndex("facebook"))));
        ev.setTwitter((c.getString(c.getColumnIndex("twitter"))));
        ev.setInstagram((c.getString(c.getColumnIndex("instagram"))));

        return ev;
    }

    public List<Event> getAll_event() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Event> events = new ArrayList<Event>();

        String selectQuery = "SELECT  * FROM event";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Event ev = new Event();
                ev.setId(c.getInt(c.getColumnIndex("id")));
                ev.setName((c.getString(c.getColumnIndex("name"))));
                ev.setDescription((c.getString(c.getColumnIndex("description"))));
                ev.setLatitude((c.getFloat(c.getColumnIndex("latitude"))));
                ev.setLongitude((c.getFloat(c.getColumnIndex("longitude"))));
                ev.setFacebook((c.getString(c.getColumnIndex("facebook"))));
                ev.setTwitter((c.getString(c.getColumnIndex("twitter"))));
                ev.setInstagram((c.getString(c.getColumnIndex("instagram"))));

                events.add(ev);

            } while (c.moveToNext());
        }
        return events;
    }

    public New get_new(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM new WHERE id =" + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        New news = new New();
        news.setId(c.getInt(c.getColumnIndex("id")));
        news.setName((c.getString(c.getColumnIndex("name"))));
        news.setDescription((c.getString(c.getColumnIndex("description"))));
        return news;

    }

    public List<New> getAll_news() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<New> new_list = new ArrayList<New>();

        String selectQuery = "SELECT  * FROM new";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                New news = new New();
                news.setId(c.getInt(c.getColumnIndex("id")));
                news.setName((c.getString(c.getColumnIndex("name"))));
                news.setDescription((c.getString(c.getColumnIndex("description"))));

                new_list.add(news);

            } while (c.moveToNext());
        }
        return new_list;
    }
}
