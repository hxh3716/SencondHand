package com.hpu.sencondhand.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hpu.sencondhand.R;
import com.hpu.sencondhand.activity.MainActivity;
import com.hpu.sencondhand.util.StringCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.hpu.sencondhand.http.api.Login;

/**
 * Created by：何学慧
 * Detail:登录页面
 * on 2019/12/29
 */

public class LoginFragment extends Fragment {
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
              //  login();
                Intent intent=new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
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
        OkHttpUtils.post()
                .url(Login)
                .addParams("userid", mEdUser.getText().toString())
                .addParams("password",mEdPwd.getText().toString())
                .build()
                .execute(new StringCallBack() {

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });
    }
}
