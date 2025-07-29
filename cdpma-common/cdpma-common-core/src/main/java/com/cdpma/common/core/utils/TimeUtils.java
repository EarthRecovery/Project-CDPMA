package com.cdpma.common.core.utils;

import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    public static Date addHours(Date date, int hours) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
}
