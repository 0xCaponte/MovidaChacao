package com.reto.chacao.map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.reto.chacao.R;
import com.reto.chacao.beans.MapProfile;
import com.reto.chacao.database.DataBaseHelper;
import com.reto.chacao.filter.activity.FilterActivities;
import com.reto.chacao.model.Event;
import com.reto.chacao.util.MapUtil;

import java.util.ArrayList;
import java.util.List;

import static android.location.Criteria.ACCURACY_FINE;
import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;
import static android.location.LocationManager.PASSIVE_PROVIDER;

public class MovidaMapActivity extends Activity {

    private static final String TAG = "Map-Fragment";

    class MyInfoWindowAdapter implements InfoWindowAdapter{

        private final View myContentsView;

        MyInfoWindowAdapter(){
            myContentsView = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoContents(Marker marker) {

            TextView title = ((TextView)myContentsView.findViewById(R.id.title));
            title.setText(marker.getTitle());
            title.setTextSize(12);
            ImageView image = (ImageView)myContentsView.findViewById(R.id.post_main_image);
            int id = getResources().getIdentifier(marker.getSnippet(),"drawable",main_context.getPackageName());
            image.setImageResource(id);

            return myContentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub
            return null;
        }

    }

    /** Local variables **/
    protected GoogleMap googleMap;
    private Context main_context;
    private ImageView mHomeButton;
    private ImageView mProfileButton;
    private ImageView mAddPostButton;

    //Filtro de busqueda
    private ImageButton busqueda;

    // Location
    private ImageButton posicion;

    // Lista de eventos Fijos de cultura
    private ArrayList<Marker> cultura_servicios = new ArrayList<Marker>();

    // Lista de eventos Fijos de deporte
    private ArrayList<Marker> deporte_servicios = new ArrayList<Marker>();

    // Lista de eventos  de cultura
    private ArrayList<Marker> cultura_eventos = new ArrayList<Marker>();

    // Lista de eventos de deporte
    private ArrayList<Marker> deporte_eventos = new ArrayList<Marker>();

    // Lista de eventos de deporte
    private ArrayList<Marker> deportes_basquet = new ArrayList<Marker>();

    // Lista de eventos de deporte
    private ArrayList<Marker> deporte_beisbol = new ArrayList<Marker>();

    // Lista de eventos de deporte
    private ArrayList<Marker> deporte_fitness = new ArrayList<Marker>();

    // Lista de eventos de deporte
    private ArrayList<Marker> deporte_futbol = new ArrayList<Marker>();

    // Lista de eventos de deporte
    private ArrayList<Marker> deporte_natacion = new ArrayList<Marker>();

    // Lista de eventos de deporte
    private ArrayList<Marker> deporte_skate = new ArrayList<Marker>();

    // Lista de eventos de deporte
    private ArrayList<Marker> deporte_tenis = new ArrayList<Marker>();

    // Lista de eventos de deporte
    private ArrayList<Marker> deporte_volleyball = new ArrayList<Marker>();

    // Lista de eventos de deporte
    private ArrayList<Marker> deporte_yoga = new ArrayList<Marker>();

    // Lista de eventos de deporte
    private ArrayList<Marker> deporte_zumba = new ArrayList<Marker>();

    // Lista de eventos de deporte
    private ArrayList<Marker> cultura_arte = new ArrayList<Marker>();

    // Lista de eventos de deporte
    private ArrayList<Marker> cultura_cine = new ArrayList<Marker>();

    // Lista de eventos de deporte
    private ArrayList<Marker> cultura_danza = new ArrayList<Marker>();

    // Lista de eventos de deporte
    private ArrayList<Marker> cultura_fotos = new ArrayList<Marker>();

    // Lista de eventos de deporte
    private ArrayList<Marker> cultura_gastronomia = new ArrayList<Marker>();

    // Lista de eventos de deporte
    private ArrayList<Marker> cultura_idiomas = new ArrayList<Marker>();

    // Lista de eventos de deporte
    private ArrayList<Marker> cultura_musica = new ArrayList<Marker>();

    // Lista de eventos de deporte
    private ArrayList<Marker> cultura_teatro = new ArrayList<Marker>();



    // Hace visibles los marcadores pertinentes
    private void loadMarkers(MapProfile mp){


        if (!deportes_basquet.isEmpty()) {
            for (Marker m : deportes_basquet) {
                m.setVisible(mp.isFiltro_basquet());
            }
        }

        if (!deporte_beisbol.isEmpty()) {
            for (Marker m : deporte_beisbol) {
                m.setVisible(mp.isFiltro_beisbol());
            }
        }

        if (!deporte_fitness.isEmpty()) {
            for (Marker m : deporte_fitness) {
                m.setVisible(mp.isFiltro_fitness());
            }
        }

        if (!deporte_futbol.isEmpty()) {
            for (Marker m : deporte_futbol) {
                m.setVisible(mp.isFiltro_futbol());
            }
        }


        if (!deporte_natacion.isEmpty()) {
            for (Marker m : deporte_natacion) {
                m.setVisible(mp.isFiltro_natacion());
            }
        }


        if (!deporte_skate.isEmpty()) {
            for (Marker m : deporte_skate) {
                m.setVisible(mp.isFiltro_skate());
            }
        }


        if (!deporte_tenis.isEmpty()) {
            for (Marker m : deporte_tenis) {
                m.setVisible(mp.isFiltro_tenis());
            }
        }


        if (!deporte_volleyball.isEmpty()) {
            for (Marker m : deporte_volleyball) {
                m.setVisible(mp.isFiltro_volleyball());
            }
        }


        if (!deporte_yoga.isEmpty()) {
            for (Marker m : deporte_yoga) {
                m.setVisible(mp.isFiltro_yoga());
            }
        }

        if (!deporte_zumba.isEmpty()) {
            for (Marker m : deporte_zumba) {
                m.setVisible(mp.isFiltro_zumba());
            }
        }

        if (!cultura_arte.isEmpty()) {
            for (Marker m : cultura_arte) {
                m.setVisible(mp.isFiltro_arte());
            }
        }

        if (!cultura_cine.isEmpty()) {
            for (Marker m : cultura_cine) {
                m.setVisible(mp.isFiltro_cine());
            }
        }

        if (!cultura_danza.isEmpty()) {
            for (Marker m : cultura_danza) {
                m.setVisible(mp.isFiltro_danza());
            }
        }

        if (!cultura_fotos.isEmpty()) {
            for (Marker m : cultura_fotos) {
                m.setVisible(mp.isFiltro_fotos());
            }
        }

        if (!cultura_gastronomia.isEmpty()) {
            for (Marker m : cultura_gastronomia) {
                m.setVisible(mp.isFiltro_gastronomia());
            }
        }

        if (!cultura_idiomas.isEmpty()) {
            for (Marker m : cultura_idiomas) {
                m.setVisible(mp.isFiltro_idiomas());
            }
        }

        if (!cultura_musica.isEmpty()) {
            for (Marker m : cultura_musica) {
                m.setVisible(mp.isFiltro_musica());
            }
        }

        if (!cultura_teatro.isEmpty()) {
            for (Marker m : cultura_teatro) {
                m.setVisible(mp.isFiltro_teatro());
            }
        }

        if (!cultura_eventos.isEmpty()) {
            for (Marker m : cultura_eventos) {
                m.setVisible(mp.isFiltro_cultura() || mp.isFiltro_cultura_eventos());
            }
        }

        if (!cultura_servicios.isEmpty()) {
            for (Marker m : cultura_servicios) {
                m.setVisible(mp.isFiltro_cultura() || mp.isFiltro_cultura_servicios());
            }
        }

        if (!deporte_eventos.isEmpty()) {
            for (Marker m : deporte_eventos) {
                m.setVisible(mp.isFiltro_deporte() || mp.isFiltro_deporte_eventos());
            }
        }

        if (!deporte_servicios.isEmpty()) {
            for (Marker m : deporte_servicios) {
                m.setVisible(mp.isFiltro_deporte() || mp.isFiltro_deporte_servicios());
            }
        }


    }

    private Location masReciente(Location location1, Location location2) {
        if (location1 == null)
            return location2;

        if (location2 == null)
            return location1;

        if (location2.getTime() > location1.getTime())
            return location2;
        else
            return location1;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        /*Set filtros cultura
        cultura = (CheckBox) findViewById(R.id.filtro_cultura);
        c_servicios = (CheckBox) findViewById(R.id.filtro_cultura_servicios);
        c_eventos = (CheckBox) findViewById(R.id.filtro_cultura_eventos);

        // Set filtros deportes
        deporte = (CheckBox) findViewById(R.id.filtro_deporte);
        d_servicios = (CheckBox) findViewById(R.id.filtro_deporte_servicios);*/
        busqueda =  (ImageButton) findViewById(R.id.busqueda);
        posicion = (ImageButton) findViewById(R.id.posicion);

        main_context = getApplicationContext();
        MapProfile mp = MapUtil.getMapFilters(main_context);

        //Crea el mapa
        createMapView();

        //Bottom Toolbar
        //setBottomToolbar();

        // Carga los marcadores
        addMarkers();

        // Initial Markers
        loadMarkers(mp);

        // My location

        posicion.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              LocationManager manager = (LocationManager) MovidaMapActivity.this
                      .getSystemService(LOCATION_SERVICE);
              Criteria criteria = new Criteria();
              criteria.setAccuracy(ACCURACY_FINE);
              String provider = manager.getBestProvider(criteria, true);
              Location mejor;

              if (provider != null)
                  mejor = manager.getLastKnownLocation(provider);
              else{
                  mejor = null;
                  return;
              }

              Location latestLocation = masReciente(mejor, manager.getLastKnownLocation(GPS_PROVIDER));
              latestLocation = masReciente(latestLocation, manager.getLastKnownLocation(NETWORK_PROVIDER));
              latestLocation = masReciente(latestLocation, manager.getLastKnownLocation(PASSIVE_PROVIDER));

              LatLng l = new LatLng(latestLocation.getLatitude(), latestLocation.getLongitude());
              googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(l, 15.0f));
          }
      });

        //Click Listener
        busqueda.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent filtro = new Intent(main_context, FilterActivities.class);
                startActivity(filtro);

            }
        });
    }


//    private void setBottomToolbar() {
//        mHomeButton = (ImageView) findViewById(R.id.toolbar_home_button);
//        mHomeButton.setOnClickListener(main_context);
//        mProfileButton = (ImageView) findViewById(R.id.toolbar_go_to_map);
//        mProfileButton.setOnClickListener(main_context);
//        mAddPostButton = (ImageView) findViewById(R.id.toolbar_go_to_AR);
//        mAddPostButton.setOnClickListener(main_context);
//    }


    /**
     * Initialises the mapview
     */
    private void createMapView(){
        /**
         * Catch the null pointer exception that
         * may be thrown when initialising the map
         */
        try {
            if(null == googleMap){
                googleMap = ((com.google.android.gms.maps.MapFragment) getFragmentManager().findFragmentById(
                        R.id.mapView)).getMap();

                //Chacao as the center of the map and enough zoom to see it all.
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(10.495343, -66.848908), 15.0f));
                googleMap.setInfoWindowAdapter(new MyInfoWindowAdapter());

                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if(null == googleMap) {
                    Toast.makeText(getApplicationContext(),
                            "Error creating map", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }

    private void filter(){

        // Read filter state

    }

    private void addMarkers(){

        /** Make sure that the map has been initialised **/
        if(null != googleMap){

            /** If it is a permanent thing. */

            DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            List<Event> events = dbHelper.getAllEvents();

            // Divide en servicios, eventos y categoria.
            for(Event e : events){

                Marker m = null;

                String tag =  e.getTags();
                int pos = tag.indexOf(';');

                if (pos != -1) {
                    tag = tag.substring(0, pos);
                }

                m = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(e.getLatitude(), e.getLongitude()))
                        .title(e.getName())
                        .snippet(e.getPhoto())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_teatro)));
                
                // Servicios
                if (e.getType().equals("Fijo")){

                    if(e.getCategory().equals("Cultura")){

                        if (tag.equals("Teatro")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_teatro));
                            cultura_teatro.add(m);
                        }else if(tag.equals("Cine")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_cine));
                            cultura_cine.add(m);
                        }else if(tag.equals("Idioma")){
                           m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_idioma));
                            cultura_idiomas.add(m);
                        }else if(tag.equals("Arte")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_arte));
                            cultura_arte.add(m);
                        }else if(tag.equals("Musica")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_musica));
                            cultura_musica.add(m);
                        }else if(tag.equals("Danza")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_danza));
                            cultura_danza.add(m);
                        }else if(tag.equals("Gastronomia")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_gastronomia));
                            cultura_danza.add(m);
                        }else{
                            //No hay icono de festival,  ponencia o turismo
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_musica));
                        }

                        cultura_servicios.add(m);

                    }else{

                        if (tag.equals("Fultbol")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_futbol));
                            deporte_futbol.add(m);
                        }else if(tag.equals("Basketball")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_basquet));
                            deportes_basquet.add(m);
                        }else if(tag.equals("Voleyball")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_voleyball));
                            deporte_volleyball.add(m);
                        }else if(tag.equals("Natacion")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_natacion));
                            deporte_natacion.add(m);
                        }else if(tag.equals("Skate")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_skate));
                            deporte_skate.add(m);
                        }else if(tag.equals("BMX")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_skate));
                            deporte_skate.add(m);
                        }else if(tag.equals("Yoga")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_yoga));
                            deporte_yoga.add(m);
                        }else if(tag.equals("Tennis")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_tenis));
                            deporte_tenis.add(m);
                        }else if(tag.equals("Carrera")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_fitness));
                            deporte_fitness.add(m);
                        }else if(tag.equals("Fitness")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_fitness));
                            deporte_fitness.add(m);
                        }else{
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_futbol));
                            deporte_futbol.add(m);
                        }

                        deporte_servicios.add(m);
                    }

                    // Eventos
                }else{

                    if(e.getCategory().equals("Cultura")) {

                        if (tag.equals("Teatro")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_teatro));
                            cultura_teatro.add(m);
                        }else if(tag.equals("Cine")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_cine));
                            cultura_cine.add(m);
                        }else if(tag.equals("Idiomas")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_idioma));
                            cultura_idiomas.add(m);
                        }else if(tag.equals("Arte")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_arte));
                            cultura_arte.add(m);
                        }else if(tag.equals("Musica")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_musica));
                            cultura_musica.add(m);
                        }else if(tag.equals("Danza")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_danza));
                            cultura_danza.add(m);
                        }else if(tag.equals("Gastronomia")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_gastronomia));
                            cultura_gastronomia.add(m);
                        }else{
                            //No hay icono de festival,  ponencia o turismo
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_musica));
                            cultura_musica.add(m);
                        }

                        cultura_eventos.add(m);

                    } else {

                        if (tag.equals("Fultbol")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_futbol));
                            deporte_futbol.add(m);
                        }else if(tag.equals("Basketball")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_basquet));
                            deportes_basquet.add(m);
                        }else if(tag.equals("Voleyball")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_voleyball));
                            deporte_volleyball.add(m);
                        }else if(tag.equals("Natacion")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_natacion));
                            deporte_natacion.add(m);
                        }else if(tag.equals("Skate")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_skate));
                            deporte_skate.add(m);
                        }else if(tag.equals("BMX")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_skate));
                            deporte_skate.add(m);
                        }else if(tag.equals("Yoga")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_yoga));
                            deporte_yoga.add(m);
                        }else if(tag.equals("Tennis")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_tenis));
                            deporte_tenis.add(m);
                        }else if(tag.equals("Carrera")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_fitness));
                            deporte_fitness.add(m);
                        }else if(tag.equals("Fitness")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_fitness));
                            deporte_fitness.add(m);
                        }else{
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_futbol));
                            deporte_futbol.add(m);
                        }

                        deporte_eventos.add(m);
                    }

                }
            }
        }
    }
}