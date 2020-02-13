package com.hpu.sencondhand.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.hpu.sencondhand.R;
import com.hpu.sencondhand.bean.User;


import com.hpu.sencondhand.util.StringCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

import static com.hpu.sencondhand.http.api.REGISTER;


/**
 * Created by：何学慧
 * Detail:注册页面
 * on 2019/12/29
 */

public class RegisterFragment extends Fragment {
    private static final String TAG = "RegisterFragment";
    @BindView(R.id.ed_user)
    EditText mEdUser;
    @BindView(R.id.ed_pwd)
    EditText mEdPwd;
    @BindView(R.id.ed_tele)
    EditText mEdTele;
    @BindView(R.id.ed_major)
    EditText mEdMajor;
    @BindView(R.id.ed_pwdagain)
    EditText mEdPwdAgain;
    @BindView(R.id.btn_reg)
    Button mBtnReg;
    @BindView(R.id.ed_name)
    EditText mEdName;

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tab_02, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick(R.id.btn_reg)
    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.btn_reg:
                reg();
                break;
        }
    }

    /**
     * 点击注册按钮后调用，访问接口
     */
    public void reg() {
        OkHttpUtils.postString()
                .url(REGISTER)
                .content(new Gson().toJson(new User("李四", "123", "12345679876")))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallBack() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        Toast.makeText(getContext(),"网络异常，请稍后再试",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d(TAG, "onResponse: " + response);
                        if (response.equals("Message-001")){
                            Toast.makeText(getContext(),"注册成功！",Toast.LENGTH_SHORT).show();
                        }else if (response.equals("Message-002 ")){
                            Toast.makeText(getContext(),"用户已存在",Toast.LENGTH_SHORT).show();
                        }else if (response.equals("Message-000")){
                            Toast.makeText(getContext(),"网络异常，请稍后再试",Toast.LENGTH_SHORT).show();
                        }
                    }


                });
    }
}
