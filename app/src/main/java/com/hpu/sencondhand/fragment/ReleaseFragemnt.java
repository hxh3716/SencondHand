package com.hpu.sencondhand.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.hpu.sencondhand.R;
import com.hpu.sencondhand.bean.Product;
import com.hpu.sencondhand.bean.User;
import com.hpu.sencondhand.util.ImgPath;
import com.hpu.sencondhand.util.StringCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.hpu.sencondhand.http.api.ADDPRO;


/**
 * Created by：何学慧
 * Detail:发布闲置界面
 * on 2019/12/31
 */

public class ReleaseFragemnt extends Fragment {
    private static final String TAG = "ReleaseFragemnt";

    private View rootView;
    @BindView(R.id.img_add)
    ImageView mImgAdd;
    @BindView(R.id.ed_title)
    EditText mEdTitle;
    @BindView(R.id.ed_category)
    Spinner mSpCategory;
    @BindView(R.id.ed_price)
    EditText mEdprice;
    @BindView(R.id.ed_detail)
    EditText mEddetail;
    @BindView(R.id.btn_release)
    Button mBtnRelease;
    @BindView(R.id.ed_phone)
    EditText mEdPhone;
    private SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_release, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @OnClick({R.id.btn_release, R.id.img_add})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.img_add:
                //从手机相册选择图片
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 111);
                break;
            case R.id.btn_release:
                //提交商品信息数据到后台
                subInfo();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            try {
                Uri uri = data.getData();
                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().
                        openInputStream(uri));//将imageUri对象的图片加载到内存
                mImgAdd.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void subInfo() {
        //发布时信息填写不完整不能继续执行
        if (((BitmapDrawable) mImgAdd.getDrawable()).getBitmap() == null || mEdTitle.getText().toString().equals("") || mEdprice.getText().toString().equals("") || mEdPhone.getText().toString().equals("") || mEddetail.getText().toString().equals("")) {
            Toast.makeText(getContext(), "信息填写不完整", Toast.LENGTH_SHORT).show();
            return;
        }
        //获取当前时间作为图片名称
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
        String filename = format.format(new Date());
        sp = getActivity().getSharedPreferences("info", MODE_PRIVATE);
        OkHttpUtils.postString()
                .url(ADDPRO)
                .content(new Gson().toJson(new Product(sp.getString("username", null), filename, mEdTitle.getText().toString(), mSpCategory.getSelectedItem().toString(), mEdprice.getText().toString(), mEdPhone.getText().toString(), mEddetail.getText().toString())))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        Toast.makeText(getContext(), "网络异常，请稍后再试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d(TAG, "onResponse: " + response);
                        if (response.equals("Message-001")) {
                            Toast.makeText(getContext(), "上传成功", Toast.LENGTH_SHORT).show();
                            //上传成功后保存图片到本地
                            Bitmap bitmap = ((BitmapDrawable) mImgAdd.getDrawable()).getBitmap();
                            ImgPath.savaImage(filename, getContext(), bitmap);
                        }
                    }
                });
    }
}
