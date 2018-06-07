package com.john.testproject.network.upload;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 生成上传文件所需的RequestBody与Map<String, RequestBody>
 */
public class RetrofitUpload {

    public static class Builder {

        Map<String, RequestBody> params;

        public Builder() {
            params = new LinkedHashMap<>();
        }

        public Builder addFile(String key, File file) {
            RequestBody requestBody = RequestBody
                    .create(MultipartBody.FORM, file);
            params.put(key + "\"; filename=\"" + file.getName(), requestBody);
            return this;
        }

        public Builder addString(String key, String value) {
            RequestBody requestBody = RequestBody
                    .create(MultipartBody.FORM, value);
            params.put(key, requestBody);
            return this;
        }

        public Builder addByte(String key, byte[] bytes) {
            RequestBody requestBody = RequestBody
                    .create(MultipartBody.FORM, bytes);
            long currentTime=System.currentTimeMillis();
            params.put(key + "\"; filename=\"" + currentTime+".jpg", requestBody);
            return this;
        }

        public Map<String, RequestBody> build() {
            return params;
        }


        public Builder addByte(String key, byte[] bytes,int size) {
            RequestBody requestBody = RequestBody
                    .create(MultipartBody.FORM, bytes);
            long currentTime=System.currentTimeMillis();
            params.put(key + "\"; filename=\"" + currentTime+size+".jpg", requestBody);
            return this;
        }


    }

}
