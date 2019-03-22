package com.jfrabbit.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD_T_HH_MM_SS_XXX = "yyyy-MM-dd'T'HH:mm:ssXXX";
	public static final String YYYY_MM_DD_T_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String YYYY_MM_DD_HH_MM_SS_FILE_TYPE = "yyyy_MM_dd_HH_mm_ss";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String HH_MM_SS = "HH:mm:ss";

	public static String dateFormat(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	public static Date formatToDate(String dateFormat) throws ParseException {
		return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).parse(dateFormat);
	}

	public static Date formatToDate(String dateFormat, String format) throws ParseException {
		return new SimpleDateFormat(format).parse(dateFormat);
	}

	public static String dateToTimestamp(Date date) {
		return String.valueOf(date.getTime());
	}

	public static String timestampToFormatDate(String timestamp, String format) {
		Date date = new Date(new Long(timestamp));
		return dateFormat(date, format);
	}

	public static Date timestampToDate(String timestamp) {
		return new Date(new Long(timestamp));
	}
}
