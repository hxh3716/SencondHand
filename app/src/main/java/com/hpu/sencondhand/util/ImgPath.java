package com.hpu.sencondhand.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Detail:存储图片和图片路径的获取
 * on 2020/2/11
 */

public class ImgPath {
    //保存图片
    public static void savaImage(String filename,Context context, Bitmap bmp) {
        try {
            //创建应用本地文件路径
            String path =context.getExternalFilesDir(null).getAbsolutePath();
            File dirFile = new File(path);
            if (!dirFile.exists()) {              //如果不存在，那就建立这个文件夹
                dirFile.mkdirs();
            }
            File file = new File(path, filename+".jpg");
           FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            Log.d("1111111111111", "savaImage: "+file.getPath() );
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //通过路径获得图片
    public static Uri getImage(String filename,Context context){
        PackageManager pm = context.getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED )==
                pm.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE","com.hpu.sencondhand");
        if (!permission){
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1
            );
            return  null;

        }else {
            FileInputStream fs = null;
            try {
                fs = new FileInputStream(context.getExternalFilesDir(null).getAbsolutePath()+"/"+filename+".jpg");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap= BitmapFactory.decodeStream(fs);
            Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(),bitmap , null,null));
            return imageUri;
        }

    }
}
