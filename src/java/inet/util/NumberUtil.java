package inet.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class NumberUtil {

    public static boolean isNumeric(String strSource) {
        return isNumeric(strSource, NumberFormat.getNumberInstance());
    }

    public static boolean isNumeric(String strSource, String strFormat) {
        DecimalFormat fmt = new DecimalFormat(strFormat);
        return isNumeric(strSource, fmt);
    }

    public static boolean isNumeric(String strSource, NumberFormat fmt) {
        try {
            ParsePosition position = new ParsePosition(0);
            if ((fmt.parse(strSource, position) == null) || (position.getIndex() < strSource.length())) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static double toNumber(String strSource) {
        return toNumber(strSource, NumberFormat.getNumberInstance());
    }

    public static double toNumber(String strSource, String strFormat) {
        DecimalFormat fmt = new DecimalFormat(strFormat);
        return toNumber(strSource, fmt);
    }

    public static double toNumber(String strSource, NumberFormat fmt) {
        try {
            ParsePosition position = new ParsePosition(0);
            Number number = fmt.parse(strSource, position);
            if ((number == null) || (position.getIndex() < strSource.length())) {
                return Double.NaN;
            }
            return number.doubleValue();
        } catch (Exception e) {
            return Double.NaN;
        }
    }
}
