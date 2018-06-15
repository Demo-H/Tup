package com.tupperware.huishengyi.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static com.android.dhunter.common.volley.toolbox.imagetool.DiskLruImageCache.calculateInSampleSize;

/**
 * Created by dhunter on 2018/3/15.
 */

public class BitmapUtils {

    /**
     * 安装bitmap 高宽比例缩放ImageView 的高度
     * @param bm
     * @param iv
     * @param ivWidth
     */
    public static void zoomImageViewWithBitmap(Bitmap bm, ImageView iv, int ivWidth) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算比例
        float scale = ((float) height) / width;

        int ivHeight = (int)(scale * ivWidth);

        iv.getLayoutParams().height = ivHeight;
    }


    /**
     * 保存图片
     * @param view
     * @param filePath
     */
    public static void saveBitmap(ImageView view, String filePath) {
        Drawable drawable = view.getDrawable();
        if (drawable == null) {
            return;
        }
        FileOutputStream outStream = null;
        File file = new File(filePath);
        if (file.isDirectory()) {// 如果是目录不允许保存
            return;
        }
        try {
            outStream = new FileOutputStream(file);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 适用于所有的view，可以将view里的全部内容绘制成bitmap并保存
     * @param view
     * @param filePath
     */

    public static void saveBitmap(View view, String filePath){

        // 创建对应大小的bitmap
        Bitmap  bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        //存储
        FileOutputStream outStream = null;
        File file=new File(filePath);
        if(file.isDirectory()){//如果是目录不允许保存
            return;
        }
        try {
            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bitmap.recycle();
                if(outStream!=null){
                    outStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 200, 380);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(InputStream is) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeStream(is, null, options);
    }

    /**
     *  服务器返回url，通过url去获取视频的第一帧
     *  Android 原生给我们提供了一个MediaMetadataRetriever类
     *  提供了获取url视频第一帧的方法,返回Bitmap对象
     *
     *  @param videoUrl
     *  @return
     */
    public static Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    /*
    *    get image from network
    *    @param [String]imageURL
    *    @return [BitMap]image
    */
    public static Bitmap getBitMap(String url){
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


}
