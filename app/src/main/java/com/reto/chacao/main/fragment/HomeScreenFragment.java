package com.reto.chacao.main.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.reto.chacao.R;
import com.reto.chacao.abstractcomponents.AppFragment;
import com.reto.chacao.beans.Comment;
import com.reto.chacao.beans.ItemCondition;
import com.reto.chacao.beans.Post;
import com.reto.chacao.beans.UserProfile;
import com.reto.chacao.database.DataBaseHelper;
import com.reto.chacao.listener.EndlessRecyclerOnScrollListener;
import com.reto.chacao.main.activity.MovidaMainActivity;
import com.reto.chacao.main.adapter.OneColumnAdapter;
import com.reto.chacao.main.adapter.TwoColumnAdapter;
import com.reto.chacao.model.Event;
import com.reto.chacao.postdetail.fragment.PostDetailScreenFragment;
import com.reto.chacao.util.AppUtil;
import com.reto.chacao.util.UserUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Domingo De Abreu on 26/06/2015.
 */
public class HomeScreenFragment extends AppFragment implements CompoundButton.OnCheckedChangeListener,
        View.OnClickListener, OneColumnAdapter.PostViewClickListener, TwoColumnAdapter.PostTwoColumnViewClickListener {

    private static final String TAG = "HomeScreen-Fragment";
    private static final long TUTORIAL_SCREEN = 2000;
    ImageSpan imageSpan;
    SpannableString content;
    private RecyclerView mOneColumnRecyclerView;
    private RecyclerView mTwoColumnsRecyclerView;
    private OneColumnAdapter mOneColumnAdapter;
    private TwoColumnAdapter mTwoColumnsAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private RelativeLayout mTutorial;
    private ArrayList<Post> mPosts;
    private RelativeLayout mNotificationButton;
    private Toolbar mToolbar;
    private FloatingActionButton mFloatingToogle;
    private Boolean mShowingTwoColumns;

    public HomeScreenFragment() {
        super();
    }

    @Override
    public String getMyTag() {
        return TAG;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("onOptionsItemSelected", "yes");
        switch (item.getItemId()) {
            case R.id.action_search:
                call_dialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mPosts = setPostItems();

        setViews(root);

        mToolbar = (Toolbar) root.findViewById(R.id.home_top_toolbar); // Attaching the layout to
/*
        ImageView settingsButton = (ImageView) root.findViewById(R.id.user_profile_settings);
        settingsButton.setOnClickListener(this);
        ImageView settingsButton2 = (ImageView) root.findViewById(R.id.toolbar_search_button);
        settingsButton2.setOnClickListener(this);*/

        if (UserUtil.getFirstTimeHome(getActivity()))
            mTutorial.setVisibility(View.GONE);


        return root;
    }


    private void setViews(View root) {
        mFloatingToogle = (FloatingActionButton) root.findViewById(R.id.FABToogle);
        mFloatingToogle.setOnClickListener(this);
        mShowingTwoColumns = true;
        mOneColumnRecyclerView = (RecyclerView) root.findViewById(R.id.home_list_view);
        mOneColumnRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mOneColumnRecyclerView.setLayoutManager(mLinearLayoutManager);
        mOneColumnRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int current_page) {

            }
        });

        mOneColumnAdapter = new OneColumnAdapter(getActivity(), this, mPosts);
        mOneColumnRecyclerView.setAdapter(mOneColumnAdapter);

        mTwoColumnsRecyclerView = (RecyclerView) root.findViewById(R.id.home_grid_view);
        mTwoColumnsRecyclerView.setHasFixedSize(true);
        if (this.getResources().getBoolean(R.bool.isATabled)) {
            mTwoColumnsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        } else {
            mTwoColumnsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }


        mTwoColumnsAdapter = new TwoColumnAdapter(getActivity(), this, mPosts);
        mTwoColumnsRecyclerView.setAdapter(mTwoColumnsAdapter);
        mTwoColumnsRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int current_page) {
            }
        });

        mTutorial = (RelativeLayout) root.findViewById(R.id.home_tutorial);
        mTutorial.setOnClickListener(this);
    }

    private void onTwoColumnClick() {
        mFloatingToogle.setImageResource(R.drawable.btn_one_column);
        mTwoColumnsRecyclerView.setVisibility(View.VISIBLE);
        mOneColumnRecyclerView.setVisibility(View.GONE);
    }

    private void onOneColumnClick() {
        mFloatingToogle.setImageResource(R.drawable.btn_two_columns);
        mOneColumnRecyclerView.setVisibility(View.VISIBLE);
        mTwoColumnsRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.FABToogle:
                if ( mShowingTwoColumns ) {
                    onOneColumnClick();
                    mShowingTwoColumns = false;
                } else {
                    onTwoColumnClick();
                    mShowingTwoColumns = true;
                }
                break;
            case R.id.home_tutorial:
                mTutorial.setVisibility(View.GONE);
                UserUtil.setFirstTimeHome(getActivity());
                break;

        }
    }

    private void response_dialog(String mesagge) {
        AlertDialog ad = new AlertDialog.Builder(getActivity())
                .create();
        ad.setCancelable(false);
        ad.setTitle("Repuesta");
        ad.setMessage(mesagge);
        ad.setButton(getActivity().getString(R.string.app_title), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ad.show();
    }

    private void call_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("¿ Qué tipo de evento buscas ?");

        // Set up the input
        final EditText input = new EditText(getActivity());
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Buscar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                DataBaseHelper dbHelper = new DataBaseHelper(getActivity());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                try {
                    dbHelper.onCreate(db);
                } catch (Exception err) {
                    err.printStackTrace();
                }

                List<String> claves = Arrays.asList(input.getText().toString().split(" "));
                HashSet<Event> set = new HashSet<Event>();

                // Busca Todas las palabras claves
                for (String s : claves) {

                    // Guarda todos los evento sque coindican con al menos una palabra clave
                    List<Event> events = dbHelper.getBySearch(input.getText().toString());
                    for (Event e : events) {
                        set.add(e);
                    }
                }

                // Cargar esos eventos a la vista.


            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public void postViewListClicked(View v, int position) {
        Log.i(TAG, "the position is: " + position);
        PostDetailScreenFragment postDetailScreenFragment = new PostDetailScreenFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(Post.TAG, mPosts.get(position));
        postDetailScreenFragment.setArguments(arguments);
        getFragmentListener().goToFragment(postDetailScreenFragment);
    }

    @Override
    public void postCommentLikeClick(View v, int position) {
        Post post = mPosts.get(position);
        AppUtil.showAToast("Like Post " + post.getPost_id() + " Comment: " + post.getComments().get
                (0).getBody());
    }


    @Override
    public void postMoreCommentClick(View v, int position) {
        Post post = mPosts.get(position);
        PostDetailScreenFragment postDetailScreenFragment = new PostDetailScreenFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(Post.TAG, mPosts.get(position));
        arguments.putBoolean("COMMENT",true);
        postDetailScreenFragment.setArguments(arguments);
        getFragmentListener().goToFragment(postDetailScreenFragment);
    }

    @Override
    public void postTwoColumnViewListClicked(View v, int position) {
        Log.i(TAG, "the position is: " + position);
        PostDetailScreenFragment postDetailScreenFragment = new PostDetailScreenFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(Post.TAG, mPosts.get(position));
        postDetailScreenFragment.setArguments(arguments);
        getFragmentListener().goToFragment(postDetailScreenFragment);
    }

    @Override
    public void postTwoCommentLikeClick(View v, int position) {
        Post post = mPosts.get(position);
        AppUtil.showAToast("Like Post " + post.getPost_id() + " Comment: " + post.getComments().get
                (0).getBody());
    }

    @Override
    public void postTwoMoreCommentClick(View v, int position) {
        Post post = mPosts.get(position);
        PostDetailScreenFragment postDetailScreenFragment = new PostDetailScreenFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(Post.TAG, mPosts.get(position));
        arguments.putBoolean("COMMENT", true);
        postDetailScreenFragment.setArguments(arguments);
        getFragmentListener().goToFragment(postDetailScreenFragment);
    }

    // Carga los eventos que retornaron de la vista
    public ArrayList<Post> setSearchPostItems(ArrayList<Event> events){

        DataBaseHelper dbHelper = new DataBaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<Post> posts = new ArrayList<Post>();

        for(Event e : events){

            ArrayList<Comment> comments = new ArrayList<Comment>();
            Post post = new Post();
            ItemCondition condition = new ItemCondition();
            UserProfile user = new UserProfile();

            post.setPost_id(e.getId());
            post.setTitle(e.getName());
            post.setDescription(e.getDescription());


            condition.setId(e.getId());
            condition.setName("Ahora");
            post.setCondition(condition);
            post.setCreated(Calendar.getInstance().getTime());

            user.setUserId(e.getId());
            user.setFirstName("Alcadía de Chacao");
            user.setFamilyName("");
            post.setUser(user);

            List<com.reto.chacao.model.Comment> cm = dbHelper.getCommentByEvent(e.getId());

            if(cm.size() >= 1){
                for(com.reto.chacao.model.Comment c : cm){
                    Comment commentTmp = new Comment();
                    commentTmp.setComment_id(c.getId());
                    commentTmp.setCommenterFirstName(c.getFirstname());
                    commentTmp.setCommenterLastName(c.getLastname());
                    commentTmp.setBody(c.getText());
                    commentTmp.setCreated(Calendar.getInstance().getTime());

                    comments.add(commentTmp);
                }

                post.setComments(comments);

            }

            posts.add(post);

        }

        return posts;
    }


    // Trae TODOS los eventos de la BD y los carga
    public ArrayList<Post> setPostItems() {

        DataBaseHelper dbHelper = new DataBaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        List<Event> events = dbHelper.getAllEvents();

        ArrayList<Post> posts = new ArrayList<Post>();

        int i = 0;

        for(Event e : events){
            if ( i == 4 ) {
                break;
            }
            i++;
            ArrayList<Comment> comments = new ArrayList<Comment>();
            Post post = new Post();
            ItemCondition condition = new ItemCondition();
            UserProfile user = new UserProfile();

            post.setPost_id(e.getId());
            post.setTitle(e.getName());
            post.setDescription(e.getDescription());

            condition.setId(e.getId());
            condition.setName("Ahora");
            post.setCondition(condition);
            post.setCreated(Calendar.getInstance().getTime());
//            int id = getResources().getIdentifier("retochacao:drawable/" + e.getPhoto(), null, null);
            int id = getResources().getIdentifier(e.getPhoto(),"drawable",getActivity().getPackageName());
            post.setMainImageUrl(Integer.toString(id));

            user.setUserId(e.getId());
            user.setFirstName("Alcadía de Chacao");
            user.setFamilyName("");
            post.setUser(user);

            List<com.reto.chacao.model.Comment> cm = dbHelper.getCommentByEvent(e.getId());

            if(cm.size() >= 1){
                for(com.reto.chacao.model.Comment c : cm){
                    id = getResources().getIdentifier(c.getFirstname().toLowerCase(),"drawable",getActivity().getPackageName());
                    Comment commentTmp = new Comment();
                    commentTmp.setImage(Integer.toString(id));
                    commentTmp.setComment_id(c.getId());
                    commentTmp.setCommenterFirstName(c.getFirstname());
                    commentTmp.setCommenterLastName(c.getLastname());
                    commentTmp.setBody(c.getText());
                    commentTmp.setCreated(Calendar.getInstance().getTime());

                    comments.add(commentTmp);
                }

                post.setComments(comments);

            }

            posts.add(post);

        }


        return posts;
    }

    @Override
    protected AppFragmentListener getFragmentListener() {
        return (MovidaMainActivity) getActivity();
    }

    @Override
    protected boolean onBackPressed() {
        Log.i(TAG, "Se regreso atras desde aqui " + this.getClass().getName());
        return true;
    }
}
