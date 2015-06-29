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

import org.json.JSONArray;

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
    private static String NAME = "RetoChacaoDb";
    private static CursorFactory FACTORY = null;

    private static final String LOG = DataBaseHelper.class.getName();

    private static final String CREATE_EVENT = "CREATE TABLE event (" +
            "ev_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "ev_name TEXT," +
            "ev_description TEXT," +
            "ev_url TEXT," +
            "ev_category TEXT," +
            "ev_tags TEXT," +
            "ev_type TEXT,"+
            "ev_facebook TEXT," +
            "ev_twitter TEXT," +
            "ev_instagram TEXT)";
    private static final String CREATE_PHOTO = "CREATE TABLE photo (" +
            "pho_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "pho_id_type INTEGER," +
            "pho_type TEXT," +
            "pho_name TEXT," +
            "pho_url TEXT)";
    private static final String CREATE_NEW = "CREATE TABLE new (" +
            "new_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "new_name TEXT," +
            "new_description TEXT)";
    private static final String CREATE_REPORT = "CREATE TABLE report (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "text TEXT)";
    private static final String CREATE_PLACE = "CREATE TABLE place (" +
            "pl_id INTEGER AUTOINCREMENT," +
            "pl_id_event INTEGER," +
            "pl_name TEXT," +
            "pl_description TEXT," +
            "pl_url TEXT," +
            "pl_latitude FLOAT," +
            "pl_longitude FLOAT," +
            "PRIMARY KEY (pl_id, pl_id_event))";

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
        db.execSQL(CREATE_PLACE);

        db.execSQL("INSERT INTO event (ev_id,ev_name,ev_description,ev_tags,ev_type) VALUES (1,'event1','descripcion 1','Teatro;Festival;','Cultura')");
        db.execSQL("INSERT INTO event (ev_id,ev_name,ev_description,ev_tags,ev_type) VALUES (2,'event2','descripcion 2','Carrera;Fitness','Deporte')");
        db.execSQL("INSERT INTO event (ev_id,ev_name,ev_description,ev_tags,ev_type) VALUES (3,'event3','descripcion 3','Festival;Idiomas','Cultura')");

        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (1,1,'place1', 10.496321, -66.848892)");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (1,2,'place1', 10.496321, -66.848892)");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (2,2,'place2', 10.503574, -66.857480)");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (3,3,'place3', 10.496699, -66.841161)");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (4,3,'place4', 10.497548, -66.856450)");


        db.execSQL("INSERT INTO new (new_id,new_name,new_description) VALUES (1,'new1','descripcion 1')");
        db.execSQL("INSERT INTO new (new_id,new_name,new_description) VALUES (2,'new2','descripcion 2')");
        db.execSQL("INSERT INTO new (new_id,new_name,new_description) VALUES (3,'new3','descripcion 3')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS event");
        db.execSQL("DROP TABLE IF EXISTS new");
        db.execSQL("DROP TABLE IF EXISTS photo");
        db.execSQL("DROP TABLE IF EXISTS report");
        db.execSQL("DROP TABLE IF EXISTS place");

        //Se crea la nueva versión de la tabla
        onCreate(db);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }


//    querys by event
    public Boolean insertEvent(JSONArray args){
        Log.e(LOG,"Insertando");
        return true;

    }
    public Boolean updateEvent(JSONArray args){
        Log.e(LOG,"Actualizando");
        return true;
    }
    public Boolean deleteEvent(JSONArray args){
        Log.e(LOG,"Borrando");
        return true;

    }

    public Event getEvent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM event AS e, place AS p  WHERE e.ev_id =" + id+" AND p.pl_id_event = "+id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Event ev = new Event();
        ev.setId(c.getInt(c.getColumnIndex("ev_id")));
        ev.setName((c.getString(c.getColumnIndex("ev_name"))));
        ev.setDescription((c.getString(c.getColumnIndex("ev_description"))));
        ev.setCategory(c.getString((c.getColumnIndex("ev_category"))));
        ev.setTags(c.getString((c.getColumnIndex("ev_tags"))));
        ev.setUrl(c.getString((c.getColumnIndex("ev_url"))));
        ev.setType(c.getString(c.getColumnIndex("ev_type")));
        ev.setFacebook((c.getString(c.getColumnIndex("ev_facebook"))));
        ev.setTwitter((c.getString(c.getColumnIndex("ev_twitter"))));
        ev.setInstagram((c.getString(c.getColumnIndex("ev_instagram"))));
        ev.setLatitude(c.getFloat(c.getColumnIndex("pl_latitude")));
        ev.setLongitude(c.getFloat(c.getColumnIndex("pl_longitude")));

        return ev;
    }

    public List<Event> getAllEvents() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Event> events = new ArrayList<Event>();

        String selectQuery = "SELECT * FROM event AS e, place AS p WHERE e.ev_id = p.pl_id_event";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Event ev = new Event();
                ev.setId(c.getInt(c.getColumnIndex("ev_id")));
                ev.setName((c.getString(c.getColumnIndex("ev_name"))));
                ev.setDescription((c.getString(c.getColumnIndex("ev_description"))));
                ev.setCategory(c.getString((c.getColumnIndex("ev_category"))));
                ev.setTags(c.getString((c.getColumnIndex("ev_tags"))));
                ev.setUrl(c.getString((c.getColumnIndex("ev_url"))));
                ev.setType(c.getString(c.getColumnIndex("ev_type")));
                ev.setFacebook((c.getString(c.getColumnIndex("ev_facebook"))));
                ev.setTwitter((c.getString(c.getColumnIndex("ev_twitter"))));
                ev.setInstagram((c.getString(c.getColumnIndex("ev_instagram"))));
                ev.setLatitude(c.getFloat(c.getColumnIndex("pl_latitude")));
                ev.setLongitude(c.getFloat(c.getColumnIndex("pl_longitude")));

                events.add(ev);

            } while (c.moveToNext());
        }
        return events;
    }

    public List<Event> getEventsByPlace (int id){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Event> events = new ArrayList<Event>();

        String selectQuery = "SELECT  * FROM event AS e, place AS p WHERE p.pl_id="+id+" AND p.pl_id_event = e.ev_id";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Event ev = new Event();
                ev.setId(c.getInt(c.getColumnIndex("ev_id")));
                ev.setName((c.getString(c.getColumnIndex("ev_name"))));
                ev.setDescription((c.getString(c.getColumnIndex("ev_description"))));
                ev.setCategory(c.getString((c.getColumnIndex("ev_category"))));
                ev.setTags(c.getString((c.getColumnIndex("ev_tags"))));
                ev.setUrl(c.getString((c.getColumnIndex("ev_url"))));
                ev.setType(c.getString(c.getColumnIndex("ev_type")));
                ev.setFacebook((c.getString(c.getColumnIndex("ev_facebook"))));
                ev.setTwitter((c.getString(c.getColumnIndex("ev_twitter"))));
                ev.setInstagram((c.getString(c.getColumnIndex("ev_instagram"))));
                ev.setLatitude(c.getFloat(c.getColumnIndex("pl_latitude")));
                ev.setLongitude(c.getFloat(c.getColumnIndex("pl_longitude")));

                events.add(ev);

            } while (c.moveToNext());
        }
        return events;
    }

    public List<Event> getBySearch(String key) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Event> events = new ArrayList<Event>();

        String selectQuery = "SELECT * FROM event AS e, place AS p WHERE e.ev_id = p.pl_id_event AND (e.ev_name LIKE ('%" +key
                +"%') OR e.ev_description LIKE ('%"+key+"%'))";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Event ev = new Event();
                ev.setId(c.getInt(c.getColumnIndex("ev_id")));
                ev.setName((c.getString(c.getColumnIndex("ev_name"))));
                ev.setDescription((c.getString(c.getColumnIndex("ev_description"))));
                ev.setCategory(c.getString((c.getColumnIndex("ev_category"))));
                ev.setTags(c.getString((c.getColumnIndex("ev_tags"))));
                ev.setUrl(c.getString((c.getColumnIndex("ev_url"))));
                ev.setType(c.getString(c.getColumnIndex("ev_type")));
                ev.setFacebook((c.getString(c.getColumnIndex("ev_facebook"))));
                ev.setTwitter((c.getString(c.getColumnIndex("ev_twitter"))));
                ev.setInstagram((c.getString(c.getColumnIndex("ev_instagram"))));
                ev.setLatitude(c.getFloat(c.getColumnIndex("pl_latitude")));
                ev.setLongitude(c.getFloat(c.getColumnIndex("pl_longitude")));

                events.add(ev);

            } while (c.moveToNext());
        }
        return events;
    }

    public List<Event> getByTag(String tag){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Event> events = new ArrayList<Event>();

        String selectQuery = "SELECT * FROM event AS e, place AS p WHERE e.ev_id = p.pl_id_event AND e.ev_tags LIKE ('%" +tag
                +"%')";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Event ev = new Event();
                ev.setId(c.getInt(c.getColumnIndex("ev_id")));
                ev.setName((c.getString(c.getColumnIndex("ev_name"))));
                ev.setDescription((c.getString(c.getColumnIndex("ev_description"))));
                ev.setCategory(c.getString((c.getColumnIndex("ev_category"))));
                ev.setTags(c.getString((c.getColumnIndex("ev_tags"))));
                ev.setUrl(c.getString((c.getColumnIndex("ev_url"))));
                ev.setType(c.getString(c.getColumnIndex("ev_type")));
                ev.setFacebook((c.getString(c.getColumnIndex("ev_facebook"))));
                ev.setTwitter((c.getString(c.getColumnIndex("ev_twitter"))));
                ev.setInstagram((c.getString(c.getColumnIndex("ev_instagram"))));
                ev.setLatitude(c.getFloat(c.getColumnIndex("pl_latitude")));
                ev.setLongitude(c.getFloat(c.getColumnIndex("pl_longitude")));

                events.add(ev);

            } while (c.moveToNext());
        }
        return events;
    }
//    querys by new
    public New getNew(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM new WHERE new_id =" + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        New news = new New();
        news.setId(c.getInt(c.getColumnIndex("new_id")));
        news.setName((c.getString(c.getColumnIndex("new_name"))));
        news.setDescription((c.getString(c.getColumnIndex("new_description"))));
        return news;

    }

    public List<New> getAllNews() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<New> new_list = new ArrayList<New>();

        String selectQuery = "SELECT  * FROM new";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                New news = new New();
                news.setId(c.getInt(c.getColumnIndex("new_id")));
                news.setName((c.getString(c.getColumnIndex("new_name"))));
                news.setDescription((c.getString(c.getColumnIndex("new_description"))));

                new_list.add(news);

            } while (c.moveToNext());
        }
        return new_list;
    }
}
