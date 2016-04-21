package inet.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimestampUtil {

	public static Timestamp toDate(String strSource, String strFormat) {
		SimpleDateFormat fmt = new SimpleDateFormat(strFormat);
		return toDate(strSource, ((DateFormat) (fmt)));
	}

	public static Timestamp toDate(String strSource, DateFormat fmt) {
		try {
			fmt.setLenient(false);
			return toDate(fmt.parse(strSource));
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public Timestamp toDate(long time) {
		return new Timestamp(time);
	}

	private static Timestamp toDate(Date dt) {
		return new Timestamp(dt.getTime());
	}

	public static Timestamp addSecond(Date dt, int iValue) {
		return add(dt, iValue, 13);
	}

	public static Timestamp addMinute(Date dt, int iValue) {
		return add(dt, iValue, 12);
	}

	public static Timestamp addHour(Date dt, int iValue) {
		return add(dt, iValue, 10);
	}

	public static Timestamp addDay(Date dt, int iValue) {
		return add(dt, iValue, 5);
	}

	public static Date addMonth(Date dt, int iValue) {
		return add(dt, iValue, 2);
	}

	public static Date addYear(Date dt, int iValue) {
		return add(dt, iValue, 1);
	}

	private static Timestamp add(Date dt, int iValue, int iType) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(dt);
		cld.add(iType, iValue);
		return toDate(cld.getTime());
	}

	public static Timestamp trunc(Timestamp dt) {
		if (dt == null) {
			return null;
		}
		return toDate(StringUtil.format(dt, "dd/MM/yyyy"), "dd/MM/yyyy");
	}

	public static Timestamp trunc(Date dt) {
		if (dt == null) {
			return null;
		}
		return toDate(StringUtil.format(dt, "dd/MM/yyyy"), "dd/MM/yyyy");
	}

	public static Date trunc(Date dt, String format) {
		if (dt == null) {
			return null;
		}
		return toDate(StringUtil.format(dt, format), format);
	}

	public static Timestamp getSysdate() {
		return new Timestamp(System.currentTimeMillis());
	}
}
