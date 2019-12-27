package com.jayqqaa12.im.business.support;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.jayqqaa12.im.common.model.consts.Resp;
import com.jayqqaa12.im.common.model.vo.RpcDTO;
import com.jayqqaa12.im.common.util.ValidatorKit;
import com.jayqqaa12.jbase.spring.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author: 12
 * @create: 2019-12-26 13:28
 **/
@Component
public class BusinessDispatcher {

  private Map<Integer, IHandler> handlerMap = Maps.newHashMap();
  @Autowired
  private ApplicationContext context;

  @PostConstruct
  public void init() {
    String[] handlers = context.getBeanNamesForAnnotation(Handler.class);

    for (String handler : handlers) {
      IHandler bean = (IHandler) context.getBean(handler);
      Handler annotation = bean.getClass().getAnnotation(Handler.class);
      if (annotation == null) throw new IllegalArgumentException("IHandler must use @Handler annotation");
      handlerMap.put(annotation.req(), bean);
    }

  }

  public Object handler(RpcDTO req) throws Exception {
    //  根据请求码去调用对应的服务

    IHandler handler = handlerMap.get(req.getCode());
    if (handler == null) throw new BusinessException(Resp.CMD_ERROR);

    Type type = ((ParameterizedType) (handler.getClass().getGenericInterfaces())[0]).getActualTypeArguments()[0];
    if (type.getTypeName().equals("java.lang.Object"))
      return handler.handle(req, req.getData());
    else {
      Object obj = req.getData();
      if (obj instanceof JSONObject) {
        obj = ((JSONObject) obj).toJavaObject(type);
      } else if (obj instanceof JSONArray) {
        obj = ((JSONArray) obj).toJavaObject(type);
      }
      ValidatorKit.validate(obj);
      return handler.handle(req,obj);
    }

  }
}
