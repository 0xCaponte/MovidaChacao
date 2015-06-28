package com.reto.chacao.statics;

import com.reto.chacao.abstractcomponents.AppFragment;

/**
 * Created by Eduardo Luttinger on 21/05/2015.
 */
public class ClamourValues {


    public static String CLAMOUR_GOOGLE_API_ID = "clamour-956";
    public static String CLAMOUR_GOOGLE_API_NUMBER = "126945037882";
    public static String CLAMOUR_GOOGLE_ANDROID_HASH_KEY = "AIzaSyBclwtLTgbgLtxlbYDHmsvoo_lRUaV-vYk";
    public static String CLAMOUR_GOOGLE_SERVER_HASH_KEY = "AIzaSyBLrJdioOGZRnZcxb_t0KMrLTUxNpNSCWE";
    public static final String PROPERTY_REG_ID = "registration_id";
    public static final String PROPERTY_APP_VERSION = "appVersion";


    public static String EMAIL_REQUEST_INTENT_EXTRA = "EMAIL_REQUEST_INTENT_EXTRA";
    public static String VERIFICATION_CODE_INTENT_EXTRA = "VERIFICATION_CODE_INTENT_EXTRA";
    public static String GROUP_SCREEN_INTENT_EXTRA = "GROUP_SCREEN_INTENT_EXTRA";
    public static String GEOLOCATION_SCREEN_INTENT_EXTRA = "GEOLOCATION_SCREEN_INTENT_EXTRA";
    public static String GROUP_ID = "GROUP_ID_EXTRA";

    public static final String SERIALIZABLE_FRAGMENT = "SERIALIZABLE_FRAGMENT";
    public static final String FRAGMENT_BUNDLE = "FRAGMENT_BUNDLE";


    //PAGINATION CONSTANT
    public static String MESSAGE_LIMIT = "10";

//    Arguments Fragments

    //    Zoomable Image Dialog
    public static String IMAGE_TO_ZOOM_ID;


    public static AppFragment.AppFragmentListener sAppFragmentListener;

    public static void setAppFragmentListener(AppFragment.AppFragmentListener listener) {
        sAppFragmentListener = listener;
    }


}
