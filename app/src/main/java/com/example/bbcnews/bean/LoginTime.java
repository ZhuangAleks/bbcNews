package com.example.bbcnews.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;
@Table(name = "LoginTime")
public class LoginTime implements Serializable {
    @Column(name = "id", isId = true, autoGen = true)
    private String id;
    @Column(name = "name")//
    private String name;
    @Column(name = "time")//
    private String time;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
