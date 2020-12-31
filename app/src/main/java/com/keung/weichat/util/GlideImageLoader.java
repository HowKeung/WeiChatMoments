package com.keung.weichat.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.keung.weichat.MainApplication;
import com.keung.weichat.R;


/**
 * Created by mc on 2018/4/25.
 */

public class GlideImageLoader {

    public static void displayImage(Object path, final ImageView imageView) {
        Glide.with(MainApplication.getContext())
                .load(path)
                .error(R.drawable.img_place_holder)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }
}