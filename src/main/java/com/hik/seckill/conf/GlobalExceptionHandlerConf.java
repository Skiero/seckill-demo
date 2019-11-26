package com.hik.seckill.conf;

import com.hik.seckill.error.CommonException;
import com.hik.seckill.enums.EmBusinessError;
import com.hik.seckill.model.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * Created by wangJinChang on 2019/11/4 20:45
 * 全局异常处理类
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandlerConf {
    @ExceptionHandler(value = BindException.class)
    public ResultVO<String> GlobalExceptionHandler(BindException e) {
        String defaultMessage = e.getAllErrors().get(0).getDefaultMessage();
        log.error(e.getCause() + "  cause:  " + e.getMessage());
        return ResultVO.error(defaultMessage);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultVO<String> GlobalExceptionHandler(MethodArgumentNotValidException e) {
        String defaultMessage = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";"));
        log.error(e.getCause() + "  cause:  " + e.getMessage());
        return ResultVO.error(defaultMessage);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResultVO<String> GlobalExceptionHandler(ConstraintViolationException e) {
        String defaultMessage = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        log.error(e.getCause() + "  cause:  " + e.getMessage());
        return ResultVO.error(defaultMessage);
    }

    @ExceptionHandler(value = CommonException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultVO<String> GlobalExceptionHandler(CommonException e) {
        log.error(e.getCause() + "  cause:  " + e.getMessage());
        return ResultVO.error(e.getErrCode(), e.getErrMsg());
    }

    @ExceptionHandler(value = Exception.class)
    public ResultVO<String> GlobalExceptionHandler(Exception e) {
        log.error(e.getCause() + "  cause:  " + e.getMessage());
        return ResultVO.error(EmBusinessError.USER_SERVICE_ERROR.getErrCode(), EmBusinessError.USER_SERVICE_ERROR.getErrMsg());
    }
}
