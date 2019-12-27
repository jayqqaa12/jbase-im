package com.jayqqaa12.im.business.support;

import com.jayqqaa12.im.common.model.consts.VersionEnum;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author: 12
 * @create: 2019-09-10 17:51
 **/

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Handler {


  int req()  ;

  VersionEnum max() default VersionEnum.V_9_9_9_9;

  VersionEnum min() default VersionEnum.V_1_0_0;

}
