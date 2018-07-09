package com.wj.dawsonwanandroid.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMediaObject;
import com.wj.base.utils.StringUtils;
import com.wj.base.utils.ToastUtils;
import com.wj.dawsonwanandroid.net.ApiRetrofit;

public class ShareUtils {


    public static void shareWeb(Activity activity, String shareTitle, String shareContent, String shareImage, String webUrl) {
        ShareAction shareAction = new ShareAction(activity);

        UMWeb web = new UMWeb(webUrl);
        if (!StringUtils.isEmpty(shareTitle)){
            web.setTitle(shareTitle);//标题
        }
        if (!TextUtils.isEmpty(shareImage)) {
            UMImage image = new UMImage(activity, shareImage);//网络图片
            web.setThumb(image);
        }
        if (!StringUtils.isEmpty(shareContent)){
            web.setDescription(shareContent);
        }

        shareAction.withMedia(web);

        shareAction.setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA)
                .setCallback(umShareListener).open();
    }

    private static UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.showShort("分享成功啦");

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showShort("分享失败啦");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showShort("分享取消了");
        }
    };

}
