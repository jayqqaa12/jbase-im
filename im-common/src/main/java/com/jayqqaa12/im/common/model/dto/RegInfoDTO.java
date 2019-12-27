package com.jayqqaa12.im.common.model.dto;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RegInfoDTO {

    Long userId;


    String token;

    String device;

    /**
     * 平台
     * ios android h5
     */
    String platform;


    transient boolean isLogin;


    public String getUserOrDevice() {
        Long id = getUserId();
        return id != null ? id + "" : getDevice();
    }

}
