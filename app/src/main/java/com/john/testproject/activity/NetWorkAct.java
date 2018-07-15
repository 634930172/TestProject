package com.john.testproject.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.john.testproject.R;
import com.john.testproject.entity.HttpResult;
import com.john.testproject.entity.LoginSub;
import com.john.testproject.entity.OauthTokenMo;
import com.john.testproject.network.AppService;
import com.john.testproject.network.HttpClient;
import com.john.testproject.network.RxRequestCallBack;
import com.john.testproject.network.download.DownLoadHttpClient;
import com.john.testproject.network.download.FileCallBack;
import com.john.testproject.network.download.FileLoadEvent;
import com.john.testproject.network.download.FileSubscriber;
import com.john.testproject.network.upload.RetrofitUpload;
import com.john.testproject.utils.BitmapUtil;
import com.john.testproject.utils.ContextHolder;
import com.john.testproject.utils.L;
import com.john.testproject.utils.SharedInfo;



import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author: ${John}
 * E-mail: 634930172@qq.com
 * Date: 2017/11/10 0010
 * <p/>
 * Description:网络请求类
 */

public class NetWorkAct extends BaseAct {

    private TextView ttv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network_layout);
        ttv = (TextView) findViewById(R.id.ttv);
        OauthTokenMo mo = SharedInfo.getInstance().getEntity(OauthTokenMo.class);
        if (mo != null) {
            Log.e("TAG", "onCreatetoken>>>>>>>>>>>>>>>>>" + mo.getToken());
        } else {
            Log.e("TAG", "onCreatetoken>>>>>>>>>>>>>>>>>>null");
        }
    }

    /**
     * 退出登录
     *
     * @param view
     */
    public void test1(View view) {
        SharedInfo.getInstance().remove(OauthTokenMo.class);
        OauthTokenMo mo = SharedInfo.getInstance().getEntity(OauthTokenMo.class);
        if (mo != null) {
            Log.e("TAG", "token: " + mo.getToken());
        } else {
            Log.e("TAG", "token:==null");
        }

    }

    /**
     * 登录
     *
     * @param view
     */
    public void test2(View view) {
        LoginSub sub = new LoginSub("15889565826", "a123456", "asdasdasd");
        HttpClient.getService(AppService.class).doLogin(sub).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxRequestCallBack<OauthTokenMo>() {
                    @Override
                    public void onSuccess(HttpResult<OauthTokenMo> httpResult) {
                        Log.e("TAG", "onSuccess: code--" + httpResult.getCode() + "msg--" + httpResult.getMsg());
                        OauthTokenMo mo = httpResult.getData();
                        mo.setUsername("15889565826");
                        SharedInfo.getInstance().saveEntity(mo);
                        OauthTokenMo mo2 = SharedInfo.getInstance().getEntity(OauthTokenMo.class);
                        Log.e("TAG", "token: " + mo2.getToken());
                    }
                });
    }

    public void test3(View view) {
        HttpClient.getService(AppService.class).updateBlackBox("aaaaaaaaaaaaaaaa").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxRequestCallBack<JsonObject>() {
                    @Override
                    public void onSuccess(HttpResult<JsonObject> httpResult) {
                        Log.e("TAG", "onSuccess: >>>>>>>>>>>>>>>>>");
                    }
                });
    }

    public void test4(View view) {
        HttpClient.getService(AppService.class).checkVersionState("TESTAPP").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxRequestCallBack<JsonObject>() {
                    @Override
                    public void onSuccess(HttpResult<JsonObject> httpResult) {
                        Log.e("TAG", "onSuccess: >>>>>>>>>>>>>>>>>" + httpResult.getData());
                    }
                });
    }

    public void test5(View view) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        //压缩图片操作-------------------------------------------------------------------------

        byte[] bytes = BitmapUtil.bitmapToBytes(bitmap);//拿到数组
        RequestBody imageStram = RequestBody.create(MultipartBody.FORM, bytes);//流转化成requestbody
        //        RequestBody imageFile = RequestBody.create(MediaType.parse("multipart/form-data"),new File(""));//文件的形式上传
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("upload", "test.jpg", imageStram);

        List<MultipartBody.Part> parts = builder.build().parts();

        HttpClient.getService(AppService.class).uploadPic(parts).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxRequestCallBack<JsonObject>() {
                    @Override
                    public void onSuccess(HttpResult<JsonObject> httpResult) {
                        Log.e("TAG", "onSuccess: >>>>>>>>>>>>>>>>>" + httpResult.getData());
                    }
                });
    }

    public void test6(View view) {
        HttpClient.getService(AppService.class).simpleGet().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxRequestCallBack<String>() {
                    @Override
                    public void onSuccess(HttpResult<String> httpResult) {
                        Log.e("TAG", "onSuccess: >>>>>>>>>>>>>>>>>" + httpResult.getData());
                    }
                });
    }

    public void test7(View view) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        byte[] bytes = BitmapUtil.bitmapToBytes(bitmap);//拿到数组
        RetrofitUpload.Builder builder = new RetrofitUpload.Builder().addByte("upload", bytes);

        HttpClient.getService(AppService.class).uploadPic(builder.build()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxRequestCallBack<JsonObject>(this) {
                    @Override
                    public void onSuccess(HttpResult<JsonObject> httpResult) {
                        Log.e("TAG", "onSuccess: >>>>>>>>>>>>>>>>>" + httpResult.getData());
                    }
                });
    }

    public void test8(View view) {
        HttpClient.getService(AppService.class).uploadPicsss("298cea3dabeb1545004451982d6c04f6").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxRequestCallBack<JsonObject>(this, "正在上传东东...") {
                    @Override
                    public void onSuccess(HttpResult<JsonObject> httpResult) {
                        Log.e("TAG", "onSuccess: >>>>>>>>>>>>>>>>>" + httpResult.getData());
                    }
                });
    }

    //文件下载需要的配置
    public void test9(View view) {
        String fileName = "app.apk";
        //        String fileStoreDir = Environment.getExternalStorageDirectory().getAbsolutePath()+"/testProject";//放到外部存储的共有目录，应用被删除了此文件也不会被删除，
        // 而且最好在共有目录下再建一个新的目录，因为最外层的目录是所有应用共享的
        File externalFilesDir = ContextHolder.getContext().getExternalFilesDir(null);//放到外部存储的私有目录，应用删除后此文件也会被删除，位置在files目录下面
        File fileDir = ContextHolder.getContext().getFilesDir();//内部文件存储，应用删除，文件也会被删除

        Log.e("TAG", "load: " + externalFilesDir.toString() + "  fileDir:" + fileDir.toString());
        final FileCallBack<ResponseBody> callBack = new FileCallBack<ResponseBody>(externalFilesDir.toString(), fileName) {

            @Override
            public void onSuccess(ResponseBody responseBody) {
                L.e("文件下载成功");
            }

            @Override
            public void progress(long progress) {
                L.e("progress: " + progress / 1024 + "kb  total: " + FileLoadEvent.getInstance().getTotal()/1024+"kb");
            }

            @Override
            public void onStart() {
                L.e("onStart");
            }

            @Override
            public void onCompleted() {
                L.e("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                //TODO: 对异常的一些处理
                e.printStackTrace();
            }
        };
        String url = "http://download.fir.im/v2/app/install/5818acbcca87a836f50014af?download_token=a01301d7f6f8f4957643c3fcfe5ba6ff";

        DownLoadHttpClient.getService(AppService.class).download(url)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(Schedulers.io()) //指定线程保存文件
                .doOnNext(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody body) {
                        callBack.saveFile(body);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) //在主线程中更新ui
                .subscribe(new FileSubscriber<ResponseBody>(callBack));
    }


    public void test10(View view) {
        String url = "http://download.fir.im/v2/app/install/5818acbcca87a836f50014af?download_token=a01301d7f6f8f4957643c3fcfe5ba6ff";

        HttpClient.getService(AppService.class).download(url)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(Schedulers.io()) //指定线程保存文件
                .observeOn(AndroidSchedulers.mainThread()) //在主线程中更新ui
                //                .subscribe(new Action1<ResponseBody>() {
                //                    @Override
                //                    public void call(ResponseBody responseBody) {
                //                        try {
                //                            InputStream is = responseBody.byteStream();
                //                            //获取文件总长度
                //                            long totalLength = is.available();
                //                            //                            String fileName = "app.apk";
                //                            //                            String fileStoreDir = Environment.getExternalStorageDirectory().getAbsolutePath();
                //
                //                            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                //                            File file = new File(path, "app.apk");
                //                            FileOutputStream fos = new FileOutputStream(file);
                //                            BufferedInputStream bis = new BufferedInputStream(is);
                //                            byte[] buffer = new byte[1024];
                //                            int len;
                //                            while ((len = bis.read(buffer)) != -1) {
                //                                fos.write(buffer, 0, len);
                //                                //此处进行更新操作
                //                                //len即可理解为已下载的字节数
                //                                //onLoading(len, totalLength);
                //                                L.e("len: "+len/1024+"kb  totalLength: "+totalLength/1024+"kb");
                //                            }
                //                            fos.flush();
                //                            fos.close();
                //                            bis.close();
                //                            is.close();
                //                            //此处就代表更新结束
                //                        } catch (IOException e) {
                //                            e.printStackTrace();
                //                        }
                //                    }
                //
                //                });
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        L.e("下载完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.e("下载出错");
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            InputStream is = responseBody.byteStream();
                            //获取文件总长度
                            long totalLength = is.available();
                            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                            File file = new File(path, "app.apk");
                            FileOutputStream fos = new FileOutputStream(file);
                            BufferedInputStream bis = new BufferedInputStream(is);
                            byte[] buffer = new byte[1024];
                            int len;
                            while ((len = bis.read(buffer)) != -1) {
                                fos.write(buffer, 0, len);
                                //此处进行更新操作
                                //len即可理解为已下载的字节数
                                //onLoading(len, totalLength);
                                L.e("len: " + len / 1024 + "kb  totalLength: " + totalLength / 1024 + "kb");
                            }
                            fos.flush();
                            fos.close();
                            bis.close();
                            is.close();
                            //此处就代表更新结束
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    //多图片上传
    public void test11(View view) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        byte[] bytes = BitmapUtil.bitmapToBytes(bitmap);//拿到数组

        RetrofitUpload.Builder builder = new RetrofitUpload.Builder();
        //多张图片
        for (int i = 0; i < 3; i++) {
            builder.addByte("image[]", bytes, i);
        }
        HttpClient.getService(AppService.class).uploadPics(builder.build()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxRequestCallBack<JsonObject>(this) {
                    @Override
                    public void onSuccess(HttpResult<JsonObject> httpResult) {
                        Log.e("TAG", "onSuccess: >>>>>>>>>>>>>>>>>" + httpResult.getData());
                    }
                });
    }

    //定制下载
    public void test12(View view){

    }

}
