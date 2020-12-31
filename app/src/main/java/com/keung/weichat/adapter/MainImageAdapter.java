package com.keung.weichat.adapter;

import android.content.Context;
import android.widget.FrameLayout;

import com.keung.weichat.R;
import com.keung.weichat.base.BaseAdapter;
import com.keung.weichat.databinding.ItemImageListBinding;
import com.keung.weichat.http.bean.TweetListBean;
import com.keung.weichat.util.DensityUtil;
import com.keung.weichat.util.GlideImageLoader;

public class MainImageAdapter extends BaseAdapter<ItemImageListBinding, TweetListBean.Images> {

    private int mWidth = 0;

    public MainImageAdapter(Context activity) {
        super(activity);
        mWidth = (DensityUtil.getScreenWidth(getContext()) - DensityUtil.dp2px(86)) / 3;
    }

    @Override
    public void onBind(ItemImageListBinding dataBinding, TweetListBean.Images tempData, int position) {
        GlideImageLoader.displayImage(tempData.getUrl(), dataBinding.imageContent);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) dataBinding.imageContent.getLayoutParams();
        params.width = mWidth;
        params.height = mWidth;
        dataBinding.imageContent.setLayoutParams(params);
    }

    @Override
    public int getLayoutRes(int position) {
        return R.layout.item_image_list;
    }
}
