package com.oitsme.widgetdemo.model;

/**
 * @author zhangkun
 * @time 2021/4/11 5:44 PM
 * @Description
 */
public class User {

    private String id;
    private String name;
    private String url;
    private String avatar_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAvatar_url() {

        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }


    @Override
    public String toString() {
        return "User{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", url='" + url + '\'' +
            ", avatar_url='" + avatar_url + '\'' +
            '}';
    }
}
