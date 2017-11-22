package com.lakala.common.exception;

import com.lakala.common.constant.ECode;
import com.lakala.model.output.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理发生的情况，统一处理。
 * 继承不继承ResponseEntityExceptionHandler的区别？
 *
 * @author Administrator
 */
@ControllerAdvice
public class ExceptionAdvice {

    private static Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(value = {ApplicationException.class, IOException.class, FileNotFoundException.class, NullPointerException.class, ArrayIndexOutOfBoundsException.class, ClassCastException.class})
    @ResponseBody
    public Result<String> errorHandler(HttpServletRequest req, ApplicationException applicationException) throws Exception {
        return getResult(req, ECode.OK.getCode(), applicationException, "业务逻辑发生错误，请检查代码。");
    }

    @ExceptionHandler(value = {SQLException.class})
    @ResponseBody
    public Result<String> errorHandler(HttpServletRequest req, SQLException sqlException) throws Exception {
        return getResult(req, ECode.OK.getCode(), sqlException, "DB发生错误，请检查代码。");
    }

    @ExceptionHandler(value = {Exception.class, UnknownError.class})
    @ResponseBody
    public Result<String> defaultErrorHandler(HttpServletRequest req, Exception exception) throws Exception {
        return getResult(req, ECode.OK.getCode(), exception, "发生未知异常错误，请检查代码。");
    }

    /**
     * 结果集合类型的返回。
     *
     * @param req          {@link HttpServletRequest}
     * @param code         {@link Integer}
     * @param exception    {@link Exception}
     * @param errorMessage {@link String}
     * @return com.lakala.model.output.Result
     */
    private Result<String> getResult(HttpServletRequest req, int code, Exception exception, String errorMessage) {

        exception.printStackTrace();
        logger.error(exception.getCause().getMessage());

        Result<String> result = new Result<>();
        result.setData(req.getRequestURL().toString() + "的URL发生错误：" + exception.getMessage());
        result.setCode(code);
        result.setMsg(errorMessage);
        return result;
    }

}
