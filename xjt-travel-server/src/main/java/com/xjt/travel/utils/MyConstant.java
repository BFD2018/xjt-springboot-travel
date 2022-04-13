package com.xjt.travel.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

public final class MyConstant {
    public static String ProjectPath = "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"travelFiles"+ System.getProperty("file.separator");
}

