package com.reto.chacao.main.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.reto.chacao.beans.Post;
import com.reto.chacao.database.DataBaseHelper;
import com.reto.chacao.map.MovidaMapFragment;
import com.reto.chacao.R;
import com.reto.chacao.abstractcomponents.AppFragment;
import com.reto.chacao.abstractcomponents.MainToolbarActivity;
import com.reto.chacao.augmented_reality.AugmentedReality;
import com.reto.chacao.main.fragment.HomeScreenFragment;
import com.reto.chacao.model.Comment;
import com.reto.chacao.model.Event;
import com.reto.chacao.postdetail.fragment.PostDetailScreenFragment;
import com.reto.chacao.settings.fragment.SettingsFragment;
import com.reto.chacao.statics.MovidaValues;
import com.reto.chacao.util.AppUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        MovidaValues.setAppFragmentListener(this);
        setBottomToolbar();

        Intent mainIntent = getIntent();
        String postId = mainIntent.getStringExtra(getString(R.string.EXTRAS_KEY_POI_ID));
        if (  postId != null ) {
            PostDetailScreenFragment postDetailScreenFragment = new PostDetailScreenFragment();
            Bundle arguments = new Bundle();
            arguments.putSerializable(Post.TAG, loadPostById(postId));
            postDetailScreenFragment.setArguments(arguments);
            runMyFragment(postDetailScreenFragment, Boolean.TRUE);
        }
    }

    private Post loadPostById(String postId) {
        Post mPosts = new Post();
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        Event event = dbHelper.getEvent(Integer.valueOf(postId));
        mPosts.setPost_id(event.getId());
        mPosts.setTitle(event.getName());
        mPosts.setDescription(event.getDescription());

        ArrayList<com.reto.chacao.beans.Comment> comments = new ArrayList<com.reto.chacao.beans.Comment>();
        List<Comment> cm = dbHelper.getCommentByEvent(event.getId());

        if(cm.size() >= 1){
            for(com.reto.chacao.model.Comment c : cm){
                int id = getResources().getIdentifier(c.getFirstname().toLowerCase(),"drawable",
                        getPackageName());
                com.reto.chacao.beans.Comment commentTmp = new com.reto.chacao.beans.Comment();
                commentTmp.setImage(Integer.toString(id));
                commentTmp.setComment_id(c.getId());
                commentTmp.setCommenterFirstName(c.getFirstname());
                commentTmp.setCommenterLastName(c.getLastname());
                commentTmp.setBody(c.getText());
                commentTmp.setCreated(Calendar.getInstance().getTime());

                comments.add(commentTmp);
            }

            mPosts.setComments(comments);

        }

        return mPosts;
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
                mHomeButton.setImageResource(R.drawable.ic_home_active);
                mProfileButton.setImageResource(R.drawable.ic_map);
                mAddPostButton.setImageResource(R.drawable.ic_ra);
                runMyFragment(new HomeScreenFragment(), true);
                break;
            case R.id.toolbar_go_to_map:
                mHomeButton.setImageResource(R.drawable.ic_home);
                mProfileButton.setImageResource(R.drawable.ic_map_active);
                mAddPostButton.setImageResource(R.drawable.ic_ra);
                runMyFragment(new MovidaMapFragment(), true);
                break;
            case R.id.toolbar_go_to_AR:
                showAddPostPopUp();
                break;
        }
    }


    @Override
    protected AppFragment getAppFragment() {
        AppFragment fragment = (AppFragment) getIntent().getSerializableExtra(MovidaValues.SERIALIZABLE_FRAGMENT);
        if(fragment == null){
            fragment = new MovidaMapFragment();
        }else{
            Bundle bundle = new Bundle();
            bundle.putString("POST_DETAIL",getIntent().getStringExtra(MovidaValues.FRAGMENT_BUNDLE));
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    private final class CancelOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
        }
    }

    private final class OkOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            mHomeButton.setImageResource(R.drawable.ic_home);
            mProfileButton.setImageResource(R.drawable.ic_map);
            mAddPostButton.setImageResource(R.drawable.ic_ra_active);
            AppUtil.runActivity(AugmentedReality.class, MovidaMainActivity.this);
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
