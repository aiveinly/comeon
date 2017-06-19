package com.planet.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * 日期工具类，包含一系列与日期相关的工具方法
 *
 * @author zuoweiyang
 *
 */
public class DateTools {

	/**
	 * 将日期类型的对象转换成字符串格式
	 *
	 * @param date
	 *            需要转换的日期对象
	 * @param fmt
	 *            转换格式，例如"yyyy年MM月dd日 HH时mm分ss秒 E"；默认是"yyyy年MM月dd日"
	 * @return 转换后的字符串格式日期
	 */
	public static String parseDate2String(Date date, String fmt) {
		if (null == fmt || "".equals(fmt.trim())) {
			fmt = "yyyy年MM月dd日";
		}
		DateFormat df = new SimpleDateFormat(fmt);
		return df.format(date);
	}

	/**
	 * 将日期字符串转换成Date类型
	 *
	 * @param dateStr
	 *            需要转换的日期字符串
	 * @param fmt
	 *            转换格式，例如"yyyy年MM月dd日 HH时mm分ss秒 E"；默认是"yyyy-MM-dd"
	 * @return
	 */
	public static Date parseString2Date(String dateStr, String fmt) {
		if (null == fmt || "".equals(fmt.trim())) {
			fmt = "yyyy-MM-dd";
		}
		SimpleDateFormat df = new SimpleDateFormat(fmt);
		Date date = null;
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取上个月
	 *
	 * @param date
	 * @return
	 * @author szy
	 */
	public static Date getLastDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 获取当前时间的上个月
	 *
	 * @param
	 * @return
	 * @author szy
	 */
	public static Date getMonthDate() {
		return parseString2Date(DateTools.parseDate2String(
				DateTools.getLastDate(new Date()), "yyyy-MM"), "yyyy-MM");
	}

	/**
	 * 获取两个日期之差
	 *
	 * @param oneDate
	 * @param twoDate
	 * @return
	 */
	public static long getDateQuot(String oneDate, String twoDate) {
		long quot = 0;
		Date date1 = DateTools.parseString2Date(oneDate, "yyyy-MM-dd");
		Date date2 = DateTools.parseString2Date(twoDate, "yyyy-MM-dd");
		quot = date1.getTime() - date2.getTime();
		quot = quot / 1000 / 60 / 60 / 24;
		return quot;
	}

	/**
	 * 获取两个月份之差
	 *
	 * @param oneDate
	 * @param twoDate
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static long getMonthQuot(String oneDate, String twoDate) {
		long quot = 0;
		Date date1 = DateTools.parseString2Date(oneDate, "yyyy-MM");
		long yearDate1 = date1.getYear();
		long monthDate1 = date1.getMonth() + 1;
		Date date2 = DateTools.parseString2Date(twoDate, "yyyy-MM");
		long yearDate2 = date2.getYear();
		long monthDate2 = date2.getMonth() + 1;
		quot = (yearDate1 - yearDate2) * 12 + (monthDate1 - monthDate2);
		return quot;
	}

	/**
	 * 计算年月字符串相差数
	 *
	 * @param oneDate
	 * @param twoDate
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static long getDateStringQuot(String oneDate, String twoDate) {
		long quot = 0;
		Date date1 = DateTools.parseString2Date(oneDate, "yyyy-MM");
		int yearDate1 = date1.getYear();
		int monthDate1 = date1.getMonth() + 1;
		String monthDateStr1 = monthDate1 > 9 ? monthDate1 + "" : "0"
				+ monthDate1;
		Date date2 = DateTools.parseString2Date(twoDate, "yyyy-MM");
		long yearDate2 = date2.getYear();
		long monthDate2 = date2.getMonth() + 1;
		String monthDateStr2 = monthDate2 > 9 ? monthDate2 + "" : "0"
				+ monthDate2;
		String string1 = (yearDate1 + 1900 + monthDateStr1 + "");
		String string2 = (yearDate2 + 1900 + monthDateStr2 + "");
		quot = (Long.parseLong(string1) - Long.parseLong(string2));
		return quot;
	}

	/**
	 * 获取2个时间之间的月份集合
	 *
	 * @param start
	 * @param end
	 * @return
	 */
	public String[] getAllMonths(String start, String end) {
		String splitSign = "-";
		String regex = "\\d{4}" + splitSign + "(([0][1-9])|([1][012]))"; // 判断YYYY-MM时间格式的正则表达式
		if (!start.matches(regex) || !end.matches(regex))
			return new String[0];

		List<String> list = new ArrayList<String>();
		if (start.compareTo(end) > 0) {
			// start大于end日期时，互换
			String temp = start;
			start = end;
			end = temp;
		}

		String temp = start; // 从最小月份开始
		while (temp.compareTo(start) >= 0 && temp.compareTo(end) < 0) {
			list.add(temp); // 首先加上最小月份,接着计算下一个月份
			String[] arr = temp.split(splitSign);
			int year = Integer.valueOf(arr[0]);
			int month = Integer.valueOf(arr[1]) + 1;
			if (month > 12) {
				month = 1;
				year++;
			}
			if (month < 10) {// 补0操作
				temp = year + splitSign + "0" + month;
			} else {
				temp = year + splitSign + month;
			}
		}

		int size = list.size();
		String[] result = new String[size];
		for (int i = 0; i < size; i++) {
			result[i] = list.get(i);
		}
		return result;
	}

	public static void main(String[] args) {

		Date date = new Date();
		if(Integer.valueOf("2014-04-04".replace("-",""))
				< Integer.valueOf(DateTools.parseDate2String(date,
				"yyyyMMdd")) && Integer.valueOf(DateTools.parseDate2String(date,
				"dd")) < Integer.valueOf("2014-04-19".replace("-",""))){
			System.out.println(123);
		}
		System.out.println(parseDate2String(getMonthDate(), "yyyy-MM"));
	}

	/***
	 * 获取16位数的流水随机号 by shuye
	 *
	 * @return
	 */
	public static String getserino() {
		String seriNo = "";
		Date dttime = new Date();
		int temp = (int) (Math.random() * 99999999);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currentTime = sdf.format(dttime);
		seriNo = currentTime + temp;
		return seriNo;
	}

	/**
	 * 将java.util.Date型日期转化指定格式的字符串型日期
	 *
	 * @param javaUtilDate
	 *            java.util.Date,传入的java.util.Date型日期
	 * @param dateFormatType
	 *            "yyyy-MM-dd"或者<br>
	 *            "yyyy-MM-dd hh:mm:ss EE"或者<br>
	 *            "yyyy年MM月dd日 hh:mm:ss EE" <br>
	 *            (年月日 时:分:秒 星期 ，注意MM/mm大小写)
	 * @return String 指定格式的字符串型日期
	 */
	public static String turnJavaUtilDateToStrDate(Date javaUtilDate,
												   String dateFormatType) {

		String strDate = "";
		if (javaUtilDate != null) {

			SimpleDateFormat sdf = new SimpleDateFormat(dateFormatType);
			strDate = sdf.format(javaUtilDate);
		}
		return strDate;
	}
	/**
	 * 获取当前时间16位字符串
	 * 修改为时间16位+4位随机数2012091811320043154
	 * @return String e.g."2012082110290016"
	 */
	public static String getCurrentDateTime16Str() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssss");

        /* 生成随机数 */
		int[] array = {0,1,2,3,4,5,6,7,8,9};
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		int result = 0;
		for(int i = 0; i < 4; i++){
			result = result * 10 + array[i];
		}

		return sdf.format(new Date())+result;
	}

	/**
	 * 功能描述：取得指定月份的第一天
	 *
	 * @param strdate
	 *            String 字符型日期
	 * @return String yyyy-MM-dd 格式
	 */
	public static String getMonthBegin(String strdate) {
		Date date = parseString2Date(strdate, "yyyy-MM");
		return parseDate2String(date, "yyyy-MM") + "-01";
	}

	public static Date getMonthBegin(Date strdate) {
		return parseString2Date(parseDate2String(strdate, "yyyy-MM") + "-01","yyyy-MM-dd");
	}
	/**
	 * 功能描述：取得指定月份的最后一天
	 *
	 * @param strdate
	 *            String 字符型日期
	 * @return String 日期字符串 yyyy-MM-dd格式
	 */
	public static String getMonthEnd(String strdate) {
		Date date = parseString2Date(getMonthBegin(strdate),"yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return parseDate2String(calendar.getTime(),"yyyy-MM-dd");
	}
	public static Date getMonthEnd(Date strdate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(strdate);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return calendar.getTime();
	}

	/**
	 * 获取指定日期的前一天
	 * @param strdate
	 * @return
	 */
	public static String getDateAfter(String strdate){
		Date date = parseString2Date(strdate, "yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		return parseDate2String(cal.getTime(), "yyyy-MM-dd");
	}
}
