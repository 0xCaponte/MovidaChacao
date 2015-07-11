package com.reto.chacao.augmented_reality;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.webkit.WebView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.reto.chacao.R;
import com.reto.chacao.database.DataBaseHelper;
import com.reto.chacao.main.activity.MovidaMainActivity;
import com.reto.chacao.model.Event;
import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.StartupConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AugmentedReality extends ActionBarActivity implements GoogleApiClient
        .ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private ArchitectView architectView;
    private final String ArUrl = "ar/index.html";
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    protected LocationRequest mLocationRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_augmented_reality);
        buildGoogleApiClient();
        WebView.setWebContentsDebuggingEnabled(true);
        this.architectView = (ArchitectView)this.findViewById(R.id.architectView);
        final StartupConfiguration config = new StartupConfiguration(getString(R.string.license_key));
        this.architectView.onCreate(config);
        this.architectView.registerUrlListener(getUrlListener());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.architectView.onPostCreate();
        try {
            this.architectView.load(ArUrl);
            this.architectView.setLocation(10.4930584, -66.8596415, 100);
            mLastLocation = new Location("");
            mLastLocation.setLatitude(10.4930584);
            mLastLocation.setLongitude(-66.8596415);
            mLastLocation.setAccuracy(100);
            loadPoi();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //this.architectView.setCullingDistance(50);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.architectView.onResume();
        mGoogleApiClient.connect();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.architectView.onPause();
        stopLocationUpdates();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.architectView.onDestroy();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(10000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if(mLastLocation == null){
            mLastLocation = new Location("eve");
            mLastLocation.setLatitude(10.4930584);
            mLastLocation.setLongitude(-66.8596415);
        }
        setupLocation(mLastLocation);
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        setupLocation(location);
    }


    /**
     * Carga puntos de la base de datos al universo de realidad aumentada.
     */
    private void loadPoi() {

        JSONArray jsonArr = new JSONArray();

        try {

            DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            List<Event> events = dbHelper.getAllEvents();

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String now = df.format(date);

            try {
                date = df.parse(now);
            } catch (ParseException e) {
                date = new Date();
            }

            for(Event e:events) {

                Date event_date = date;

                if (e.getType().equals("Movil")) {

                    String[] d = e.getDateStart().split(" ");
                    try {
                        event_date = df.parse(d[0]);
                    } catch (ParseException e1) {

                    }

                    // Diferencia de dias
                    long days = event_date.getTime() - date.getTime();
                    days /=  (1000 * 60 * 60 * 24);

                    // Valor Absoluto
                    long diff = (days < 0) ? -days : days;

                    // Si no es en los proximos tres dias no sale en el radar
                    if (diff > 3)
                        continue;
                }


                JSONObject jsonObj = new JSONObject();


                // Ubicación actual
                Location current = new Location("");
                current.setLatitude(mLastLocation.getLatitude());
                current.setLongitude(mLastLocation.getLongitude());

                // Ubicación del evento
                Location event_location = new Location("");
                event_location.setLatitude(e.getLatitude());
                event_location.setLongitude(e.getLongitude());

                // Calculo de distancia.
                float distance = current.distanceTo(event_location);
                distance = (float) (Math.floor(distance * 100) / 100);
                String unit = "Km";

                // Llevo a km o metros
                if (distance >= 1000){
                    distance /= 1000;
                    distance = (float) (Math.floor(distance * 100) / 100);
                }else{
                    unit = "M";
                }

                jsonObj.put("id",e.getId());
                jsonObj.put("latitude", e.getLatitude());
                jsonObj.put("longitude", e.getLongitude());
                jsonObj.put("altitude", 100); // TODO
                // Si el nombre es muy largo lo acorta y pone "..."
                jsonObj.put("title", e.getName());
                jsonObj.put("description", distance + " "
                        + unit);
                jsonObj.put("category", e.getCategory());
                jsonArr.put(jsonObj);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String invocation = "Movida.newData(" + jsonArr.toString() + ");";
        architectView.callJavascript(invocation);
    }

    /**
     * Establece la ubicación de AR.
     *
     * @param location
     *            la ubicación, puede ser null si no hay ninguna.
     */
    private void setupLocation(Location location) {
        Log.w("AR","ADDED LOCATION");
        if (architectView == null) {
            return;
        }

        if (location.hasAltitude()) {
            architectView.setLocation(location.getLatitude(),
                    location.getLongitude(), location.getAltitude(),
                    location.hasAccuracy() ? location.getAccuracy() : 1000);
        } else {
            architectView.setLocation(location.getLatitude(),
                    location.getLongitude(),
                    location.hasAccuracy() ? location.getAccuracy() : 1000);
        }
    }

    public ArchitectView.ArchitectUrlListener getUrlListener() {
        return new ArchitectView.ArchitectUrlListener() {

            @Override
            public boolean urlWasInvoked(String uriString) {
                Uri invokedUri = Uri.parse(uriString);

                // pressed "More" button on POI-detail panel
                if ("markerselected".equalsIgnoreCase(invokedUri.getHost())) {
                    final Intent poiDetailIntent = new Intent(AugmentedReality.this, MovidaMainActivity
                            .class);
                    poiDetailIntent.putExtra(getString(R.string.EXTRAS_KEY_POI_ID), String.valueOf(invokedUri
                            .getQueryParameter
                            ("id")) );
                    AugmentedReality.this.startActivity(poiDetailIntent);
                }

                return true;
            }
        };
    }
}
