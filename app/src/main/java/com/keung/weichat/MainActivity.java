package com.keung.weichat;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.keung.weichat.adapter.MainAdapter;
import com.keung.weichat.databinding.ActivityMainBinding;
import com.keung.weichat.http.bean.TweetListBean;
import com.keung.weichat.http.bean.UserInfoBean;
import com.keung.weichat.util.AppUtil;
import com.keung.weichat.util.GlideImageLoader;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity implements MainContract.View,
        SwipeRefreshLayout.OnRefreshListener {

    private MainAdapter mAdapter;
    private ActivityMainBinding mDataBinding;
    private MainContract.Presenter mPresenter;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLightStatusBar();
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mPresenter = new MainPresenter(this, this);

        mAdapter = new MainAdapter(this);
        mDataBinding.listComments.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mDataBinding.listComments.setLayoutManager(manager);

        mPresenter.requestUserInfo();
        mPresenter.requestTweetsList();

        mDataBinding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float percent = (Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange());
                mDataBinding.toolBar.setAlpha(percent);
            }
        });
        mDataBinding.layoutRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onUpdateUserInfo(UserInfoBean bean) {
        updateUI(new Runnable() {
            @Override
            public void run() {
                GlideImageLoader.displayImage(bean.getAvatar(), mDataBinding.imageAvatar);
                GlideImageLoader.displayImage(bean.getProfile_image(), mDataBinding.imageBack);
                mDataBinding.textName.setText(bean.getUsername());
            }
        });
    }

    @Override
    public void onUpdateTweetsList(List<TweetListBean.DataBean> list) {
        updateUI(new Runnable() {
            @Override
            public void run() {
                mAdapter.setData(list);
                mDataBinding.layoutRefresh.setRefreshing(false);
            }
        });
    }

    public void updateUI(Runnable runnable) {
        if (runnable != null) {
            mHandler.post(runnable);
        }
    }

    protected void initLightStatusBar() {
        if (AppUtil.isXiaomi()) {
            AppUtil.MIUISetStatusBarLightMode(this, true);
        } else if (AppUtil.isMeizu()) {
            AppUtil.FlymeSetStatusBarLightMode(this, true);
        } else {
            //                LogUtils.d("setUserVisibleHint -> HDAssetFragment");
            //            setStatusBar(R.color.color_primary_background);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View
                                .SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                //_mActivity.getWindow().setStatusBarColor(Color.WHITE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        mPresenter.requestUserInfo();
        mPresenter.requestTweetsList();
    }
}