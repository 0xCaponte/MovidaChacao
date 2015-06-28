package com.reto.chacao.util;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by ULISES HARRIS on 27/05/2015.
 */
public class DateUtil {

    public static final String DATE_FORMAT = "dd-M-yyyy hh:mm:ss";

    public static String getTimeText(Date date) {
        Calendar calendar = Calendar.getInstance();

        long diff = calendar.getTimeInMillis() - date.getTime();
        long diffSeconds = TimeUnit.MILLISECONDS.toSeconds(diff);
        long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        long diffHours = TimeUnit.MILLISECONDS.toHours(diff);
        long diffInDays = TimeUnit.MILLISECONDS.toDays(diff);

        if (diffMinutes <= 0) {
            return diffSeconds + " seconds ago";
        } else if (diffMinutes > 0 & diffHours <= 0) {
            return diffMinutes + " mins ago";
        } else if (diffHours > 0 & diffInDays <= 0) {
            return diffHours + " hours ago";
        }

        return diffInDays + " days go";
    }
}
