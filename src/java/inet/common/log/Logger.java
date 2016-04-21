package inet.common.log;

import inet.util.StringUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.spi.LoggerFactory;

public class Logger {

	private static ConcurrentHashMap<String, LoggerFactory> factories = new ConcurrentHashMap<String, LoggerFactory>();
	private static PatternLayout layout = new PatternLayout();

	private org.apache.log4j.Logger logger;

	private static final Object sync = new Object();
	private static String logDir = "logs/";
	private boolean isDebug = false;

	private static String maxFileSize = "20MB";
	private static int maxBackupIndex = 10;
	private static String conversionPattern = "%d{MM/dd HH:mm:ss.SSS} [%p] %m%n";

	public Logger(final String name) {

		String nameExt = name;
		nameExt = StringUtil.nvl(nameExt, "console").toLowerCase();

		LoggerFactory loggerFactory = null;

		try {
			if (nameExt.equals("console")) {
				loggerFactory = factories.get(nameExt);
				logger = org.apache.log4j.Logger.getLogger(nameExt, loggerFactory);
			} else {
				loggerFactory = factories.get(nameExt);
				if (loggerFactory == null) {
					synchronized (sync) {
						loggerFactory = factories.get(nameExt);
						if (loggerFactory == null) {

							String path = logDir + nameExt + "/";
							final RollingFileAppenderExt appenderExt = new RollingFileAppenderExt(layout, path, path);
							appenderExt.setMaxFileSize(maxFileSize);
							appenderExt.setMaxBackupIndex(maxBackupIndex);

							loggerFactory = new LoggerFactory() {
								@Override
								public org.apache.log4j.Logger makeNewLoggerInstance(String string) {
									org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(name);
									logger.addAppender(appenderExt);
									logger.setAdditivity(false);
									return logger;
								}
							};
							factories.put(nameExt, loggerFactory);
						}
					}
				}
			}
			logger = org.apache.log4j.Logger.getLogger(name, loggerFactory);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	static {
		try {
			org.apache.log4j.Logger.getRootLogger().getLoggerRepository().resetConfiguration();

			layout.setConversionPattern(conversionPattern);

			final RollingFileAppender console = new RollingFileAppender(layout, logDir + "console.log");
			console.setMaxFileSize("10MB");
			console.setMaxBackupIndex(0);

			LoggerFactory loggerFactory = new LoggerFactory() {
				@Override
				public org.apache.log4j.Logger makeNewLoggerInstance(String string) {
					org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("console");
					logger.addAppender(console);
					logger.setAdditivity(false);
					return logger;
				}
			};
			factories.put("console", loggerFactory);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void setLogDir(String dir) {
		logDir = dir;
	}

	public static void setConversionPattern(String pattern) {
		conversionPattern = pattern;
	}

	public static void setMaxBackupIndex(int index) {
		maxBackupIndex = index;
	}

	public static void setMaxFileSize(String fileSize) {
		maxFileSize = fileSize;
	}

	public String getLogDir() {
		return logDir;
	}

	public void log(Priority priority, String message) {
		if(isDebug){
			System.out.println(StringUtil.format(new Date(),"MM/dd HH:mm:ss: ") + message);
		}
		logger.log(priority, message);
	}

	public void log(Priority priority, Exception ex) {
		if(isDebug){
			System.out.println(StringUtil.format(new Date(),"MM/dd HH:mm:ss: ") + getStackTrace(ex));
		}
		logger.log(priority, getStackTrace(ex));
	}

	public void info(String message) {
		if(isDebug){
			System.out.println(StringUtil.format(new Date(),"MM/dd HH:mm:ss: ") + message);
		}
		logger.info(message);
	}

	public void info(Exception ex) {
		if(isDebug){
			System.out.println(StringUtil.format(new Date(),"MM/dd HH:mm:ss: ") + getStackTrace(ex));
		}
		logger.info(getStackTrace(ex));
	}

	public void error(String message) {
		if(isDebug){
			System.out.println(StringUtil.format(new Date(),"MM/dd HH:mm:ss: ") + message);
		}
		logger.error(message);
	}

	public void error(Exception ex) {
		if(isDebug){
			System.out.println(StringUtil.format(new Date(),"MM/dd HH:mm:ss: ") + getStackTrace(ex));
		}
		logger.error(getStackTrace(ex));
	}

	public void fatal(String message) {
		if(isDebug){
			System.out.println(StringUtil.format(new Date(),"MM/dd HH:mm:ss: ") + message);
		}
		logger.fatal(message);
	}

	public void fatal(Exception ex) {
		if(isDebug){
			System.out.println(StringUtil.format(new Date(),"MM/dd HH:mm:ss: ") + getStackTrace(ex));
		}
		logger.fatal(getStackTrace(ex));
	}

	public void warn(String message) {
		if(isDebug){
			System.out.println(StringUtil.format(new Date(),"MM/dd HH:mm:ss: ") + message);
		}
		logger.warn(message);
	}

	public void warn(Exception ex) {
		if(isDebug){
			System.out.println(StringUtil.format(new Date(),"MM/dd HH:mm:ss: ") + getStackTrace(ex));
		}
		logger.warn(getStackTrace(ex));
	}

	public void trace(String message) {
		if(isDebug){
			System.out.println(StringUtil.format(new Date(),"MM/dd HH:mm:ss: ") + message);
		}
		logger.trace(message);
	}

	public void trace(Exception ex) {
		if(isDebug){
			System.out.println(StringUtil.format(new Date(),"MM/dd HH:mm:ss: ") + getStackTrace(ex));
		}
		logger.trace(getStackTrace(ex));
	}

	public void debug(String message) {
		if(isDebug){
			System.out.println(StringUtil.format(new Date(),"MM/dd HH:mm:ss: ") + message);
		}
		logger.debug(message);
	}

	public void debug(Exception ex) {
		if(isDebug){
			System.out.println(StringUtil.format(new Date(),"MM/dd HH:mm:ss: ") + getStackTrace(ex));
		}
		logger.debug(getStackTrace(ex));
	}

	public static String getStackTrace(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		return sw.toString();
	}

	public void setDebug(boolean debug) {
		this.isDebug = debug;
	}
}
