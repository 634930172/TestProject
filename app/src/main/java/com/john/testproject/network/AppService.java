package com.john.testproject.network;

import com.google.gson.JsonObject;
import com.john.testproject.entity.HttpResult;
import com.john.testproject.entity.LoginSub;
import com.john.testproject.entity.OauthTokenMo;
import com.john.testproject.network.intercepter.AcheInterceptor;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Author: ${John}
 * E-mail: 634930172@qq.com
 * Date: 2017/12/5 0005
 * <p/>
 * Description:
 */

public interface AppService {

    /**
     * 登录
     */
    @POST("user/login.htm")
    Observable<HttpResult<OauthTokenMo>> doLogin(@Body LoginSub sub);

    //    @GET("top250")
    //    Call<JsonObject> getTopMovie(@Query("start") int start, @Query("count") int count);

    /**
     * 更新tokenkey给后台供白骑士
     */
    @FormUrlEncoded
    @POST("/api/user/updateBlackBox.htm")
    Observable<HttpResult<JsonObject>> updateBlackBox(@Field("blackBox") String blackBox);

    /**
     * 首页检查版本
     */
    @GET("app/checkAppVersion.htm")
    Observable<HttpResult<JsonObject>> checkVersionState(@Query("channelCode") String channelCode);

    @Multipart
    @POST("allen_restful/upload/upload.php")
    Observable<HttpResult<JsonObject>> uploadPic(@Part List<MultipartBody.Part> part);

    /**
     * 带缓存
     * @return
     */
//    @Headers(AcheInterceptor.CACHE)
    @GET("https://www.mrallen.cn/test.php")
    Observable<HttpResult<String>> simpleGet();

    @Multipart
    @POST("allen_restful/upload/upload.php")
    Observable<HttpResult<JsonObject>> uploadPic(@PartMap Map<String, RequestBody> map);

    @FormUrlEncoded
    @POST("https://www.mrallen.cn/api/allen_restful/rcloud/group/queryGroupMemberInfo.php")
    Observable<HttpResult<JsonObject>> uploadPicsss(@Field("group_id") String groupId);


    @Streaming//文件下载需要的接口
    @GET
    Observable<ResponseBody> download(@Url String url);

    //多图片上传
    @Multipart
    @POST("https://www.mrallen.cn/api/allen_restful/upload/testMuchUploadImg.php")
    Observable<HttpResult<JsonObject>> uploadPics(@PartMap Map<String, RequestBody> map);

}