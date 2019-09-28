package work.codehub.library.util;

import work.codehub.library.constants.CommonConstants;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理工具类
 *
 * @author jiangming.huang
 * @date 2019/3/13 16:22
 */
public final class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    private DateUtils() {
    }

    public static final DateTimeFormatter FMT_MONTH = DateTimeFormatter.ofPattern(CommonConstants.DateFormat.TO_MONTH);
    public static final DateTimeFormatter FMT_DATE = DateTimeFormatter.ofPattern(CommonConstants.DateFormat.TO_DATE);
    public static final DateTimeFormatter FMT_MINUTE = DateTimeFormatter.ofPattern(CommonConstants.DateFormat.TO_MINUTE);
    public static final DateTimeFormatter FMT_SECOND = DateTimeFormatter.ofPattern(CommonConstants.DateFormat.TO_SECOND);
    public static final DateTimeFormatter FMT_MILLISECOND = DateTimeFormatter.ofPattern(CommonConstants.DateFormat.TO_MILLISECOND);
    public static final DateTimeFormatter FMT_MONTHDATE = DateTimeFormatter.ofPattern(CommonConstants.DateFormat.TO_MONTHDATE);

    public static final DateTimeFormatter FMT_DATE_N = DateTimeFormatter.ofPattern(CommonConstants.DateFormat.TO_DATE_N);
    public static final DateTimeFormatter FMT_MINUTE_N = DateTimeFormatter.ofPattern(CommonConstants.DateFormat.TO_MINUTE_N);
    public static final DateTimeFormatter FMT_SECOND_N = DateTimeFormatter.ofPattern(CommonConstants.DateFormat.TO_SECOND_N);
    public static final DateTimeFormatter FMT_MILLISECOND_N = DateTimeFormatter.ofPattern(CommonConstants.DateFormat.TO_MILLISECOND_N);
    public static final DateTimeFormatter FMT_SHORT_MILLISECOND_N = DateTimeFormatter.ofPattern(CommonConstants.DateFormat.TO_SHORT_MILLISECOND_N);

    public static final DateTimeFormatter FMT_DATE_SLASH = DateTimeFormatter.ofPattern(CommonConstants.DateFormat.TO_DATE_SLASH);

    public static final DateTimeFormatter FMT_HOUR_N = DateTimeFormatter.ofPattern(CommonConstants.DateFormat.HOUR_N);

    /**
     * Date转LocalDateTime
     *
     * @param date
     * @return java.time.LocalDateTime
     * @author jiangming.huang
     * @date 2019/3/13 16:23
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        if (null == date) {
            return null;
        }
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime
     * @return java.util.Date
     * @author jiangming.huang
     * @date 2019/3/13 16:26
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 获取日期的月份 .
     *
     * @param date
     * @return int
     * @author fu.tong
     * @date 2019/6/5 16:35
     */
    public static int getYear(Date date) {
        return getValueByField(date, Calendar.YEAR);
    }

    /**
     * 获取日期的月份 .
     *
     * @param date
     * @return int
     * @author fu.tong
     * @date 2019/6/5 16:35
     */
    public static int getMonth(Date date) {
        // 因月份从0开始，故加1
        return getValueByField(date, Calendar.MONTH) + 1;
    }

    /**
     * 根据Calendar中的日期字段获取日期的对应值 .
     *
     * @param date
     * @param field
     * @return int
     * @author fu.tong
     * @date 2019/6/5 16:34
     */
    public static int getValueByField(Date date, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(field);
    }

    /**
     * 根据指定的格式 格式化日期
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDateToString(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }
}
