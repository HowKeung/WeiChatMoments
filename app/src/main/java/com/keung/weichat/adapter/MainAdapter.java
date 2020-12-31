package com.keung.weichat.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.keung.weichat.R;
import com.keung.weichat.base.BaseRecyclerAdapter;
import com.keung.weichat.databinding.ItemMutiImageBinding;
import com.keung.weichat.databinding.ItemSingleImageBinding;
import com.keung.weichat.http.bean.TweetListBean;
import com.keung.weichat.util.GlideImageLoader;
import com.keung.weichat.widget.ListViewForScrollView;

import androidx.databinding.ViewDataBinding;

public class MainAdapter extends BaseRecyclerAdapter<ViewDataBinding, TweetListBean.DataBean> {

    private int TYPE_SINGLE_IMAGE = 0;
    private int TYPE_MUTI_IMAGE = 1;

    public MainAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutRes(int viewType) {
        if (viewType == TYPE_SINGLE_IMAGE) {
            return R.layout.item_single_image;
        } else {
            return R.layout.item_muti_image;
        }
    }

    @Override
    public void onBind(ViewDataBinding dataBinding, TweetListBean.DataBean data, int position) {

        if (getItemViewType(position) == TYPE_SINGLE_IMAGE) {
            ItemSingleImageBinding binding = (ItemSingleImageBinding) dataBinding;
            setUserState(binding.textName, binding.imageAvatar, data);

            if (data.getImages() != null && data.getImages().size() > 0) {
                GlideImageLoader.displayImage(data.getImages().get(0), binding.imageContent);
                binding.imageContent.setVisibility(View.VISIBLE);
            } else {
                binding.imageContent.setVisibility(View.GONE);
            }

            setCommentState(binding.listComment, data);
            setShowAllState(binding.textContent, binding.textMore, data);
        } else {
            ItemMutiImageBinding binding = (ItemMutiImageBinding) dataBinding;
            setUserState(binding.textName, binding.imageAvatar, data);

            MainImageAdapter adapter = new MainImageAdapter(getContext());
            binding.imageContent.setAdapter(adapter);
            adapter.setData(data.getImages());

            setCommentState(binding.listComment, data);
            setShowAllState(binding.textContent, binding.textMore, data);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mDataList.get(position).getImages() == null || mDataList.get(position).getImages().size() <= 1) {
            return TYPE_SINGLE_IMAGE;
        } else {
            return TYPE_MUTI_IMAGE;
        }
    }

    /**
     * 处理用户信息
     *
     * @param textName
     * @param imageAvatar
     * @param data
     */
    private void setUserState(TextView textName, ImageView imageAvatar, TweetListBean.DataBean data) {
        if (data.getSender() != null) {
            GlideImageLoader.displayImage(data.getSender().getAvatar(), imageAvatar);
            textName.setText(data.getSender().getUsername());
        }
    }

    /**
     * 处理评论内容
     *
     * @param listComment
     * @param data
     */
    private void setCommentState(ListViewForScrollView listComment, TweetListBean.DataBean data) {
        MainCommentAdapter commentAdapter = new MainCommentAdapter(getContext());
        if (data.getComments() != null && data.getComments().size() > 0) {
            listComment.setVisibility(View.VISIBLE);
        } else {
            listComment.setVisibility(View.GONE);
        }
        listComment.setAdapter(commentAdapter);
        commentAdapter.setData(data.getComments());
    }

    /**
     * 处理文字内容展开/显示
     *
     * @param textContent
     * @param textMore
     * @param data
     */
    private void setShowAllState(TextView textContent, TextView textMore, TweetListBean.DataBean data) {
        if (!TextUtils.isEmpty(data.getContent())) {
            textContent.setVisibility(View.VISIBLE);
            if (data.getContent().length() > 108) {
                textMore.setVisibility(View.VISIBLE);
                textContent.setText(data.getContent().substring(0, 108));
            } else {
                textMore.setVisibility(View.GONE);
                textContent.setText(data.getContent());
            }
        } else {
            textContent.setVisibility(View.GONE);
            textMore.setVisibility(View.GONE);
        }
        textMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = textContent.getText().toString();
                if (content.length() <= 108) {
                    textContent.setText(data.getContent());
                    textMore.setText("收起");
                } else {
                    textContent.setText(data.getContent().substring(0, 108));
                    textMore.setText("全文");
                }
            }
        });
    }
}
