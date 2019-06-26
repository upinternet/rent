package com.hyh.ease.rent.security.domain;

import java.util.HashMap;
import java.util.Map;

public class AccessKey {

    public static Map<String,String> pare = new HashMap<>();

    static
    {
        pare.put("app" , "app123");
        pare.put("web" , "web123");

    }

    public AccessKey(String id , String keyContent)
    {
        this.id = id;
        this.keyContent = keyContent;
    }

    private String id;
    private String keyContent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyContent() {
        return keyContent;
    }

    public void setKeyContent(String keyContent) {
        this.keyContent = keyContent;
    }
}


