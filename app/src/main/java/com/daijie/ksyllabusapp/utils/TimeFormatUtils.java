package com.daijie.ksyllabusapp.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by daidaijie on 2017/3/16.
 * 对时间进行格式化处理的工具类，根据距离当前时间的数值进行转化
 */

public class TimeFormatUtils {

    /**
     * 根据距离当前时间的数值进行转化
     *
     * 注意以下的相隔是数值上的相隔，如3.31和4.1号实际上也算入相隔1个月
     *
     * @param timeString time的String显示
     * @param timeFormat time的格式
     * @return 解析后的String
     * @throws ParseException
     */
    public static String formatTime(String timeString, String timeFormat) throws ParseException {
        Calendar nowTime = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat);
        Calendar pushTime = Calendar.getInstance();
        pushTime.setTime(simpleDateFormat.parse(timeString));

        int betweenYears = nowTime.get(Calendar.YEAR) - pushTime.get(Calendar.YEAR);

        // 判断离相隔时间是否小于一年
        if (betweenYears < 1) {

            int betweenMonths = nowTime.get(Calendar.MONTH) - pushTime.get(Calendar.MONTH);
            // 判断离相隔时间是否小于一个月
            if (betweenMonths == 0) {
                int betweenDays = nowTime.get(Calendar.DAY_OF_YEAR) - pushTime.get(Calendar.DAY_OF_YEAR);
                // 判断相隔时间是否小于一天
                if (betweenDays == 0) {
                    int betweenHours = (nowTime.get(Calendar.HOUR_OF_DAY) - pushTime.get(Calendar.HOUR_OF_DAY));
                    int betweenMinutes = betweenHours * 60 + nowTime.get(Calendar.MINUTE) - pushTime.get(Calendar.MINUTE);
                    // 判断相隔分钟是否小于1或者60
                    if (betweenMinutes < 1) {
                        // 小于1 显示刚刚
                        return "刚刚";

                    } else if (betweenMinutes < 60) {
                        // 小于60，则显示N分钟前
                        return betweenMinutes + "分钟前";

                    } else {
                        // 大于60分钟显示完整的时分
                        return getTimeString(pushTime);

                    }

                } else if (betweenDays == 1) {
                    // 相隔一天显示昨天+完整的时分
                    return "昨天 " + getTimeString(pushTime);

                } else if (betweenDays == 2) {
                    // 相隔两天显示前天+完整的时分
                    return "前天 " + getTimeString(pushTime);

                } else {
                    // 大于三天则进行完整的月日时分显示
                    return getDateString(pushTime, false) + " " + getTimeString(pushTime);
                }

            } else {
                // 大于一个月则进行完整的月日时分显示
                return getDateString(pushTime, false) + " " + getTimeString(pushTime);
            }

        } else {
            // 大于一年则进行完整的年月日时分显示
            return getDateString(pushTime, true) + " " + getTimeString(pushTime);
        }
    }

    private static String getTimeString(Calendar dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(dateTime.getTime());
    }

    private static String getDateString(Calendar dateTime, boolean hasYear) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                hasYear ? "yyyy-MM-dd" : "MM-dd");
        return simpleDateFormat.format(dateTime.getTime());
    }

}
