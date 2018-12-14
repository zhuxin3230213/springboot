package com.example.mybatis.model;

import java.io.Serializable;


public class Config  implements Serializable {

    private String id;

    private String code;

    private String name;

    private String value;

    private int sort;

    public Config() {
    }

    public Config(String id, String code, String name, String value, int sort) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.value = value;
        this.sort = sort;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "Config{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", sort=" + sort +
                '}';
    }
}
