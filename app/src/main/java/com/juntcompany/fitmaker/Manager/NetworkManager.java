package com.juntcompany.fitmaker.Manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.juntcompany.fitmaker.Data.Course;
import com.juntcompany.fitmaker.Data.Curriculum;
import com.juntcompany.fitmaker.Data.CurationType;
import com.juntcompany.fitmaker.Data.Friend;
import com.juntcompany.fitmaker.FitMaker;
import com.juntcompany.fitmaker.util.PersistentCookieStore;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by EOM on 2016-02-22.
 */
public class NetworkManager {
    private static NetworkManager instance;
    public static NetworkManager getInstance(){
        if(instance == null){
            instance = new NetworkManager();
        }
        return instance;
    }
    OkHttpClient mClient;
    private static final int MAX_CACHE_SIZE = 10 * 1024 * 1024;
    private  NetworkManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Context context = FitMaker.getContext();
        File cachefile = new File(context.getExternalCacheDir(), "mycache");
        if (!cachefile.exists()) {
            cachefile.mkdirs();
        }
        Cache cache = new Cache(cachefile, MAX_CACHE_SIZE);
        builder.cache(cache);

        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL);
        builder.cookieJar(new JavaNetCookieJar(cookieManager));

//        disableCertificateValidation(builder);

        mClient = builder.build();
    }

    static void disableCertificateValidation(OkHttpClient.Builder builder) {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }};

        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            builder.sslSocketFactory(sc.getSocketFactory());
            builder.hostnameVerifier(hv);
        } catch (Exception e) {
        }
    }

    public void cancelAll() {
        mClient.dispatcher().cancelAll();
    }

    public interface OnResultListener<T> {
        public void onSuccess(Request request, T result);
        public void onFailure(Request request, int code, Throwable cause);
    }

    private static final int MESSAGE_SUCCESS = 0;
    private static final int MESSAGE_FAILURE = 1;

    static class NetworkHandler extends Handler {
        public NetworkHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CallbackObject object = (CallbackObject)msg.obj;
            Request request = object.request;
            OnResultListener listener = object.listener;
            switch (msg.what) {
                case MESSAGE_SUCCESS :
                    listener.onSuccess(request, object.result);
                    break;
                case MESSAGE_FAILURE :
                    listener.onFailure(request, -1, object.exception);
                    break;
            }
        }
    }

    static class CallbackObject<T> {
        Request request;
        T result;
        IOException exception;
        OnResultListener<T> listener;
    }

    public void cancelAll(Object tag) {

    }

    Handler mHandler = new Handler(Looper.getMainLooper());

    private static final String URL_FORMAT = "https://openapi.naver.com/v1/search/movie.xml?target=movie&query=%s&start=%s&display=%s";


//    public Request getCurriculum(Context context, int   final OnResultListener<List<Curriculum>> listener) {
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                List<Curriculum> list = new ArrayList<Curriculum>();
//                for (int i = 0; i < 3; i++) {
//                    Curriculum c = new Curriculum();
//                    // c...
//                    list.add(c);
//                }
//                listener.onSuccess(list);
//            }
//        }, 1000);
//    }

//    public Request getFriend(int id, String friend_name, String friend_image, int friend_exercise_hour, final OnResultListener<List<Friend>> listener){
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                List<Friend> list = new ArrayList<Friend>();
//                Random r = new Random();
//                for(int i=0; i <5; i ++){
//                    Friend f = new Friend();
//                    f.user_name = "name : " + i;
//                    f.user_exercise_hour = r.nextInt(50);
//                    list.add(f);
//                }
//                listener.onSuccess();
//            }
//        },1000);
//    }

    public void getCurationType(int type_id, String type_name, String type_picture, String type_info, OnResultListener<CurationType> listener ){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CurationType t = new CurationType();
//                t.type_picture = ....;
                t.type_name = "발레리나 타입";
                t.type_info = "당신은 유연하고 아름다운 선을 바라시는 타입이군요! 그런 당신에게 이 운동을 추천해 드립니다.";
            }
        }, 1000);
    }

//    public void getCourse(int )

//    public void login(Context context,String userId, String password,final OnResultListener<String> listener){
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                listener.onSuccess("success");
//            }
//        },1000);
//    }
//
//    public void signUp(Context context, String userId, String userName, String password, final OnResultListener<String> listener){
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                listener.onSuccess("success");
//            }
//        },1000);
//    }

    public Course getCourse(){

        Course course = new Course();
        for(int i = 0; i<4 ; i++) {
            course.course_name = "fff : " + i;
        }
        return course;
    }
}
