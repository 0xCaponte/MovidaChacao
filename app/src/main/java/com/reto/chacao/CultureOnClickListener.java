package com.reto.chacao;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by caponte on 6/24/15.
 */
public class CultureOnClickListener implements View.OnClickListener {

    private Context context;

    public void MyClickListener(Context c) {
        context = c;
    }

    @Override
    public void onClick(View v) {
        Toast t = Toast.makeText(context, "Culture", Toast.LENGTH_LONG);
        t.show();
    }
};