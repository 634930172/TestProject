package com.john.testproject.network;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.john.testproject.constans.BaseParmas;
import com.john.testproject.entity.OauthTokenMo;
import com.john.testproject.utils.MDUtil;
import com.john.testproject.utils.SharedInfo;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Author: ${John}
 * E-mail: 634930172@qq.com
 * Date: 2017/12/6 0006
 * <p/>
 * Description:OkHttp拦截操作，添加默认的参数，完整url的打印等
 */

public class HttpIntercepter implements Interceptor {

    private static final String TAG = "HttpIntercepter";
    //数据格式
    private static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private static final String APPLICATION_FORM_URL = "application/x-www-form-urlencoded;charset=UTF-8";

    /**
     * header 参数Map
     */
    private Map<String, String> headerParamsMap = new HashMap<>();
    /**
     * body 参数Map
     */
    private Map<String, String> bodyParamsMap = new HashMap<>();
    /**
     * url 参数Map
     */
    private Map<String, String> urlParamsMap = new HashMap<>();
    /**
     * 通用参数
     */
    private TreeMap<String, String> commonParamsTreeMap = new TreeMap<>();
    private Headers.Builder headerBuilder;
    private Request.Builder requestBuilder;

    @Override
    public synchronized Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        bodyParamsMap.clear();
        bodyParamsMap.put(ResponseParams.MOBILE_TYPE, "2");
        bodyParamsMap.put(ResponseParams.VERSION_NUMBER, "1.0.9");
        commonParamsTreeMap.clear();
        commonParamsTreeMap.put(ResponseParams.MOBILE_TYPE, "2");
        commonParamsTreeMap.put(ResponseParams.VERSION_NUMBER, "1.0.9");

        switch (request.method()) {
            case "GET":
                requestBuilder = request.newBuilder();
                headerBuilder = request.headers().newBuilder();
                headerParamsMap = signParams(splitGetUrl(request.url().toString(), headerParamsMap));
                request = delGetRequest(request);
                break;
            case "POST":
                requestBuilder = request.newBuilder();
                headerBuilder = request.headers().newBuilder();
                headerParamsMap = signParams(splitPostUrl(bodyToString(request.body()), headerParamsMap));
                Log.e(TAG, "headerParamsMap.size: " + headerParamsMap.size());
                request = delPostRequest(request);
                break;
        }
        Log.e(TAG, "intercept:url " + request.url().toString());
        return chain.proceed(request);
    }

    /**
     * 处理post请求
     *
     * @param request
     */
    private Request delPostRequest(Request request) {
        RequestBody body = request.body();
        MediaType contentType = body != null ? body.contentType() : null;
        Log.e(TAG, "delPostRequest: contentType：" + contentType);
        if (body != null && contentType != null && contentType.toString().contains(MULTIPART_FORM_DATA)) {
            request = uploadPost(request);
        } else {
            request = normalPost(request);
        }
        return request;
    }

    /**
     * 一般POST
     *
     * @param request
     * @return
     */
    private Request normalPost(Request request) {
        if (headerParamsMap.size() > 0) {
            for (Map.Entry entry : headerParamsMap.entrySet()) {
                headerBuilder.add((String) entry.getKey(), (String) entry.getValue());
            }
        }
        requestBuilder.headers(headerBuilder.build());// 签名的头部已经确定 里面有sign和token
        //对body进行排序
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        // add new params to new formBodyBuilder
        if (bodyParamsMap.size() > 0) {//添加公共参数到formbody
            for (Map.Entry entry : bodyParamsMap.entrySet()) {
                formBodyBuilder.add((String) entry.getKey(), (String) entry.getValue());
            }
        }
        // add old params to new formBodyBuilder
        FormBody formBody = formBodyBuilder.build();
        String postBodyString = bodyToString(request.body());
        Log.e(TAG, "postBodyString1: " + postBodyString);
        postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(formBody);
        Log.e(TAG, "postBodyString2: " + postBodyString);
        postBodyString = dynamicParams(postBodyString);
        Log.e(TAG, "postBodyString3: " + postBodyString);
        requestBuilder.post(RequestBody.create(formBody.contentType(), postBodyString));
        request = requestBuilder.build();
        return request;
    }

    /**
     * 上传文件的 POST
     *
     * @param request
     * @return
     */
    private Request uploadPost(Request request) {
        return request;
    }

    /**
     * 处理get请求
     *
     * @param request
     */
    private Request delGetRequest(Request request) {
        if (headerParamsMap.size() > 0) {
            for (Map.Entry entry : headerParamsMap.entrySet()) {
                headerBuilder.add((String) entry.getKey(), (String) entry.getValue());
            }
        }
        requestBuilder.headers(headerBuilder.build());// 签名的头部已经确定 里面有sign和token
        injectParamsIntoUrl(request, requestBuilder, dynamicParams(new TreeMap<>(bodyParamsMap)));
        request = requestBuilder.build();
        return request;
    }

    // func to inject params into url
    private void injectParamsIntoUrl(Request request, Request.Builder requestBuilder, Map<String, String> paramsMap) {
        HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
        if (paramsMap.size() > 0) {
            for (Map.Entry entry : paramsMap.entrySet()) {
                httpUrlBuilder.addQueryParameter((String) entry.getKey(), (String) entry.getValue());
                Log.e(TAG, "QueryParameter>>" + entry.getKey() + ":" + entry.getValue());
            }
        }
        requestBuilder.url(httpUrlBuilder.build());
    }

    /**
     * 将RequestBody转换成对应的字符串
     */
    private String bodyToString(final RequestBody request) {
        try {
            final Buffer buffer = new Buffer();
            if (request != null) {
                request.writeTo(buffer);
                return buffer.readUtf8();
            } else {
                return "";
            }
        } catch (final IOException e) {
            return "";
        }
    }


    /**
     * 分割请求参数，放入treeMap中,拼接动态参数
     *
     * @param postBodyString 请求参数
     */
    private TreeMap<String, String> splitPostString(String postBodyString) {
        TreeMap<String, String> map = new TreeMap<>();
        for (String s : postBodyString.split("&")) {
            String[] keyValue = s.split("=");
            map.put(keyValue[0], keyValue.length > 1 ? keyValue[1] : "");
        }
        return map;
    }


    /**
     * 动态拼接请求参数
     */
    public TreeMap<String, String> dynamicParams(TreeMap<String, String> map) {
        String token = getToken();
        String userId = getUserId();
        if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(userId)) {
            map.put("token", token);
            map.put("userId", userId);
        }
        return map;
    }


    /**
     * 将map拼装成请求字符串
     *
     * @return 返回请求参数
     */
    public String getPostParamsStr(TreeMap map) {
        Iterator it = map.entrySet().iterator();
        StringBuilder sb = new StringBuilder();
        try {
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                sb.append(entry.getKey()).append("=").append(entry.getValue().toString()).append("&");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sb.toString().length() > 1) {
            return sb.toString().substring(0, sb.length() - 1);
        } else {
            return sb.toString();
        }
    }

    /**
     * 对字符串进行签名
     */
    public String dynamicParams(String postBodyString) {
        TreeMap<String, String> treeMap = splitPostString(postBodyString);
        treeMap = dynamicParams(treeMap);
        //String sign = getSign(treeMap);
        //treeMap.put(Constant.SIGNA, sign);
        return getPostParamsStr(treeMap);
    }

    /**
     * 对Map数据进行签名
     */
    public Map signParams(Map<String, String> treeMap) {
        TreeMap map = new TreeMap();
        map.putAll(commonParamsTreeMap);
        map.putAll(treeMap);
        map.remove(BaseParmas.SIGNA);
        map.remove(BaseParmas.TOKEN);
        map = dynamicParams(map);
        for (Object o : map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            Object key = entry.getKey();
            Object value = entry.getValue();
            Log.e(TAG, "key=" + key + " value=" + value);
        }
        String sign = getSign(map);
        Log.e(TAG, "sign>>>>>>" + sign);
        TreeMap headMap = new TreeMap();
        headMap.put(BaseParmas.SIGNA, sign);
        headMap.put(BaseParmas.TOKEN, getToken());
        return headMap;
    }

    private Map<String, String> splitPostUrl(String url, Map<String, String> bodyParamsMap) {
        Map<String, String> signMap = new HashMap<>();
        signMap.putAll(bodyParamsMap);
        if (url.length() > 0) {
            String[] params = url.split("&");
            Map<String, String> temp;
            for (String param : params) {
                temp = new HashMap<>();
                String[] s = param.split("=");
                if (s.length > 1) {
                    temp.put(s[0], s[1]);
                } else {
                    temp.put(s[0], "");
                }
                signMap.putAll(temp);
            }
        }
        return signMap;
    }


    /**
     * 一般接口调用-signa签名生成规则
     *
     * @param map 有序请求参数map
     */
    private String getSign(TreeMap map) {
        String signa = "";
        try {
            Iterator it = map.entrySet().iterator();
            StringBuilder sb = new StringBuilder();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                if (entry.getValue() instanceof File)
                    continue;//URLEncoder.encode(, "UTF-8")
                sb.append(entry.getKey()).append("=").append(URLDecoder.decode(entry.getValue().toString(), "UTF-8")).append("|");
            }
            // 所有请求参数排序后的字符串后进行MD5（32）
            //signa = MDUtil.encode(MDUtil.TYPE.MD5, sb.toString());
            // 得到的MD5串拼接appsecret再次MD5，所得结果转大写
            String sign = "";
            if (sb.toString().length() > 1) {
                sign = sb.toString().substring(0, sb.length() - 1);
            } else {
                sign = sb.toString();
            }
            System.out.println("sb.toString()" + BaseParmas.APP_KEY + getToken() + sign);
            signa = MDUtil.encode(MDUtil.TYPE.MD5, BaseParmas.APP_KEY + getToken() + sign).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signa;
    }

    /**
     * 获取oauthToken
     */
    public String getToken() {
        OauthTokenMo mo = SharedInfo.getInstance().getEntity(OauthTokenMo.class);
        if (mo != null) {
            return mo.getToken();
        }
        return "";
    }

    /**
     * 获取userId
     */
    public String getUserId() {
        OauthTokenMo mo = SharedInfo.getInstance().getEntity(OauthTokenMo.class);
        if (mo != null) {
            return mo.getUserId();
        }
        return "";
    }

    private Map splitGetUrl(String url, Map bodyParamsMap) {
        Map signMap = new HashMap();
        signMap.putAll(bodyParamsMap);
        if (url.split("\\?").length > 1) {
            String paramStr = url.split("\\?")[1];
            String[] params = paramStr.split("&");
            Map<String, String> temp;
            for (int i = 0; i < params.length; i++) {
                temp = new HashMap<>();
                String[] s = params[i].split("=");
                if (s.length > 1) {
                    temp.put(s[0], s[1]);
                } else {
                    temp.put(s[0], "");
                }
                signMap.putAll(temp);
            }
        }
        return signMap;
    }
}
