package com.hpu.sencondhand.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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

import static com.hpu.sencondhand.http.api.CHANGEPHONE;
import static com.hpu.sencondhand.http.api.CHANGEPWD;


public class MySettingActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ed_fix)
    EditText mEdFix;
    @BindView(R.id.tx_phone)
    TextView mTxPhone;
    @BindView(R.id.tx_pwd)
    TextView mTxPwd;
    @BindView(R.id.tx_remind)
    TextView mTxREmind;
    @BindView(R.id.layout_fix)
    LinearLayout mLayoutFix;
    @BindView(R.id.btn_cancle)
    Button mBtnCancle;
    @BindView(R.id.btn_sure)
    Button mBtnSure;
    @BindView(R.id.btn_exit)
    Button mBtnExit;
    private SharedPreferences sp;
    private static final String TAG = "MySettingActivity";


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting);
        ButterKnife.bind(this);
        //导航返回点击
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.tx_pwd, R.id.tx_phone, R.id.btn_sure, R.id.btn_cancle,R.id.btn_exit})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tx_phone:
                mLayoutFix.setVisibility(View.VISIBLE);
                mTxREmind.setText("请输入修改后的手机号");
                break;
            case R.id.tx_pwd:
                mLayoutFix.setVisibility(View.VISIBLE);
                mTxREmind.setText("请输入修改后的密码");
                break;
            case R.id.btn_sure:
                if (mTxREmind.getText().toString().equals("请输入修改后的手机号")) {
                    //修改手机号
                    EditPhone();
                } else if (mTxREmind.getText().toString().equals("请输入修改后的密码")) {
                    //修改用户密码
                    EditPwd();
                }
                break;
            case R.id.btn_cancle:
                mLayoutFix.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn_exit:
                Intent intent=new Intent(MySettingActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }

    //修改电话号
    public void EditPhone() {
        sp = getSharedPreferences("info", MODE_PRIVATE);
        OkHttpUtils.postString()
                .url(CHANGEPHONE)
                .content(new Gson().toJson(new User(sp.getString("username", null), null,mEdFix.getText().toString())))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        Toast.makeText(MySettingActivity.this, "网络异常，请稍后再试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d(TAG, "onResponse: " + response);
                        if (response.equals("Message-001")) {
                            Toast.makeText(MySettingActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            mLayoutFix.setVisibility(View.INVISIBLE);

                        }
                    }
                });
    }

    //修改用户密码
    public void EditPwd() {
        sp = getSharedPreferences("info", MODE_PRIVATE);
        OkHttpUtils.postString()
                .url(CHANGEPWD)
                .content(new Gson().toJson(new User(sp.getString("username", null),mEdFix.getText().toString(), null)))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        Toast.makeText(MySettingActivity.this, "网络异常，请稍后再试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d(TAG, "onResponse: " + response);
                        if (response.equals("Message-001")) {
                            Toast.makeText(MySettingActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            mLayoutFix.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }
}
