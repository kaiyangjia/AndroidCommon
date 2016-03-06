package com.jiakaiyang.androidcommon.androidcommon.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jiakaiyang on 2015/12/2.
 */
public class StringUtils {

    public static boolean NotEmpty(String string){
        if(string != null
                && !"".equals(string)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 格式化传入的数据，保留一位小数
     * @param num
     * @return
     */
    public static String FormatFloat(String num){
        return num;

        // android端不做处理
        /*if(StringUtils.NotEmpty(num)){
            float f = Float.valueOf(num);
            float result = (float) (Math.round(f*100)/100);

            return String.valueOf(result);
        }else{
            return "";
        }*/
    }


    /**
     * 把传入的数字格式的字符串保留两位小数并返回字符串，同时后面加上单位
     * @param str
     * @param unit 单位
     * @return
     */
    public static String getStringNum(String str, String unit){
        String content = !StringUtils.NotEmpty(StringUtils.FormatFloat(str))?"":StringUtils.FormatFloat(str);
        return StringUtils.NotEmpty(content)? content + unit:"";
    }

    /**
     * 把传入的日期格式的字符串去掉最后的秒并返回字符串
     * @param timeStr
     * @return
     */
    public static String formatTime(String timeStr){
        if(StringUtils.NotEmpty(timeStr)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                Date date = format.parse(timeStr);
                format.applyPattern("yyyy-MM-dd HH:mm");
                String s = format.format(date);
                return s;
            }catch (ParseException e){
                e.printStackTrace();
                return "";
            }
        }else{
            return "";
        }
    }

    /**
     * 根据开始时间和结束时间计算中间时间端，返回值以小时时长计算
     * @param timeStart
     * @param timeEnd
     * @return
     */
    public static String getTimeByDifference(String timeStart, String timeEnd){
        if(StringUtils.NotEmpty(timeStart)
                && StringUtils.NotEmpty(timeEnd)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date dateStart = format.parse(timeStart);
                Date dateEnd = format.parse(timeEnd);

                if(dateStart.after(dateEnd)){
                    return "-1";
                }else{
                    long t = dateEnd.getTime() - dateStart.getTime();
                    long timeHour = (t/(1000*60*60));

                    return String.valueOf(timeHour);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return "";
            }
        }else{
            return "";
        }
    }
}
