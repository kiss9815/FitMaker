package com.juntcompany.fitmaker.Manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.gson.Gson;
import com.juntcompany.fitmaker.Data.Curriculum;
import com.juntcompany.fitmaker.Data.CurationType;
import com.juntcompany.fitmaker.Data.Structure.FriendListResponse;
import com.juntcompany.fitmaker.Data.JoinRequest;
import com.juntcompany.fitmaker.Data.JoinResult;
import com.juntcompany.fitmaker.Data.ProjectRequest;
import com.juntcompany.fitmaker.Data.ProjectRequestResult;
import com.juntcompany.fitmaker.Data.ProjectResponse;
import com.juntcompany.fitmaker.Data.ProjectResponseResult;
import com.juntcompany.fitmaker.Data.Structure.FriendListResult;
import com.juntcompany.fitmaker.Data.Structure.FriendResponse;
import com.juntcompany.fitmaker.Data.Structure.FriendResult;
import com.juntcompany.fitmaker.Data.Structure.MyResponse;
import com.juntcompany.fitmaker.Data.Structure.MyResult;
import com.juntcompany.fitmaker.Data.Structure.RankingResult;
import com.juntcompany.fitmaker.Data.RecordRequest;
import com.juntcompany.fitmaker.Data.RecordResult;
import com.juntcompany.fitmaker.Data.Structure.BadgeResponse;
import com.juntcompany.fitmaker.Data.Structure.BadgeResult;
import com.juntcompany.fitmaker.Data.Structure.RankingResponse;
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
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

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

    private static final String URL_FORMAT = "https://ec2-52-79-78-37.ap-northeast-2.compute.amazonaws.com";
    private static final String POST_BODY_LOGIN_NAME = "name";
    private static final String POST_BODY_LOGIN_EMAIL = "email";
    private static final String POST_BODY_LOGIN_PASSWORD = "password";

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

      private static final String URL_FORMAT_RECOMMEND = "https://ec2-52-79-78-37.ap-northeast-2.compute.amazonaws.com/curriculum";

    //파라미터에 Context 가 꼭 있어야 함 없으면 백키를 누를때 액티비티가 없는데어서도 구동하려 함
    public Request getCurriculum(Context context, final OnResultListener<List<Curriculum>> listener)
            throws UnsupportedEncodingException {

        String url = String.format(URL_FORMAT_RECOMMEND);
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
    private static final String URL_FORMAT_PROJECT = "https://ec2-52-79-78-37.ap-northeast-2.compute.amazonaws.com/projects/%s";
    public Request getCourse(Context context, String projectNumber, final OnResultListener<ProjectResponseResult> listener)
            throws UnsupportedEncodingException {

        String url = String.format(URL_FORMAT_PROJECT, URLEncoder.encode(projectNumber, "utf-8"));
        final CallbackObject<ProjectResponseResult> callbackObject = new CallbackObject<ProjectResponseResult>();

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
                ProjectResponse project = gson.fromJson(response.body().string(), ProjectResponse.class);
                callbackObject.result = project.result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String URL_FORMAT_SIGN_UP= "https://ec2-52-79-78-37.ap-northeast-2.compute.amazonaws.com/users"; //post는 url에 요청하는거 안들어감

    public Request signUp(Context context, String name, String email, String password, final OnResultListener<JoinResult> listener)throws UnsupportedEncodingException {

        String url = String.format(URL_FORMAT_SIGN_UP);
        final CallbackObject<JoinResult> callbackObject = new CallbackObject<JoinResult>();
        RequestBody body = new FormBody.Builder()
                .add(POST_BODY_LOGIN_NAME,name)  // 1이 네임으로 들어가는 값
                .add(POST_BODY_LOGIN_EMAIL, email)
                .add(POST_BODY_LOGIN_PASSWORD, password)
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

    private static final String URL_FORMAT_LOGIN = "https://ec2-52-79-78-37.ap-northeast-2.compute.amazonaws.com/auth/login";//post는 url에 요청하는거 안들어감

    public Request login(Context context, String email, String password, final OnResultListener<JoinResult> listener)throws UnsupportedEncodingException {

        String url = String.format(URL_FORMAT_LOGIN);
        final CallbackObject<JoinResult> callbackObject = new CallbackObject<JoinResult>();
        RequestBody body = new FormBody.Builder()
                .add(POST_BODY_LOGIN_EMAIL, email)
                .add(POST_BODY_LOGIN_PASSWORD, password)
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

    private static final String URL_FORMAT_LOGIN_OUT = "https://ec2-52-79-78-37.ap-northeast-2.compute.amazonaws.com/auth/logout";

    public Request logout(Context context, final OnResultListener<JoinResult> listener)throws UnsupportedEncodingException {

        String url = String.format(URL_FORMAT_LOGIN_OUT);
        final CallbackObject<JoinResult> callbackObject = new CallbackObject<JoinResult>();
        RequestBody body = new FormBody.Builder()
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

    private static final String URL_FORMAT_SET_PROJECT = "https://ec2-52-79-78-37.ap-northeast-2.compute.amazonaws.com/projects";
    private static final String POST_BODY_CURRI_ID = "curri_id";

    public Request setProject(Context context, String curriculumId, final OnResultListener<ProjectRequestResult> listener)throws UnsupportedEncodingException {

        String url = String.format(URL_FORMAT_SET_PROJECT);
        final CallbackObject<ProjectRequestResult> callbackObject = new CallbackObject<ProjectRequestResult>();
        RequestBody body = new FormBody.Builder()
                .add(POST_BODY_CURRI_ID, curriculumId) // 여기 들어갈 String값은 서버에서 프로토콜 정의서에 요구한 그대로 넘겨야 함
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
                ProjectRequest project = gson.fromJson(response.body().string(), ProjectRequest.class);
                callbackObject.result = project.result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String URL_FORMAT_GET_PROJECT = "https://ec2-52-79-78-37.ap-northeast-2.compute.amazonaws.com/projects/%s";

    public Request getProject(Context context, String projectId, final OnResultListener<ProjectResponseResult> listener)throws UnsupportedEncodingException {

        String url = String.format(URL_FORMAT_GET_PROJECT, URLEncoder.encode(projectId, "utf-8"));
        final CallbackObject<ProjectResponseResult> callbackObject = new CallbackObject<ProjectResponseResult>();

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
                ProjectResponse project = gson.fromJson(response.body().string(), ProjectResponse.class);
                callbackObject.result = project.result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String URL_FORMAT_SET_RECORD = "https://ec2-52-79-78-37.ap-northeast-2.compute.amazonaws.com/records";
    private static final String POST_BODY_PROJECT_ID = "project_id";

    public Request setRecord(Context context, String projectId, final OnResultListener<RecordResult> listener)throws UnsupportedEncodingException {

        String url = String.format(URL_FORMAT_GET_PROJECT);
        final CallbackObject<RecordResult> callbackObject = new CallbackObject<RecordResult>();
        RequestBody body = new FormBody.Builder()
                .add(POST_BODY_PROJECT_ID, projectId)
                .build();

        Request request = new Request.Builder().url(url)
                .tag(context)
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
                RecordRequest project = gson.fromJson(response.body().string(), RecordRequest.class);
                callbackObject.result = project.result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String URL_FORMAT_GET_BADGE = "https://ec2-52-79-78-37.ap-northeast-2.compute.amazonaws.com/badges/%s";

    public Request getBadge(Context context, String badgeId, final OnResultListener<BadgeResult> listener)throws UnsupportedEncodingException {

        String url = String.format(URL_FORMAT_GET_BADGE, URLEncoder.encode(badgeId, "utf-8"));
        final CallbackObject<BadgeResult> callbackObject = new CallbackObject<BadgeResult>();

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
                BadgeResponse badge = gson.fromJson(response.body().string(), BadgeResponse.class);
                callbackObject.result = badge.result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String URL_FORMAT_GET_RANK = "https://ec2-52-79-78-37.ap-northeast-2.compute.amazonaws.com/ranking";

    public Request getRank(Context context, final OnResultListener<RankingResult> listener)throws UnsupportedEncodingException {

        String url = String.format(URL_FORMAT_GET_RANK);
        final CallbackObject<RankingResult> callbackObject = new CallbackObject<RankingResult>();

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
                RankingResponse rank = gson.fromJson(response.body().string(), RankingResponse.class);
                callbackObject.result = rank.result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String URL_FORMAT_GET_MY_PROFILE = "https://ec2-52-79-78-37.ap-northeast-2.compute.amazonaws.com/users/me";

    public Request getMyProfile(Context context, final OnResultListener<MyResult> listener)throws UnsupportedEncodingException {

        String url = String.format(URL_FORMAT_GET_MY_PROFILE);
        final CallbackObject<MyResult> callbackObject = new CallbackObject<MyResult>();

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
                MyResponse user = gson.fromJson(response.body().string(), MyResponse.class);
                callbackObject.result = user.result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String URL_FORMAT_GET_FRIEND = "https://ec2-52-79-78-37.ap-northeast-2.compute.amazonaws.com/friends/%s";

    public Request getFriend(Context context, String friendId, final OnResultListener<FriendResult> listener)throws UnsupportedEncodingException {

        String url = String.format(URL_FORMAT_GET_FRIEND, URLEncoder.encode(friendId, "utf-8"));
        final CallbackObject<FriendResult> callbackObject = new CallbackObject<FriendResult>();

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
                FriendResponse friend = gson.fromJson(response.body().string(), FriendResponse.class);
                callbackObject.result = friend.result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String URL_FORMAT_GET_FRIEND_LIST = "https://ec2-52-79-78-37.ap-northeast-2.compute.amazonaws.com/friends";

    public Request getFriendList(Context context, final OnResultListener<FriendListResult> listener)throws UnsupportedEncodingException {

        String url = String.format(URL_FORMAT_GET_FRIEND_LIST);
        final CallbackObject<FriendListResult> callbackObject = new CallbackObject<FriendListResult>();

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
                FriendListResponse friendList = gson.fromJson(response.body().string(), FriendListResponse.class);
                callbackObject.result = friendList.result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

}
