package com.hpu.sencondhand.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hpu.sencondhand.R;
import com.hpu.sencondhand.activity.CategoryActivity;

import com.hpu.sencondhand.activity.MySettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by：何学慧
 * Detail:我的界面fragment
 * on 2019/12/31
 */

public class MeFragment extends Fragment {
    private View rootView;
    @BindView(R.id.tx_myrelease)
    TextView mTxRelease;
    @BindView(R.id.tx_settings)
    TextView mTxSetting;
    @BindView(R.id.tx_favorite)
    TextView mTxFavorite;
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @OnClick({R.id.tx_settings, R.id.tx_myrelease, R.id.tx_favorite})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.tx_settings:
                intent=new Intent(getContext(), MySettingActivity.class);
                startActivity(intent);
                break;
            case R.id.tx_myrelease:
                intent=new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("category","我的发布");
                startActivity(intent);
                break;
            case R.id.tx_favorite:
                intent=new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("category","我的收藏");
                startActivity(intent);
                break;

        }
    }
}
