package com.jayqqaa12.im.gateway.protool.model.dto;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RegInfoDTO {

    Integer userId;

    String token;

    String device;

    /**
     * 平台
     * ios android h5
     */
    String platform;


    transient boolean isLogin;


    public String getUserOrDevice() {
        Integer id = getUserId();
        return id != null ? id + "" : getDevice();
    }

}
