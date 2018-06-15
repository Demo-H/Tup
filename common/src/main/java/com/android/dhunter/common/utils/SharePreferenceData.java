package com.android.dhunter.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by dhunter on 2018/2/1.
 */

public class SharePreferenceData {

    private Context mContext;
    private static final String DEFAULT_DATA_CACHE="sp_tupperware_cache";
    public static final String BASE_APP_SETTING = "tupperware_setting";

    private String dataCachePoolName;
    private SharedPreferences sp;

    public SharePreferenceData(Context context) {
        this(DEFAULT_DATA_CACHE, context);
    }

    public SharePreferenceData(String poolName, Context context) {
        dataCachePoolName = poolName;
        mContext = context;
        sp = context.getSharedPreferences(poolName,
                Context.MODE_PRIVATE);
    }

    /**
     * add a key(String)-value(Serializable object) into SharedPreference
     * @param key
     * @param value
     * @return
     */
    public boolean setSerializable(String key, Serializable value) {
        boolean flag = false;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(value);
            String base64String = Base64.encodeToString(bos.toByteArray(),
                    Base64.DEFAULT);
            sp.edit().putString(key, base64String).apply();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 保存数据到sp的方法，根据value类型调用不同的保存方法,默认用的sp的文件名字为DEFAULT_DATA_CACHE
     * @param key
     * @param value
     */
    public boolean setParam(String key, Object value){
        HashMap<String ,Object> map = new HashMap<>();
        map.put(key ,value);
        return setParam(map);
    }

    public boolean setParam(HashMap<String ,Object> values){
        SharedPreferences.Editor editor = sp.edit();
        Set<String> keys = values.keySet();
        for(String tempKey :keys){

            Object value = values.get(tempKey);
            String type;
            if(value != null && value.getClass() != null) {
                type = value.getClass().getSimpleName();
            } else {
                type = "String";
            }

            if("String".equals(type)){
                editor.putString(tempKey, (String)value);
            }
            else if("Integer".equals(type)){
                editor.putInt(tempKey, (Integer)value);
            }
            else if("Boolean".equals(type)){
                editor.putBoolean(tempKey, (Boolean)value);
            }
            else if("Float".equals(type)){
                editor.putFloat(tempKey, (Float)value);
            }
            else if("Long".equals(type)){
                editor.putLong(tempKey, (Long)value);
            }
        }
        return editor.commit();
    }

    /**
     * get value(Serializable Object) from DataPool(SharedPreference) with the
     * given Key
     *
     * @param key key of this pair ,with the defalut key="temp"
     * @return one Serializable Object
     */
    public Serializable getSerializable(String key) {
//        if (!sp.contains(key))
//            return null;
        String base64String = sp.getString(key, "");
        byte[] buf = Base64.decode(base64String, Base64.DEFAULT);
        ByteArrayInputStream bis = new ByteArrayInputStream(buf);
        ObjectInputStream ois = null;
        Serializable result = null;
        try {
            ois = new ObjectInputStream(bis);
            result = (Serializable) ois.readObject();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return result;
    }

    /**
     * 根据defaultValue的类型返回相应的类型值,默认用的sp的文件名字为DEFAULT_FILE_NAME
     * @param key
     * @param defaultValue
     * @return
     */

    public Object getParam(String key, Object defaultValue){
        String type;
        if(defaultValue != null && defaultValue.getClass() != null) {
            type = defaultValue.getClass().getSimpleName();
        } else {
            type = "String";
        }

        if("String".equals(type)){
            return sp.getString(key, (String)defaultValue);
        }
        else if("Integer".equals(type)){
            return sp.getInt(key, (Integer)defaultValue);
        }
        else if("Boolean".equals(type)){
            return sp.getBoolean(key, (Boolean)defaultValue);
        }
        else if("Float".equals(type)){
            return sp.getFloat(key, (Float)defaultValue);
        }
        else if("Long".equals(type)){
            return sp.getLong(key, (Long)defaultValue);
        }

        return null;
    }

/*    public static boolean setDBParam(Context context, String key, Object value) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(key, value);
        return setDBParam(DEFAULT_DATA_CACHE, context, map);
    }

    public static boolean setDBParam(Context context, HashMap<String, Object> values) {
        return setDBParam(DEFAULT_DATA_CACHE, context, values);
    }

    public static boolean setDBParam(String fileName, Context context, String key, Object value) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(key, value);
        return setDBParam(fileName, context, map);
    }

    public static boolean setDBParam(String fileName, Context context, HashMap<String, Object> values) {
        if (context == null || fileName == null) {
            return false;
        }
        ContentResolver contentResolver = context.getContentResolver();
        Set<String> keys = values.keySet();
        for (String tempKey : keys) {
            Object value = values.get(tempKey);
            String type = value.getClass().getSimpleName();
            PreferenceTable.Item item = new PreferenceTable.Item(-1, fileName, tempKey, String.valueOf(value), type);
            PreferenceUtils.setItem(contentResolver, item);
        }
        return true;
    }

    public static Object getDBParam(Context context, String key, Object defaultValue) {
        return getDBParam(DEFAULT_DATA_CACHE, context, key, defaultValue);
    }

    public static Object getDBParam(String fileName, Context context, String key, Object defaultValue) {
        String type = "String";
        if (defaultValue != null) {
            type = defaultValue.getClass().getSimpleName();
        }
        return getDBParam(fileName, context, key, type, defaultValue);
    }

    private static Object getDBParam(String fileName, Context context, String key, String type, Object defaultValue) {
        if (context == null || fileName == null) {
            return defaultValue;
        }
        ContentResolver contentResolver = context.getContentResolver();
        PreferenceTable.Item item = new PreferenceTable.Item(-1, fileName, key, null, type);
        PreferenceTable.Item result = PreferenceUtils.getItem(contentResolver, item);
        if (result == null || TextUtils.isEmpty(result.getValue())) {
            return defaultValue;
        }
        if ("String".equals(type)) {
            return String.valueOf(result.getValue());
        } else if ("Integer".equals(type)) {
            return Integer.valueOf(result.getValue());
        } else if ("Boolean".equals(type)) {
            return Boolean.valueOf(result.getValue());
        } else if ("Float".equals(type)) {
            return Float.valueOf(result.getValue());
        } else if ("Long".equals(type)) {
            return Long.valueOf(result.getValue());
        }
        return defaultValue;
    }

    public static void removeAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(DEFAULT_DATA_CACHE, Context.MODE_PRIVATE);
        Map<String , ?> map = sp.getAll();
        for (String key : map.keySet()) {
            if(sp.contains(key)) {
                sp.edit().remove(key).apply();
            }
        }
    }*/

    public void remove(String key) {
        if (!sp.contains(key))
            return;
        sp.edit().remove(key).apply();
    }

    public void removeAll() {
        Map<String , ?> map = sp.getAll();
        for (String key : map.keySet()) {
            if(sp.contains(key)) {
                sp.edit().remove(key).apply();
            }
        }
    }

}
