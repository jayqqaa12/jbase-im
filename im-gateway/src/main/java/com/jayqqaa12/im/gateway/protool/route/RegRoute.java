package com.jayqqaa12.im.gateway.protool.route;

import cn.hutool.core.lang.Assert;
import com.jayqqaa12.im.gateway.protool.model.tcp.Route;
import com.jayqqaa12.im.gateway.protool.model.tcp.Router;
import com.jayqqaa12.im.gateway.protool.model.tcp.TcpContext;
import com.jayqqaa12.im.common.model.consts.Req;
import com.jayqqaa12.im.common.model.consts.Resp;
import com.jayqqaa12.im.gateway.protool.model.dto.RegInfoDTO;
import com.jayqqaa12.im.gateway.protool.model.vo.TcpReqVO;
import com.jayqqaa12.im.gateway.support.RegHelper;
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

//    @Autowired
//    RedisTemplate redisTemplate;


    @Override
    public void handle(TcpContext context, TcpReqVO req, RegInfoDTO info) {
        if (context.isLogin()) return;

        Assert.isTrue(info.getDevice() != null || info.getUserId() != null, "userid or device can't null");
        //  验证token
        checkToken(info);

        regHelper.register(info, context.getRespChannel());
        context.setUserId(info.getUserId());
        context.setDevice(info.getDevice());
        context.setLogin(true);

        context.response(req, Resp.OK);

    }

    private void checkToken(RegInfoDTO info) {
        String token = info.getToken();
        if (token == null) return;


//        Object redisToken = redisTemplate.opsForValue().get(key);
//        if (redisToken != null && token.equals(redisToken.toString())) {
//            info.setLogin(true);
//        } else throw new BusinessException(TOKEN_ERROR, "TOKEN异常", null);

    }


}
