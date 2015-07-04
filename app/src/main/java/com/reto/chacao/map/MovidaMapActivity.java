package com.reto.chacao.map;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
import com.reto.chacao.database.DataBaseHelper;
import com.reto.chacao.model.Event;

import java.util.ArrayList;
import java.util.List;

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


    // Lista de eventos Fijos de cultura
    private ArrayList<Marker> cultura_servicios = new ArrayList<Marker>();

    // Lista de eventos Fijos de deporte
    private ArrayList<Marker> deporte_servicios = new ArrayList<Marker>();

    // Lista de eventos  de cultura
    private ArrayList<Marker> cultura_eventos = new ArrayList<Marker>();

    // Lista de eventos de deporte
    private ArrayList<Marker> deporte_eventos = new ArrayList<Marker>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        main_context = getApplicationContext();

        //Crea el mapa
        createMapView();

        //Bottom Toolbar
        //setBottomToolbar();

        // Carga los marcadores
        addMarkers();

        // Set filtro cultura
        final CheckBox cultura = (CheckBox) findViewById(R.id.filtro_cultura);
        cultura.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

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

        final CheckBox c_servicios = (CheckBox) findViewById(R.id.filtro_cultura_servicios);
        c_servicios.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!cultura_servicios.isEmpty()) {
                    for (Marker m : cultura_servicios) {
                        m.setVisible(c_servicios.isChecked());
                    }
                }
            }
        });

        final CheckBox c_eventos = (CheckBox) findViewById(R.id.filtro_cultura_eventos);
        c_eventos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!cultura_eventos.isEmpty()) {
                    for (Marker m : cultura_eventos) {
                        m.setVisible(c_eventos.isChecked());
                    }
                }
            }
        });


        // Set filtro deportes
        final CheckBox deporte = (CheckBox) findViewById(R.id.filtro_deporte);
        deporte.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!deporte_eventos.isEmpty()) {
                    for (Marker m : deporte_eventos) {

                        m.setVisible(deporte.isChecked());
                    }
                }

                if (!deporte_servicios.isEmpty()) {
                    for (Marker m : deporte_servicios) {
                        m.setVisible(deporte.isChecked());
                    }
                }
            }
        });

        final CheckBox d_servicios = (CheckBox) findViewById(R.id.filtro_deporte_servicios);
        d_servicios.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!deporte_servicios.isEmpty()) {
                    for (Marker m : deporte_servicios) {
                        m.setVisible(d_servicios.isChecked());
                    }
                }
            }
        });

        final CheckBox d_eventos = (CheckBox) findViewById(R.id.filtro_deporte_eventos);
        d_eventos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

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

                // Servicios
                if (e.getType().equals("Fijo")){

                    Marker m = googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(e.getLatitude(), e.getLongitude()))
                            .title(e.getName())
                            .snippet(e.getDescription())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

                    if(e.getCategory().equals("Cultura")){

                        cultura_servicios.add(m);

                    }else{

                        deporte_servicios.add(m);
                    }

                    // Eventos
                }else{

                    Marker m2 = googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(e.getLatitude(), e.getLongitude()))
                            .title(e.getName())
                            .snippet(e.getDescription())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                    if(e.getCategory().equals("Cultura")) {

                        cultura_eventos.add(m2);

                    } else {

                        deporte_eventos.add(m2);
                    }

                }
            }
        }
    }
}