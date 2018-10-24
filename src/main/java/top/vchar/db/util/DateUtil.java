package top.vchar.db.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p> deal with date tool </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2018/10/11 9:41
 */
public class DateUtil {

    public final static String YYYY_MM_DD = "yyyy-MM-dd";
    public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     *  String change to date
     * @param dateStr dateString
     * @param df format string
     * @return date
     * @throws ParseException
     */
    public static Date getDateByStr(String dateStr, String df) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(df);
        return sdf.parse(dateStr);
    }

    /**
     * date change to String
     * @param date date
     * @param df format string
     * @return dateString
     */
    public static String getStrByDate(Date date, String df){
        SimpleDateFormat sdf = new SimpleDateFormat(df);
        return sdf.format(date);
    }



}
