package com.android.dhunter.common.volley.toolbox.imagetool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.android.dhunter.common.volley.toolbox.ImageLoader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by dhunter on 2018/3/1.
 */

public class DiskLruImageCache implements ImageLoader.ImageCache {

    private DiskLruCache mDiskCache;
    private CompressFormat mCompressFormat = CompressFormat.JPEG;
    private static int IO_BUFFER_SIZE = 8 * 1024;
    private int mCompressQuality = 70;
    private static final int APP_VERSION = 1;
    private static final int VALUE_COUNT = 1;

    public DiskLruImageCache(Context context, String uniqueName, int diskCacheSize,
                             CompressFormat compressFormat, int quality) {
        try {
            final File diskCacheDir = getDiskCacheDir(context, uniqueName);
            if (!diskCacheDir.exists()) {
                diskCacheDir.mkdirs();
            }

            mDiskCache = DiskLruCache.open(diskCacheDir, APP_VERSION, VALUE_COUNT, diskCacheSize);
            mCompressFormat = compressFormat;
            mCompressQuality = quality;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean writeBitmapToFile(Bitmap bitmap, DiskLruCache.Editor editor)
            throws IOException, FileNotFoundException {
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(editor.newOutputStream(0), IO_BUFFER_SIZE); //8k
            return bitmap.compress(mCompressFormat, mCompressQuality, out);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private File getDiskCacheDir(Context context, String uniqueName) {

//        final String cachePath = context.getCacheDir().getPath();
        String cachePath;
        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    || !Environment.isExternalStorageRemovable()) {
                cachePath = context.getExternalCacheDir().getPath();
            } else {
                cachePath = context.getCacheDir().getPath();
            }
        } catch (Exception e) {
            cachePath = context.getCacheDir().getPath();
        }



        return new File(cachePath + File.separator + uniqueName);
    }

    @Override
    public void putBitmap(String key, Bitmap data) {
        DiskLruCache.Editor editor = null;
        try {
            String keyNew = hashKeyForDisk(key);
            editor = mDiskCache.edit(keyNew);
            if (editor == null) {
                return;
            }

            if (writeBitmapToFile(data, editor)) {
                mDiskCache.flush();
                editor.commit();
//                if ( BuildConfig.DEBUG ) {
//                   BambooLog.d("cache_test_DISK_", "image put on disk cache " + keyNew);
//                }
            } else {
                editor.abort();
//                if ( BuildConfig.DEBUG ) {
//                    BambooLog.d( "cache_test_DISK_", "ERROR on: image put on disk cache " + keyNew );
//                }
            }
        } catch (IllegalArgumentException ie) {
//            if ( BuildConfig.DEBUG ) {
//                BambooLog.d( "cache_test_DISK_keys", "keys must match regex " + key );
//            }
        } catch (IOException e) {
//            if ( BuildConfig.DEBUG ) {
//                BambooLog.d( "cache_test_DISK_", "ERROR on: image put on disk cache " + key );
//            }
            try {
                if (editor != null) {
                    editor.abort();
                }
            } catch (IOException ignored) {
            }
        }

    }

    @Override
    public Bitmap getBitmap(String key) {
        Bitmap bitmap = null;
        DiskLruCache.Snapshot snapshot = null;
        int maxMemory = 0;
        try {
            String keyNew = hashKeyForDisk(key);
            snapshot = mDiskCache.get(keyNew);
            if (snapshot == null) {
                return null;
            }
            final InputStream in = snapshot.getInputStream(0);
            if (in != null) {
                final BufferedInputStream buffIn =
                        new BufferedInputStream(in, IO_BUFFER_SIZE);

                maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

                BitmapFactory.Options options = new BitmapFactory.Options();
//                加上这些就出问题了
//                options.inJustDecodeBounds = true;
//                BitmapFactory.decodeStream(buffIn, null, options);//.decodeResource(getResources(), R.store_employee_id.myimage, options);
//                int imageHeight = options.outHeight;
//                int imageWidth = options.outWidth;
//                String imageType = options.outMimeType;
//
//                options.inSampleSize = calculateInSampleSize(options, MainApplication.ScreenWidth, MainApplication.ScreenHeight);
                options.inJustDecodeBounds = false;

                bitmap = BitmapFactory.decodeStream(buffIn, null, options);//BitmapFactory.decodeStream( buffIn );

            }
        } catch (IllegalArgumentException ie) {
//            if ( BuildConfig.DEBUG ) {
//                BambooLog.d( "cache_test_DISK_keys", "keys must match regex " + key );
//            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (snapshot != null) {
                snapshot.close();
            }
        }


//        if ( BuildConfig.DEBUG ) {
//            BambooLog.d( "cache_test_DISK_", bitmap == null ? "" : "image read from disk " + key);
//        }
        return bitmap;

    }

    /**
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public boolean containsKey(String key) {

        boolean contained = false;
        DiskLruCache.Snapshot snapshot = null;
        try {
            snapshot = mDiskCache.get(key);
            contained = snapshot != null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (snapshot != null) {
                snapshot.close();
            }
        }

        return contained;

    }

    public void clearCache() {
//        if ( BuildConfig.DEBUG ) {
//            BambooLog.d( "cache_test_DISK_", "disk cache CLEARED");
//        }
        try {
            mDiskCache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getCacheFolder() {
        return mDiskCache.getDirectory();
    }

    /**
     * MD5 code
     *
     * @param key
     * @return
     */
    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
