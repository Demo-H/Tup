package com.tupperware.huishengyi.utils.logutils;

import android.os.Environment;
import android.os.Process;

import com.tupperware.huishengyi.utils.Base64Util;
import com.android.dhunter.common.utils.FileUtils;
import com.tupperware.huishengyi.utils.thread.PriorityThreadFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by dhunter on 2018/3/5.
 */

public class LogF {

    private static final long LOG_FILE_SIZE_MAX = 5 * 1024 * 1024;
    private static final int LOG_FILE_SUM_MAX = 10;

    public final static boolean ENTRYCODE = false;// 控制日志是否加密
    private static boolean DEBUG = false;

    private static final String TAG = "LogF";
    private static final String LEVEL_D = "D";
    private static final String LEVEL_W = "W";
    private static final String LEVEL_E = "E";
    private static final ThreadPoolExecutor mExecutor = new ThreadPoolExecutor(
            0, 1, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
            new PriorityThreadFactory(TAG, Process.THREAD_PRIORITY_BACKGROUND),
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r,
                                              ThreadPoolExecutor executor) {
                    if (r instanceof FutureTask<?>) {
                        ((FutureTask<?>) r).cancel(true);
                    }
                    if (LogF.DEBUG) {
                        Log.d(TAG, "mExecutor.rejectedExecution.r = " + r);
                    }
                }
            });

    public static void setDebug(boolean d) {
        DEBUG = d;
    }

    public static boolean isDebug() {
        return DEBUG;
    }

    public static int v(String tag, String msg) {
        if (msg == null) {
            msg = "null";
        }
        return Log.v(tag, msg);
    }

    public static int v(String tag, String msg, Throwable tr) {
        if (msg == null) {
            msg = "null";
        }
        return Log.v(tag, msg, tr);
    }

    public static int i(String tag, String msg) {
        if (msg == null) {
            msg = "null";
        }
        return Log.i(tag, msg);
    }

    /**
     * 打印debug级别的日志
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @return The number of bytes written.
     */
    public static int d(String tag, String msg) {
        if (msg == null) {
            msg = "null";
        }
        writeToFile(LEVEL_D, tag, msg, null);
        return Log.d(tag, msg);
    }

    /**
     * 打印debug级别的日志
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     * @return The number of bytes written.
     */
    public static int d(String tag, String msg, Throwable tr) {
        if (msg == null) {
            msg = "null";
        }
        writeToFile(LEVEL_D, tag, msg, tr);
        return Log.d(tag, msg, tr);
    }

    public static int w(String tag, String msg) {
        if (msg == null) {
            msg = "null";
        }
        writeToFile(LEVEL_W, tag, msg, null);
        return Log.w(tag, msg);
    }

    public static int w(String tag, String msg, Throwable tr) {
        if (msg == null) {
            msg = "null";
        }
        writeToFile(LEVEL_W, tag, msg, tr);
        return Log.w(tag, msg, tr);
    }

    public static int e(String tag, String msg) {
        if (msg == null) {
            msg = "null";
        }
        writeToFile(LEVEL_E, tag, msg, null);
        return Log.e(tag, msg);
    }

    public static int e(String tag, String msg, Throwable tr) {
        if (msg == null) {
            msg = "null";
        }
        writeToFile(LEVEL_E, tag, msg, tr);
        return Log.e(tag, msg, tr);
    }

    public static void flush() {
        mExecutor.getQueue().clear();
    }

    public static void destroy() {
        if (!mExecutor.isShutdown()) {
            mExecutor.shutdown();
        }
    }

    private static void writeToFile(final String level, final String tag,
                                    final String msg, final Throwable tr) {
        final int myTid = Process.myTid();
        mExecutor.execute(new FutureTask<Void>(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                if (Environment.MEDIA_MOUNTED.equals(Environment
                        .getExternalStorageState())) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(level)
                            .append(" ")
                            .append(new SimpleDateFormat(
                                    "yyyy-MM-dd HH:mm:ss.SSS")
                                    .format(new Date())).append(" ")
                            .append(Process.myPid()).append(" ")
                            .append(myTid).append(" ")
                            .append(tag).append(" ").append(msg);
                    if (tr != null) {
                        sb.append(
                                System.getProperties().getProperty(
                                        "line.separator")).append(
                                Log.getStackTraceString(tr));
                    }
                    sb.append(System.getProperties().getProperty(
                            "line.separator"));

                    String userDirPath = FileUtils.DIR_PUBLLIC_ROOT + "/log/";
                    String fileName = "corelog-";
                    int logFileId = 1;
                    File nowFile = null;
                    File nextFile = null;

                    for (int i = 1; i <= 10; i++) {
                        nowFile = new File(userDirPath, fileName + i + ".log");
                        if (nowFile.length() > LOG_FILE_SIZE_MAX) {
                            if (i == LOG_FILE_SUM_MAX) {
                                nowFile = new File(userDirPath, fileName + "1.log");
                                nowFile.delete();
                                logFileId = 1;
                                break;
                            } else {
                                nextFile = new File(userDirPath, fileName + (i + 1) + ".log");
                                if (nowFile.lastModified() > nextFile.lastModified()) {
                                    nextFile.delete();
                                    logFileId = i + 1;
                                    break;
                                }
                            }
                        } else {
                            logFileId = i;
                            break;
                        }
                    }
                    nowFile = new File(userDirPath, fileName + logFileId + ".log");

                    writeStringToFile(nowFile, sb.toString(), true);
                    sb.setLength(0);
                }
                return null;
            }
        }));
    }

    /**
     * 写字符串到文件，文件父目录如果不存在，会自动创建
     *
     * @param file
     * @param content
     * @param isAppend
     * @return
     */
    private static boolean writeStringToFile(File file, String content,
                                             boolean isAppend) {
        boolean isWriteOk = false;
        char[] buffer = null;
        int count = 0;
        BufferedReader br = null;
        BufferedWriter bw = null;
        String contents;
        if (ENTRYCODE && !isDebug()) {
            contents = Base64Util.encode(content);
            if (contents == null) {
                contents = content;
            }
        } else {
            contents = content;
        }
        try {
            if (!file.exists()) {
                File dir = file.getParentFile();
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (file.exists()) {
                br = new BufferedReader(new StringReader(contents));
                bw = new BufferedWriter(new FileWriter(file, isAppend));
                buffer = new char[1024];
                int len = 0;
                while ((len = br.read(buffer, 0, 1024)) != -1) {
                    bw.write(buffer, 0, len);
                    count += len;
                }
                bw.flush();
            }
            isWriteOk = contents.length() == count;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                    bw = null;
                }
                if (br != null) {
                    br.close();
                    br = null;
                }
                buffer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isWriteOk;
    }


}
