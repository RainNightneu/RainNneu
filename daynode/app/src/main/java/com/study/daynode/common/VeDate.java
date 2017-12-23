package com.study.daynode.common;

import java.util.*;
import java.text.*;
import java.util.Calendar;

/**
 * Created by duxiao on 17/4/2.
 */

public class VeDate {
  /**
   * 获取现在时间
   *
   * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
   */
  public static String getStringDate() {
    Date currentTime = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String dateString = formatter.format(currentTime);
    return dateString;
  }

  /**
   * 获取现在时间
   *
   * @return 返回短时间字符串格式yyyy-MM-dd
   */
  public static String getStringDateShort() {
    Date currentTime = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = formatter.format(currentTime);
    return dateString;
  }

  /**
   * 获取时间 小时:分;秒 HH:mm:ss
   *
   * @return
   */
  public static String getTimeShort() {
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    Date currentTime = new Date();
    String dateString = formatter.format(currentTime);
    return dateString;
  }
  /**
   * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
   *
   * @param dateDate
   * @return
   */
  public static String dateToStrLong(java.util.Date dateDate) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String dateString = formatter.format(dateDate);
    return dateString;
  }
  /**
   * 将短时间格式时间转换为字符串 yyyy-MM-dd
   *
   * @param dateDate
   * @param k
   * @return
   */
  public static String dateToStr(java.util.Date dateDate) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = formatter.format(dateDate);
    return dateString;
  }
  /**
   * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
   *
   * @param sformat
   *            yyyyMMddhhmmss
   * @return
   */
  public static String getUserDate(String sformat) {
    Date currentTime = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat(sformat);
    String dateString = formatter.format(currentTime);
    return dateString;
  }

}
