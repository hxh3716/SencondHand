package com.hpu.sencondhand.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.hpu.sencondhand.R;
import com.hpu.sencondhand.activity.MainActivity;
import com.hpu.sencondhand.bean.User;
import com.hpu.sencondhand.util.StringCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

import static android.content.Context.MODE_PRIVATE;

import static com.hpu.sencondhand.http.api.LOGIN;


/**
 * Detail:登录页面
 * on 2019/12/29
 */

public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    @BindView(R.id.image_eye)
    ImageView mImgEye;
    @BindView(R.id.ed_user)
    EditText mEdUser;
    @BindView(R.id.ed_pwd)
    EditText mEdPwd;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    private View rootView;
    private boolean isPwdVisible = false;
    private SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tab_01, container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    /**
    *现实和隐藏密码和按钮点击事件
     */
    @OnClick({R.id.image_eye,R.id.btn_login})
    public void onclick(View view){
        switch (view.getId()){
            case R.id.image_eye:
                isPwdVisible=!isPwdVisible;
                if (isPwdVisible){
                    mEdPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mImgEye.setImageResource(R.drawable.visable);
                }else {
                    mEdPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mImgEye.setImageResource(R.drawable.eye);
                }
                break;
            case R.id.btn_login:
               login();
                break;
        }
    }

    /**
     * 登录按钮点击时访问接口验证用户信息
     */
    public void  login(){
        if (mEdUser.getText().toString().equals("")){
            Toast.makeText(getContext(),"用户名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (mEdPwd.getText().toString().equals("")){
            Toast.makeText(getContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        OkHttpUtils.postString()
                .url(LOGIN)
                .content(new Gson().toJson(new User(mEdUser.getText().toString(), mEdPwd.getText().toString(), null)))
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
                            //登录成功，跳转界面
                            Intent intent=new Intent(getActivity(), MainActivity.class);
                           intent.putExtra("username",mEdUser.getText().toString());
                            startActivity(intent);
                            getActivity().finish();
                            //存储用户的登录信息
                            sp = getActivity().getSharedPreferences("info", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("username",mEdUser.getText().toString());
                            editor.putString("password",mEdPwd.getText().toString());
                            editor.commit();
                        }else if (response.equals("Message-003")){
                            Toast.makeText(getContext(),"用户名或密码错误",Toast.LENGTH_SHORT).show();
                        }else if (response.equals("Message-000")){
                            Toast.makeText(getContext(),"网络异常，请稍后再试",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
