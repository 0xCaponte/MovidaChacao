package com.reto.chacao.augmented_reality;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.reto.chacao.R;
import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.StartupConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class AugmentedReality extends ActionBarActivity {

    private ArchitectView architectView;
    private final String ArUrl = "ar/index.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_augmented_reality);
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
            loadPoi();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.architectView.setLocation(10.487375, -66.937133,1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_augmented_reality, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.architectView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.architectView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.architectView.onDestroy();
    }

    /**
     * Carga puntos de la base de datos al universo de realidad aumentada.
     */
    private void loadPoi() {

        JSONArray jsonArr = new JSONArray();

        try {
            JSONObject jsonObj = new JSONObject();

            jsonObj.put("latitude", 10.485434);
            jsonObj.put("longitude", -66.938378);
            jsonObj.put("altitude", 100); // TODO
            // Si el nombre es muy largo lo acorta y pone "..."
            jsonObj.put("title", "La Coromoto");
            jsonObj.put("description", 30 + " "
                    + "mts");
            jsonObj.put("category", "Oficina");

            JSONObject jsonObj2 = new JSONObject();

            jsonObj2.put("latitude", 10.488810);
            jsonObj2.put("longitude", -66.937863);
            jsonObj2.put("altitude", 100); // TODO
            // Si el nombre es muy largo lo acorta y pone "..."
            jsonObj2.put("title", "San Agustin");
            jsonObj2.put("description", 55 + " "
                    + "mts");
            jsonObj2.put("category", "Oficina");

            jsonArr.put(jsonObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String invocation = "Movida.newData(" + jsonArr.toString() + ");";
        architectView.callJavascript(invocation);
    }
}
