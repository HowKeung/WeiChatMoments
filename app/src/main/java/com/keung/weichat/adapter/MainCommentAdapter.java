package com.keung.weichat.adapter;

import android.content.Context;

import com.keung.weichat.R;
import com.keung.weichat.base.BaseAdapter;
import com.keung.weichat.databinding.ItemCommentListBinding;
import com.keung.weichat.http.bean.TweetListBean;

public class MainCommentAdapter extends BaseAdapter<ItemCommentListBinding, TweetListBean.Comments> {

    public MainCommentAdapter(Context activity) {
        super(activity);
    }

    @Override
    public void onBind(ItemCommentListBinding dataBinding, TweetListBean.Comments tempData, int position) {
        if (tempData.getSender() != null) {
            dataBinding.textName.setText(tempData.getSender().getUsername() + " : ");
        }
        dataBinding.textComment.setText(tempData.getContent());
    }

    @Override
    public int getLayoutRes(int position) {
        return R.layout.item_comment_list;
    }
}
