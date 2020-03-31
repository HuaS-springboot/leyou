package com.vsj.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

/**
 * @ClassName: DateUtil
 * @Description: 日期工具类
 * @author: mario
 * @date: 2019年7月20日 下午9:06:44
 * @copyright: 青岛微视角文化传媒有限公司
 */
public class DateUtil {

    public final static String DATETIME_FOEMAT = "yyyy-MM-dd HH:mm:ss";

    public final static String DATE_FOEMAT = "yyyy-MM-dd";

    public final static String TIME_FOEMAT = "HH:mm:ss";

    public final static String DATETIME_FOEMAT_STR = "yyyyMMddHHmmss";

    /**
     * @param date
     * @return
     * @Title: parseDate2Str
     * @Description: 日期转时间字符串 字符串格式:"yyyy-MM-dd HH:mm:ss"
     * @author mario
     * @return: String
     */
    public static String parseDate2Str(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FOEMAT);
        return sdf.format(date);
    }

    /**
     * @return
     * @Title: parse2TimeDate
     * @Description: 转字符串成时间日期格式 字符串格式:"yyyy-MM-dd HH:mm:ss"
     * @author mario
     * @return: Date
     */
    public static Date parse2TimeDate(String str) {
        if (StringUtil.isEmptyStr(str)) {
            return null;
        }
        return parseDate(str, new String[]{DATETIME_FOEMAT});
    }

    /**
     * @return
     * @Title: parseToTimeDate2
     * @Description: 转字符串成时间日期格式 字符串格式:"yyyy-MM-dd"
     * @author mario
     * @return: Date
     */
    public static Date parse2TimeDate2(String str) {
        if (StringUtil.isEmptyStr(str)) {
            return null;
        }
        return parseDate(str, new String[]{DATE_FOEMAT});
    }


    private static Date parseDate(String str, String[] parsePatterns) {
        try {
            return DateUtils.parseDate(str, parsePatterns);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  当前时间增加几天
     * @param num 增加几天
     * @return
     */
    public static String addDay(int num) {
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currdate = format.format(d);
        System.out.println("现在的日期是：" + currdate);
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        d = ca.getTime();
        String enddate = format.format(d);
        System.out.println("增加天数以后的日期：" + enddate);
        return enddate;
    }

}
