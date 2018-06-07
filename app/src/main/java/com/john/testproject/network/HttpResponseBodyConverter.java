package com.john.testproject.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.john.testproject.entity.HttpResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.ResponseBody;
import retrofit2.Converter;


/**
 * Author: ${John}
 * E-mail: 634930172@qq.com
 * Date: 2017/12/6 0006
 * <p/>
 * Description:对最外层作解析
 */

public class HttpResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    HttpResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string().trim();
        try {
            JSONObject object = new JSONObject(response);
            System.out.println(object.toString());
            // 解析 resCode ,对不成功的返回做统一处理
            Log.e("TAG", "HttpResponseBodyConverter: code--"+object.getInt(ResponseParams.RES_CODE)+"--msg:"+object.getString(ResponseParams.RES_MSG));
            HttpResult httpResult = gson.fromJson(response, HttpResult.class);
            if (object.optInt(ResponseParams.RES_CODE) != ResponseParams.RES_SUCCEED && object.optInt(ResponseParams.RES_CODE) != ResponseParams.PWD_ERROR) {
                throw new ApiException(httpResult);
            }
            Log.e("TAG", "HttpResponseBodyConverter: >>>>>>>>>>>>");
            StringReader reader     = new StringReader(response);
            JsonReader jsonReader = gson.newJsonReader(reader);
            return adapter.read(jsonReader);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("TAG", "convert: 解析抛出异常" );
            return null;
        }finally {
            value.close();
        }
    }
}
