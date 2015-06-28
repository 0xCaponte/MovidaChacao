package com.reto.chacao.util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.reto.chacao.App;
import com.reto.chacao.beans.AppBean;

/**
 * Created by Eduardo Luttinger on 20/05/2015.
 */
public class AppUtil {

    private static final String TAG = "AppUtil";


    public static boolean isNetworkAvailable() {
        boolean isNetworkAvailable = Boolean.FALSE;
        ConnectivityManager cm = (ConnectivityManager) App.getContext().getSystemService(App.getContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            isNetworkAvailable = Boolean.TRUE;
        }
        return isNetworkAvailable;
    }

    public static void hideKeyboard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static void hideKeyboard(Context context) {
        View view = ((Activity) context).getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void showKeyboard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }

    public static void showAToast(String message) {
        Toast.makeText(App.getContext().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public static void runActivity(Class clazz, Context context) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    public static void runActivityClearTop(Class clazz, Context context) {
        Intent intent = new Intent(context, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void runActivity(Class clazz, Context context, String serializableTAG, Serializable object) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra(serializableTAG, object);
        context.startActivity(intent);
    }

    public static void runFragment(FragmentTransaction fragmentTransaction, int fragmentLayoutId, Fragment fragment, Bundle params, boolean isADialog) {
        if (isADialog) {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        }
        if (params != null) {
            fragment.setArguments(params);
        }
        fragmentTransaction.add(fragmentLayoutId, fragment).addToBackStack(null).commit();
    }


    public static String getEncryptedPassword(String rawPassword, String salt) {
        return BCrypt.hashpw(rawPassword, salt);
    }

    public static String getValuesFromApiMessageObject(String valueName, JSONObject jsonObject) {
        String value = "";
        try {
            value = jsonObject.getJSONObject("message").optString(valueName);
        } catch (Exception e) {
            Log.e(TAG, "", e);
        }
        return value;
    }

    public static int getValuesFromApiMessageObjectInt(String valueName, JSONObject jsonObject) {
        int value = 0;
        try {
            value = jsonObject.getJSONObject("message").optInt(valueName);
        } catch (Exception e) {
            Log.e(TAG, "", e);
        }
        return value;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return StringUtil.capitalize(model);
        } else {
            return StringUtil.capitalize(manufacturer) + " " + model;
        }
    }

    public static String getUniqueImageFilename() {
        String DATE_FORMAT = "yyyyMMdd_HHmmss-SSS";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
        String time = sdf.format(new Date());

        java.util.Random nahcis = new java.util.Random();
        String randomNumber = String.valueOf(nahcis.nextInt(9999));

        return "IMG_" + time + "_" + randomNumber;
    }


    /**
     * Unifica listas de objetos que hereden del mismo padre retornando una generica para ser utilizada en los adaptadores.
     *
     * @param listOfBeans
     * @return
     */
    public static List<AppBean> mergeCommunBeansInASingleList(List... listOfBeans) {
        List<AppBean> mergedList = new ArrayList<>();
        for (List<AppBean> list : listOfBeans) {
            mergedList.addAll(list);
        }
        return mergedList;
    }

    /**
     * Obtiene el mensaje segun el id del Archivo String.xml desde una clase sin contexto de actividad como un holder, adapter u otros
     *
     * @param messageId
     * @return
     */
    public static String getString(int messageId) {
        return App.getContext().getString(messageId);
    }


    public static void sendIntentEmail(Context context, String subject) {
        Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "support@clamour.net", null));
        i.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        try {
            context.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There are no email clients installed.",
                    Toast.LENGTH_SHORT).show();
        }
    }


}

