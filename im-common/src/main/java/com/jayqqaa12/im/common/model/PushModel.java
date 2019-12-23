package com.jayqqaa12.im.common.model;

import lombok.Data;

import java.util.Set;

/**
 * 推送的实体类 Created by nessary on 16-9-27.
 */
@Data
public class PushModel {

   
    /**
     * 推送的消息
     */
    private String msg;
    /**
     * 推送的主题
     */
    private String title;
  


    /**
     * 不是纯文本时候的链接
     */
    private String url;


    private String iconUrl;
 
    /**
     * 类型
     * 1.IM消息   
     */
    private Integer type;

    /**
     * 类别
     */
    private Integer classify;

    private Object data;



    /**
     * 设备标识 0安卓 1 苹果
     */
    private Integer deviceType;

    /**
     * 设备标识id
     */
    private String device;

    /**
     * 别名
     */
    private String alias;

 
    /**
     * 要推送的设备id
     * 和别名选一个
     */
    private Set<String> deviceList;

    /**
     * 要推送的别名
     * 和设备号设置一个
     */
    private Set<String> aliasList;


}
