package com.keung.weichat;

import com.keung.weichat.http.bean.TweetListBean;
import com.keung.weichat.http.bean.UserInfoBean;

import java.util.List;

public class MainContract {

    public interface View {

        void onUpdateUserInfo(UserInfoBean bean);

        void onUpdateTweetsList(List<TweetListBean.DataBean> list);
    }

    public interface Presenter {

        void requestUserInfo();

        void requestTweetsList();
    }
}
