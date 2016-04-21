package inet.common.log;

import inet.util.StringUtil;

import java.io.IOException;
import java.io.Writer;
import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.CountingQuietWriter;
import org.apache.log4j.spi.LoggingEvent;

public class RollingFileAppenderExt extends FileAppender {

	/**
	 * The default maximum file size is 10MB.
	 */
	protected long maxFileSize = 10 * 1024 * 1024;

	/**
	 * There is one backup file by default.
	 */
	protected int maxBackupIndex = 1;

	private long nextRollover = 0;
	private String path;
	private static boolean isExist = false;

	private int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

	public RollingFileAppenderExt(String path) {
		super();
	}

	public RollingFileAppenderExt(Layout layout, String filename, boolean append)
			throws IOException {
		super(layout, filename, append);
	}

	public RollingFileAppenderExt(Layout layout, String filename, String path)
			throws IOException {
		super(layout, filename);
		this.path = path;
	}

	public int getMaxBackupIndex() {
		return maxBackupIndex;
	}

	public long getMaximumFileSize() {
		return maxFileSize;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void rollOver(boolean isNewPath) {

		// táº¡o folder má»›i Ä‘á»ƒ lÆ°u trá»¯ log ngÃ y má»›i (isNewPath =
		// true)
		Date sysdate = Calendar.getInstance().getTime();

		// Ä‘Ã¡nh dáº¥u Ä‘á»ƒ khÃ´ng khá»Ÿi táº¡o láº¡i file name
		if (!isExist) {
			isExist = true;
		}

		if (qw != null) {
			long size = ((CountingQuietWriter) qw).getCount();
			nextRollover = size + maxFileSize;
		}

		// XÃ³a folder log lÆ°u trá»¯ quÃ¡ má»™t thÃ¡ng
		File file = new File(path);
		if (file.exists()) {
			File[] fileList = file.listFiles();
			if (file.listFiles().length > 30) {											
				Arrays.sort(fileList, new Comparator() {
	                public int compare(Object f1, Object f2) {
	                    return ((File) f1).getName().compareTo(((File) f2).getName());
	                }
	            });
				
				for (File item : file.listFiles()) {
					if (item.isDirectory()) {
						for (File item2 : item.listFiles()) {
							item2.delete();
						}
						item.delete();
					} else {
						file.delete();
					}
					break;
				}
				
			}
		}

		if (isNewPath) {
			try {

				// Ä�á»•i tÃªn file vÃ  táº¡o file má»›i
				this.closeFile();

				// keep windows happy.
				this.setFile(buildFileName(sysdate), true, bufferedIO, bufferSize);
				nextRollover = 0;

			} catch (IOException e) {
				LogLog.error("setFile(" + fileName + ", false) call failed.", e);
			}

		} else {
			// XÃ³a file log chá»‰ sá»‘ lá»›n hÆ¡n maxBackupIndex trong thÆ°
			// má»¥c ngÃ y hiá»‡n táº¡i
			file = new File(buildPath(sysdate));
			if (file.exists() && file.listFiles().length > maxBackupIndex) {
				File[] files = file.listFiles();
				Arrays.sort(files, new Comparator() {
	                public int compare(Object f1, Object f2) {
	                    return ((File) f1).getName().compareTo(((File) f2).getName());
	                }
	            });

				for (File item : files) {
					if (item.delete()) {
						break;
					}
				}

				this.closeFile(); // keep windows happy.

				// Ä�á»•i tÃªn file vÃ  táº¡o file má»›i
				file = new File(fileName);
				File target = new File(buildTargetName(sysdate, isNewPath));
				file.renameTo(target);

				try {
					this.setFile(buildFileName(sysdate), false, bufferedIO, bufferSize);
					nextRollover = 0;
				} catch (IOException e) {
					LogLog.error("setFile(" + fileName + ", false) call failed.", e);
				}
			} else {
				try {

					// Ä�á»•i tÃªn file vÃ  táº¡o file má»›i
					this.closeFile(); // keep windows happy.

					file = new File(fileName);
					File target = new File(buildTargetName(sysdate, isNewPath));
					file.renameTo(target);

					this.setFile(buildFileName(sysdate), true, bufferedIO, bufferSize);
					nextRollover = 0;
				} catch (IOException e) {
					LogLog.error("setFile(" + fileName + ", false) call failed.", e);
				}
			}
		}
	}

	@Override
	public synchronized void setFile(String fileName, boolean append,
			boolean bufferedIO, int bufferSize) throws IOException {
		if (!isExist) {
			path = fileName;
			fileName = buildFileName(new Date());
		}
		super.setFile(fileName, append, this.bufferedIO, this.bufferSize);
		if (append) {
			File f = new File(fileName);
			((CountingQuietWriter) qw).setCount(f.length());
		}
	}

	public void setMaxBackupIndex(int maxBackups) {
		this.maxBackupIndex = maxBackups;
	}

	public void setMaximumFileSize(long maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	public void setMaxFileSize(String value) {
		maxFileSize = OptionConverter.toFileSize(value, maxFileSize + 1);
	}

	@Override
	protected void setQWForFiles(Writer writer) {
		this.qw = new CountingQuietWriter(writer, errorHandler);
	}

	@Override
	protected void subAppend(LoggingEvent event) {
		super.subAppend(event);

		Calendar sysdate = Calendar.getInstance();

		if (currentDay != sysdate.get(Calendar.DAY_OF_MONTH)) {
			currentDay = sysdate.get(Calendar.DAY_OF_MONTH);
			rollOver(true);
		} else if (fileName != null && qw != null) {
			long size = ((CountingQuietWriter) qw).getCount();
			if (size >= maxFileSize && size >= nextRollover) {
				rollOver(false);
			}
		}
	}

	public String buildFileName(Date date) {
		return path + StringUtil.format(date, "yyyyMMdd") + File.separator + StringUtil.format(date, "yyyy-MM-dd") + ".log";
	}

	public String buildTargetName(Date date, boolean isNewPath) {
		return path + StringUtil.format(date, "yyyyMMdd") + File.separator + StringUtil.format(date, "yyyy-MM-dd_HH.mm.ss") + ".log";
	}

	public String buildPath(Date date) {
		return path + StringUtil.format(date, "yyyyMMdd") + File.separator;
	}

}
