package com.wj.base.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.wj.base.R;

/**
 * Created by wj on 2018/2/7.
 * 加载的对话框
 */

public class LoadingProgress extends Dialog {

    private String TAG=LoadingProgress.class.getSimpleName();
    private ImageView mLoadingImg;
    private TextView mTitleTv;
    private AnimationDrawable mAnimation;
    private static LoadingProgress instance;
    private boolean mCancelAble = true;
    private InterfaceOnKeyBack mInterfaceKeyBack;
    private static Handler mHandler;

    /**
     * 当前的网络请求标识
     */
    private String mRequestId = null;
    private static String mShowMsg = "请稍候...";


    public static LoadingProgress getInstance(Context context) {
        if (null != instance) {
            if (instance.getContext() == context)
                return instance;
            instance.dimiss();
            instance = null;
        }
        instance = new LoadingProgress(context);
        return instance;
    }

    public static void destroyInstance(Context context) {
        if (null != instance) {
            if (instance.getContext() == context) {
                instance.cancel();
                instance.onDetachedFromWindow();
                instance = null;
            }
        }
    }

    private static Handler getmHandler() {
        if (mHandler == null)
            mHandler = new Handler(Looper.getMainLooper());
        return mHandler;
    }

    public static void dismissByContext(final Context context) {
        getmHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    getInstance(context).dimiss();
                } catch (NullPointerException e) {
                }
            }
        }, 500);
    }

    public static LoadingProgress getInstance(Context context, String msg) {
        mShowMsg = msg;
        return getInstance(context);
    }

    private LoadingProgress(Context context) {
        super(context, R.style.LoadingDialog);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog);
        Window w = instance.getWindow();
//        w.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        mTitleTv = (TextView) findViewById(R.id.tv_msg);
        if (null != mShowMsg)
            mTitleTv.setText(mShowMsg);
//        mLoadingImg = (ImageView) findViewById(R.id.loading_Img);
//        initData();
    }

    private void initData() {
        mAnimation = (AnimationDrawable) mLoadingImg.getBackground();
        mLoadingImg.post(new Runnable() {
            @Override
            public void run() {
                mAnimation.start();
            }
        });

    }

    public void show() {
        try {
            if (null != instance && !isShowing()) {
                super.show();
            }
        } catch (WindowManager.BadTokenException e) {
            Log.e(TAG, e.toString());
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.toString());
        }

    }

    private void dimiss() {
        if (null != instance && isShowing()) {
            try {
                LoadingProgress.super.dismiss();
            } catch (WindowManager.BadTokenException e) {
                Log.e(TAG, e.toString());
            } catch (IllegalArgumentException e) {
                Log.e(TAG, e.toString());
            }
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler=null;
        }
    }

    @Override
    public void onBackPressed() {
        if (!mCancelAble)
            return;
        if (null != mInterfaceKeyBack)
            mInterfaceKeyBack.cancel();
        super.onBackPressed();
    }

    public String getRequestId() {
        return mRequestId;
    }

    public void setRequestId(String requestId) {
        mRequestId = requestId;
    }

    public InterfaceOnKeyBack getInterfaceKeyBack() {
        return mInterfaceKeyBack;
    }

    public void setInterfaceKeyBack(InterfaceOnKeyBack mInterfaceKeyBack) {
        this.mInterfaceKeyBack = mInterfaceKeyBack;
    }

    public interface InterfaceOnKeyBack {
        void cancel();
    }


}
