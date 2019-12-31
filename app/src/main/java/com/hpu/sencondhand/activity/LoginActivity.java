package com.hpu.sencondhand.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hpu.sencondhand.R;
import com.hpu.sencondhand.fragment.LoginFragment;
import com.hpu.sencondhand.fragment.RegisterFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.tx_login)
    TextView TvLogin;
    @BindView(R.id.tx_reg)
    TextView TvReg;
    @BindView(R.id.viewpaper)
    ViewPager viewPager;
    private List<Fragment> mList;
    private TextView[] titles;
    //手机偏移量
    private int offset = 0;
    //下划线图片宽度
    private int lineWidth;
    //当前选项卡位置
    private int current_index = 0;
    //选项卡总数
    private static final int TAB_COUNT = 2;
    private static final int TAB_0 = 0;
    private static final int TAB_1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initVPager();


    }



    /**
     * 初始化ViewPager并添加监听事件
     */
    private void initVPager() {
        mList = new ArrayList<>();
        mList.add(new LoginFragment());
        mList.add(new RegisterFragment());
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);
        titles = new TextView[]{TvLogin, TvReg};
        viewPager.setOffscreenPageLimit(titles.length);
        //重写页面切换的方法
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                current_index = position;
                if (current_index==0){
                    TvLogin.setTextColor(getResources().getColor(R.color.blue));
                    TvReg.setTextColor(getResources().getColor(R.color.balck));
                }else {
                    TvReg.setTextColor(getResources().getColor(R.color.blue));
                    TvLogin.setTextColor(getResources().getColor(R.color.balck));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * ViewPager适配器
     */
    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }



//注册和登录切换按钮的点击
    @OnClick({R.id.tx_login, R.id.tx_reg})
    public void onClick(View view) {
        switch (view.getId()) {
            //点击登录
            case R.id.tx_login:
                //防止页面重复 下同
                if (viewPager.getCurrentItem() != TAB_0) {
                    viewPager.setCurrentItem(TAB_0);
                }
                break;
            //点击注册
            case R.id.tx_reg:
                if (viewPager.getCurrentItem() != TAB_1) {
                    viewPager.setCurrentItem(TAB_1);
                }
                break;

        }

    }


}
