package com.reto.chacao.settings.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.reto.chacao.R;
import com.reto.chacao.abstractcomponents.AppFragment;
import com.reto.chacao.main.activity.MovidaMainActivity;


public class SettingsFragment extends AppFragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "Settings-Fragment";
    private View mRootView;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public String getMyTag() {
        return TAG;
    }

    @Override
    protected AppFragmentListener getFragmentListener() {
        return (MovidaMainActivity) getActivity();
    }

    @Override
    protected boolean onBackPressed() {
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_settings, container, false);

        setCategoryData();
        inflateNotificationPreferences();


        return mRootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void setCategoryData() {
        String[] testData = getResources().getStringArray(R.array.category_array);

        //Setting data for the first spinner
        Spinner mCategorySpinner = (Spinner) mRootView.findViewById(R.id.first_category_spinner);
        ArrayAdapter<String> mSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, testData);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mCategorySpinner.setAdapter(mSpinnerAdapter);
        mCategorySpinner.setOnItemSelectedListener(this);

        //Setting data for the second spinner
        mCategorySpinner = (Spinner) mRootView.findViewById(R.id.second_category_spinner);
        mSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, testData);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mCategorySpinner.setAdapter(mSpinnerAdapter);
        mCategorySpinner.setOnItemSelectedListener(this);

        //Setting data for the third spinner
        mCategorySpinner = (Spinner) mRootView.findViewById(R.id.third_category_spinner);
        mSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, testData);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mCategorySpinner.setAdapter(mSpinnerAdapter);
        mCategorySpinner.setOnItemSelectedListener(this);
    }

    private void inflateNotificationPreferences() {
        LinearLayout mainLayout = (LinearLayout) mRootView.findViewById(R.id.notification_preferences_layout);
        LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();

        for (int i = 0; i < 3; i++) {
            View mView = mLayoutInflater.inflate(R.layout.notification_settings_row_layout, mainLayout, false);
            if ( i % 2 == 0 ) {
                mView.findViewById(R.id.main_row_layout).setBackgroundColor(getResources().getColor(R.color.GrisLigero));
            }
            mainLayout.addView(mView);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if ( position ==  0 ) {
            ((TextView) view.findViewById(android.R.id.text1)).setTextColor(getResources().getColor(R.color.GrisMedio));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
