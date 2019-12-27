package com.jayqqaa12.im.common.model.vo;

import com.jayqqaa12.im.common.model.consts.TcpConstants;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: 12
 * @create: 2019-09-19 15:48
 **/
@Data
@Accessors(chain = true)
public class RetryVo {

    private int retryTimes = 0;

    private int maxRetryTimes = TcpConstants.MAX_RETRY_COUNT;





}
