package com.reto.chacao.main.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.reto.chacao.R;
import com.reto.chacao.abstractcomponents.AppFragment;
import com.reto.chacao.beans.Comment;
import com.reto.chacao.beans.ItemCondition;
import com.reto.chacao.beans.Post;
import com.reto.chacao.beans.UserProfile;
import com.reto.chacao.database.DataBaseHelper;
import com.reto.chacao.filter.activity.FilterScreenActivity;
import com.reto.chacao.listener.EndlessRecyclerOnScrollListener;
import com.reto.chacao.main.activity.MovidaMainActivity;
import com.reto.chacao.main.adapter.OneColumnAdapter;
import com.reto.chacao.main.adapter.TwoColumnAdapter;
import com.reto.chacao.model.Event;
import com.reto.chacao.postdetail.fragment.PostDetailScreenFragment;
import com.reto.chacao.settings.fragment.SettingsFragment;
import com.reto.chacao.statics.ClamourValues;
import com.reto.chacao.util.AppUtil;
import com.reto.chacao.util.UserUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Domingo De Abreu on 26/06/2015.
 */
public class HomeScreenFragment extends AppFragment implements CompoundButton.OnCheckedChangeListener,
        View.OnClickListener, OneColumnAdapter.PostViewClickListener, TwoColumnAdapter.PostTwoColumnViewClickListener {

    private static final String TAG = "HomeScreen-Fragment";
    private RelativeLayout mFilterLayout;
    private ToggleButton mFilterToggle;
    private ToggleButton mOneColumnToggle;
    private ToggleButton mTwoColumnsToggle;
    private RecyclerView mOneColumnRecyclerView;
    private RecyclerView mTwoColumnsRecyclerView;
    private OneColumnAdapter mOneColumnAdapter;
    private TwoColumnAdapter mTwoColumnsAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private RelativeLayout mTutorial;
    private static final long TUTORIAL_SCREEN = 2000;
    private ArrayList<Post> mPosts;
    private RelativeLayout mNotificationButton;



    ImageSpan imageSpan;

    SpannableString content;

    public HomeScreenFragment() {
        super();
    }

    @Override
    public String getMyTag() {
        return TAG;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mPosts = setPostItems();
        setViews(root);
        setToolBar(root);

        ImageView settingsButton = (ImageView) root.findViewById(R.id.user_profile_settings);
        settingsButton.setOnClickListener(this);
        ImageView settingsButton2 = (ImageView) root.findViewById(R.id.toolbar_search_button);
        settingsButton2.setOnClickListener(this);

        if (UserUtil.getFirstTimeHome(getActivity()))
            mTutorial.setVisibility(View.GONE);

        if (mTwoColumnsToggle.isChecked())
            onTwoColumnClick();
        else if (mOneColumnToggle.isChecked())
            onOneColumnClick();


        return root;
    }

    private void setToolBar(View root) {
        mFilterLayout = (RelativeLayout) root.findViewById(R.id.home_filter_button);
        mFilterLayout.setOnClickListener(this);
        mFilterToggle = (ToggleButton) root.findViewById(R.id.home_filter_toggle);
        mOneColumnToggle = (ToggleButton) root.findViewById(R.id.home_one_column_toggle);
        mOneColumnToggle.setTransformationMethod(null);
        mOneColumnToggle.setOnClickListener(this);
        imageSpan = new ImageSpan(getActivity(), R.drawable.btn_one_column);
        content = new SpannableString("X");
        content.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mOneColumnToggle.setText(content);
        mOneColumnToggle.setTextOn(content);
        mOneColumnToggle.setTextOff(content);

        mTwoColumnsToggle = (ToggleButton) root.findViewById(R.id.home_two_columns_toggle);
        mTwoColumnsToggle.setTransformationMethod(null);
        mTwoColumnsToggle.setOnClickListener(this);
        imageSpan = new ImageSpan(getActivity(), R.drawable.btn_two_columns);
        content = new SpannableString("X");
        content.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTwoColumnsToggle.setText(content);
        mTwoColumnsToggle.setTextOn(content);
        mTwoColumnsToggle.setTextOff(content);
    }

    private void setViews(View root) {
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
        if (mOneColumnToggle.isChecked()) {
            mOneColumnToggle.setChecked(false);
        }
        mTwoColumnsToggle.setChecked(true);
        mTwoColumnsRecyclerView.setVisibility(View.VISIBLE);
        mOneColumnRecyclerView.setVisibility(View.GONE);
    }

    private void onOneColumnClick() {
        if (mTwoColumnsToggle.isChecked()) {
            mTwoColumnsToggle.setChecked(false);
        }
        mOneColumnToggle.setChecked(true);
        mOneColumnRecyclerView.setVisibility(View.VISIBLE);
        mTwoColumnsRecyclerView.setVisibility(View.GONE);
    }

    private void onFilterClick() {
        AppUtil.runActivity(FilterScreenActivity.class, getActivity(), ClamourValues.GROUP_ID, 2);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_filter_button:
                onFilterClick();
                break;
            case R.id.home_one_column_toggle:
                onOneColumnClick();
                break;
            case R.id.home_two_columns_toggle:
                onTwoColumnClick();
                break;
            case R.id.home_tutorial:
                mTutorial.setVisibility(View.GONE);
                UserUtil.setFirstTimeHome(getActivity());
                break;
            case R.id.user_profile_settings:
                getFragmentListener().goToFragment(new SettingsFragment());
                break;
            case R.id.toolbar_search_button:
                call_dialog();

        }
    }

    private void response_dialog(String mesagge){
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

    private void call_dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Title");

        // Set up the input
        final EditText input = new EditText(getActivity());
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DataBaseHelper dbHelper = new DataBaseHelper(getActivity());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                try {
                    dbHelper.onCreate(db);
                }catch (Exception err){
                    err.printStackTrace();
                }
                List<Event> events = dbHelper.getBySearch(input.getText().toString());
                for(Event e : events){
                    Toast t = Toast.makeText(getActivity(), e.getName(), Toast.LENGTH_LONG);
                    t.show();
//                    response_dialog(e.getName());
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
        AppUtil.showAToast("Comments size: " + post.getComments().size());
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
        AppUtil.showAToast("Comments size: " + post.getComments().size());
    }


    public ArrayList<Post> setPostItems() {
        Post post = new Post();
        post.setPost_id(1);
        post.setTitle("1ra Carrera Anual de Botes");
        post.setDescription("1ra Carrera Anual de Botes patrocinada por la Alcadía de Chacao " +
                "asdasd asd asd  asdas asd asd ");
        ItemCondition condition = new ItemCondition();
        condition.setId(0);
        condition.setName("Ahora");
        post.setCondition(condition);
        post.setCreated(Calendar.getInstance().getTime());
        post.setLocation("100003");
        post.setPrice("50$");
        UserProfile user = new UserProfile();
        user.setUserId(1);
        user.setFirstName("Alcadía de Chacao");
        user.setFamilyName("");
        post.setUser(user);
        Comment comment = new Comment();
        comment.setCreated(Calendar.getInstance().getTime());
        comment.setComment_id(2);
        comment.setCommenterFirstName("George");
        comment.setCommenterLastName("Clooney");
        comment.setBody("Finalmente una carrera de botes en Caracas!");
        ArrayList<Comment> comments = new ArrayList<Comment>();
        comments.add(comment);
        post.setComments(comments);
        ArrayList<Post> posts = new ArrayList<Post>();
        posts.add(post);
        posts.add(post);

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
