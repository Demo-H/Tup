package com.android.dhunter.common.network;

import android.content.Context;

import com.android.dhunter.common.config.GlobalConfig;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by admin on 2017/3/9.
 */
@Singleton
public class DataManager {

    private HttpHelper httpHelper;

    private SharePreferenceHelper sharePreferenceHelper;

    private DataBaseHelper dataBaseHelper;

    private Context context;


    @Inject
    public DataManager( Context context ,HttpHelper httpHelper , SharePreferenceHelper sharePreferenceHelper
            , DataBaseHelper dataBaseHelper) {
        this.context = context.getApplicationContext();
        this.httpHelper = httpHelper;
        this.sharePreferenceHelper = sharePreferenceHelper;
        this.dataBaseHelper = dataBaseHelper;
    }

    public <S> S getService(Class<S> serviceClass){
        return httpHelper.getService(serviceClass);
    }

    public void setHeader() {
        httpHelper.setHeader();
    }

    public void saveSPData(String key , String value){
        sharePreferenceHelper.saveData(key , value);
    }

    public void saveSPMapData(Map<String,String> map){
        sharePreferenceHelper.saveData(map);
    }

    public void saveSPObjectData(String key, Object obj) {
        sharePreferenceHelper.saveObjectData(key, obj);
    }

    public void saveSPObjectData(String key, Object obj, String fileName) {
        sharePreferenceHelper.saveObjectData(key, obj, fileName);
    }

    public String getSPData(String key){
        return sharePreferenceHelper.getValue(key);
    }

    public Map<String ,String> getSPMapData(){
        return sharePreferenceHelper.readData();
    }

    public Object getSpObjectData(String key, Object defaultValue) {
        return sharePreferenceHelper.getObjectData(key, defaultValue);
    }

    public Object getSpObjectData(String key, Object defaultValue,String fileName) {
        return sharePreferenceHelper.getObjectData(key, defaultValue, fileName);
    }

    public void removeSpData(String key) {
        sharePreferenceHelper.remove(key);
    }

    public void deleteSPData(){
        sharePreferenceHelper.deletePreference();
        GlobalConfig.headers.clear();
    }

    public Context getContext() {
        return context;
    }

    /**
     * 添加公共头
     */
//    private HashMap<String, String> getHeader() {
//        HashMap<String, String> list = new HashMap<>();
//        String token =  getSPData(GlobalConfig.LOGIN_TOKEN);
//        String userId =  getSPData(GlobalConfig.KEY_DATA_USERID);
//        String employeeGroup =  getSPData(GlobalConfig.EMPLOYEE_GROUP);
//        list.put("x_request_platform", GlobalConfig.PLATFORM);
//        list.put("x_auth_token",token);
//        list.put("x_user_id",userId);
//        list.put("x_employee_group", employeeGroup);
//        return list;
//    }

}
