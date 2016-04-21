package inet.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileUtil {

    public static String getAbsolutePath(String strCurrenDir, String strFileName) {
        if (!strFileName.startsWith("/") && !strFileName.startsWith("\\")) {
            if (!strCurrenDir.endsWith("/") && !strCurrenDir.endsWith("\\")) {
                return strCurrenDir + "/" + strFileName;
            }
            return strCurrenDir + strFileName;
        }
        return strFileName;
    }

    public static void forceFolderExist(String strFolder) throws IOException {
        File flTemp = new File(strFolder);
        if (!flTemp.exists()) {
            if (!flTemp.mkdirs()) {
                throw new IOException("Could not create folder " + strFolder);
            }
        } else if (!flTemp.isDirectory()) {
            throw new IOException("A file with name" + strFolder + " already exist");
        }
    }

    public static boolean rename(String strSrc, String strDest, boolean deleteIfExist) throws IOException {
        File flSrc = new File(strSrc);
        File flDest = new File(strDest);
        if (flSrc.getAbsolutePath().equals(flDest.getAbsolutePath())) {
            return false;
        }
        if (flDest.exists()) {
            if (deleteIfExist) {
                flDest.delete();
            } else {
                throw new IOException("File '" + strDest + "' already exist");
            }
        }
        return flSrc.renameTo(flDest);
    }

    public static boolean rename(String strSrc, String strDest) {
        File flSrc = new File(strSrc);
        File flDest = new File(strDest);
        if (flSrc.getAbsolutePath().equals(flDest.getAbsolutePath())) {
            return true;
        }
        if (flDest.exists()) {
            flDest.delete();
        }
        return flSrc.renameTo(flDest);
    }

    public static boolean copyFile(String strSrc, String strDest) {
        FileInputStream isSrc = null;
        FileOutputStream osDest = null;
        File flDest = null;
        File flSrc = null;
        try {
            flDest = new File(strDest);
            if (flDest.exists()) {
                flDest.delete();
            }
            flSrc = new File(strSrc);
            if (!flSrc.exists()) {
                return false;
            }
            isSrc = new FileInputStream(flSrc);
            osDest = new FileOutputStream(flDest);

            byte[] btData = new byte[65536];
            int iLength;
            while ((iLength = isSrc.read(btData)) != -1) {
                osDest.write(btData, 0, iLength);
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            safeClose(isSrc);
            safeClose(osDest);
        }
    }

    public static boolean deleteFile(String strSrc) {
        File flSrc = new File(strSrc);
        return flSrc.delete();
    }

    public static boolean copyResource(Class cls, String strResSource, String strFile) {
        InputStream isSrc = null;
        FileOutputStream osDest = null;
        try {
            isSrc = cls.getResourceAsStream(strResSource);
            if (isSrc == null) {
                throw new IOException("Resource " + strResSource + " not found");
            }
            osDest = new FileOutputStream(strFile);

            byte[] btData = new byte[65536];
            int iLength = 0;
            while ((iLength = isSrc.read(btData)) != -1) {
                osDest.write(btData, 0, iLength);
            }
        } catch (IOException e) {
            return false;
        } finally {
            safeClose(isSrc);
            safeClose(osDest);
        }
        return true;
    }

    public static void backup(String strFileName, int iMaxSize, int iRemainSize) throws Exception {
        if (iMaxSize <= iRemainSize) {
            throw new IllegalArgumentException();
        }
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        File flSource = new File(strFileName);
        if (flSource.length() > iMaxSize) {
            String strNewName = strFileName + "." + fmt.format(new Date());
            rename(strFileName, strNewName);
            RandomAccessFile fl = null;
            FileOutputStream os = null;
            try {
                os = new FileOutputStream(strFileName);
                fl = new RandomAccessFile(strNewName, "rw");
                fl.seek(fl.length() - iRemainSize);
                byte[] bt = new byte[iRemainSize];
                int iByteRead = fl.read(bt);
                if (iByteRead != iRemainSize) {
                    throw new IOException();
                }
                os.write(bt, 0, iByteRead);
                fl.setLength(fl.length() - iRemainSize);
            } finally {
                safeClose(fl);
                safeClose(os);
            }
        }
    }

    public static void backup(String strFileName, int iMaxSize) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        File flSource = new File(strFileName);
        if (flSource.length() > iMaxSize) {
            String strNewName = "";
            if (strFileName.indexOf(".") >= 0) {
                strNewName = strFileName.substring(0, strFileName.lastIndexOf(".")) + fmt.format(new Date()) + strFileName.substring(strFileName.lastIndexOf("."));
            } else {
                strNewName = strFileName + fmt.format(new Date());
            }
            rename(strFileName, strNewName);
        }
    }

    public static String backup(String strSourcePath, String strBackupPath, String strSourceFile, String strBackupFile, String strBackupStyle)
            throws Exception {
        return backup(strSourcePath, strBackupPath, strSourceFile, strBackupFile, strBackupStyle, true);
    }

    public static String backup(String strSourcePath, String strBackupPath, String strSourceFile, String strBackupFile, String strBackupStyle, boolean bReplace)
            throws Exception {
        return backup(strSourcePath, strBackupPath, strSourceFile, strBackupFile, strBackupStyle, "", bReplace);
    }

    public static String backup(String strSourcePath, String strBackupPath, String strSourceFile, String strBackupFile, String strBackupStyle, String strAdditionPath)
            throws Exception {
        return backup(strSourcePath, strBackupPath, strSourceFile, strBackupFile, strBackupStyle, strAdditionPath, true);
    }

    public static String backup(String strSourcePath, String strBackupPath, String strSourceFile, String strBackupFile, String strBackupStyle, String strAdditionPath, boolean bReplace)
            throws Exception {
        if (strBackupStyle.equals("Delete file")) {
            if (!deleteFile(strSourcePath + strSourceFile)) {
                throw new Exception("Cannot delete file " + strSourcePath + strSourceFile);
            }
        } else if (strBackupPath.length() > 0) {
            String strCurrentDate = "";
            if (strBackupStyle.equals("Daily")) {
                strCurrentDate = StringUtil.format(new Date(), "yyyyMMdd") + "/";
            } else if (strBackupStyle.equals("Monthly")) {
                strCurrentDate = StringUtil.format(new Date(), "yyyyMM") + "/";
            } else if (strBackupStyle.equals("Yearly")) {
                strCurrentDate = StringUtil.format(new Date(), "yyyy") + "/";
            }
            forceFolderExist(strBackupPath + strCurrentDate + strAdditionPath);
            if (!rename(strSourcePath + strSourceFile, strBackupPath + strCurrentDate + strAdditionPath + strBackupFile, bReplace)) {
                throw new Exception("Cannot rename file " + strSourcePath + strSourceFile + " to " + strBackupPath + strCurrentDate + strBackupFile);
            }
            return strBackupPath + strCurrentDate + strBackupFile;
        }
        return "";
    }

    public static void safeClose(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void safeClose(OutputStream os) {
        try {
            if (os != null) {
                os.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void safeClose(RandomAccessFile fl) {
        try {
            if (fl != null) {
                fl.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String formatFileName(String strFileName, String strFileFormat) throws Exception {
        if ((strFileName == null) || (strFileName.length() == 0) || (strFileFormat == null) || (strFileFormat.length() == 0)) {
            return strFileName;
        }
        int iExtIndex = strFileName.lastIndexOf('.');
        if (iExtIndex < 0) {
            iExtIndex = strFileName.length();
        }
        int iBaseIndex = strFileName.lastIndexOf('/');
        if (iBaseIndex < 0) {
            iBaseIndex = strFileName.lastIndexOf('\\');
        }
        if (iBaseIndex < 0) {
            iBaseIndex = 0;
        }
        String strBaseFileName = strFileName.substring(iBaseIndex, iExtIndex);
        String strFileExtension = "";
        if (iExtIndex < strFileName.length() - 1) {
            strFileExtension = strFileName.substring(iExtIndex + 1, strFileName.length());
        }
        strFileFormat = StringUtil.replaceAll(strFileFormat, "$FileName", strFileName);
        strFileFormat = StringUtil.replaceAll(strFileFormat, "$BaseFileName", strBaseFileName);
        strFileFormat = StringUtil.replaceAll(strFileFormat, "$FileExtension", strFileExtension);
        return strFileFormat;
    }

    public static URL getResource(String strName) {
        try {
            File fl = new File(strName);
            if ((fl.exists()) && (fl.isFile())) {
                return fl.toURI().toURL();
            }
        } catch (Exception e) {
        }
        if (!strName.startsWith("/")) {
            return FileUtil.class.getResource("/" + strName);
        }
        return FileUtil.class.getResource(strName);
    }

    public static Map<String, String> getPackageVersions() {

        Map<String, String> ret = new LinkedHashMap<String, String>();

        URLClassLoader sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        URL[] urls = sysLoader.getURLs();
        for (int i = 0; i < urls.length; i++) {
            String strFile = urls[i].getFile();
            strFile = strFile.toLowerCase();
            if (strFile.endsWith(".jar")) {
                URLClassLoader classLoader = null;
                try {
                    classLoader = new URLClassLoader(new URL[]{urls[i]});

                    InputStream in = classLoader.getResourceAsStream("version.properties");
                    if (in != null) {
                        try {
                            byte[] buf = new byte[in.available()];
                            in.read(buf);
                            ret.put(urls[i].getPath(), new String(buf));
                        } finally {
                            in.close();
                        }
                    }
                } catch (IOException ex1) {
                    ex1.printStackTrace();
                } finally {
                    classLoader = null;
                }
            }
        }
        return ret;
    }
}
