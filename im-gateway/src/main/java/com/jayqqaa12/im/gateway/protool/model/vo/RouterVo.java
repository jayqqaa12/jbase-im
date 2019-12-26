package com.jayqqaa12.im.gateway.protool.model.vo;

import com.jayqqaa12.im.gateway.protool.model.tcp.Route;
import com.jayqqaa12.im.gateway.protool.model.tcp.Router;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: 12
 * @create: 2019-12-13 15:32
 **/
@Data
@AllArgsConstructor
public class RouterVo {
  Router router;
  Route route;


}
