package com.hpu.sencondhand.http;

import com.hpu.sencondhand.fragment.HomeFragment;

import java.util.logging.Handler;

/**
 * Created by：何学慧
 * Detail:接口
 * on 2019/12/30
 */

public interface api {
String HOME="http://localhost:8080/";
String LOGIN=HOME+"signin";
String REGISTER=HOME+"signup";
String ADDPRO=HOME+"addproduct";
String GETALLPRODUCT=HOME+"getallproduct";
String CHANGEPWD=HOME+"changepwd";
String CHANGEPHONE=HOME+"changephone";
String GETMYPRODUCT= HOME+"getmyproduct";
String GETFAVORITE=HOME+"getfavorite";
String CATEGORY=HOME+"category";
}
