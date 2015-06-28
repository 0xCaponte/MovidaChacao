package com.reto.chacao.util;

import android.content.Context;

import com.reto.chacao.beans.UserProfile;

/**
 * Created by ULISES HARRIS on 22/05/2015.
 */
public class UserUtil {

    public static String PREFERENCE_FIRST_NAME = "first-name";
    public static String PREFERENCE_FAMILY_NAME = "family-name";
    public static String PREFERENCE_USER_ID = "user-id";
    public static String PREFERENCE_EMAIL = "email";
    public static String PREFERENCE_FACEBOOK_ID = "facebook-id";
    public static String PREFERENCE_FACEBOOK_TOKEN = "facebook-token";
    public static String PREFERENCE_CONFIRMATION_TOKEN = "confirmation-token";
    public static String PREFERENCE_NUMERIC_TOKEN = "numeric-token";
    public static String PREFERENCE_SALT = "salt";
    public static String PREFERENCE_API_TOKEN = "api-token";

    public static String PREFERENCE_REGISTER = "register";
    public static String PREFERENCE_FIRST_TIME_HOME = "first-time-home";


    public static UserProfile getUserRegister(Context context) {
        UserProfile user = new UserProfile();

        user.setUserId(SharedPreferenceUtil.getInt(context, PREFERENCE_USER_ID));
        user.setFirstName(SharedPreferenceUtil.getString(context, PREFERENCE_FIRST_NAME));
        user.setFamilyName(SharedPreferenceUtil.getString(context, PREFERENCE_FAMILY_NAME));
        user.setEmail(SharedPreferenceUtil.getString(context, PREFERENCE_EMAIL));
        user.setFbId(SharedPreferenceUtil.getString(context, PREFERENCE_FACEBOOK_ID));
        user.setFbToken(SharedPreferenceUtil.getString(context, PREFERENCE_FACEBOOK_TOKEN));
        user.setConfirmationToken(SharedPreferenceUtil.getString(context, PREFERENCE_CONFIRMATION_TOKEN));
        user.setNumericConfirmationToken(SharedPreferenceUtil.getInt(context, PREFERENCE_NUMERIC_TOKEN));
        user.setSalt(SharedPreferenceUtil.getString(context, PREFERENCE_SALT));
        user.setApiToken(SharedPreferenceUtil.getString(context, PREFERENCE_API_TOKEN));

        return user;

    }

    public static void saveUserRegister(Context context, UserProfile user) {

        SharedPreferenceUtil.save(context, PREFERENCE_USER_ID, user.getUserId());
        SharedPreferenceUtil.saveString(context, PREFERENCE_FIRST_NAME, user.getFirstName());
        SharedPreferenceUtil.saveString(context, PREFERENCE_FAMILY_NAME, user.getFamilyName());
        SharedPreferenceUtil.saveString(context, PREFERENCE_EMAIL, user.getEmail());
        SharedPreferenceUtil.saveString(context, PREFERENCE_FACEBOOK_ID, user.getFbId());
        SharedPreferenceUtil.saveString(context, PREFERENCE_FACEBOOK_TOKEN, user.getFbToken());
        SharedPreferenceUtil.saveString(context, PREFERENCE_CONFIRMATION_TOKEN, user.getConfirmationToken());
        SharedPreferenceUtil.saveInt(context, PREFERENCE_NUMERIC_TOKEN, user.getNumericConfirmationToken());
        SharedPreferenceUtil.saveString(context, PREFERENCE_SALT, user.getSalt());
        SharedPreferenceUtil.saveString(context, PREFERENCE_API_TOKEN, user.getApiToken());
    }


    public static void setRegister(Context context, boolean register) {
         SharedPreferenceUtil.save(context, PREFERENCE_REGISTER, register);
   }

    public static boolean getRegister(Context context) {
        return SharedPreferenceUtil.getBoolean(context, PREFERENCE_REGISTER);
    }

    public static void setFacebookRegister(Context context, boolean register) {
        SharedPreferenceUtil.save(context, PREFERENCE_REGISTER, register);
    }

    public static boolean getFacebookRegister(Context context) {
        return SharedPreferenceUtil.getBoolean(context, PREFERENCE_REGISTER);
    }

    public static void setFirstTimeHome(Context context) {
        SharedPreferenceUtil.save(context, PREFERENCE_FIRST_TIME_HOME, true);
    }

    public static boolean getFirstTimeHome(Context context) {
        return SharedPreferenceUtil.getBoolean(context, PREFERENCE_FIRST_TIME_HOME);
    }

    public static void clearUser(Context context) {

    }
}
