package com.keung.weichat;

import android.content.Context;
import android.text.TextUtils;

import com.keung.weichat.config.Constants;
import com.keung.weichat.http.HttpManager;
import com.keung.weichat.http.bean.TweetListBean;
import com.keung.weichat.http.bean.UserInfoBean;
import com.keung.weichat.util.GsonUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class MainPresenter implements MainContract.Presenter {

    private WeakReference<Context> mContext;
    private MainContract.View mView;

    public MainPresenter(Context context, MainContract.View view) {
        mContext = new WeakReference<>(context);
        mView = view;
    }

    @Override
    public void requestUserInfo() {
        OkGo.<String>get(Constants.HOST + HttpManager.ACTION_USER_INFO).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (response != null) {
                    String image = "";
                    try {
                        JSONObject o = new JSONObject(response.body());
                        image = o.getString("profile-image");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    UserInfoBean bean = GsonUtil.getInstance().fromJson(response.body(), UserInfoBean.class);
                    if (bean != null) {
                        if (!TextUtils.isEmpty(image)) {
                            bean.setProfile_image(image);
                        }
                        mView.onUpdateUserInfo(bean);
                    }
                }
            }
        });
    }

    @Override
    public void requestTweetsList() {
        OkGo.<String>get(Constants.HOST + HttpManager.ACTION_TWEET_LIST).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (response != null) {
                    String body = "{\"data\":" + response.body() + "}";
                    TweetListBean bean = GsonUtil.getInstance().fromJson(body, TweetListBean.class);
                    if (bean != null) {
                        mView.onUpdateTweetsList(bean.getData());
                    }
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
            }
        });
    }
}
