package com.wj.dawsonwanandroid.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wj on 2018/5/15.
 */
public class ArticleListBean implements Serializable {

    public int curPage;
    public int offset;
    public boolean over;
    public int pageCount;
    public int size;
    public int total;
    public List<ArticleBean> datas;
}
