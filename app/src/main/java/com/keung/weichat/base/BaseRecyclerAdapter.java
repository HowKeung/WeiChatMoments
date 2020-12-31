package com.keung.weichat.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by lion on 2017/4/10.
 */

public abstract class BaseRecyclerAdapter<VDB extends ViewDataBinding, T> extends
        RecyclerView.Adapter<BaseRecyclerAdapter.ViewHolder<VDB>> {

    protected WeakReference<Context> mContext;
    protected List<T> mDataList;
    protected OnItemClickListener mOnItemClickListener;

    public BaseRecyclerAdapter(Context context) {
        mContext = new WeakReference<>(context);
        mDataList = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @LayoutRes
    public abstract int getLayoutRes(int viewType);

    public abstract void onBind(VDB dataBinding, T data, int position);

    @Override
    public ViewHolder<VDB> onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext.get()),
                getLayoutRes(viewType), parent, false);
        ViewHolder<VDB> holder = new ViewHolder<>(dataBinding.getRoot());
        holder.setDataBinding((VDB) dataBinding);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        VDB dataBinding = (VDB) (holder.getDataBinding());
        final T tempData = mDataList.get(position);
        dataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, tempData);
                }
            }
        });
        onBind(dataBinding, tempData, position);
    }

    @Override
    public int getItemCount() {
        if (mDataList != null) {
            return mDataList.size();
        } else {
            return 0;
        }
    }

    public void clear() {
        if (mDataList != null) {
            mDataList.clear();
        }
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mDataList;
    }

    public void setData(List<T> wallets) {
        this.mDataList = wallets;
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        mDataList.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(T data) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        mDataList.add(data);
        notifyItemChanged(mDataList.size(), "payload");
    }

    public void addData(int index, T data) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        mDataList.add(index, data);
        notifyItemChanged(index, "payload");
    }

    public void addData(int index, List<T> data) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        mDataList.addAll(index, data);
        notifyDataSetChanged();
    }

    public void removeData(int index) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        mDataList.remove(index);
        notifyDataSetChanged();
    }

    public void clearData() {
        if (mDataList != null) {
            mDataList.clear();
        }
        notifyDataSetChanged();
    }

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }

    public static class ViewHolder<VDB> extends RecyclerView.ViewHolder {

        private VDB mDataBinding;

        public VDB getDataBinding() {
            return mDataBinding;
        }

        public void setDataBinding(VDB dataBinding) {
            this.mDataBinding = dataBinding;
        }

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    protected Context getContext() {
        return mContext.get();
    }

    protected int getColor(int resId) {
        return mContext.get().getResources().getColor(resId);
    }

    protected String getString(int resId) {
        return mContext.get().getResources().getString(resId);
    }
}
