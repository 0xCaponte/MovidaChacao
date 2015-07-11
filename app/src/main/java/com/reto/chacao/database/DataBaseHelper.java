package com.reto.chacao.database;

/**
 * Created by gustavo on 25/06/15.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.reto.chacao.model.Comment;
import com.reto.chacao.model.Event;
import com.reto.chacao.model.New;
import com.reto.chacao.model.Photo;
import com.reto.chacao.model.Place;

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

 formato de fecha yyyy-MM-dd HH:mm
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
            "ev_type TEXT," +
            "ev_dateStart TEXT," +
            "ev_dateEnd TEXT,"+
            "ev_facebook TEXT," +
            "ev_twitter TEXT," +
            "ev_instagram TEXT)";
    private static final String CREATE_PHOTO = "CREATE TABLE photo (" +
            "pho_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "pho_id_event INTEGER," +
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
            "pl_id INTEGER," +
            "pl_id_event INTEGER," +
            "pl_name TEXT," +
            "pl_description TEXT," +
            "pl_url TEXT," +
            "pl_latitude FLOAT," +
            "pl_longitude FLOAT," +
            "PRIMARY KEY (pl_id, pl_id_event))";

    private static final String CREATE_COMMENT  = "CREATE TABLE comment (" +
            "cm_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "cm_id_event INTEGER," +
            "cm_firstname TEXT," +
            "cm_lastname TEXT," +
            "cm_text TEXT," +
            "cm_date TEXT," +
            "cm_photo TEXT)";


    public DataBaseHelper(Context context) {
        super(context, NAME, FACTORY, VERSION);
    }

    private void load_data(SQLiteDatabase db){
        //ev1
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type) VALUES ('Proidiomas','DIR: Centro Plaza, Torre B, piso 6','Idioma;','Cultura','Fijo')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (1,1,'Centro Plaza', 10.497661, -66.846371)");
        db.execSQL("INSERT INTO comment (cm_id_event, cm_firstname,cm_lastname, cm_text) VALUES (1,'Domingo','de Abreu', 'Al fin un lugar donde aprender idiomas en general')");
        db.execSQL("INSERT INTO comment (cm_id_event, cm_firstname, cm_lastname, cm_text) VALUES (1,'Carlos','Aponte', 'Idiomaaaas!!!')");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (1,'proidiomas','png', 'proidiomas')");
        //ev2
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_url,ev_type) VALUES ('Cursos de Portugues','Quinta 65, 6ta Transversal con Av San Juan Bosco, Caracas\n" +
                "0212-2679107','Idioma','Cultura','www.icbv.org.ve','Fijo')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (2,2,'Instituto Cultural Brasil-Venezuela', 10.504211, -66.851001)");
        db.execSQL("INSERT INTO comment (cm_id_event, cm_firstname, cm_lastname, cm_text) VALUES (2,'Andrea', 'Balbas', 'o melhor é aprender Português')");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (2,'Curso de Portugues','png', 'portugues')");


        //ev3
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type) VALUES ('Cursos de Frances','Quinta Wilmarú Avenida Mohedano, Caracas\n" +
                "0212-2644611','Idioma;','Cultura','Fijo')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (3,3,'Alianza Francesa', 10.503536, -66.857572)");
        db.execSQL("INSERT INTO comment (cm_id_event, cm_firstname, cm_lastname, cm_text) VALUES (3,'Gustavo','Ortega', 'un deux trois quatre cinq six sept huit neuf dix')");
        db.execSQL("INSERT INTO comment (cm_id_event, cm_firstname, cm_lastname,cm_text) VALUES (3,'Carlos','Aponte', 'omelet du fromage omelet du fromage omelet du fromage omelet du fromage')");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (3,'Curso de Frances','png', 'alianzafrancesa')");

        //ev4
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type) VALUES ('Cursos de Aleman','Torre Altávila, Avenida Luis Roche\n" +
                "+ 58 212 814 3030','Idioma;','Cultura','Fijo')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (4,4,'Goethe-Institut Caracas', 10.501405, -66.848544)");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (4,'Curso de Aleman','png', 'aleman')");

        //ev5 PDVSA La estancia
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type) VALUES ('Yoga PDVSA La Estancia','Horarios\n" +
                "Martes: 5:30PM a 6:30PM\n" +
                "Sábados: 11:00AM a 12:00M\n" +
                "Para la mujer embarazada:\n" +
                "Jueves: 5:30PM a 6:30PM\n" +
                "Viernes: 11:00AM a 12:00M','Fitness;','Deporte','Fijo')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (5,5,'PDVSA La Estancia', 10.495116, -66.847530)");
        db.execSQL("INSERT INTO comment (cm_id_event, cm_firstname, cm_lastname, cm_text) VALUES (5,'Karen','Troiano', 'La mejor forma de estirar los musculos y relajar el cuerpoi')");
        db.execSQL("INSERT INTO comment (cm_id_event, cm_firstname, cm_lastname, cm_text) VALUES (5,'Domingo','de Abreu', 'Vine gracias a una amiga, ahora no me quiero ir')");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (5,'YOGA','png', 'yogaestancia')");

        //ev6 Centro Cultura Cacao
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,'ev_dateStart','ev_dateEnd') VALUES ('CCS FORUM 2015','Larry Black\n" +
                "cssforum2015@gmail.com\n" +
                "Centro Cultural Chacao Av.Tamanaco. El Rosal','Ponencia;','Cultura','Movil','2015-07-07 11:00','2015-07-07')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (6,6,'Centro Cultural Chacao', 10.490290, -66.863381)");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (6,'CCS FORUM','jpg', 'cssforum')");

        //ev7 Sala Cabrujas
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_dateEnd,ev_dateStart,ev_type) VALUES ('Charla sobre Festival Nuevas Bandas','Sala Cabrujas 7pm','Musica;Concierto','Cultura','2015-07-07','2015-07-07','Movil')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (7,7,'Sala Cabrujas', 10.497285, -66.843743)");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (7,'Charla Nuevas Bandas','png', 'festivalnuevasbandas')");

        // Eugenio Mendoza
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (8,0,'Polideportivo Eugenio Medoza', 10.504332, -66.856002)");

        //ev8
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_dateStart,ev_dateEnd) VALUES ('FOTOMARATÓN ACNUR','El FOTOMARATÓN ACNUR,  es un concurso de fotografía, una actividad de entretenimiento dirigido a los aficionados de las fotos, donde deberán interpretar un tema a través de imágenes.\n" +
                "\n" +
                "Sin importar el grado de conocimiento que se posea del lenguaje fotográfico, el objetivo del FOTOMARATÓN es que puedan vivir la experiencia de retratar la ciudad y sus habitantes en el marco de una campaña en específico.\n" +
                "Punto de Salida: Plaza Miranda, Altamira','Carrera;Fotografia','Cultura','Movil','2015-09-12','2015-09-12')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (9,8,'Plaza Miranda', 10.504595, -66.851025)");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (8,'Fotomaraton ACNUR','png', 'fotomaraton')");

        //ev9
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type) VALUES ('Yoga Parque Miranda','Parque Miranda','Fitness','Deporte','Fijo')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (10,9,'Parque Miranda', 10.4953988, -66.83787)");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (9,'YOGA','jpg', 'yogamiranda')");

        //ev10
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url,ev_dateStart,ev_dateEnd) VALUES ('Festival nuevas Bandas','Homenajeando a los 25 años del Festival Nuevas Bandas, el Festival Nuevas Bandas llega cargado de actividades a desarrollarse en los espacios del Centro Cultural Chacao del 14 al 19 de julio','Festival;Musica','Cultura','Movil','http://www.centroculturalchacao.com/','2015-07-14','2015-07-14')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (11,10,'Centro Cultural de Chacao', 10.4902528, -66.8631565)");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (10,'Festival Nuevas Bandas','png', 'festivalnuevasbandas')");

        //ev11
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url,ev_dateStart,ev_dateEnd) VALUES ('Caracas Inmortal','Caracas inmortal es la continuidad de la investigación sobre la ciudad que ha venido desarrollando Javier León. En esta ocasión parte de una aproximación a la evolución desde sus orígenes, y desarrolla un cuerpo de obras amplio y puntual, constituido por pinturas, gráficas, fotografías y bocetos digitales, que el artista acompaña de una conferencia que complementa la proposición.','Arte;Pintura;Fotografia','Cultura','Movil','http://www.centroculturalchacao.com/','2015-06-28 11:00','2015-08-23 17:00')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (11,11,'Centro Cultural de Chacao', 10.4902528, -66.8631565)");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (11,'Caracas Inmortal','png', 'caracasinmortal')");

        //ev12
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url,ev_dateStart,ev_dateEnd) VALUES ('De Tango y Filosofía','El profesor Alfredo Vallota expondrá la relación \"De tango y filosofía\" el 9 de julio\n" +
                "Para celebrar los cinco años de Filosofía en la ciudad y unirnos a la conmemoración del día de la Independencia de Argentina tendremos una exhibición de tango.','Arte;Baile','Cultura','Movil','http://www.centroculturalchacao.com/','2015-07-09 18:00','2015-07-09 21:00')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (11,12,'Centro Cultural de Chacao', 10.4902528, -66.8631565)");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (12,'De Tango y Filosofia','png', 'detangoyfilosofia')");

        //ev13
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url,ev_dateStart,ev_dateEnd) VALUES ('Alfredo Naranjo y el Guajeo','El conocido y respetado internacionalmente grupo Alfredo Naranjo y Guajeo, en esta oportunidad van por el lado de la descarga, especial para el melómano y el bailador. A través de la improvisación los músicos de Guajeo demostrarán porque son tan solicitados y queridos  por el público, sus destrezas interpretativas se pondrán de manifiesto la tarde del viernes 10 de julio.','Musica','Cultura','Movil','http://www.centroculturalchacao.com/','2015-07-10 17:00','2015-07-10 21:00')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (11,13,'Centro Cultural de Chacao', 10.4902528, -66.8631565)");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (13,'Alfredo Naranjo y el Guajeo','png', 'alfredonaranjo')");

        //ev14
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url,ev_dateStart,ev_dateEnd) VALUES ('El Concurso Internacional de Ballet Clásico (CIBC-Caracas)','El Concurso Internacional de Ballet Clásico (CIBC-Caracas) es el único evento en su tipo en Venezuela y funciona con estándares similares a las más importantes competiciones de ballet a nivel mundial.','Baile','Cultura','Movil','http://www.centroculturalchacao.com/','2015-07-10 17:00','2015-07-12 21:00')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (11,14,'Centro Cultural de Chacao', 10.4902528, -66.8631565)");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (14,'El Concurso Internacional de Ballet Clásico (CIBC-Caracas)','png', 'concursointernacionaldeballet')");

        //ev15
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url,ev_dateStart,ev_dateEnd) VALUES ('El Cocotero Mágico: Una Adaptación Tropical De La Leyenda De Las Habichuelas Mágicas (Taller-Montaje Infantil Teatro Abierto)'," +
                "'El pequeño Chuito tendrá la oportunidad, gracias al Cocotero Mágico, de descubrir un mundo fantástico: el Reino de las Siete Potencias. Allí se topará con un mundo de riquezas, con las cuales podrá encontrarle solución a todos sus problemas. Sin embargo, no es fácil tener tanto dinero repentinamente, una lección que Chuito tendrá que aprender. Esta historia fantástica, inspirada en la leyenda anglo-sajona de \"Las Habichuelas Mágicas\", nos llevará por hilarantes situaciones con personajes divertidos y dinámicos, una obra para todo público.'," +
                "'Teatro','Cultura','Movil','http://www.centroculturalchacao.com/','2015-07-25 15:00','2015-07-26 18:00')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (11,15,'Centro Cultural de Chacao', 10.4902528, -66.8631565)");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (15,'El Cocotero Magico','png', 'cocoteromagico')");

        //ev16 contratiempo casa cultura
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url,ev_dateStart,ev_dateEnd) VALUES ('Festival Caracas en Contratiempo','La tercera edición del Festival Caracas en Contratiempo es una vitrina que reúne a los mejores exponentes de las nuevas tendencias  de la música venezolana de vanguardia.  Esta fiesta reunirá en escena a más de 200 artistas, repartidos en 15 conciertos, talleres y conservatorios, gracias al respaldo de Pepsi, Telefónica|Movistar, Natuchips y el Centro Cultural Chacao','Musica;Festival','Cultura','Movil','http://www.centroculturalchacao.com/','2015-07-30','2015-08-02')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (11,16,'Centro Cultural de Chacao', 10.4902528, -66.8631565)");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (16,'Festival Caracas en Contratiempo','png', 'caracascontratiempo')");

        //ev16 contratiempo torre bod
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (12,16,'Torre BOD', 10.4982346, -66.8527346)");

        //ev16 contratiempo plaza francia
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (13,16,'Plaza Francia', 10.4964012, -66.8489576)");

        //ev17
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url,ev_dateStart,ev_dateEnd) VALUES ('Taller de Teatro Musical','El Taller de Teatro Musical con La Escuela Jr. consiste en brindar a niños y adolescentes un espacio para acercarse a la experiencia del teatro musical como disciplina integral en donde podrán tener en un mismo espacio clases de canto, baile y actuación todos en nivel de iniciación y atendido por profesionales en cada uno de los aspectos.','Teatro','Cultura','Movil','http://www.centroculturalchacao.com/','2015-07-27 9:00','2015-08-14 18:00')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (11,17,'Centro Cultural de Chacao', 10.4902528, -66.8631565)");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (17,'Taller de Teatro Musica','png', 'tallerdeteatromusical')");

        //ev18
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url,ev_dateStart,ev_dateEnd) VALUES ('Proyecto Celuloide','Proyecto Celuloide es un programa de estudio de alto poder recreativo, diseñado para lograr el aprendizaje de las posibilidades que brinda el arte audiovisual para forjarse un mejor futuro y vivir experiencias gratificantes para el desarrollo personal y profesional de los jóvenes participantes. Durante este taller vacacional, los jóvenes asumirán el compromiso de realizar un cortometraje desde la creación de la idea hasta la proyección, contando con el apoyo y asesoría de profesionales del área.','Arte','Cultura','Movil','http://www.centroculturalchacao.com/','2015-08-03 9:00','2015-08-21 13:00')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (11,18,'Centro Cultural de Chacao', 10.4902528, -66.8631565)");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (18,'Proyecto Celuloide','png', 'proyectoceluloide')");

        //ev19
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url,ev_dateStart,ev_dateEnd) VALUES ('Arquitour especial en homenaje a Jan Gorecki','11 de julio 8:30  am  \n" +
                "Una ruta arquitectónica especial ofrecerá Cultura Chacao dentro de su agenda de arquitours, el sábado 11 de julio a partir de las 8:30 a.m., en conmemoración al Día del Arquitecto que se celebra cada 4 de julio, y en homenaje al arquitecto de origen ruso Jan Gorecki, quien recientemente cumplió 101 años de vida, gran parte de los cuales se los ha dedicado a desarrollar proyectos en Venezuela, especialmente en Caracas.','Arte','Cultura','Movil','http://www.centroculturalchacao.com/','2015-07-11 8:30','2015-07-11 17:00')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (14,19,'Plaza Bolivar de Chacao', 10.495096, -66.853248)");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (19,'Arquitour especial en homenaje a Jan Gorecki','png', 'arquitour')");

        //ev20
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url,ev_dateStart,ev_dateEnd) VALUES ('Noches de Poesía Reunida','25 de junio de 2015 hasta el 31 de julio de 2015 de 7:00 pm a 8:00 pm  \n " +
                "El Centro Cultural Chacao presenta Noches de Poesía Reunida, un programa de recitales poéticos mensuales, con la participación de Eleonora Requena, Hernán Zamora y Fedosy Santaella, con Hayfer Brea como artista invitado','Poesia','Cultura','Movil','http://www.centroculturalchacao.com/','2015-06-25 19:00','2015-07-31 20:00')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (15,20,'Sala Experimental del centro cultural Chacao', 10.4902528,-66.8631565)");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (20,'Noches de Poesía Reunida','png', 'poesiareunida')");

        //ev21
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_dateStart,ev_dateEnd) VALUES ('Alianza Francesa exhibirá la muestra individual “Andamios”','04 de junio de 2015 hasta el 30 de julio de 2015 de 7:00 pm \n " +
                "La exposición mostrará al público una serie de pinturas y esculturas de arte contemporáneo que son la expresión de las vivencias del autor merideño durante su estancia en la ciudad de Caracas, un  un retrato a escala del proceso de construcción de edificios.','Arte;Pintura;Escultura','Cultura','Movil','2015-06-04 19:00','2015-07-30 22:00')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (16,21,'Alianza Francesa sede Chacaito', 10.4926462, -66.8707684)");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (21,'Alianza Francesa exhibirá la muestra individual “Andamios”','png', 'alianzaandamios')");

        //ev22
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_dateStart,ev_dateEnd) VALUES ('Fiesta de la tradición venezolana','Los domingos de 12, 19 y 26 de Julio 4:00 pm.\n" +
                "Para el domingo 12 se presentará la Parranda de San Pedro. La  Parranda de San Pedro Patrimonio Inmaterial de la Humanidad. Será  realizada por la Fundación Parranda De San Pedro De Guatire 23 de Enero, quienes representan una función teatral sobre el origen y el porqué de esta tradición. Luego se presentará la propia parranda en devoción al santo. Habrá pasacalle por la plaza.\n" +
                "El domingo 19 celebraremos a San Juan con la participación de la Fundación Parrandita Infantil Amayra Avariano, de Guatire. En honor a San Juan se realizará una procesión  que recorrerá el pueblo de Guatire recibiendo dádivas, agradecimientos y reconocimientos,  y cada cierto tiempo  se rendirá a viva voz homenaje a San Juan.  Los bailes al ritmo del tambor se dan en cada parada; los tambores suenan fervientemente, y a su compás el hombre acosa a la mujer y esta; entre ritmos eróticos y provocadores se le escurre;  y vale la pena destacar que  todos llevan pañuelos de colores que agitan en todo el camino, mientras la  procesión,  se dirige a la casa de donde salió el santo;  allí se reúnen y continúan la celebración entre fuegos artificiales, bebidas, tambores y bailes”.\n" +
                "\n" +
                "El domingo 26 será el turno a los Velorios de Cruz de Mayo con Las Estrellas de Birongo, tambores (instrumentos típicos como culo e puya, quitiplas), cantos y baile en esta manifestación mirandina. Se demostrará cómo montar el altar, cómo adorna la cruz de mayo, y se explicará el ritual barloventeño. Las Estrellas de Bilongo están conformadas principalmente por niños.','Festival','Cultura','Movil','2015-07-12','2015-07-26')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (17,22,'Anfiteatro Plaza Altamira', 10.4951279,-66.8488187)");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (22,'Fiesta de la tradición venezolana','png', 'fiestatradicionalvenezolana')");

        //ev23
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_dateStart,ev_dateEnd) VALUES ('Boutique Itinerante','17  de Julio, 14 agosto y 16 octubre  12:00pm a 8:30pm \n" +
                "Correo:BoutiqueIitineranVzla@gmail.com \n" +
                "Boutique Itinerante será́ un encuentro mensual de talento y emprendimiento venezolano, donde 20 expositores de Talento Nacional, 7 Comerciantes y 5 Opciones Gastronómicas, comercializarán y darán a conocer sus propuestas. Este evento busca resaltar el trabajo que diseñadores, artesanos, creativos y emprendedores nacionales vienen realizando y crear costumbre en el consumidor, ya que se realizará periódicamente, el día viernes más cercano al 15 de cada mes.','Arte;Pintura;Escultura','Cultura','Movil','2015-07-17 12:00','2015-10-16 20:30pm')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (12,23,'Torre BOD', 10.4982346, -66.8527346)");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (23,'Boutique Itinerante','png', 'boutiqueitinerante')");

        //ev24
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url) VALUES ('ZUMBA FITNESS','Todos los domingos a las 11:00 AM\n" +
                "La plaza Isabel La Católica de La Castellana volverá a llenarse de ritmo, deporte y sano esparcimiento con el reinicio de las actividades del programa Moviendo la Calle, impulsado por la Alcaldía de Chacao, a través de la Dirección de Deportes.','Fitness','Deporte','Fijo','http://www.deporte.chacao.gob.ve/')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (18,24,'Plaza Isabel la Catolica(Plaza la Castellana)', 10.4975169,-66.8515945)");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (24,'ZUMBA FITNESS','png', 'zumba')");

        //ev25
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url) VALUES ('PONTE EN FORMA CON DRAGON FIGHT','Martes y Jueves 7 pm\n" +
                "El Dragon Fight es un sistema de entrenamiento de artes marciales y boxeo, de alto impacto, que te permite ponerte en forma y tú puedes disfrutarlo de manera gratuita en nuestra Plaza Isabélica La Católica de La Castellana los días Martes y Jueves a las 7:00 p.m. ¡Acércate! Estamos Moviendo La Calle','Fitness','Deporte','Fijo','http://www.deporte.chacao.gob.ve/')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (18,25,'Plaza Isabel la Catolica(Plaza la Castellana)', 10.4975169,-66.8515945)");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (25,'PONTE EN FORMA CON DRAGON FIGHT','png', 'dragonfight')");

        //ev26
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url) VALUES ('Yoga Plaza de los Palos Grandes','domingos en la mañana, de 10:00 a.m.- 11:00 a.m\n" +
                "La relajación y el ejercicio se apoderan de nuevos espacios del municipio Chacao. En esta ocasión, la plaza Los Palos Grandes, será el lugar destinado para que todos los domingos el experto instructor de yoga, Amadeo Porras, imparta sus amenas clases.','Fitness','Deporte','Fijo','http://www.deporte.chacao.gob.ve/')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (19,26,'Plaza de los Palos Grandes', 10.500004, -66.843995)");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (26,'YOGA','png', 'yoga')");

        //ev27
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url,ev_dateStart,ev_dateEnd) VALUES ('Torneo Interescuelas de Preparación  Técnica  (Gimnasia Rítmica)','12 de Julio  7 am\n" +
                "Torneo Interescuelas de Preparación  Técnica  (Gimnasia Rítmica)','Fitness','Deporte','Movil','http://www.deporte.chacao.gob.ve/','2015-07-12 7:00','2015-07-12 15:00')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (20,27,'Gimnasio Vertical  de Chacao', 10.4924672,-66.8504914)");
        db.execSQL("INSERT INTO photo (pho_id_event, pho_name, pho_type, pho_url) VALUES (27,'Torneo Interescuelas de Preparación  Técnica  (Gimnasia Rítmica)','png', 'torneogimnasia')");


        db.execSQL("INSERT INTO new (new_id,new_name,new_description) VALUES (1,'new1','descripcion 1')");
        db.execSQL("INSERT INTO new (new_id,new_name,new_description) VALUES (2,'new2','descripcion 2')");
        db.execSQL("INSERT INTO new (new_id,new_name,new_description) VALUES (3,'new3','descripcion 3')");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(CREATE_EVENT);
        db.execSQL(CREATE_NEW);
        db.execSQL(CREATE_PHOTO);
        db.execSQL(CREATE_REPORT);
        db.execSQL(CREATE_PLACE);
        db.execSQL(CREATE_COMMENT);

        load_data(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS event");
        db.execSQL("DROP TABLE IF EXISTS new");
        db.execSQL("DROP TABLE IF EXISTS photo");
        db.execSQL("DROP TABLE IF EXISTS report");
        db.execSQL("DROP TABLE IF EXISTS place");
        db.execSQL("DROP TABLE IF EXISTS comment");

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

/*    Insertar enventos
*            si es true el evento se inserto correctamente
*            si es false es que hubo un error
    */
    public Boolean insertEvent(Event e, Place p, List<Photo> photos){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean ret = true;
        long id = 0;
        ContentValues newRegistryEv = new ContentValues();
        ContentValues newRegistryPl = new ContentValues();
        ContentValues newRegistryPh = new ContentValues();
        newRegistryEv.put("ev_name",e.getName());
        newRegistryEv.put("ev_description",e.getDescription());
        newRegistryEv.put("ev_url",e.getUrl());
        newRegistryEv.put("ev_category",e.getCategory());
        newRegistryEv.put("ev_tags",e.getTags());
        newRegistryEv.put("ev_type",e.getType());
        newRegistryEv.put("ev_dateEnd",e.getDateEnd());
        newRegistryEv.put("ev_facebook",e.getFacebook());
        newRegistryEv.put("ev_twitter",e.getTwitter());
        newRegistryEv.put("ev_instagram", e.getInstagram());
        newRegistryEv.put("ev_dateEnd",e.getDateEnd());
        newRegistryEv.put("ev_dateStart",e.getDateStart());

        newRegistryPl.put("pl_name",p.getName());
        newRegistryPl.put("pl_description",p.getDescription());
        newRegistryPl.put("pl_url",p.getUrl());
        newRegistryPl.put("pl_latitude",p.getLatitude());
        newRegistryPl.put("pl_longitude",p.getLongitude());


        db.beginTransaction();
        try{
//            insertando evento
            id = db.insertOrThrow("event", null, newRegistryEv);
//          insertando lugar
            newRegistryPl.put("pl_id_event",id);
            db.insertOrThrow("place", null, newRegistryPl);
            //insertando foto(s)

            for(Photo ph : photos){
                newRegistryPh.put("pho_id_event", id);
                newRegistryPh.put("pho_name",ph.getName());
                newRegistryPh.put("pho_type",ph.getType());
                newRegistryPh.put("pho_url", ph.getUrl());
                db.insertOrThrow("photo",null,newRegistryPh);
            }

            db.setTransactionSuccessful();
        }catch(Exception err){
            err.printStackTrace();
            ret =false;

        }finally {
            db.endTransaction();
        }
        return ret;

    }

    public Boolean updateEvent(JSONArray args){
        Log.e(LOG, "Actualizando");
        return true;
    }
    public Boolean deleteEvent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String values[] = {Integer.toString(id)};
        boolean ret = true;
        db.beginTransaction();
        try {
//        eliminando de photo
            db.delete("photo", "pho_id_event=?", values);
//        eliminando de place
            db.delete("place", "pl_id_event=?", values);
//        eliminando evento
            db.delete("event", "id=?", values);
            db.setTransactionSuccessful();
        }catch(Exception err){
            err.printStackTrace();
            ret =false;

        }finally {
            db.endTransaction();
        }
        return ret;



    }

    public Event getEvent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM event AS e, place AS p, photo as ph  WHERE e.ev_id =" + id+" AND p.pl_id_event = "+id+" AND ph.pho_id_event = "+id;

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
        ev.setDateEnd(c.getString(c.getColumnIndex("ev_dateEnd")));
        ev.setDateStart(c.getString(c.getColumnIndex("ev_dateStart")));
        ev.setFacebook((c.getString(c.getColumnIndex("ev_facebook"))));
        ev.setTwitter((c.getString(c.getColumnIndex("ev_twitter"))));
        ev.setInstagram((c.getString(c.getColumnIndex("ev_instagram"))));
        ev.setLatitude(c.getFloat(c.getColumnIndex("pl_latitude")));
        ev.setLongitude(c.getFloat(c.getColumnIndex("pl_longitude")));
        ev.setPhoto(c.getString(c.getColumnIndex("pho_url")));

        return ev;
    }

    public List<Event> getAllEvents() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Event> events = new ArrayList<Event>();

        String selectQuery = "SELECT * FROM event AS e, place AS p, photo AS ph WHERE e.ev_id = p.pl_id_event AND e.ev_id = pho_id_event";

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
                ev.setDateEnd(c.getString(c.getColumnIndex("ev_dateEnd")));
                ev.setDateStart(c.getString(c.getColumnIndex("ev_dateStart")));
                ev.setFacebook((c.getString(c.getColumnIndex("ev_facebook"))));
                ev.setTwitter((c.getString(c.getColumnIndex("ev_twitter"))));
                ev.setInstagram((c.getString(c.getColumnIndex("ev_instagram"))));
                ev.setLatitude(c.getFloat(c.getColumnIndex("pl_latitude")));
                ev.setLongitude(c.getFloat(c.getColumnIndex("pl_longitude")));
                ev.setPhoto(c.getString(c.getColumnIndex("pho_url")));

                events.add(ev);

            } while (c.moveToNext());
        }
        return events;
    }

    public List<Event> getEventsByPlace (int id){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Event> events = new ArrayList<Event>();

        String selectQuery = "SELECT * FROM event AS e, place AS p, photo AS ph WHERE p.pl_id="+id+" AND p.pl_id_event = e.ev_id AND e.ev_id = ph.pho_id_event";

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
                ev.setDateEnd(c.getString(c.getColumnIndex("ev_dateEnd")));
                ev.setDateStart(c.getString(c.getColumnIndex("ev_dateStart")));
                ev.setFacebook((c.getString(c.getColumnIndex("ev_facebook"))));
                ev.setTwitter((c.getString(c.getColumnIndex("ev_twitter"))));
                ev.setInstagram((c.getString(c.getColumnIndex("ev_instagram"))));
                ev.setLatitude(c.getFloat(c.getColumnIndex("pl_latitude")));
                ev.setLongitude(c.getFloat(c.getColumnIndex("pl_longitude")));
                ev.setPhoto(c.getString(c.getColumnIndex("pho_url")));

                events.add(ev);

            } while (c.moveToNext());
        }
        return events;
    }

    public List<Event> getBySearch(String key) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Event> events = new ArrayList<Event>();

        String selectQuery = "SELECT * FROM event AS e, place AS p, photo AS ph WHERE e.ev_id = p.pl_id_event AND e.ev_id = ph.pho.id_event AND (e.ev_name LIKE ('%" +key
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
                ev.setDateEnd(c.getString(c.getColumnIndex("ev_dateEnd")));
                ev.setDateStart(c.getString(c.getColumnIndex("ev_dateStart")));
                ev.setFacebook((c.getString(c.getColumnIndex("ev_facebook"))));
                ev.setTwitter((c.getString(c.getColumnIndex("ev_twitter"))));
                ev.setInstagram((c.getString(c.getColumnIndex("ev_instagram"))));
                ev.setLatitude(c.getFloat(c.getColumnIndex("pl_latitude")));
                ev.setLongitude(c.getFloat(c.getColumnIndex("pl_longitude")));
                ev.setPhoto(c.getString(c.getColumnIndex("pho_url")));

                events.add(ev);

            } while (c.moveToNext());
        }
        return events;
    }

    public List<Event> getByTag(String tag){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Event> events = new ArrayList<Event>();

        String selectQuery = "SELECT * FROM event AS e, place AS p, photo AS ph WHERE e.ev_id = p.pl_id_event AND e.ev_id = ph.pho_id_event AND e.ev_tags LIKE ('%" +tag
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
                ev.setDateEnd(c.getString(c.getColumnIndex("ev_dateEnd")));
                ev.setDateStart(c.getString(c.getColumnIndex("ev_dateStart")));
                ev.setFacebook((c.getString(c.getColumnIndex("ev_facebook"))));
                ev.setTwitter((c.getString(c.getColumnIndex("ev_twitter"))));
                ev.setInstagram((c.getString(c.getColumnIndex("ev_instagram"))));
                ev.setLatitude(c.getFloat(c.getColumnIndex("pl_latitude")));
                ev.setLongitude(c.getFloat(c.getColumnIndex("pl_longitude")));
                ev.setPhoto(c.getString(c.getColumnIndex("pho_url")));

                events.add(ev);

            } while (c.moveToNext());
        }
        return events;
    }


//querys by comments

    public List<Comment> getCommentByEvent(int id_event){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Comment> comments = new ArrayList<Comment>();

        String selectQuery = "SELECT * FROM comment AS c WHERE c.cm_id_event = '" +id_event+"'" ;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Comment cm = new Comment();
                cm.setId(c.getInt(c.getColumnIndex("cm_id")));
                cm.setFirstname((c.getString(c.getColumnIndex("cm_firstname"))));
                cm.setLastname((c.getString(c.getColumnIndex("cm_lastname"))));
                cm.setText((c.getString(c.getColumnIndex("cm_text"))));
                cm.setId_event(c.getInt((c.getColumnIndex("cm_id_event"))));
                cm.setPhoto(c.getString((c.getColumnIndex("cm_photo"))));

                comments.add(cm);

            } while (c.moveToNext());
        }
        return comments;
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

