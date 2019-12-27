package com.jayqqaa12.im.common.util;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: 12
 * @create: 2019-12-27 15:40
 **/
@Slf4j
public class NodeKit {

  private static final String nodeId = IdWorker.getIdStr();

  public NodeKit() {
    log.info("init node id={}", nodeId);

  }

  public static String  getNodeId(){
    return nodeId;
  }



}
