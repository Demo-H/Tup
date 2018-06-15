package com.tupperware.huishengyi.network;

import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.android.dhunter.common.volley.toolbox.ImageLoader;
import com.android.dhunter.common.volley.toolbox.imagetool.ImageCacheManager;


/**
 * @author vector.huang
 * @version V1.0
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date 2015/6/29.
 */
public class TImageLoader {

    /**
     * 全部图片请求使用同一个请求队列完成
     */
    private static ImageLoader mImageLoader = ImageCacheManager.getInstance().getImageLoader();//new ImageLoader

    /**
     * 加载图片，如果传入的URL 无法解析就会忽略这次请求
     *
     * @param url       image url
     * @param iv        show ImageView
     * @param maxWidth  if maxWidth == 0 , get actual width
     * @param maxHeight if maxHeight == 0 , get actual height
     * @param loading   show resource image when loading，if loading == 0, not show
     * @param loadFail  show resource image when failed,if loadFail == 0, not show
     */
    public static void get(String url, ImageView iv, int maxWidth, int maxHeight, int loading, int loadFail) {

        if (url == null || url.isEmpty()) {
            return;
        }
         Log.d("url == ImageLoader  ", url);

        url = parseUrl(url);
        String host = Uri.parse(url).getHost();
        if (host == null || host.isEmpty()) {
            return;
        }

        ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv, loading, loadFail);
        mImageLoader.get(url, listener, maxWidth, maxHeight);
    }


    /**
     * 加载图片，如果传入的URL 无法解析就会忽略这次请求
     *
     * @param url       image url
     * @param maxWidth  if maxWidth == 0 , get actual width
     * @param maxHeight if maxHeight == 0 , get actual height
     */
    public static void get(String url, ImageLoader.ImageListener listener, int maxWidth, int maxHeight) {
        if (url == null || url.isEmpty()) {
            return;
        }
            Log.d("url == ImageLoader  ", url);

        url = parseUrl(url);
        String host = Uri.parse(url).getHost();
        if (host == null || host.isEmpty()) {
            return;
        }

        mImageLoader.get(url, listener, maxWidth, maxHeight);
    }

    private static String parseUrl(String url){
        String[] strings = url.split("://");
        if(strings[0].equalsIgnoreCase("http")){
            return url;
        }
        return ServerURL.URL_IMG + url;
    }
}
