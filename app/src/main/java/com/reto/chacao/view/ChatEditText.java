package com.reto.chacao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

import com.reto.chacao.util.AppUtil;

/**
 * Created by Eduardo Luttinger on 10/06/2015.
 */
public class ChatEditText extends EditText {

    private Context mContext;

    public ChatEditText(Context context) {
        super(context);
        mContext = context;
    }

    public ChatEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public ChatEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
             AppUtil.hideKeyboard(mContext, this);
        }
        return false;
    }
}
