package com.reto.chacao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Toast;

import com.reto.chacao.database.DataBaseHelper;
import com.reto.chacao.model.Event;

import java.util.List;

/**
 * Created by caponte on 6/24/15.
 */
public class SearchOnClickListener implements View.OnClickListener {

    private Context context;

    public void MyClickListener(Context c) {
        context = c;
    }

    @Override
    public void onClick(View v) {
//        DataBaseHelper dbHelper = new DataBaseHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        dbHelper.onUpgrade(db,2,3);

        Toast t = Toast.makeText(context, "Search", Toast.LENGTH_LONG);
        t.show();
       ;
    }
};