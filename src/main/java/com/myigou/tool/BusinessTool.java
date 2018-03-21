package com.myigou.tool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author ab1324ab
 *         Created by ab1324ab on 2018/3/21.
 */
public class BusinessTool {

    /**
     * 获取时间星期一 到 星期天的日期
     *
     * @return List
     */
    public static List<Date> getStartDateAndEndDate() {
        List<Date> dateList = new ArrayList<Date>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int difference = 1 - (week == 0 ? 7 : week);
        // 星期一
        calendar.add(Calendar.DAY_OF_YEAR, difference);
        //String monday = sdf.format(calendar.getTime());
        dateList.add(calendar.getTime());
        for (int i = 1; i < 7; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            //String tuesday = sdf.format(calendar.getTime());
            dateList.add(calendar.getTime());
        }
        return dateList;
    }
}
