package com.lakala.interceptor;

import com.lakala.common.constant.ECode;
import com.lakala.common.constant.MsgConsts;
import com.lakala.model.output.Result;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 返回值统一类型的设定
 *
 * @author Administrator
 */
@ControllerAdvice
@Order(10) // 最后执行返回值的设定
public class ControllerMethodAdvice implements ResponseBodyAdvice<Object> {

    private static Log log = LogFactory.getLog(ControllerMethodAdvice.class);

    @Override
    public Object beforeBodyWrite(Object paramT, MethodParameter paramMethodParameter, MediaType paramMediaType, Class paramClass, ServerHttpRequest paramServerHttpRequest, ServerHttpResponse paramServerHttpResponse) {
        log.info("ControllerMethodAdvice class is running");

        // 返回值已经是ResultInfo的场合，不进行处理。
        if (paramT != null && "com.lakala.model.output.Result".equals(paramT.getClass().getName())) {
            log.info("ControllerMethodAdvice class is end");
            return paramT;
        }

        // 对返回值添加code和结果集合
        Result resultInfo = new Result<Object>();
        resultInfo.setData(paramT);
        resultInfo.setCode(ECode.OK.getCode());
        resultInfo.setMsg(MsgConsts.SUCCESS);

        log.info("ControllerMethodAdvice class is end");
        return resultInfo;

    }

    @Override
    public boolean supports(MethodParameter paramMethodParameter, Class paramClass) {
        return true;
    }

}
