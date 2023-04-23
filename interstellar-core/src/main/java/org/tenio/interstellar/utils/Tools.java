package org.tenio.interstellar.utils;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author sunkaihan
 * @date 2022-04-18
 */
public class Tools {
    private static final Logger log = LoggerFactory.getLogger(Tools.class);

    public static final Base64.Encoder BASE64_ENCODER;
    public static final Base64.Decoder BASE64_DECODER;

    static {
        BASE64_ENCODER = Base64.getUrlEncoder().withoutPadding();
        BASE64_DECODER = Base64.getUrlDecoder();
    }

    public static final String UTF_8_STR = StandardCharsets.UTF_8.displayName();


    public static final int RANDOM_FOUR_NUMBER_SIZE_MAX_VALUE = 9990;

    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String generateSn(int max) {
        return SystemClock.nowMilli() + String.valueOf(randomInt(max) + 1000);
    }

    public static int randomInt(int max) {
        return (int) (Math.random() * max);
    }

    public static String encodeUtf8(String url) throws UnsupportedEncodingException {
        return URLEncoder.encode(url, "UTF-8");
    }

    public static String decodeUtf8(String encodeUrl) throws UnsupportedEncodingException {
        return URLDecoder.decode(encodeUrl, "UTF-8");
    }

    public static byte[] join(byte[] arr1, byte[] arr2) {
        byte[] target = new byte[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, target, 0, arr1.length);
        System.arraycopy(arr2, 0, target, arr1.length, arr2.length);
        return target;
    }

    public static Optional<Integer> tryToInt(String val) {
        try {
            return Optional.of(Integer.valueOf(val));
        } catch (Exception ignore) {
            return Optional.empty();
        }
    }

    public static Optional<Integer> tryToInt(Object val) {
        try {
            return Optional.of(Integer.valueOf(String.valueOf(val)));
        } catch (Exception ignore) {
            return Optional.empty();
        }
    }

    //region time functions

    public static Date getMinutesZero(int minuteAmount) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, minuteAmount);
        //
        return cal.getTime();
    }

    public static int getMinutesZeroTs(int minuteAmount) {
        return (int) (getMinutesZero(minuteAmount).getTime() / 1000);
    }

    public static int getMinutes(int minuteAmount) {
        return (SystemClock.nowSecond() + (60 * minuteAmount));
    }

    /**
     * Get n hour zero h/m/s timestamp
     *
     * @param hourAmount
     * @return second
     */
    public static int getHoursZeroTs(int hourAmount) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.add(Calendar.HOUR_OF_DAY, hourAmount);
        //
        return (int) (cal.getTimeInMillis() / 1000);
    }

    public static int getHoursTs(int hourAmount) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cal.add(Calendar.HOUR_OF_DAY, hourAmount);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    /**
     * Get n day zero h/m/s timestamp
     *
     * @param dayAmount
     * @return second
     */
    public static int getDaysZeroTs(int dayAmount) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.add(Calendar.DAY_OF_MONTH, dayAmount);
        //
        return (int) (cal.getTimeInMillis() / 1000);
    }

    /**
     * Get n day 23hour h/m/s timestamp
     *
     * @param dayAmount
     * @return second
     */
    public static int getDays23HourTs(int dayAmount) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.add(Calendar.DAY_OF_MONTH, dayAmount);
        //
        return (int) (cal.getTimeInMillis() / 1000);
    }

    public static Date getDays23HourDate(int dayAmount) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.add(Calendar.DAY_OF_MONTH, dayAmount);
        //
        return cal.getTime();
    }

    /**
     * Get n month zero h/m/s timestamp
     *
     * @param monthAmount
     * @return month
     */
    public static int getMonthsZeroTs(int monthAmount) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, monthAmount);
        //
        return (int) (cal.getTimeInMillis() / 1000);
    }

    public static int getMonthsTs(int monthAmount) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cal.add(Calendar.MONTH, monthAmount);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    public static long toMsTimestamp(final int timestamp) {
        return ((long) timestamp) * 1000;
    }

    public static int toSeTimestamp(final long msTimestamp) {
        return (int) (msTimestamp / 1000);
    }

    public static int getYearsTs(int yearAmount) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cal.add(Calendar.YEAR, yearAmount);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    public static int getTodayZeroHour() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        //
        return (int) (cal.getTimeInMillis() / 1000);
    }

    public static int getToday23Hour() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        //
        return (int) (cal.getTimeInMillis() / 1000);
    }

    public static int getCurrTimestamp() {
        return SystemClock.nowSecond();
    }
    // endregion

    public static long toAsciiSum(String str) {
        long ret = 0;
        for (char c : str.toCharArray()) {
            int i = c;
            ret += i;
        }
        return ret;
    }

    public static String getMessage(String content, Map<String, String> replaceArgs) {
        if (StrUtil.isBlank(content)) {
            return content;
        }
        if (replaceArgs != null && replaceArgs.size() > 0) {
            int i = 0;

            for (Iterator<Map.Entry<String, String>> var3 = replaceArgs.entrySet().iterator(); var3.hasNext(); ++i) {
                Map.Entry<String, String> entry = var3.next();
                content = content.replace("{" + entry.getKey() + "}", entry.getValue()).replace("{" + i + "}", entry.getValue());
            }
        }
        return content;
    }

}
