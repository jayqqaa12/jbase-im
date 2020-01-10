package com.jayqqaa12.im.gateway.protool.route;

import cn.hutool.core.lang.Assert;
import com.jayqqaa12.im.common.model.consts.Req;
import com.jayqqaa12.im.common.model.consts.Resp;
import com.jayqqaa12.im.common.model.dto.RegInfoDTO;
import com.jayqqaa12.im.common.model.vo.TcpReqVO;
import com.jayqqaa12.im.gateway.protool.base.Route;
import com.jayqqaa12.im.gateway.protool.base.Router;
import com.jayqqaa12.im.gateway.protool.base.RouterChain;
import com.jayqqaa12.im.gateway.protool.base.TcpContext;
import com.jayqqaa12.im.gateway.support.RegHelper;
import com.jayqqaa12.jbase.spring.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 登录指令
 *
 * @author: 12
 * @create: 2019-09-11 11:11
 **/
@Route(req = Req.REGISTER, checkLogin = false)
public class RegRoute implements Router<RegInfoDTO> {

  @Autowired
  RegHelper regHelper;


  @Override
  public void handle(TcpContext context, TcpReqVO req, RegInfoDTO info) throws Exception {
    if (context.isLogin()) return;

    //todo 为方便测试直接用客户端传过来的userid 实践使用中应该从token中获取 取消下面注释
//    info.setUserId(null);

    Assert.isTrue(info.getDevice() != null || info.getToken() != null, "token or device can't null");
    //  验证token
    checkToken(info);

    context.setUserId(info.getUserId());
    context.setDevice(info.getDevice());
    context.setPlatform(info.getPlatform());
    context.setLogin(true);

    regHelper.register(  context);

    context.response(req, Resp.OK);

    //登录成功 自动触发登录事件
    RouterChain.exec(TcpReqVO.req(Req.BUSINESS_EVENT_LOGIN,null),context);
  }

  private void checkToken(RegInfoDTO info) {

    if(info.getToken()==null)return;

    //todo 接入其他系统需要 重写token 验证逻辑 获取uid

    throw new BusinessException(Resp.TOKEN_ERROR, "TOKEN异常", null);

  }


}
