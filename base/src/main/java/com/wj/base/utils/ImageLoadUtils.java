package com.wj.base.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by wj on 2018/5/18.
 */
public class ImageLoadUtils {

    /**
     * 加载图片
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void display(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }

}
