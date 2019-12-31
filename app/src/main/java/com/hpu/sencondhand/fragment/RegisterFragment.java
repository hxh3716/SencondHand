package com.hpu.sencondhand.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.hpu.sencondhand.R;
import com.hpu.sencondhand.bean.register;

import com.hpu.sencondhand.util.StringCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

import static com.hpu.sencondhand.http.api.Register;

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
        rootView = inflater.inflate(R.layout.tab_02, container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @OnClick(R.id.btn_reg)
    public void onclick(View v){
        switch (v.getId()){
            case R.id.btn_reg:
                reg();
                break;
        }
    }

    /**
     * 点击注册按钮后调用，访问接口
     */
    public void reg(){
        OkHttpUtils.postString()
                .url(Register)
                .content(new Gson().toJson(new register("12", "王老师","12345679876","物联网工程","123")))
              .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallBack() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.d(TAG, "onError: "+e);

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "onResponse: "+response);
                    }



                });
    }
}
