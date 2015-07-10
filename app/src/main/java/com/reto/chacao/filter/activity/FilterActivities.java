package com.reto.chacao.filter.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.reto.chacao.R;
import com.reto.chacao.map.MovidaMapActivity;
import com.reto.chacao.util.MapUtil;

import java.util.ArrayList;

public class FilterActivities extends ActionBarActivity implements View.OnClickListener {

    protected Button selectCategoriasDeporte;
    protected Button selectCategoriasCultura;

    protected CharSequence[] deportes = { "Basquet", "Beisbol", "Fitness", "Futbol", "Natacion", "Skate", "Tenis", "Volleyball", "Yoga", "Zumba" };
    protected ArrayList<CharSequence> deportesSeleccionados = new ArrayList<CharSequence>();

    protected CharSequence[] cultura = { "Arte", "Cine", "Danza", "Fotos", "Gastronomia", "Idiomas", "Musica", "Teatro" };
    protected ArrayList<CharSequence> culturasSeleccionados = new ArrayList<CharSequence>();

    private Context main_context;
    private Button boton_busqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_activities);
        selectCategoriasDeporte = (Button) findViewById(R.id.select_categorias_deporte);
        selectCategoriasDeporte.setOnClickListener(this);
        selectCategoriasCultura = (Button) findViewById(R.id.select_categorias_cultura);
        selectCategoriasCultura.setOnClickListener(this);
        main_context = getApplicationContext();
        boton_busqueda = (Button)findViewById(R.id.buscar_categorias);
        boton_busqueda.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Guardo el cambio de filtro
                MapUtil.setFiltrosCategoriasDeporte(FilterActivities.this, deportesSeleccionados);
                MapUtil.setFiltrosCategoriasCultura(FilterActivities.this, culturasSeleccionados);
                Intent filtro = new Intent(main_context, MovidaMapActivity.class);
                startActivity(filtro);

            }
        });

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
    public void onClick(View v) {
        switch(v.getId()) {

            case R.id.select_categorias_deporte:
                mostrarDropdownDeporte();
                break;
            case R.id.select_categorias_cultura:
                mostrarDropdownCultura();
                break;
            default:
                break;

        }

    }

    protected void onChangeDeportesSeleccionados() {

        StringBuilder stringBuilder = new StringBuilder();

        if (deportesSeleccionados.size()!=0){
            for (int i=0; i < deportesSeleccionados.size(); i++){
                if (i==0)
                    stringBuilder.append(deportesSeleccionados.get(i));
                else
                    stringBuilder.append(", "+deportesSeleccionados.get(i));
            }
        }else{
            stringBuilder.append("Selecciona una actividad de deporte");
        }


        selectCategoriasDeporte.setText(stringBuilder.toString());

    }

    protected void mostrarDropdownDeporte() {

        boolean[] deportesMarcados = new boolean[deportes.length];

        for(int i = 0; i < deportes.length; i++)
            deportesMarcados[i] = deportesSeleccionados.contains(deportes[i]);

        DialogInterface.OnMultiChoiceClickListener deportesDialogListener = new DialogInterface.OnMultiChoiceClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                if(isChecked)
                    deportesSeleccionados.add(deportes[which]);
                else
                    deportesSeleccionados.remove(deportes[which]);

                onChangeDeportesSeleccionados();

            }

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccione Deportes");

        builder.setMultiChoiceItems(deportes, deportesMarcados, deportesDialogListener);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    protected void onChangeCulturasSeleccionadas() {

        StringBuilder stringBuilder = new StringBuilder();

        if (culturasSeleccionados.size()!=0){
            for (int i=0; i < culturasSeleccionados.size(); i++){
                if (i==0)
                    stringBuilder.append(culturasSeleccionados.get(i));
                else
                    stringBuilder.append(", "+culturasSeleccionados.get(i));
            }
        }else{
            stringBuilder.append("Selecciona una actividad de cultura");
        }


        selectCategoriasCultura.setText(stringBuilder.toString());

    }

    protected void mostrarDropdownCultura() {

        boolean[] culturasMarcadas = new boolean[cultura.length];

        for(int i = 0; i < cultura.length; i++)
            culturasMarcadas[i] = culturasSeleccionados.contains(cultura[i]);

        DialogInterface.OnMultiChoiceClickListener culturaDialogListener = new DialogInterface.OnMultiChoiceClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                if(isChecked)
                    culturasSeleccionados.add(cultura[which]);
                else
                    culturasSeleccionados.remove(cultura[which]);

                onChangeCulturasSeleccionadas();

            }

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccione Culturas");

        builder.setMultiChoiceItems(cultura, culturasMarcadas, culturaDialogListener);
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
