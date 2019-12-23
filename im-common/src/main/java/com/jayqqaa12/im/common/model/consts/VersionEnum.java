package com.jayqqaa12.im.common.model.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: 12
 * @create: 2019-10-29 10:50
 **/
@AllArgsConstructor
@Getter
public enum VersionEnum {

    V_1_0_0("1.0.0", 100),

    V1_5_0("1.5.0", 150),
    V1_5_1("1.5.1", 151),
    V1_5_2("1.5.2", 152),
    V1_6_0("1.6.0", 160),
    V1_6_1("1.6.1", 161),
    V1_6_2("1.6.2", 162),



    V_9_9_9_9("9.9.9.9",9999);


    private String code;
    private int version;

     public static VersionEnum of(String  code){

         for (VersionEnum versionEnum : values()) {
             if (versionEnum.code.equals(code)) {
                 return versionEnum;
             }
         }

         return V_9_9_9_9;
     }






}
