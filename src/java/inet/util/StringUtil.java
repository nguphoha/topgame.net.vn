package inet.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import sun.misc.BASE64Encoder;

public class StringUtil {

    public static final int ALPHA = 0;
    public static final int ALPHANUMERIC = 1;
    public static final int NUMERIC = 2;

    public static String generate(int length, int mode) throws Exception {

        StringBuilder buffer = new StringBuilder();
        String characters = "";

        switch (mode) {

            case ALPHA:
                characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
                break;

            case ALPHANUMERIC:
                characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                break;

            case NUMERIC:
                characters = "1234567890";
                break;
        }

        int charactersLength = characters.length();

        for (int i = 0; i < length; i++) {
            double index = Math.random() * charactersLength;
            buffer.append(characters.charAt((int) index));
        }
        return buffer.toString();
    }

    public static final int ALIGN_CENTER = 0;
    public static final int ALIGN_LEFT = 1;
    public static final int ALIGN_RIGHT = 2;

    private static char[] SOURCE_CHARACTERS = {'À', 'Á', 'Â', 'Ã', 'È', 'É',
        'Ê', 'Ì', 'Í', 'Ò', 'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â',
        'ã', 'è', 'é', 'ê', 'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý',
        'Ă', 'ă', 'Đ', 'đ', 'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ',
        'ạ', 'Ả', 'ả', 'Ấ', 'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ',
        'Ắ', 'ắ', 'Ằ', 'ằ', 'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ',
        'ẻ', 'Ẽ', 'ẽ', 'Ế', 'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ',
        'Ỉ', 'ỉ', 'Ị', 'ị', 'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ',
        'ổ', 'Ỗ', 'ỗ', 'Ộ', 'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ',
        'Ợ', 'ợ', 'Ụ', 'ụ', 'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ',
        'ữ', 'Ự', 'ự', 'ỷ', 'ỹ', 'ỳ', 'ý', 'ỵ', 'Ỷ', 'Ỹ', 'Ỳ', 'Ý', 'Ỵ'};

    // Mang cac ky tu thay the khong dau
    private static char[] DESTINATION_CHARACTERS = {'A', 'A', 'A', 'A', 'E',
        'E', 'E', 'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a',
        'a', 'a', 'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u',
        'y', 'A', 'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u',
        'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
        'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e',
        'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E',
        'e', 'I', 'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
        'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
        'o', 'O', 'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
        'U', 'u', 'U', 'u', 'y', 'y', 'y', 'y', 'y', 'Y', 'Y', 'Y', 'Y', 'Y'};

    private static char clearUnicode(char ch) {
        int i = 0;
        for (char c : SOURCE_CHARACTERS) {
            if (c == ch) {
                ch = DESTINATION_CHARACTERS[i];
                break;
            }
            i++;
        }
        return ch;
    }

    public static String clearUnicode(String s) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < sb.length(); i++) {
            sb.setCharAt(i, clearUnicode(sb.charAt(i)));
        }
        return sb.toString();
    }

    public static String encrypt(String strValue, String strAlgorithm)
            throws Exception {
        return encrypt(strValue.getBytes(), strAlgorithm);
    }

    public static String encrypt(byte[] btValue, String strAlgorithm)
            throws Exception {
        BASE64Encoder enc = new BASE64Encoder();
        MessageDigest md = MessageDigest.getInstance(strAlgorithm);
        return enc.encodeBuffer(md.digest(btValue));
    }

    public static String format(Date dtImput, String strPattern) {
        if (dtImput == null) {
            return null;
        }
        SimpleDateFormat fmt = new SimpleDateFormat(strPattern);
        return fmt.format(dtImput);
    }

    public static String format(long lngNumber, String strPattern) {
        DecimalFormat fmt = new DecimalFormat(strPattern);
        return fmt.format(lngNumber);
    }

    public static String format(double dblNumber, String strPattern) {
        DecimalFormat fmt = new DecimalFormat(strPattern);
        return fmt.format(dblNumber);
    }

    public static String replaceAll(String strSrc, String strFind,
            String strReplace) {
        if ((strFind == null) || (strFind.length() == 0)) {
            return strSrc;
        }
        int iLocation = 0;
        int iPrevLocation = 0;
        StringBuilder strResult = new StringBuilder();
        while ((iLocation = strSrc.indexOf(strFind, iLocation)) >= 0) {
            strResult.append(strSrc.substring(iPrevLocation, iLocation));
            strResult.append(strReplace);
            iLocation += strFind.length();
            iPrevLocation = iLocation;
        }
        strResult.append(strSrc.substring(iPrevLocation, strSrc.length()));
        return strResult.toString();
    }

    public static String replaceAll(String strSrc, String strFind,
            String strReplace, int iMaxReplacement) {
        int iLocation = 0;
        if ((strFind == null) || (strFind.length() == 0)) {
            return strSrc;
        }
        int iPrevLocation = 0;
        int iCount = 0;
        StringBuilder strResult = new StringBuilder();
        while (((iLocation = strSrc.indexOf(strFind, iLocation)) >= 0)
                && (iCount < iMaxReplacement)) {
            strResult.append(strSrc.substring(iPrevLocation, iLocation));
            strResult.append(strReplace);
            iCount++;
            iLocation += strFind.length();
            iPrevLocation = iLocation;
        }
        strResult.append(strSrc.substring(iPrevLocation, strSrc.length()));
        return strResult.toString();
    }

    public static String replaceAll(String strSrc, String strFind,
            String[] strReplace) {
        int iLocation = 0;
        if ((strFind == null) || (strFind.length() == 0)) {
            return strSrc;
        }
        int iPrevLocation = 0;
        int iCount = 0;
        StringBuilder strResult = new StringBuilder();
        while (((iLocation = strSrc.indexOf(strFind, iLocation)) >= 0)
                && (iCount < strReplace.length)) {
            strResult.append(strSrc.substring(iPrevLocation, iLocation));
            strResult.append(strReplace[iCount]);
            iCount++;
            iLocation += strFind.length();
            iPrevLocation = iLocation;
        }
        strResult.append(strSrc.substring(iPrevLocation, strSrc.length()));
        return strResult.toString();
    }

    public static String replaceAllIgnoreCase(String strSrc, String strFind,
            String strReplace) {
        if ((strFind == null) || (strFind.length() == 0)) {
            return strSrc;
        }
        String strSrcUpper = strSrc.toUpperCase();
        strFind = strFind.toUpperCase();

        int iLocation = 0;
        int iPrevLocation = 0;
        StringBuilder strResult = new StringBuilder();
        while ((iLocation = strSrcUpper.indexOf(strFind, iLocation)) >= 0) {
            strResult.append(strSrc.substring(iPrevLocation, iLocation));
            strResult.append(strReplace);
            iLocation += strFind.length();
            iPrevLocation = iLocation;
        }
        strResult.append(strSrc.substring(iPrevLocation, strSrc.length()));
        return strResult.toString();
    }

    public static String nvl(Object objInput, String strNullValue) {
        if (objInput == null) {
            return strNullValue;
        }
        return objInput.toString();
    }

    public static String evl(Object objInput, String strEmptyValue) {
        if (objInput == null) {
            return strEmptyValue;
        }
        String str = objInput.toString();
        if (str.trim().length() == 0) {
            return strEmptyValue;
        }
        return str;
    }

    public static int indexOfLetter(String strSource, int iOffset) {
        while (iOffset < strSource.length()) {
            char c = strSource.charAt(iOffset);
            if (c > ' ') {
                return iOffset;
            }
            iOffset++;
        }
        return -1;
    }

    public static int indexOfSpace(String strSource, int iOffset) {
        while (iOffset < strSource.length()) {
            char c = strSource.charAt(iOffset);
            if (c > ' ') {
                iOffset++;
            } else {
                return iOffset;
            }
        }
        return -1;
    }

    public static int countSymbol(String strSource, String chrSymbol,
            int iOffset) {
        if ((chrSymbol == null) || (chrSymbol.length() == 0)) {
            return 0;
        }
        int iCount = 0;
        while ((iOffset = strSource.indexOf(chrSymbol, iOffset) + 1) > 0) {
            iCount++;
        }
        return iCount;
    }

    public static String[] toStringArray(String strSource, String strSeparator) {
        Vector vtReturn = toStringVector(strSource, strSeparator);
        String[] strReturn = new String[vtReturn.size()];
        for (int iIndex = 0; iIndex < strReturn.length; iIndex++) {
            strReturn[iIndex] = ((String) vtReturn.elementAt(iIndex));
        }
        return strReturn;
    }

    public static Vector toStringVector(String strSource, String strSeparator) {
        Vector vtReturn = new Vector();
        if (strSource.length() > 0) {
            int iIndex = 0;
            int iLastIndex = 0;
            while ((iIndex = strSource.indexOf(strSeparator, iLastIndex)) >= 0) {
                vtReturn.addElement(strSource.substring(iLastIndex, iIndex)
                        .trim());
                iLastIndex = iIndex + strSeparator.length();
            }
            if (iLastIndex <= strSource.length()) {
                vtReturn.addElement(strSource.substring(iLastIndex,
                        strSource.length()).trim());
            }
        }
        return vtReturn;
    }

    public static String[] toStringArray(String strSource) {
        return toStringArray(strSource, ",");
    }

    public static Vector toStringVector(String strSource) {
        return toStringVector(strSource, ",");
    }

    public static Vector toStringVector(String[] strSource) {
        Vector vtReturn = new Vector();
        for (String strSource1 : strSource) {
            vtReturn.addElement(strSource1);
        }
        return vtReturn;
    }

    public static String align(String str, int iAlignment, int iLength, char c) {
        if (str == null) {
            return null;
        }
        if (str.length() > iLength) {
            return str.substring(0, iLength);
        }
        StringBuilder buf = new StringBuilder();
        if (iAlignment == 0) {
            str = lpad(str, str.length() + (iLength - str.length()) / 2, c);
            return rpad(str, iLength, c);
        }
        if (iAlignment == 2) {
            return lpad(str, iLength, c);
        }
        if (iAlignment == 1) {
            return rpad(str, iLength, c);
        }
        return buf.toString();
    }

    public static String align(String str, int iAlignment, int iLength) {
        return align(str, iAlignment, iLength, ' ');
    }

    public static String lpad(String str, int iLength) {
        return lpad(str, iLength, ' ');
    }

    public static String rpad(String str, int iLength) {
        return rpad(str, iLength, ' ');
    }

    public static String lpad(String str, int iLength, char c) {
        if (str == null) {
            return null;
        }
        int iCount = iLength - str.length();
        if (iCount > 0) {
            return createMonoString(c, iCount) + str;
        }
        return str;
    }

    public static String rpad(String str, int iLength, char c) {
        if (str == null) {
            return null;
        }
        int iCount = iLength - str.length();
        if (iCount > 0) {
            return str + createMonoString(c, iCount);
        }
        return str;
    }

    public static String createMonoString(char c, int iLength) {
        StringBuilder buf = new StringBuilder();
        for (int iIndex = 0; iIndex < iLength; iIndex++) {
            buf.append(c);
        }
        return buf.toString();
    }

    public static String join(String[] items, String delim) {
        if ((items == null) || (items.length == 0)) {
            return "";
        }
        StringBuilder sbuf = new StringBuilder();
        for (int i = 0; i < items.length; i++) {
            sbuf.append(items[i]);
            if (i < items.length - 1) {
                sbuf.append(delim);
            }
        }
        return sbuf.toString();
    }

    public static String join(Vector vt, String delim) {
        if ((vt == null) || (vt.isEmpty())) {
            return "";
        }
        StringBuilder sbuf = new StringBuilder();
        for (int i = 0; i < vt.size(); i++) {
            sbuf.append(nvl(vt.elementAt(i), ""));
            if (i < vt.size() - 1) {
                sbuf.append(delim);
            }
        }
        return sbuf.toString();
    }

    public static String capitalize(String str) {
        int iOffset = 0;
        StringBuilder buf = new StringBuilder();
        while ((iOffset = indexOfLetter(str, buf.length())) >= 0) {
            buf.append(str.substring(buf.length(), iOffset));
            buf.append(Character.toUpperCase(str.charAt(buf.length())));
            iOffset = indexOfSpace(str, buf.length());
            if (iOffset < 0) {
                buf.append(str.substring(buf.length(), str.length())
                        .toLowerCase());
                break;
            }
            buf.append(str.substring(buf.length(), iOffset).toLowerCase());
        }
        return buf.toString();
    }

    public static String byteArrayToHexString(byte[] btValue, int iOffset,
            int iLength) {
        int iLastOffset = iOffset + iLength;
        if ((btValue.length < iLastOffset) || (iLength < 1)) {
            return "";
        }
        StringBuilder value = new StringBuilder();
        for (int i = iOffset; i < iLastOffset; i++) {
            byte h = (byte) ((btValue[i] & 0xF0) >>> 4);
            if (h < 10) {
                h = (byte) (48 + h);
            } else {
                h = (byte) (65 + h - 10);
            }
            byte l = (byte) (btValue[i] & 0xF);
            if (l < 10) {
                l = (byte) (48 + l);
            } else {
                l = (byte) (65 + l - 10);
            }
            value.append((char) h);
            value.append((char) l);
        }
        return value.toString();
    }

    public byte[] hexStringToByteArray(String s) throws Exception {
        if (s.length() % 2 != 0) {
            throw new Exception("Invalid hex String value");
        }
        int len = s.length() >> 1;
        byte[] bt = new byte[len];

        s = s.toUpperCase();
        for (int i = 0; i < len; i++) {
            int pos = i << 1;
            byte h = (byte) s.charAt(pos);
            if (h >= 65) {
                h = (byte) (h - 65 + 10);
            } else {
                h = (byte) (h - 48);
            }
            byte l = (byte) s.charAt(pos + 1);
            if (l >= 65) {
                l = (byte) (l - 65 + 10);
            } else {
                l = (byte) (l - 48);
            }
            bt[i] = h;
            int tmp137_135 = i;
            byte[] tmp137_134 = bt;
            tmp137_134[tmp137_135] = ((byte) (tmp137_134[tmp137_135] << 4));
            int tmp146_144 = i;
            byte[] tmp146_143 = bt;
            tmp146_143[tmp146_144] = ((byte) (tmp146_143[tmp146_144] | l));
        }
        return bt;
    }

    public static String standardized(String str) {
        str = str.trim();
        str = str.replaceAll("\\s+", " ");
        return str;
    }

    public static String standardizedFullName(String str) {
        str = standardized(str);
        String temp[] = str.split(" ");
        str = "";
        for (int i = 0; i < temp.length; i++) {
            str += String.valueOf(temp[i].charAt(0)).toUpperCase()
                    + temp[i].substring(1);
            if (i < temp.length - 1) {
                str += " ";
            }
        }
        return str;
    }

    public static String[] PRONOUNCE_TEMP = {"không", "một", "hai", "ba",
        "bốn", "năm", "sáu", "bảy", "tám", "chín"};

    private static String docDozen(double so, boolean daydu) {
        String chuoi = "";
        int chuc = (int) Math.floor(so / 10);
        int donvi = (int) so % 10;
        if (chuc > 1) {
            chuoi = " " + PRONOUNCE_TEMP[chuc] + " mươi";
            if (donvi == 1) {
                chuoi += " mốt";
            }
        } else if (chuc == 1) {
            chuoi = " mười";
            if (donvi == 1) {
                chuoi += " một";
            }
        } else if (daydu && donvi > 0) {
            chuoi = " lẻ";
        }
        if (donvi == 5 && chuc >= 1) {
            chuoi += " lăm";
        } else if (donvi > 1 || (donvi == 1 && chuc == 0)) {
            chuoi += " " + PRONOUNCE_TEMP[donvi];
        }
        return chuoi;
    }

    // Đọc block 3 số
    private static String readBlock(double so, boolean full) {
        String chuoi = "";
        int hundred = (int) Math.floor(so / 100);
        so = so % 100;
        if (full || hundred > 0) {
            chuoi = " " + PRONOUNCE_TEMP[hundred] + " trăm";
            chuoi += docDozen(so, true);
        } else {
            chuoi = docDozen(so, false);
        }
        return chuoi;
    }

    // Đọc số hàng triệu
    private static String readMillion(double so, boolean full) {
        String chuoi = "";
        int trieu = (int) Math.floor(so / 1000000);
        so = so % 1000000;
        if (trieu > 0) {
            chuoi = readBlock(trieu, full) + " triệu, ";
            full = true;
        }
        double nghin = Math.floor(so / 1000);
        so = so % 1000;
        if (nghin > 0) {
            chuoi += readBlock(nghin, full) + " nghìn, ";
            full = true;
        }
        if (so > 0) {
            chuoi += readBlock(so, full);
        }
        return chuoi;
    }

    // Đọc số
    public static String pronounceVietnameseNumber(long so) {
        if (so == 0) {
            return "không";
        }
        String chuoi = "", hauto = "";
        do {
            long ty = so % 1000000000;
            so = (long) Math.floor(so / 1000000000);
            if (so > 0) {
                chuoi = readMillion(ty, true) + hauto + chuoi;
            } else {
                chuoi = readMillion(ty, false) + hauto + chuoi;
            }
            hauto = " tỷ, ";
        } while (so > 0);
        try {
            if (",".equals(chuoi.trim().substring(chuoi.trim().length() - 1, 1))) {
                chuoi = chuoi.trim().substring(0, chuoi.trim().length() - 1);
            }
        } catch (Exception ex) {
        }
        chuoi = chuoi.trim();
        if (chuoi.endsWith(",")) {
            chuoi = chuoi.substring(0, chuoi.length() - 1);
        }
        return chuoi;
    }

    public static String removeSpecialCharsInString(String paramString) {
        if (paramString == null) {
            return null;
        }
        return paramString.replaceAll("[^a-zA-Z0-9_-]", "");
    }

    public static String getStackTrace(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }
}
