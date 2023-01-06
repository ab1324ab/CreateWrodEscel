package com.myigou.tool;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理工具
 * 2023年1月6日20点04分
 * @author hawk
 */
public class DateTimeTool {

    /**
     * 日期转换成文字*
     *
     * @param starDayStr
     * @return
     */
    public static String dateConversion(String starDayStr) {
        Date parse = null;
        long timeDiff;
        try {
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
            if (StringUtils.isEmpty(starDayStr)) return df.format(new Date());
             else {
                parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(starDayStr);
                timeDiff = todayTimeDiff(starDayStr);
            }
            if (timeDiff == 0) {
                return df.format(parse);
            } else if (timeDiff == 1) {
                return "昨天";
            } else if (timeDiff == 2) {
                return "前天";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat df = new SimpleDateFormat("yy/MM/dd");//设置日期格式
        return df.format(parse);
    }

    /**
     * 计算今天和其他时间的差*
     *
     * @param starDayStr
     * @return
     */
    public static long todayTimeDiff(String starDayStr) {

        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date star = null, endDay = new Date();//开始时间
        try {
            star = dft.parse(starDayStr);//开始时间
        } catch (ParseException e) {
            e.printStackTrace();
            return 99;
        }
        Long starTime = star.getTime();
        Long endTime = endDay.getTime();
        Long num = endTime - starTime;//时间戳相差的毫秒数
        long diff = num / 24 / 60 / 60 / 1000;
        return diff;
    }

}