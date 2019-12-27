package com.jayqqaa12.im.gateway.protool.route;

import com.jayqqaa12.im.gateway.protool.base.Route;
import com.jayqqaa12.im.gateway.protool.base.Router;
import com.jayqqaa12.im.gateway.protool.base.TcpContext;
import com.jayqqaa12.im.common.model.consts.Req;
import com.jayqqaa12.im.gateway.protool.model.vo.TcpReqVO;
import com.jayqqaa12.im.gateway.support.RegHelper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 退出 清空session
 *
 * @author: 12
 * @create: 2019-09-11 11:11
 **/
@Route(req = Req.UNREGISTER,checkLogin = false)
public class UnRegRoute implements Router<Object> {
    @Autowired
    RegHelper regHelper;

    @Override
    public void handle(TcpContext context, TcpReqVO req, Object data) {

        regHelper.unregister(context.getRespChannel());


    }


}
