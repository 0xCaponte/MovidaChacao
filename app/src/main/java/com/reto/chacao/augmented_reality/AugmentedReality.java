package com.reto.chacao.augmented_reality;

import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.reto.chacao.R;
import com.reto.chacao.database.DataBaseHelper;
import com.reto.chacao.model.Event;
import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.StartupConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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

            for(Event e:events){
                JSONObject jsonObj = new JSONObject();
                DistanceResult distance = distance(mLastLocation.getLatitude(), mLastLocation.getLongitude(),
                        e.getLatitude(), e.getLongitude());

                jsonObj.put("latitude", e.getLatitude());
                jsonObj.put("longitude", e.getLongitude());
                jsonObj.put("altitude", 100); // TODO
                // Si el nombre es muy largo lo acorta y pone "..."
                jsonObj.put("title", e.getName());
                jsonObj.put("description", distance.distance + " "
                        + distance.unit);
                jsonObj.put("category", e.getCategory());
                jsonArr.put(jsonObj);

            }


            // String strDistance = String.valueOf(distance);
            // String[] parts = strDistance.split(".");


//            JSONObject jsonObj2 = new JSONObject();


            // String strDistance = String.valueOf(distance);
            // String[] parts = strDistance.split(".");
//            distance = distance(mLastLocation.getLatitude(), mLastLocation.getLongitude(),
//                    10.4594226, -66.8711889);
//            jsonObj2.put("latitude", 10.4594226);
//            jsonObj2.put("longitude", -66.8711889);
//            jsonObj2.put("altitude", 100); // TODO
//            // Si el nombre es muy largo lo acorta y pone "..."
//            jsonObj2.put("title", "Emil Friedman");
//            jsonObj2.put("description", distance.distance + " "
//                    + distance.unit);
//            jsonObj2.put("category", "Oficina");



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

    private DistanceResult distance(double oLon, double oLat, double pLon,
                                    double pLat) {

        String unit = "mts.";

        Location dest = new Location("Dest");
        dest.setLatitude(pLat);
        dest.setLongitude(pLon);

        float distance = mLastLocation.distanceTo(dest);

        if (distance > 1000) {
            unit = "km.";
            distance = distance / 1000;
        }

        DecimalFormat df = new DecimalFormat("#.#", new DecimalFormatSymbols(
                Locale.ENGLISH));
        String distanceStr = df.format(distance);
        double finalDistance = Double.valueOf(distanceStr);

        return new DistanceResult(finalDistance, unit);
    }

    /**
     * Representa distancias basadas en metros.
     *
     * @author Domingo De Abreu
     */
    class DistanceResult {

        double distance;
        String unit;

        public DistanceResult(double distance, String unit) {

            this.distance = distance;
            this.unit = unit;
        }
    }
}
