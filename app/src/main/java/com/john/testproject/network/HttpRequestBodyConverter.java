package com.john.testproject.network;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.john.testproject.utils.SerializedUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;


/**
 * Author: ${John}
 * E-mail: 634930172@qq.com
 * Date: 2017/12/6 0006
 * <p/>
 * Description:请求体处理
 */

public class HttpRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
    private static final Charset CHARSET_NAME = Charset.forName("UTF-8");

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    HttpRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public RequestBody convert(@NonNull T args) throws IOException {
        StringBuilder builder = new StringBuilder();
        // 反射获取属性值
        Field fields[] = args.getClass().getDeclaredFields();
        Field.setAccessible(fields, true);
        for (Field field : fields) {
            try {
                Object[] result = SerializedUtil.convertToRequestContent(args, field);
                if (null != result) {
                    String key   = result[0].toString();
                    String value = result[1].toString();
                    if (!TextUtils.isEmpty(value)) {
                        builder.append(key);
                        builder.append("=");
                        builder.append(URLEncoder.encode(value,"UTF-8"));
                        builder.append("&");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 删除最后一个 '&' 符号
        int    index = builder.length() - 1;
        byte[] bytes;
        if (index >= 0) {
            bytes = builder.deleteCharAt(index).toString().getBytes(CHARSET_NAME);
        } else {
            bytes = builder.toString().getBytes(CHARSET_NAME);
        }
        return RequestBody.create(MEDIA_TYPE, bytes);
    }

}
