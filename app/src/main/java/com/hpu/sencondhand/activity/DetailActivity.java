package com.hpu.sencondhand.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.gson.Gson;
import com.hpu.sencondhand.R;
import com.hpu.sencondhand.bean.User;
import com.hpu.sencondhand.util.ImgPath;
import com.hpu.sencondhand.util.StringCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

import static com.hpu.sencondhand.http.api.REGISTER;
import static com.hpu.sencondhand.http.api.SETFAVORITE;


public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    @BindView(R.id.tx_title)
    TextView mTxTitle;
    @BindView(R.id.tx_detail)
    TextView mTxDetail;
    @BindView(R.id.tx_price)
    TextView mTxPrice;
    @BindView(R.id.tx_phone)
    TextView mTxPhone;
    @BindView(R.id.img_phone)
    ImageView mImgPhone;
    @BindView(R.id.img_favorite)
    ImageView mImgFavorite;
    @BindView(R.id.img_show)
    ImageView mImgShow;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Intent intent;
    private String imgpath;
    private SharedPreferences sp;
    private String id;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        intent=getIntent();
        mTxTitle.setText(intent.getStringExtra("title"));
        mTxDetail.setText(intent.getStringExtra("detail"));
        mTxPrice.setText(intent.getStringExtra("price"));
        mTxPhone.setText(intent.getStringExtra("phone"));
        imgpath=intent.getStringExtra("imgpath");
            mImgShow.setImageURI(ImgPath.getImage(imgpath,DetailActivity.this));
        id=intent.getStringExtra("id");
    }
    @OnClick({R.id.img_phone,R.id.img_favorite})
    public void onclick(View v){
        if (v.getId()==R.id.img_phone){
            Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +mTxPhone.getText().toString() ));//跳转到拨号界面，同时传递电话号码
            startActivity(dialIntent);
        }else if (v.getId()==R.id.img_favorite){
            //收藏商品
            favorite();
        }
    }

    /**
     * 点击收藏商品
     */
    public void favorite() {
        sp = getSharedPreferences("info", MODE_PRIVATE);
        OkHttpUtils.post()
                .url(SETFAVORITE)
                .addParams("username",sp.getString("username",null))
                .addParams("productid", id)
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        Toast.makeText(DetailActivity.this,"网络异常，请稍后再试",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Log.d(TAG, "onResponse: " + response);
                        if (response.equals("Message-001")){
                            Toast.makeText(DetailActivity.this,"收藏成功！",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}
