package com.wj.dawsonwanandroid.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wj on 2018/7/3.
 */
@Entity
public class TagsBean implements Serializable {


    private static final long serialVersionUID = -7983245178424713825L;

    @Id
    private long id;
    private long tagsId;
    private String name;
    private String url;

    @Generated(hash = 1304861986)
    public TagsBean(long id, long tagsId, String name, String url) {
        this.id = id;
        this.tagsId = tagsId;
        this.name = name;
        this.url = url;
    }

    @Generated(hash = 1691342839)
    public TagsBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTagsId() {
        return tagsId;
    }

    public void setTagsId(long tagsId) {
        this.tagsId = tagsId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
