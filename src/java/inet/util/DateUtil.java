package inet.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date toDate(String strSource, String strFormat) {
        SimpleDateFormat fmt = new SimpleDateFormat(strFormat);
        return toDate(strSource, ((DateFormat) (fmt)));
    }

    public static Date toDate(String strSource, DateFormat fmt) {
        try {
            fmt.setLenient(false);
            return fmt.parse(strSource);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Date addSecond(Date dt, int iValue) {
        return add(dt, iValue, 13);
    }

    public static Date addMinute(Date dt, int iValue) {
        return add(dt, iValue, 12);
    }

    public static Date addHour(Date dt, int iValue) {
        return add(dt, iValue, 10);
    }

    public static Date addDay(Date dt, int iValue) {
        return add(dt, iValue, 5);
    }

    public static Date addMonth(Date dt, int iValue) {
        return add(dt, iValue, 2);
    }

    public static Date addYear(Date dt, int iValue) {
        return add(dt, iValue, 1);
    }

    private static Date add(Date dt, int iValue, int iType) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(dt);
        cld.add(iType, iValue);
        return cld.getTime();
    }

    public static int compareDateTime(String strStartDate, String strEndDate, String formatMask) throws Exception {
        try {
            Date datStaDateTime;
            Date datEndDateTime;
            SimpleDateFormat fmtDate = new SimpleDateFormat(formatMask);
            datStaDateTime = fmtDate.parse(strStartDate);
            datEndDateTime = fmtDate.parse(strEndDate);
            return datEndDateTime.compareTo(datStaDateTime);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static Date trunc(Date dt) {
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
    
    public static Date getSysdate(){
    	return new Date();
    }    
}
