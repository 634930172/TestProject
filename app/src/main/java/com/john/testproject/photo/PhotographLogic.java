package com.john.testproject.photo;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;


import com.john.testproject.R;
import com.john.testproject.utils.BitmapUtil;
import com.john.testproject.utils.ContextHolder;
import com.john.testproject.utils.FileSizeUtil;
import com.john.testproject.utils.FileUtil;
import com.john.testproject.utils.StorageUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/2/8 9:35
 * <p/>
 * Description: 拍照逻辑处理类
 */
public class PhotographLogic {
    // 相册
    private static final int REQUEST_CODE_PICK = 0;
    // 拍照
    private static final int REQUEST_CODE_TAKE = 1;
    // 剪裁
    private static final int REQUEST_CODE_CUTTING = 2;
    // 照片存储路径
    private static String FILE_PATH = StorageUtils.getIndividualCacheDirectory(ContextHolder.getContext(),"Photo_Cache").getAbsolutePath() ;
    // 本次操作需要保存的照片名
    private static String IMAGE_FILE_NAME;
    private static boolean IS_CUT = true;

    /**
     * 获取照片
     *
     * @param view      view
     * @param photoName 照片名称
     */
    public static void obtain(View view, String photoName, boolean isCut) {
        if (TextUtils.isEmpty(photoName)) {
            return;
        }
        check();
        IMAGE_FILE_NAME = photoName;
        IS_CUT = isCut;
        switch (view.getId()) {
            case R.id.firstBtn:
                Intent photoIntent = new Intent();
                photoIntent.setAction(Intent.ACTION_PICK);
                photoIntent.setType("image/*");
                (getActivity(view)).startActivityForResult(photoIntent, REQUEST_CODE_PICK);
                break;
            case R.id.secondBtn:
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(FILE_PATH, IMAGE_FILE_NAME)));
                    (getActivity(view)).startActivityForResult(cameraIntent, REQUEST_CODE_TAKE);
                }
                break;
        }

    }

    /**
     * onActivityResult
     */
    public static void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data, PhotographCallback callback) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                // 相册
                case REQUEST_CODE_PICK:
                    // 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
                    if (null != data) {
                        if (!IS_CUT) {
                            if (!TextUtils.isEmpty(IMAGE_FILE_NAME)) {
                                callback.obtainBitmap(decodeUriAsBitmap(activity, data.getData()), IMAGE_FILE_NAME);
                            }
                        } else {
                            startPhotoZoom(activity, data.getData());
                        }
                    }
                    break;

                // 拍照
                case REQUEST_CODE_TAKE:
                    File file = new File(FILE_PATH + File.separator + IMAGE_FILE_NAME);
                    if (!IS_CUT) {
                        if (!TextUtils.isEmpty(IMAGE_FILE_NAME)) {
                            Uri uri = Uri.fromFile(file);
                            callback.obtainBitmap(decodeUriAsBitmap(activity, uri), IMAGE_FILE_NAME);
                        }
                    } else {
                        startPhotoZoom(activity, Uri.fromFile(file));
                    }
                    break;

                // 剪裁
                case REQUEST_CODE_CUTTING:
                    if (!TextUtils.isEmpty(IMAGE_FILE_NAME)) {
                        Uri uri = Uri.fromFile(new File(FILE_PATH + File.separator + IMAGE_FILE_NAME));
                        callback.obtainCroppedFile(decodeUriAsFile(activity, uri), IMAGE_FILE_NAME);
                    }
                    break;
            }
        }
    }

    /**
     * 剪裁头像
     */
    private static void startPhotoZoom(Activity activity, Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 512);
        intent.putExtra("aspectY", 512);
        // outputX outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 512);
        intent.putExtra("outputY", 512);
        // 文件输出格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 是否保留比例  拉伸 解决黑边问题
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        // 是否返回数据
        intent.putExtra("return-data", false);
        // 关闭人脸检测
        intent.putExtra("noFaceDetection", true);
        // 保存为文件
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(FILE_PATH + File.separator + IMAGE_FILE_NAME)));
        activity.startActivityForResult(intent, REQUEST_CODE_CUTTING);
    }

    /**
     * Uri 转 File
     */
    private static File decodeUriAsFile(Activity activity, Uri uri) {
        Bitmap bitmap = decodeUriAsBitmap(activity, uri);
        if (null == bitmap) {
            return null;
        }
        return FileUtil.saveFile(activity, FILE_PATH, IMAGE_FILE_NAME, bitmap, FileSizeUtil.SIZE_TYPE_MB, 2.0);
    }

    /**
     * Uri 转 Bitmap
     */
    private static Bitmap decodeUriAsBitmap(Activity activity, Uri uri) {
        try {
            return BitmapUtil.compressBitmapFormUri(activity, uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检验目录是否存在
     */
    private static void check() {
        File dir = new File(FILE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }


    public interface PhotographCallback {
        void obtainCroppedFile(File file, String photoName);

        void obtainBitmap(Bitmap bitmap, String photoName);
    }

    /**
     * 通过view暴力获取getContext()(Android不支持view.getContext()了)
     *
     * @param view 要获取context的view
     * @return 返回一个activity
     */
    private static Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return (Activity) view.getRootView().getContext();
    }
}
