package com.android.dhunter.common.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by dhunter on 2018/2/1.
 */

public class FileUtils {
    public static final String TAG = "FileUtils";
    public static final String APP_NAME = "Tupperware";
    public static final String DIR_PUBLLIC_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + APP_NAME;

    public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值
    public static final int MAX_RECORD_TIME = 180 * 1000; // ms


    private static final int DEFAULT_BUFFER_SIZE = 8 * 1024;

    /**
     * 存储路径方式
     */
    public enum PathType {
        /**
         * 存储在SDCard中
         */
        SDCARD,
        /**
         * 存储在软件Cache中
         */
        CACHE,
        /**
         * 存储在软件DATA目录下的app_photos中
         */
        APP_PHOTOS,
        /**
         * 存储在多媒体文件夹下
         */
        SDCARD_PHOTOS,
        /**
         * 存储在多媒体文件夹下
         */
        MEDIA_DIR,
        /**
         * 存储在软件DATA目录下的app_xmls中
         */
        APP_XML
    }


    public static final String PHOTO_DIR = "photos";
    public static final String XML_DIR = "xmls";
    public static final String Log_DIR = "log";
    public static final String TEMP_DIR = "temp";
    public static final String CRASH_LOG_DIR = "crash_log";
    public static final String DOWNLOAD_DIR = "download";
    public static final String CACHE = "cache";

    public static String DIR_LOG = DIR_PUBLLIC_ROOT + File.separator + Log_DIR;
    public static String DIR_TEMP = DIR_PUBLLIC_ROOT + File.separator + TEMP_DIR;
    public static String DIR_CRASH_LOG = DIR_PUBLLIC_ROOT + File.separator + CRASH_LOG_DIR;
    public static String DIR_DOWNLOAD = DIR_PUBLLIC_ROOT + File.separator + DOWNLOAD_DIR;
    public static String DIR_PHOTO_CRASH = DIR_PUBLLIC_ROOT + File.separator + PHOTO_DIR;
    public static String DIR_CACHE = DIR_PUBLLIC_ROOT + File.separator + CACHE;


    public static void initSdcardDirs(Context context) {
        initSdcardDirs(context, DIR_PUBLLIC_ROOT, APP_NAME);
    }

    public static void initSdcardDirs(Context context, String sdcardPath, String appName) {
        Log.d(TAG, "init SDcard Dirs");

//        if (TextUtils.isEmpty(sdcardPath)) {
//            sdcardPath = DIR_PUBLLIC_ROOT;
//        }
        File file = new File(sdcardPath);
        if(!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        file = new File(DIR_LOG);
        file.mkdirs();
        file = new File(DIR_TEMP);
        file.mkdirs();
        file = new File(DIR_CRASH_LOG);
        file.mkdirs();
        file = new File(DIR_DOWNLOAD);
        file.mkdirs();
        file = new File(DIR_PHOTO_CRASH);
        file.mkdirs();
        file = new File(DIR_CACHE);
        file.mkdirs();

    }

    public static void downloadFileIsExists() {
        if(!fileIsExists(DIR_DOWNLOAD)) {
            File file = new File(DIR_DOWNLOAD);
            file.mkdirs();
        }
    }

    public static void photoFileIsExists() {
        if(!fileIsExists(DIR_PHOTO_CRASH)) {
            File file = new File(DIR_PHOTO_CRASH);
            file.mkdirs();
        }
    }

    public static File getcacheDirectory() {
        File file = new File(FileUtils.DIR_CACHE, "MyCache");
        if(!file.exists()) {
            boolean b = file.mkdirs();
            Log.e("file", "文件不存在  创建文件    "+b);
        }else{
            Log.e("file", "文件存在");
        }
        return file;
    }


    /**
     * 判断文件是否存在
     *
     * @param strFile
     * @return 是否存在
     */
    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 获得文件的路径，如果有SD卡，那么首先获得SD卡的路径
     *
     * @param context
     *            上下文
     * @param dir
     *            子目录名
     * @return 子目录的绝对路径
     */
    /**
     * @param context
     * @param dir
     * @return
     */
    public static String getPathSDCardFirst(Context context, String dir) {
        String absolutePath = "";
        if (sdcardAvailable()) {
            absolutePath = createSavePath(context, PathType.SDCARD);
            absolutePath += File.separator + APP_NAME + File.separator + dir;
        } else {
            absolutePath = context.getDir(dir, Context.MODE_PRIVATE).getPath();
        }
        File file = new File(absolutePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return absolutePath;
    }

    /**
     * 判断是否有SD卡
     *
     * @return 是否有SD卡
     */
    public static boolean sdcardAvailable() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获得文件的路径
     *
     * @param context  上下文
     * @param pathType 路径类型
     * @return 特定路径类型的路径
     */
    public static String createSavePath(Context context, PathType pathType) {
        String path;
        switch (pathType) {
            case CACHE:
                path = context.getCacheDir().getPath();
                break;
            case APP_PHOTOS:
                path = context.getDir(PHOTO_DIR, Context.MODE_WORLD_WRITEABLE).getPath();
                break;
            case APP_XML:
                path = context.getDir(XML_DIR, Context.MODE_WORLD_WRITEABLE).getPath();
                break;
            case SDCARD:
                path = Environment.getExternalStorageDirectory().getPath();
                break;
            case SDCARD_PHOTOS:
                path = Environment.getExternalStorageDirectory().getPath() + "/" + PHOTO_DIR;
                File fileDir = new File(path);
                if (!fileDir.exists()) {
                    fileDir.mkdir();
                }
                break;
            default:
                path = Environment.getExternalStorageDirectory().getPath();
                break;
        }
        return path;
    }

    /**
     * 拷贝文件 将文件A拷贝到文件B fileChannel方式
     *
     * @param fileNameA 原文件
     * @param fileNameB 目标文件
     * @return 是否拷贝成功
     */
    public static boolean copyTo(String fileNameA, String fileNameB) {
        File s = new File(fileNameA);
        File t = new File(fileNameB);
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        boolean result = false;
        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);
            in = fi.getChannel();// 得到对应的文件通道
            out = fo.getChannel();// 得到对应的文件通道
            in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fi != null)
                    fi.close();
                if (in != null)
                    in.close();
                if (fo != null)
                    fo.close();
                if (out != null)
                    out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 创建指定路径的文件夹
     *
     * @param path 路径
     * @return 通过路径创建的File对象
     * @throws SecurityException 安全异常
     */
    public static File createDir(String path) throws SecurityException {
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return fileDir;
    }

    /**
     * 创建文件 若该文件存在删除该文件再创建，若不存在则创建
     *
     * @param file 需要创建的文件
     * @return 是否创建成功
     * @throws IOException IO异常
     */
    public static boolean createFile(File file) throws IOException {
        if (file.exists()) {
            deleteFile(file);
        }
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        return file.createNewFile();
    }

    /**
     * 删除指定文件
     *
     * @param file 需要删除的文件
     * @return
     */
    public static final boolean deleteFile(File file) {
        boolean result = true;
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    result &= deleteFile(children[i]);
                }
            }
        }
        result &= file.delete();
        return result;
    }

    /**
     * 删除文件夹或文件下面的文件
     *
     * @param f 需要删除的文件或文件夹
     */
    public static void deletE(File f) {
        File[] ff;
        int length;
        if (f.isFile()) {
            f.delete();
            return;
        } else if (f.isDirectory()) {
            ff = f.listFiles();
            if (ff != null) {
                length = ff.length;
                int i = 0;
                while (length != 0) {
                    deletE(ff[i]);
                    i++;
                    length--;
                }
                if (length == 0) {
                    f.delete();
                    return;
                }
            }
        }
    }

    /**
     * @param TimeInMillis    指定时间的时间戳，最好不要为当前获取的时间
     * @param f               需要清理子文件的文件夹或需要删除的文件
     * @param haveIgnore      是否有要忽略都文件
     * @param ignoreFilsNames 要忽略文件的开头字符串,haveIgnore为true时有效
     * @作用: 删除最后编辑时间在指定时间之前的文件或文件夹下面的文件(不包括文件夹)
     * @author: zcc
     */
    public static void deleteFilesByTime(final Long TimeInMillis, File f, final boolean haveIgnore, String... ignoreFilsNames) {
        if (TimeInMillis == null || f == null || ignoreFilsNames == null) {
            Log.d(TAG, "deleteFilesByTime() 参数为空！！！");
            return;
        }

        File[] ff;
        int length;
        if (f.isFile()) {
            if (f.lastModified() < TimeInMillis) {
                boolean isIgnoreFile = false;
                if (haveIgnore) {
                    for (int i = 0; i < ignoreFilsNames.length; i++) {
                        if (f.getName().startsWith(ignoreFilsNames[i])) {
                            isIgnoreFile = true;
                            break;
                        }
                    }
                } else {
                    isIgnoreFile = true;
                }
                if (!isIgnoreFile) {
                    Log.e(TAG, "delete file -->file.lastModified()=" + f.lastModified() + "TimeInMillis=" + TimeInMillis + ",file.getName()=" + f.getName());
                    f.delete();
                }
            }
            return;
        } else if (f.isDirectory()) {
            ff = f.listFiles();
            if (ff != null) {
                length = ff.length;
                int i = 0;
                while (length != 0) {
                    deleteFilesByTime(TimeInMillis, ff[i], haveIgnore, ignoreFilsNames);
                    i++;
                    length--;
                }
            }
        }
    }

    /**
     * 获得文件名
     *
     * @param filePath 文件路径
     * @return 文件名
     */
    public static String getFileName(String filePath) {
        int tmp = filePath.lastIndexOf(File.separatorChar);
        String fileName = filePath.substring(tmp + 1);
        return fileName;
    }

    /**
     * 写字节数组到文件，文件父目录如果不存在，会自动创建
     *
     * @param filePath
     * @param bytes
     * @return
     */
    public static boolean writeBytesToFile(String filePath, byte[] bytes) {
        return writeBytesToFile(filePath, bytes, false);
    }

    /**
     * 写字节数组到文件，文件父目录如果不存在，会自动创建
     *
     * @param filePath
     * @param bytes
     * @param isAppend
     * @return
     */
    public static boolean writeBytesToFile(String filePath, byte[] bytes, boolean isAppend) {
        if (TextUtils.isEmpty(filePath) || bytes == null) {
            return false;
        }
        File file = new File(filePath);

        boolean isWriteOk = false;
        byte[] buffer = null;
        int count = 0;
        ByteArrayInputStream bais = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            if (!file.exists()) {
                createNewFileAndParentDir(file);
            }
            if (file.exists()) {
                bos = new BufferedOutputStream(new FileOutputStream(file, isAppend), DEFAULT_BUFFER_SIZE);
                bais = new ByteArrayInputStream(bytes);
                bis = new BufferedInputStream(bais, DEFAULT_BUFFER_SIZE);
                buffer = new byte[DEFAULT_BUFFER_SIZE];
                int len = 0;
                while ((len = bis.read(buffer, 0, DEFAULT_BUFFER_SIZE)) != -1) {
                    bos.write(buffer, 0, len);
                    count += len;
                }
                bos.flush();
            }
            isWriteOk = bytes.length == count;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                    bos = null;
                }
                if (bis != null) {
                    bis.close();
                    bis = null;
                }
                if (bais != null) {
                    bais.close();
                    bais = null;
                }
                buffer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "writeByteArrayToFile.file = " + file + ", bytes.length = " + (bytes == null ? 0 : bytes.length) + ", isAppend = " + isAppend + ", isWriteOk = " + isWriteOk);
        return isWriteOk;
    }

    /**
     * 创建文件父目录
     *
     * @param file
     * @return 是否创建成功
     */
    public static boolean createParentDir(File file) {
        boolean isMkdirs = true;
        if (!file.exists()) {
            File dir = file.getParentFile();
            if (!dir.exists()) {
                isMkdirs = dir.mkdirs();
                Log.v(TAG, "createParentDir.dir = " + dir + ", isMkdirs = " + isMkdirs);
            }
        }
        return isMkdirs;
    }

    /**
     * 创建文件及其父目录
     *
     * @param file
     * @return
     */
    public static boolean createNewFileAndParentDir(File file) {
        boolean isCreateNewFileOk = true;
        isCreateNewFileOk = createParentDir(file);
        //创建父目录失败，直接返回false，不再创建子文件
        if (isCreateNewFileOk) {
            if (!file.exists()) {
                try {
                    isCreateNewFileOk = file.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                    isCreateNewFileOk = false;
                }
            }
        }
        Log.d(TAG, "createFileAndParentDir.file = " + file + ", isCreateNewFileOk = " + isCreateNewFileOk);
        return isCreateNewFileOk;
    }

}
