package com.keung.weichat.util;

import com.google.gson.Gson;
import com.keung.weichat.http.bean.UserInfoBean;

import java.util.ArrayList;
import java.util.List;

public class GsonUtil {

    private static Gson mGson = new Gson();

    public static Gson getInstance() {
        return mGson;
    }

    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        stringList.add("test");
        stringList.add("test");
        stringList.add("test");
        System.out.println(mGson.toJson(stringList));

    }
}
