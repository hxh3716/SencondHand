package com.hpu.sencondhand.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.hpu.sencondhand.R;
import com.hpu.sencondhand.fragment.HomeFragment;
import com.hpu.sencondhand.fragment.MeFragment;
import com.hpu.sencondhand.fragment.ReleaseFragemnt;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.container)
    FrameLayout mContainer;
    @BindView(R.id.bottombar)
    BottomBar bottomBar;
    private HomeFragment homeFragment;
    private ReleaseFragemnt releaseFragemnt;
    private MeFragment meFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        homeFragment=new HomeFragment();
        releaseFragemnt=new ReleaseFragemnt();
        meFragment=new MeFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container,homeFragment);
        fragmentTransaction.commit();
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId){
                    case R.id.tab_home:
                        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container,homeFragment).commit();
                        break;
                    case R.id.tab_release:
                        FragmentTransaction fragmentTransaction1=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction1.replace(R.id.container,releaseFragemnt).commit();
                        break;
                    case R.id.tab_me:
                        FragmentTransaction fragmentTransaction2=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction2.replace(R.id.container,meFragment).commit();
                        break;
                }
            }
        });

    }
}
