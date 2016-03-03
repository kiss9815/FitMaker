package com.juntcompany.fitmaker.Manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.gson.Gson;
import com.juntcompany.fitmaker.Data.Course;
import com.juntcompany.fitmaker.Data.Curriculum;
import com.juntcompany.fitmaker.Data.CurationType;
import com.juntcompany.fitmaker.Data.Friend;
import com.juntcompany.fitmaker.Data.JoinRequest;
import com.juntcompany.fitmaker.Data.JoinResult;
import com.juntcompany.fitmaker.Data.TypeCurriculumResponse;
import com.juntcompany.fitmaker.Data.TypeCurriculumResult;
import com.juntcompany.fitmaker.FitMaker;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.util.PersistentCookieStore;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by EOM on 2016-02-22.
 */
public class NetworkManager {
    private static NetworkManager instance;

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    OkHttpClient mClient;
    private static final int MAX_CACHE_SIZE = 10 * 1024 * 1024;

    private NetworkManager() {
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

        disableCertificateValidation(context, builder);

        mClient = builder.build();
    }

    static void disableCertificateValidation(Context context, OkHttpClient.Builder builder) {

        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = context.getResources().openRawResource(R.raw.site);
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, tmf.getTrustManagers(), null);
            HostnameVerifier hv = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            sc.init(null, tmf.getTrustManagers(), null);
            builder.sslSocketFactory(sc.getSocketFactory());
            builder.hostnameVerifier(hv);
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
            CallbackObject object = (CallbackObject) msg.obj;
            Request request = object.request;
            OnResultListener listener = object.listener;
            switch (msg.what) {
                case MESSAGE_SUCCESS:
                    listener.onSuccess(request, object.result);
                    break;
                case MESSAGE_FAILURE:
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

    Handler mHandler = new NetworkHandler(Looper.getMainLooper());

    private static final String URL_FORMAT_RECOMMEND = "https://ec2-52-79-78-37.ap-northeast-2.compute.amazonaws.com/curriculum?q1=%s&q2=%s&q3=%s";

    //파라미터에 Context 가 꼭 있어야 함 없으면 백키를 누를때 액티비티가 없는데어서도 구동하려 함
    public Request getCurriculum(Context context, String q1, String q2, String q3, final OnResultListener<List<Curriculum>> listener)
            throws UnsupportedEncodingException {

        String url = String.format(URL_FORMAT_RECOMMEND, URLEncoder.encode(q1, "utf-8"), URLEncoder.encode(q1, "utf-8"), URLEncoder.encode(q1, "utf-8"));
        final CallbackObject<List<Curriculum>> callbackObject = new CallbackObject<List<Curriculum>>();

        Request request = new Request.Builder().url(url)
                .tag(context)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                TypeCurriculumResponse curriculum = gson.fromJson(response.body().string(), TypeCurriculumResponse.class);
                callbackObject.result = curriculum.result.curriculums;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;

    }

    private static final String URL_FORMAT_CURATION_QUESTION = "https://ec2-52-79-78-37.ap-northeast-2.compute.amazonaws.com/curriculum?q1=%s&q2=%s&q3=%s";

    //파라미터에 Context 가 꼭 있어야 함 없으면 백키를 누를때 액티비티가 없는데어서도 구동하려 함
    public Request getCuration(Context context, String q1, String q2, String q3, final OnResultListener<CurationType> listener) throws UnsupportedEncodingException {

        String url = String.format(URL_FORMAT_CURATION_QUESTION, URLEncoder.encode(q1, "utf-8"), URLEncoder.encode(q1, "utf-8"), URLEncoder.encode(q1, "utf-8"));
        final CallbackObject<CurationType> callbackObject = new CallbackObject<CurationType>();

        Request request = new Request.Builder().url(url)
                .tag(context)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                TypeCurriculumResponse curriculum = gson.fromJson(response.body().string(), TypeCurriculumResponse.class);
                callbackObject.result = curriculum.result.exctype;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;

    }

    public Request getCurriculumCuration(Context context, String q1, String q2, String q3, final OnResultListener<TypeCurriculumResult> listener)
            throws UnsupportedEncodingException {

        String url = String.format(URL_FORMAT_CURATION_QUESTION, URLEncoder.encode(q1, "utf-8"), URLEncoder.encode(q1, "utf-8"), URLEncoder.encode(q1, "utf-8"));
        final CallbackObject<TypeCurriculumResult> callbackObject = new CallbackObject<TypeCurriculumResult>();

        Request request = new Request.Builder().url(url)
                .tag(context)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                TypeCurriculumResponse curriculum = gson.fromJson(response.body().string(), TypeCurriculumResponse.class);
                callbackObject.result = curriculum.result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;

    }

    private static final String URL_FORMAT_SIGN_UP= "https://ec2-52-79-78-37.ap-northeast-2.compute.amazonaws.com/users";


    public Request signUp(Context context, final OnResultListener<JoinResult> listener)throws UnsupportedEncodingException {

        String url = String.format(URL_FORMAT_SIGN_UP);
        final CallbackObject<JoinResult> callbackObject = new CallbackObject<JoinResult>();
        RequestBody body = new FormBody.Builder()
                .add("name","1")  // 1이 네임으로 들어가는 값
                .add("email", "2")
                .add("password","3")
                .build();
        Request request = new Request.Builder().url(url)
                .post(body)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                JoinRequest join = gson.fromJson(response.body().string(), JoinRequest.class);
                callbackObject.result = join.result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

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

//    public void getCurationType(int type_id, String type_name, String type_picture, String type_info, OnResultListener<CurationType> listener ){
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                CurationType t = new CurationType();
////                t.type_picture = ....;
//                t.type_name = "발레리나 타입";
//                t.type_info = "당신은 유연하고 아름다운 선을 바라시는 타입이군요! 그런 당신에게 이 운동을 추천해 드립니다.";
//            }
//        }, 1000);
//    }

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
