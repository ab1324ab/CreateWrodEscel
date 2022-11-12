package com.myigou.tool;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author ab1324ab
 * Created by ab1324ab on 2018/3/21.
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

    /**
     * 文件大小计算加上单位
     *
     * @param fileSize
     * @return
     */
    public static String fileSizeCalculation(Long fileSize) {
        BigDecimal fileSizeBig = new BigDecimal(fileSize);
        BigDecimal kb = new BigDecimal(1024);
        if (kb.compareTo(fileSizeBig) == 1)
            return fileSize + "";
        if (kb.compareTo(fileSizeBig) == -1 && kb.multiply(kb).compareTo(fileSizeBig) == 1)
            return fileSizeBig.divide(kb).setScale(1, BigDecimal.ROUND_DOWN) + " KB";
        if (kb.multiply(kb).compareTo(fileSizeBig) == -1 && kb.multiply(kb).multiply(kb).compareTo(fileSizeBig) == 1)
            return fileSizeBig.divide(kb).divide(kb).setScale(1, BigDecimal.ROUND_DOWN) + " MB";
        if (kb.multiply(kb).multiply(kb).compareTo(fileSizeBig) == -1 && kb.multiply(kb).multiply(kb).multiply(kb).compareTo(fileSizeBig) == 1)
            return fileSizeBig.divide(kb).divide(kb).divide(kb).setScale(1, BigDecimal.ROUND_DOWN) + " GB";
        if (kb.multiply(kb).multiply(kb).multiply(kb).compareTo(fileSizeBig) == -1 && kb.multiply(kb).multiply(kb).multiply(kb).multiply(kb).compareTo(fileSizeBig) == 1)
            return fileSizeBig.divide(kb).divide(kb).divide(kb).divide(kb).setScale(1, BigDecimal.ROUND_DOWN) + " TB";
        return fileSize + "";
    }
}
