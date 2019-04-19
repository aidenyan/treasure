package com.aiden.common.utils;

import com.aiden.exception.FormatException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2019/4/19/019.
 */
public class DateUtils {

    public static Date convert(String date, String format) {
        if (date == null) {
            return null;
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
         return   simpleDateFormat.parse(date);
        } catch (Exception e) {
            throw new FormatException("格式错误");
        }
    }

}
