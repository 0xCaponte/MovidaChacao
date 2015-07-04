package com.reto.chacao.main.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.reto.chacao.MovidaMapActivity;
import com.reto.chacao.R;
import com.reto.chacao.abstractcomponents.AppFragment;
import com.reto.chacao.abstractcomponents.MainToolbarActivity;
import com.reto.chacao.augmented_reality.AugmentedReality;
import com.reto.chacao.main.dialog.AddPostDialog;
import com.reto.chacao.main.fragment.HomeScreenFragment;
import com.reto.chacao.settings.fragment.SettingsFragment;
import com.reto.chacao.statics.ClamourValues;

/**
 * Created by Eduardo Luttinger on 05/06/2015.
 */
public class MovidaMainActivity extends MainToolbarActivity implements AppFragment.AppFragmentListener, View.OnClickListener {


    private ImageView mHomeButton;
    private ImageView mProfileButton;
    private ImageView mAddPostButton;

    public MovidaMainActivity() {
        super(R.layout.activity_main_home_screen, R.id.home_top_toolbar,R.id.fragmentContainer);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ClamourValues.setAppFragmentListener(this);
        setBottomToolbar();
    }

    @Override
    public void goToFragment(AppFragment fragment) {
        runMyFragment(fragment, Boolean.TRUE);
    }

    private void setBottomToolbar() {
        mHomeButton = (ImageView) findViewById(R.id.toolbar_home_button);
        mHomeButton.setOnClickListener(this);
        mProfileButton = (ImageView) findViewById(R.id.toolbar_go_to_map);
        mProfileButton.setOnClickListener(this);
        mAddPostButton = (ImageView) findViewById(R.id.toolbar_go_to_AR);
        mAddPostButton.setOnClickListener(this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.bottomToolbar);
        mToolbar.setContentInsetsAbsolute(0,0);

    }

    private void showAddPostPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Modo de Realidad Aumentada");
        builder.setMessage("Ud. está a punto de entrar en el modo de realidad aumentada. ¿Desea " +
                "continuar?");
        builder.setCancelable(true);
        builder.setPositiveButton("Continuar", new OkOnClickListener());
        builder.setNegativeButton("Ir atrás", new CancelOnClickListener());
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_home_button:
                runMyFragment(new HomeScreenFragment(), true);
                break;
            case R.id.toolbar_go_to_map:
                Intent myTriggerActivityIntent=new Intent(this, MovidaMapActivity.class);
                startActivity(myTriggerActivityIntent);

                break;
            case R.id.toolbar_go_to_AR:
                showAddPostPopUp();
                break;
        }
    }


    @Override
    protected AppFragment getAppFragment() {
        AppFragment fragment = (AppFragment) getIntent().getSerializableExtra(ClamourValues.SERIALIZABLE_FRAGMENT);
        if(fragment == null){
            fragment = new HomeScreenFragment();
        }else{
            Bundle bundle = new Bundle();
            bundle.putString("POST_DETAIL",getIntent().getStringExtra(ClamourValues.FRAGMENT_BUNDLE));
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    private final class CancelOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getApplicationContext(), "Cancle selected, activity continues",
                    Toast.LENGTH_LONG).show();
        }
    }

    private final class OkOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Intent intent = new Intent(MovidaMainActivity.this, AugmentedReality.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.default_menu, menu);
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
            goToFragment(new SettingsFragment());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
