package com.reto.chacao.map;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
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
            TextView snippet = ((TextView)myContentsView.findViewById(R.id.snippet));
            snippet.setText(marker.getSnippet());

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

    // Checkboxs
    private CheckBox cultura;
    private CheckBox c_servicios;
    private CheckBox c_eventos;
    private CheckBox deporte;
    private CheckBox d_servicios;
    private CheckBox d_eventos;

    // Lista de eventos Fijos de cultura
    private ArrayList<Marker> cultura_servicios = new ArrayList<Marker>();

    // Lista de eventos Fijos de deporte
    private ArrayList<Marker> deporte_servicios = new ArrayList<Marker>();

    // Lista de eventos  de cultura
    private ArrayList<Marker> cultura_eventos = new ArrayList<Marker>();

    // Lista de eventos de deporte
    private ArrayList<Marker> deporte_eventos = new ArrayList<Marker>();


    //Establece los filtros iniciales
    private void setFilters(MapProfile m){

        cultura.setChecked(m.isFiltro_cultura());
        c_servicios.setChecked(m.isFiltro_cultura_servicios());
        c_eventos.setChecked(m.isFiltro_cultura_eventos());
        deporte.setChecked(m.isFiltro_deporte());
        d_servicios.setChecked(m.isFiltro_deporte_servicios());
        d_eventos.setChecked(m.isFiltro_deporte_eventos());

    }

    // Hace visibles los marcadores pertinentes
    private void loadMarkers(){

        if (!cultura_eventos.isEmpty()) {
                for (Marker m : cultura_eventos) {
                    m.setVisible(cultura.isChecked() || c_eventos.isChecked());
                }
        }

        if (!cultura_servicios.isEmpty()) {
                for (Marker m : cultura_servicios) {
                    m.setVisible(cultura.isChecked() || c_servicios.isChecked());
                }
        }

        if (!deporte_eventos.isEmpty()) {
                for (Marker m : deporte_eventos) {
                    m.setVisible((deporte.isChecked() || d_eventos.isChecked()));
                }
        }

        if (!deporte_servicios.isEmpty()) {
                for (Marker m : deporte_servicios) {
                    m.setVisible(deporte.isChecked() || d_servicios.isChecked());
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

        // Set filtros cultura
        cultura = (CheckBox) findViewById(R.id.filtro_cultura);
        c_servicios = (CheckBox) findViewById(R.id.filtro_cultura_servicios);
        c_eventos = (CheckBox) findViewById(R.id.filtro_cultura_eventos);

        // Set filtros deportes
        deporte = (CheckBox) findViewById(R.id.filtro_deporte);
        d_servicios = (CheckBox) findViewById(R.id.filtro_deporte_servicios);
        d_eventos = (CheckBox) findViewById(R.id.filtro_deporte_eventos);

        main_context = getApplicationContext();
        MapProfile mp = MapUtil.getMapFilters(main_context);

        //Establece los filtros iniciales
        setFilters(mp);

        //Crea el mapa
        createMapView();

        //Bottom Toolbar
        //setBottomToolbar();

        // Carga los marcadores
        addMarkers();

        // Initial Markers
        loadMarkers();

        // My location
        googleMap.setMyLocationEnabled(true);

        googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {

                LocationManager manager = (LocationManager) MovidaMapActivity.this
                        .getSystemService(LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                criteria.setAccuracy(ACCURACY_FINE);
                String provider = manager.getBestProvider(criteria, true);
                Location mejor;

                if (provider != null)
                    mejor = manager.getLastKnownLocation(provider);
                else
                    mejor = null;

                Location latestLocation = masReciente(mejor, manager.getLastKnownLocation(GPS_PROVIDER));
                latestLocation = masReciente(latestLocation, manager.getLastKnownLocation(NETWORK_PROVIDER));
                latestLocation = masReciente(latestLocation, manager.getLastKnownLocation(PASSIVE_PROVIDER));

                LatLng l = new LatLng(latestLocation.getLatitude(),latestLocation.getLongitude());
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(l, 14.0f));

                return true;
            }
        });

        //Click Listener
        cultura.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Guardo el cambio de filtro
                MapUtil.setFiltroCultura(MovidaMapActivity.this, cultura.isChecked());
                MapUtil.setFiltroCulturaEventos(MovidaMapActivity.this, cultura.isChecked());
                MapUtil.setFiltroCulturaServicios(MovidaMapActivity.this, cultura.isChecked());

                if (!cultura_eventos.isEmpty()) {
                    for (Marker m : cultura_eventos) {
                        m.setVisible(cultura.isChecked());
                    }
                }

                if (!cultura_servicios.isEmpty()) {
                    for (Marker m : cultura_servicios) {
                        m.setVisible(cultura.isChecked());
                    }
                }
            }
        });


        //Click Listener
        c_servicios.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Guardo el cambio de filtro
                MapUtil.setFiltroCulturaServicios(MovidaMapActivity.this, c_servicios.isChecked());

                if (!cultura_servicios.isEmpty()) {
                    for (Marker m : cultura_servicios) {
                        m.setVisible(c_servicios.isChecked());
                    }
                }
            }
        });


        //Click Listener
        c_eventos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Guardo el cambio de filtro
                MapUtil.setFiltroCulturaEventos(MovidaMapActivity.this, c_eventos.isChecked());

                if (!cultura_eventos.isEmpty()) {
                    for (Marker m : cultura_eventos) {
                        m.setVisible(c_eventos.isChecked());
                    }
                }
            }
        });


        //Click Listener
        deporte.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Guardo el cambio de filtro
                MapUtil.setFiltroDeportes(MovidaMapActivity.this, deporte.isChecked());
                MapUtil.setFiltroDeportesEventos(MovidaMapActivity.this, deporte.isChecked());
                MapUtil.setFiltroDeportesServicios(MovidaMapActivity.this, deporte.isChecked());

                if (!cultura_eventos.isEmpty()) {
                    for (Marker m : cultura_eventos) {
                        m.setVisible(cultura.isChecked());
                    }
                }

                if (!cultura_servicios.isEmpty()) {
                    for (Marker m : cultura_servicios) {
                        m.setVisible(cultura.isChecked());
                    }
                }
            }
        });

        //Click Listener
        d_servicios.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Guardo el cambio de filtro
                MapUtil.setFiltroDeportesServicios(MovidaMapActivity.this, d_servicios.isChecked());

                if (!deporte_servicios.isEmpty()) {
                    for (Marker m : deporte_servicios) {
                        m.setVisible(d_servicios.isChecked());
                    }
                }
            }
        });


        //Click Listener
        d_eventos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Guardo el cambio de filtro
                MapUtil.setFiltroDeportesEventos(MovidaMapActivity.this, d_eventos.isChecked());

                if (!deporte_eventos.isEmpty()) {
                    for (Marker m : deporte_eventos) {
                        m.setVisible(d_eventos.isChecked());
                    }
                }
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
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(10.499175, -66.854197), 14.0f));
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
                        .snippet(e.getDescription())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_teatro)));
                
                // Servicios
                if (e.getType().equals("Fijo")){

                    if(e.getCategory().equals("Cultura")){

                        if (tag.equals("Teatro")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_teatro));
                        }else if(tag.equals("Cine")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_cine));
                        }else if(tag.equals("Idioma")){
                           m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_idioma));
                        }else if(tag.equals("Arte")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_arte));
                        }else if(tag.equals("Musica")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_musica));
                        }else if(tag.equals("Danza")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_danza));
                        }else if(tag.equals("Gastronomia")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_gastronomia));
                        }else{
                            //No hay icono de festival,  ponencia o turismo
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_musica));
                        }

                        cultura_servicios.add(m);

                    }else{

                        if (tag.equals("Fultbol")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_futbol));
                        }else if(tag.equals("Basketball")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_basquet));
                        }else if(tag.equals("Voleyball")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_voleyball));
                        }else if(tag.equals("Natacion")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_natacion));
                        }else if(tag.equals("Skate")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_skate));
                        }else if(tag.equals("BMX")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_skate));
                        }else if(tag.equals("Yoga")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_yoga));
                        }else if(tag.equals("Tennis")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_tenis));
                        }else if(tag.equals("Carrera")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_fitness));
                        }else if(tag.equals("Fitness")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_fitness));
                        }else{
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_futbol));
                        }

                        deporte_servicios.add(m);
                    }

                    // Eventos
                }else{

                    if(e.getCategory().equals("Cultura")) {

                        if (tag.equals("Teatro")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_teatro));
                        }else if(tag.equals("Cine")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_cine));
                        }else if(tag.equals("Idiomas")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_idioma));
                        }else if(tag.equals("Arte")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_arte));
                        }else if(tag.equals("Musica")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_musica));
                        }else if(tag.equals("Danza")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_danza));
                        }else if(tag.equals("Gastronomia")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_gastronomia));
                        }else{
                            //No hay icono de festival,  ponencia o turismo
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_musica));
                        }

                        cultura_eventos.add(m);

                    } else {

                        if (tag.equals("Fultbol")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_futbol));
                        }else if(tag.equals("Basketball")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_basquet));
                        }else if(tag.equals("Voleyball")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_voleyball));
                        }else if(tag.equals("Natacion")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_natacion));
                        }else if(tag.equals("Skate")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_skate));
                        }else if(tag.equals("BMX")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_skate));
                        }else if(tag.equals("Yoga")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_yoga));
                        }else if(tag.equals("Tennis")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_tenis));
                        }else if(tag.equals("Carrera")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_fitness));
                        }else if(tag.equals("Fitness")){
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_fitness));
                        }else{
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_event_futbol));
                        }

                        deporte_eventos.add(m);
                    }

                }
            }
        }
    }
}