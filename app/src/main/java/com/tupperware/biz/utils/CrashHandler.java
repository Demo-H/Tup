package com.tupperware.biz.utils;

/**
 * Created by dhunter on 2018/2/8.
 */

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

import com.android.dhunter.common.config.GlobalConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Properties;


/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类
 * 来接管程序,并记录 发送错误报告.
 *
 */
public class CrashHandler implements UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";
    /** 是否开启日志输出,在Debug状态下开启,
     * 在Release状态下关闭以提示程序性能
     * */
    public static final boolean DEBUG = true;
    /** 系统默认的UncaughtException处理类 */
    private UncaughtExceptionHandler mDefaultHandler;
    /** CrashHandler实例 */
    private static CrashHandler INSTANCE;
    /** 程序的Context对象 */
    private Context mContext;

    /** 使用Properties来保存设备的信息和错误堆栈信息*/
    private Properties mDeviceCrashInfo = new Properties();
    private static final String VERSION_NAME = "versionName";
    private static final String VERSION_CODE = "versionCode";
    private static final String ACTIVATE_STATUS = "ActivateStatus";
    private static final String STACK_TRACE = "STACK_TRACE";
    private static final String ACTIVITY = "CurrentActivity";
    /** 错误报告文件的扩展名 */
    private static final String CRASH_REPORTER_EXTENSION = ".txt";

    /** 保证只有一个CrashHandler实例 */
    private CrashHandler() {}
    /** 获取CrashHandler实例 ,单例模式*/
    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;
    }

    /**
     * 初始化,注册Context对象,
     * 获取系统默认的UncaughtException处理器,
     * 设置该CrashHandler为程序的默认处理器
     *
     * @param ctx
     */
    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            //Sleep一会后结束程序
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "Error : ", e);
            }

            mDefaultHandler.uncaughtException(thread, ex);
        }
    }

    /**
     * 自定义错误处理,收集错误信息
     * 发送错误报告等操作均在此完成.
     * 开发者可以根据自己的情况来自定义异常处理逻辑
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return true;
        }
        final String msg = ex.getLocalizedMessage();
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                try{
                    Looper.prepare();
                    Toast.makeText(mContext, "程序出错啦:" + msg, Toast.LENGTH_LONG).show();
                    Looper.loop();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

        }.start();

        //收集设备信息
        collectCrashDeviceInfo(mContext);
        //保存错误报告文件
        String crashFileName = saveCrashInfoToFile(ex);
        //发送错误报告到服务器
//		sendCrashReportsToServer(mContext);
        return true;
    }

    /**
     * 在程序启动时候, 可以调用该函数来发送以前没有发送的报告
     */
//	public void sendPreviousReportsToServer() {
//		sendCrashReportsToServer(mContext);
//	}
//
//	/**
//	 * 把错误报告发送给服务器,包含新产生的和以前没发送的.
//	 *
//	 * @param ctx
//	 */
//	private void sendCrashReportsToServer(final Context ctx) {
//
//		if(iMusicApplication.getInstance().isEmulator())
//			return;
//
//		new Thread(){
//			@Override
//			public void run(){
//				try{
//					String[] crFiles = getCrashReportFiles(ctx);
//					if (crFiles != null && crFiles.length > 0) {
//						TreeSet<String> sortedFiles = new TreeSet<String>();
//						sortedFiles.addAll(Arrays.asList(crFiles));
//
//						for (String fileName : sortedFiles) {
//							File cr = new File(iMusicConstant.BASE_PATH + iMusicConstant.DEFAULT_TEMP_PATH, fileName);
//							boolean result = postReport(cr);
//							if(result)
//								cr.delete();// 删除已发送的报告
//						}
//					}
//				}catch(Exception e){
//
//				}
//			}
//		}.start();
//	}
//
//	private boolean postReport(File file) {
//		SimpleFTP ftp = new SimpleFTP();
//		try{
//	        boolean result = ftp.connect("223.5.12.77", 21, "logftp", "logftp@dj");
//	        result = ftp.ascii();
//	        if(iMusicApplication.getInstance().getVersion().contains("_t"))
//	        	result = ftp.cd("lab");
//	        else
//	        	result = ftp.cd("production");
//	        result = ftp.stor(file);
//	        ftp.disconnect();
//	        return result;
//        }catch(Exception e){
//        	Log.e(this.getClass().getName(), "",e);
//        }finally{
//        	ftp.disconnect();
//        }
//        return false;
//	}

    /**
     * 获取错误报告文件名
     * @param ctx
     * @return
     */
    private String[] getCrashReportFiles(Context ctx) {
        File filesDir =new File(GlobalConfig.BASE_PATH + GlobalConfig.DEFAULT_TEMP_PATH);
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(CRASH_REPORTER_EXTENSION);
            }
        };
        return filesDir.list(filter);
    }
    /**
     * 保存错误信息到文件中
     * @param ex
     * @return
     */
    private String saveCrashInfoToFile(Throwable ex) {
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        ex.printStackTrace(printWriter);
        Log.e(this.getClass().getName(), "",ex);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        String result = info.toString();
        printWriter.close();

        mDeviceCrashInfo.put(VERSION_CODE, AppUtils.getVersionName());
        mDeviceCrashInfo.put(STACK_TRACE, result);



        try {
            Time t = new Time("GMT+8");
            t.setToNow(); // 取得系统时间
            int date = t.year * 10000 + (t.month+1) * 100 + t.monthDay;
            int time = t.hour * 10000 + t.minute * 100 + t.second;

            String dateStr = date + "-" + time;

            String fileName = "crash-" + dateStr + CRASH_REPORTER_EXTENSION;

            if(AppUtils.getVersionName().contains("_t"))
                fileName = "crash-" + dateStr + ".txt";

            File path = new File(GlobalConfig.BASE_PATH + GlobalConfig.DEFAULT_TEMP_PATH);
            if(!path.exists() || !path.isDirectory())
                path.mkdirs();

            File file = new File(GlobalConfig.BASE_PATH + GlobalConfig.DEFAULT_TEMP_PATH, fileName);
            FileOutputStream trace = new FileOutputStream(file);
            //trace.write(result.getBytes());
            sendCrashInfoToServer(mDeviceCrashInfo.toString() , fileName);
            trace.write(mDeviceCrashInfo.toString().getBytes());
//			FileOutputStream trace = mContext.openFileOutput(fileName,
//					Context.MODE_PRIVATE);
//			mDeviceCrashInfo.store(trace, "");

            trace.flush();
            trace.close();
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing report file...", e);
        }
        return null;
    }


    private void sendCrashInfoToServer(final String content , final String title){
        try{

        }catch(Exception e){
            Log.d(this.getClass().getName(), "", e);
        }
    }

    /**
     * 收集程序崩溃的设备信息
     *
     * @param ctx
     */
    public void collectCrashDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                mDeviceCrashInfo.put(VERSION_NAME,
                        pi.versionName == null ? "not set" : pi.versionName);
                mDeviceCrashInfo.put(VERSION_CODE, pi.versionCode);
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "Error while collect package info", e);
        }
        //使用反射来收集设备信息.在Build类中包含各种设备信息,
        //例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mDeviceCrashInfo.put(field.getName(), field.get(null));
                if (DEBUG) {
                    Log.d(TAG, field.getName() + " : " + field.get(null));
                }
            } catch (Exception e) {
                Log.e(TAG, "Error while collect crash info", e);
            }

        }

    }

}