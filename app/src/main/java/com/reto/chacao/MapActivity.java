package com.reto.chacao;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends ActionBarActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        main_context = getApplicationContext(); //Getting current cuntext.

        //Getting the image butttons
        ImageButton search = (ImageButton)findViewById(R.id.imageButtonSearch);
        ImageButton settings = (ImageButton)findViewById(R.id.imageButtonSettigns);
        ImageButton sports = (ImageButton)findViewById(R.id.imageButtonSports);
        ImageButton culture = (ImageButton)findViewById(R.id.imageButtonCulture);
        ImageButton ar = (ImageButton)findViewById(R.id.imageButtonAR);


        //Creating click listeners
        SearchOnClickListener l1 = new SearchOnClickListener();
        l1.MyClickListener(main_context);

        SettingsOnClickListener l2 = new SettingsOnClickListener();
        l2.MyClickListener(main_context);

        SportsOnClickListener l3 = new SportsOnClickListener();
        l3.MyClickListener(main_context);

        CultureOnClickListener l4 = new CultureOnClickListener();
        l4.MyClickListener(main_context);

        AROnClickListener l5 = new AROnClickListener();
        l5.MyClickListener(main_context);

        //Setting up click listeners
        search.setOnClickListener(l1);
        settings.setOnClickListener(l2);
        sports.setOnClickListener(l3);
        culture.setOnClickListener(l4);
        ar.setOnClickListener(l5);




        createMapView();
        addMarker();
    }

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
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(
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

    private void addMarker(){

        String type = "event";
        /** Make sure that the map has been initialised **/
        if(null != googleMap){

            /** If it is a permanent thing. */
            if (type.equals("event")){
                googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(10.503383, -66.857439))
                                .title("Permanente - Alianza Francesa")

                                .snippet(" Omelette du fromage  Omelette du fromage Omelette du fromage " +
                                                "Omelette du fromage  Omelette du fromage Omelette du fromage " +
                                                "Omelette du fromage  Omelette du fromage Omelette du fromage " +
                                                "Omelette du fromage  Omelette du fromage Omelette du fromage" +
                                                " Omelette du fromage  Omelette du fromage Omelette du fromage " +
                                                "Omelette du fromage  Omelette du fromage Omelette du fromage " +
                                                "Omelette du fromage  Omelette du fromage Omelette du fromage " +
                                                "Omelette du fromage  Omelette du fromage Omelette du fromage " +
                                                "Omelette du fromage  Omelette du fromage Omelette du fromage " +
                                                " Omelette du fromage  Omelette du fromage Omelette du fromage " +
                                                "Omelette du fromage  Omelette du fromage Omelette du fromage " +
                                                "Omelette du fromage  Omelette du fromage Omelette du fromage " +
                                                "Omelette du fromage  Omelette du fromage Omelette du fromage" +
                                                " Omelette du fromage  Omelette du fromage Omelette du fromage " +
                                                "Omelette du fromage  Omelette du fromage Omelette du fromage " +
                                                "Omelette du fromage  Omelette du fromage Omelette du fromage " +
                                                "Omelette du fromage  Omelette du fromage Omelette du fromage " +
                                                "Omelette du fromage  Omelette du fromage Omelette du fromage")

                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                );

                googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(10.497804, -66.851758))
                                .title("Evento")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                );

            }else{  /** If it is a temporal event */
                googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(10.497804, -66.851758))
                                .title("Evento")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                );
            }
        }
    }
}