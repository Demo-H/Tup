package com.tupperware.huishengyi.model;

/**
 * Created by dhunter on 2018/4/20.
 */

public class HomePageModel{

//    private HttpHelper httpHelper;
//
//    private SharePreferenceHelper sharePreferenceHelper;
//
//    private DataBaseHelper dataBaseHelper;
//
//    private Context context;
//
//    public HomePageModel( Context context ,HttpHelper httpHelper , SharePreferenceHelper sharePreferenceHelper
//            , DataBaseHelper dataBaseHelper) {
//        this.context = context;
//        this.httpHelper = httpHelper;
//        this.sharePreferenceHelper = sharePreferenceHelper;
//        this.dataBaseHelper = dataBaseHelper;
//    }
//
//    public Disposable getMainData(int start , int count , DisposableObserver<ResponseBody> consumer){
//        Map<String,Object> map = new HashMap<>(2);
//        map.put("start",start);
//        map.put("count",count);
//        return changeIOToMainThread(httpHelper.getService(BaseApiService.class).executeGet("http://www.baidu.com",map),consumer);
//
//    }
//
//    private Disposable changeIOToMainThread(Observable<ResponseBody> observable , DisposableObserver<ResponseBody> consumer ){
//        return observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(consumer);
//    }
//
//    public void saveSPData(String key , String value){
//        sharePreferenceHelper.saveData(key , value);
//    }
//
//    public void saveSPMapData(Map<String,String> map){
//        sharePreferenceHelper.saveData(map);
//    }
//
//    public String getSPData(String key){
//        return sharePreferenceHelper.getValue(key);
//    }
//
//    public void deleteSPData(){
//        sharePreferenceHelper.deletePreference();
//    }
//
//    public Map<String ,String> getSPMapData(){
//        return sharePreferenceHelper.readData();
//    }
//
////    public List<String> getTypeOfNameData(){
////        ArrayList<String> list = new ArrayList<>(20);
////        for (int i = 0; i < 20; i++) {
////            list.add("家用电器");
////        }
////        return list;
////    }
//
//    public<S> Disposable getData(DisposableObserver<S> consumer , final Class<S> clazz , final String fillName) {
//        return Observable.create(new ObservableOnSubscribe<S>() {
//            @Override
//            public void subscribe(ObservableEmitter<S> e) throws Exception {
//                InputStream is = context.getAssets().open(fillName);
//                String text = FillUtil.readTextFromFile(is);
//                Gson gson = new Gson();
//                e.onNext(gson.fromJson(text, clazz));
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(consumer);
//    }

//    public void loadData(final OnLoadDataListListener listener, boolean isUseCache, String date) {
//        HttpData.getInstance().getHomeInfo(new Observer<ToutiaoBean>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                listener.onFailure(e);
//            }
//
//            @Override
//            public void onNext(ToutiaoBean toutiaoResults) {
//                listener.onSuccess(toutiaoResults);
//
//            }
//        }, isUseCache, date);
//    }

}
