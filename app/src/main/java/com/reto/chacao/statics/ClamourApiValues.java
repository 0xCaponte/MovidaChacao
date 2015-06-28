package com.reto.chacao.statics;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eduardo Luttinger on 20/05/2015.
 */
public class ClamourApiValues {

    private static final String TAG = "Api-Values";
    public static Map API_AUTH_MAP;
    public static String API_TOKEN = "apitoken";

    public static final int DEVELOP = 0;
    public static final int QA = 1;
    public static final int PRODUCTION = 2;

    public static final int ENVIRONMENT = QA;

    public static final int TYPE_USER = 0;
    public static final int TYPE_GROUP = 1;
    public static final int TYPE_CATEGORY = 2;
    public static final int TYPE_MESSAGE = 3;
    public static final int TYPE_NOTIFICATION = 4;

    //--------- Web Services Api Version ------------------------------//
    public static final String API_VERSION = "1";

    //--------- Web Services Environments ------------------------------//
    public static final String HOST_DEV = "https://api.clamour.co";
    public static final String HOST_QA = "https://qa.api.clamour.co";

    //--------- Web Services URL ------------------------------//
    public static final String TYPE_USER_URL = "user/";
    public static final String TYPE_GROUP_URL = "group/";
    public static final String TYPE_CATEGORY_URL = "category";
    public static final String TYPE_MESSAGE_URL = "message/";
    public static final String TYPE_NOTIFICATION_URL = "notification/";
    public static final String GROUPS_URL = "groups";
    public static final String NOTIFICATIONS_URL = "notifications";
    public static final String GROUP_REQUEST_JOIN_URL = "requestjoin";
    public static final String IMAGE_URL = "image";
    public static final String ZIP_CODE_URL = "zipcode";

//    public static final String GET_SALT_URL = HOST_QA + "/1/user/salt";
//    public static final String REGISTER_WITH_EMAIL = HOST_QA + "/1/user/register/byemail";
//    public static final String VERICIFATION_CODE_URL = HOST_QA + "/1/user/verify/account";
//    public static final String RECOVER_VERIFICATION_CODE_URL = HOST_QA + "/1/user/resendverificationemail";
//    public static final String REGISTER_WITH_FACEBOOK = HOST_QA + "/1/user/register/byfb";
//    public static final String LOGIN_WITH_FACEBOOK = HOST_QA + "/1/user/login/byfb";
//    public static final String LOGIN_BY_EMAIL = HOST_QA + "/1/user/login/byemail";
//    public static final String DEVICE_REGISTER_URL = HOST_QA + "/1/device/register";

    public static final String GET_SALT_URL = HOST_DEV + "/1/user/salt";
    public static final String LOGIN_BY_EMAIL = HOST_DEV + "/" + API_VERSION + "/user/login/byemail";
    public static final String LOGIN_WITH_FACEBOOK = HOST_DEV + "/" + API_VERSION + "/user/login/byfb";
    public static final String REGISTER_WITH_FACEBOOK = HOST_DEV + "/" + API_VERSION + "/user/register/byfb";
    public static final String REGISTER_WITH_EMAIL = "https://api.clamour.co/1/user/register/byemail";
    public static final String VERICIFATION_CODE_URL = HOST_DEV + "/" + API_VERSION + "/user/verify/account";
    public static final String RECOVER_VERIFICATION_CODE_URL = HOST_DEV + "/" + API_VERSION + "/user/resendverificationemail";
    public static final String DEVICE_REGISTER_URL = HOST_DEV + "/1/device/register";

    // GET PARAMETERS
    public static final String GROUP_ID = "groupId";
    public static final String PAGE = "page";
    public static final String LIMIT = "limit";

    public static final String SUCCESS_CODE = "200";

    static {

        API_AUTH_MAP = new HashMap();
        API_AUTH_MAP.put("clientid", "iosver10");

    }


    public static String createUrl(String userId, String url, int type) {
        String finalUrl;

        if (!userId.equals("0")) {
            if (ENVIRONMENT == DEVELOP)
                finalUrl = HOST_DEV + "/" + API_VERSION + "/";
            if (ENVIRONMENT == QA)
                finalUrl = HOST_QA + "/" + API_VERSION + "/";
            if (type == TYPE_USER)
                finalUrl = finalUrl + TYPE_USER_URL;
            if (type == TYPE_GROUP)
                finalUrl = finalUrl + TYPE_GROUP_URL;
            if (type == TYPE_NOTIFICATION)
                finalUrl = finalUrl + TYPE_NOTIFICATION_URL;

            finalUrl = finalUrl + userId + "/" + url;

            return finalUrl;

        }


        return null;
    }

    public static String createUrlId(String id, int type, HashMap<String, String> parameters) {
        String finalUrl;
        String finalParameters = "";

        if (!id.equals("0")) {
            if (ENVIRONMENT == DEVELOP)
                finalUrl = HOST_DEV + "/" + API_VERSION + "/";
            if (ENVIRONMENT == QA)
                finalUrl = HOST_QA + "/" + API_VERSION + "/";
            if (type == TYPE_USER)
                finalUrl = finalUrl + TYPE_USER_URL;
            if (type == TYPE_GROUP)
                finalUrl = finalUrl + TYPE_GROUP_URL;
            if (type == TYPE_MESSAGE)
                finalUrl = finalUrl + TYPE_MESSAGE_URL;

            if (parameters != null) {
                int i = 0;
                for (String key : parameters.keySet()) {
                    if (i > 0)
                        finalParameters = finalParameters + "&" + key + "=" + parameters.get(key);
                    else if (i <= 0)
                        finalParameters = "?" + key + "=" + parameters.get(key);
                    Log.i(TAG, "position " + i);
                    Log.i(TAG, finalParameters);
                    i++;
                }
                Log.i(TAG, finalParameters);
                finalUrl = finalUrl + id + finalParameters;
                return finalUrl;
            } else
                return finalUrl;
        }
        return null;
    }

    public static String createStaticUrlGet(int type, HashMap<String, String> parameters) {
        String finalUrl;
        String finalParameters = "";

        if (ENVIRONMENT == DEVELOP)
            finalUrl = HOST_DEV + "/" + API_VERSION + "/";
        else if (ENVIRONMENT == QA)
            finalUrl = HOST_QA + "/" + API_VERSION + "/";
        if (type == TYPE_USER)
            finalUrl = finalUrl + TYPE_USER_URL;
        else if (type == TYPE_GROUP)
            finalUrl = finalUrl + TYPE_GROUP_URL;
        else if (type == TYPE_CATEGORY)
            finalUrl = finalUrl + TYPE_CATEGORY_URL;
        if (parameters != null) {
            int i = 0;
            for (String key : parameters.keySet()) {
//                finalParameters = finalParameters + key + "=" + parameters.get(key);
                if (i > 0)
                    finalParameters = "&" + finalParameters + key + "=" + parameters.get(key);
                else
                    finalParameters = "?" + finalParameters + key + "=" + parameters.get(key);
                Log.i(TAG, "position " + i);
                i++;
            }
            Log.i(TAG, finalParameters);
            finalUrl = finalUrl + finalParameters;
            return finalUrl;
        } else
            return finalUrl;
    }


}
