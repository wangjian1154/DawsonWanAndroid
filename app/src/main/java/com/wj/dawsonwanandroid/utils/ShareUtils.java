package com.wj.dawsonwanandroid.utils;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

public class ShareUtils {

    public static void share(Activity activity, SHARE_MEDIA media, UMImage umImage,
                             String content,String title,String jumpUrl){
        new ShareAction(activity).setPlatform(media).setCallback(umShareListener)
                .withMedia(umImage)
                .withText(content)
                .withText(title)
                .share();
    }

    private static UMShareListener umShareListener=new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {

        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    };
}
