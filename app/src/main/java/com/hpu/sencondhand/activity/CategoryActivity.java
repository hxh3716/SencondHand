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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.hpu.sencondhand.R;
import com.hpu.sencondhand.bean.Product;
import com.hpu.sencondhand.fragment.HomeFragment;
import com.hpu.sencondhand.util.StringCallBack;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostStringBuilder;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.hpu.sencondhand.http.api.CATEGORY;
import static com.hpu.sencondhand.http.api.GETALLPRODUCT;
import static com.hpu.sencondhand.http.api.GETFAVORITE;
import static com.hpu.sencondhand.http.api.GETMYPRODUCT;


public class CategoryActivity extends AppCompatActivity {
    @BindView(R.id.listview)
    ListView mListView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tx_toobal)
    TextView mTxTooBal;

    private String URL;
    private Intent intent;
    private Mydoadapter mydoadapter;
    List<Product> messageInfoList = new ArrayList<>();
    private static final String TAG = "CategoryActivity";
    private SharedPreferences sp;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        //获取页面的值传递
        intent = getIntent();
        mTxTooBal.setText(intent.getStringExtra("category"));
        //导航返回点击
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //   Getinfo();
        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //列表点击后，跳转至详情页面
                Intent intent = new Intent(CategoryActivity.this, DetailActivity.class);
                intent.putExtra("title", messageInfoList.get(position).getTitle());
                intent.putExtra("detail", messageInfoList.get(position).getDetail());
                intent.putExtra("price", messageInfoList.get(position).getPrie());
                intent.putExtra("phone", messageInfoList.get(position).getContactDetail());
                intent.putExtra("imgpath", messageInfoList.get(position).getImgPath());
                startActivity(intent);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    class Mydoadapter extends BaseAdapter {
        private List<Product> datainfo;

        public Mydoadapter(List<Product> data) {
            this.datainfo = data;
        }

        @Override
        public int getCount() {
            return datainfo.size();
        }

        @Override
        public Object getItem(int position) {
            return datainfo.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            viewholder holder = null;
            if (convertView == null) {
                holder = new viewholder();
                convertView = getLayoutInflater().inflate(R.layout.listview_layout, parent, false);
                holder.mTxTitle = (TextView) convertView.findViewById(R.id.tx_title);
                holder.mTxCategory = (TextView) convertView.findViewById(R.id.tx_category);
                holder.mTxDetail = (TextView) convertView.findViewById(R.id.tx_detail);
                holder.mTxPrice = (TextView) convertView.findViewById(R.id.tx_price);
                holder.mImg = (ImageView) convertView.findViewById(R.id.img);
                convertView.setTag(holder);
            } else {
                holder = (viewholder) convertView.getTag();
            }
            holder.mTxTitle.setText(datainfo.get(position).getTitle());
            holder.mTxCategory.setText(datainfo.get(position).getCategory());
            holder.mTxDetail.setText(datainfo.get(position).getDetail());
            holder.mTxPrice.setText(datainfo.get(position).getPrie());

            return convertView;
        }
    }

    static class viewholder {
        TextView mTxTitle, mTxCategory, mTxDetail, mTxPrice;
        ImageView mImg;
    }

    /**
     * 获得全部商品
     */
    public void Getinfo() {
        sp = getSharedPreferences("info", MODE_PRIVATE);
        GetBuilder builder = OkHttpUtils.get();
        if (intent.getStringExtra("category").equals("all")) {
            builder.url(GETALLPRODUCT);
        } else if (intent.getStringExtra("category").equals("我的发布")) {
            builder.url(GETMYPRODUCT);
            builder.addParams(" owner ",sp.getString("username",null));
        }else if (intent.getStringExtra("category").equals("我的收藏")) {
            builder.url(GETFAVORITE);
            builder.addParams(" owner ",sp.getString("username",null));
        }else if (intent.getStringExtra("category").equals("学习用品")) {
            builder.url(CATEGORY);
            builder.addParams("category ","学习用品");
        }
        else if (intent.getStringExtra("category").equals("电子用品")) {
            builder.url(CATEGORY);
            builder.addParams("category ","电子用品");
        }
        else if (intent.getStringExtra("category").equals("生活用品")) {
            builder.url(CATEGORY);
            builder.addParams("category ","生活用品");
        }else if (intent.getStringExtra("category").equals("体育用品")) {
            builder.url(CATEGORY);
            builder.addParams("category ","体育用品");
        }
                builder.build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        Toast.makeText(CategoryActivity.this, "网络异常，请稍后再试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Product product = null;
                        Log.d(TAG, "onResponse: " + response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject category = jsonArray.getJSONObject(i);
                                product = new Product();
                                product.setOwner(category.getString("owner"));
                                product.setTitle(category.getString("title"));
                                product.setDetail(category.getString("contactDetail"));
                                product.setPrie(category.getString("price"));
                                messageInfoList.add(product);
                            }
                            if (messageInfoList.size() != 0) {
                                mydoadapter = new Mydoadapter(messageInfoList);
                                mListView.setAdapter(mydoadapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
    }
}
