package com.hpu.sencondhand.util;

import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;


/**
 * Created by：何学慧
 * Detail:
 * on 2019/12/30
 */

public abstract class StringCallBack extends Callback<String> {
    @Override
    public String parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        return string;
    }
}

