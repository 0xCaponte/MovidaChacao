package com.reto.chacao;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class MapActivity extends Activity {

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
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
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
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(10.499175, -66.854197), 14.0f) );
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
                                                "Omelette du fromage  Omelette du fromage Omelette du fromage "
                                                + " Omelette du fromage  Omelette du fromage Omelette du fromage " +
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