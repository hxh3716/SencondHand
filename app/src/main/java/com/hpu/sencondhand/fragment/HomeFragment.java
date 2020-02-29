package com.hpu.sencondhand.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hpu.sencondhand.R;
import com.hpu.sencondhand.activity.CategoryActivity;
import com.hpu.sencondhand.activity.DetailActivity;
import com.hpu.sencondhand.bean.Product;
import com.hpu.sencondhand.util.ImgPath;
import com.hpu.sencondhand.util.StringCallBack;
import com.hpu.sencondhand.widget.ClearEditText;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.hpu.sencondhand.http.api.GETALLPRODUCT;


/**
 * Detail:主页fragment
 * on 2019/12/31
 */

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private View rootView;
    @BindView(R.id.tx_tip)
    TextView mTip;
    @BindView(R.id.ed_search)
    EditText mSearched;
    @BindView(R.id.listview)
    ListView mListview;
    @BindView(R.id.img_study)
    ImageView mImgStudy;
    @BindView(R.id.img_ele)
    ImageView mImgEle;
    @BindView(R.id.img_bag)
    ImageView mImgBag;
    @BindView(R.id.img_soccer)
    ImageView mImgSoccer;
    private List<String> idList;
    private Intent intent;
    private Mydoadapter mydoadapter;
    List<Product> messageInfoList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //获取全部的商品列表
        Getinfo();
        mSearched.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //在文本框改变之前的动作
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //在改变时候的动作
                //mydoadapter.getFilter().filter(s);
                Toast.makeText(getContext(), "输入改变", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //选中商品时的点击事件，进入商品详情页面
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击后，跳转至详情页面
                Intent intent=new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("title",messageInfoList.get(position).getTitle());
                intent.putExtra("detail",messageInfoList.get(position).getDetail());
                intent.putExtra("price",messageInfoList.get(position).getPrie());
                intent.putExtra("phone",messageInfoList.get(position).getContactDetail());
                intent.putExtra("imgpath",messageInfoList.get(position).getImgPath());
                intent.putExtra("id",idList.get(position));
                startActivity(intent);
            }
        } );
    }

    class Mydoadapter extends BaseAdapter implements Filterable {
        private List<Product> datainfo;
        private final Object mLock = new Object();
        private ArrayList<Product> mOriginalValues;
        private ArrayFilter mFilter;

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
            if (datainfo.get(position).getImgPath()!=null)
                if (datainfo.get(position).getImgPath()!=null){
                    holder.mImg.setImageURI(ImgPath.getImage(datainfo.get(position).getImgPath(),getContext()));
                }


            return convertView;
        }

        @Override
        public Filter getFilter() {
            if (mFilter == null) {
                mFilter = new ArrayFilter();
            }
            return mFilter;
        }

        private class ArrayFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence prefix) {
                FilterResults results = new FilterResults();
                if (mOriginalValues == null) {
                    synchronized (mLock) {
                        mOriginalValues = new ArrayList<Product>(datainfo);
                    }
                }

                if (prefix == null || prefix.length() == 0) {
                    ArrayList<Product> list;
                    synchronized (mLock) {
                        list = new ArrayList<Product>(mOriginalValues);
                    }
                    results.values = list;
                    results.count = list.size();
                } else {
                    String prefixString = prefix.toString().toLowerCase();

                    ArrayList<Product> values;
                    synchronized (mLock) {
                        values = new ArrayList<Product>(mOriginalValues);
                    }

                    final int count = values.size();
                    final ArrayList<Product> newValues = new ArrayList<Product>();

                    for (int i = 0; i < count; i++) {
//                    final Product value = values.get(i);
//                    final String valueText = value.toString().toLowerCase();
                        final Product value = values.get(i);
                        final String valuetitle = value.getTitle().toLowerCase();
                        final String valuename = value.getCategory().toLowerCase();
                        if (valuetitle.startsWith(prefixString) || valuename.startsWith(prefixString))  {
                            newValues.add(value);
                        }else {
                            final String[] words = valuetitle.split(" ");
                            final int wordCount = words.length;{
                                newValues.add(value);
                            }

                            // Start at index 0, in case valueText starts with space(s)
                            for (int k = 0; k < wordCount; k++) {
                                if (words[k].startsWith(prefixString)) {
                                    newValues.add(value);
                                    break;
                                }
                            }
                        }
                    }

            results.values = newValues;
            results.count = newValues.size();
        }
                return results;
    }

    @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                datainfo = (List<Product>) results.values;
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
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
        OkHttpUtils.get()
                .url(GETALLPRODUCT)
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        Toast.makeText(getContext(),"网络异常，请稍后再试",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Product product=null;
                        System.out.println("---------------");
                        Log.d(TAG, "onResponse: "+response);
                        if (response.equals("[]")){
                            mTip.setVisibility(View.GONE);
                            mListview.setVisibility(View.INVISIBLE);
                            return;
                        }
                        messageInfoList=new ArrayList<>();
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            idList=new ArrayList<>();
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject category = jsonArray.getJSONObject(i);
                                product = new Product();
                                product.setOwner(category.optString("owner"));
                                product.setTitle(category.optString("title"));
                                product.setCategory(category.optString("category"));
                                product.setContactDetail(category.optString("contactdetail"));
                                product.setDetail(category.optString("description"));
                                product.setPrie(category.optString("price"));
                                product.setImgPath(category.optString("imgPath"));
                                idList.add(category.optString("id"));
                                messageInfoList.add(product);
                            }
                            if (messageInfoList.size()!=0){
                                mydoadapter=new Mydoadapter(messageInfoList);
                                mListview.setAdapter(mydoadapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
    }
    @OnClick({R.id.img_study,R.id.img_ele,R.id.img_bag,R.id.img_soccer})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.img_study:
                intent=new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("category","学习用品");
                startActivity(intent);
                break;
            case R.id.img_ele:
                intent=new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("category","电子用品");

                startActivity(intent);
                break;
            case R.id.img_bag:
                intent=new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("category","生活用品");

                startActivity(intent);
                break;
            case R.id.img_soccer:
                intent=new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("category","体育用品");
                startActivity(intent);
                break;

        }
    }
}
