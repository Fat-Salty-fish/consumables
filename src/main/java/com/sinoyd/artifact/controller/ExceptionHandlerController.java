package com.sinoyd.artifact.controller;

import com.sinoyd.artifact.result.ResultBean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.text.ParseException;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-17 16:34
 */
@ControllerAdvice
public class ExceptionHandlerController  {

    /** 异常截获 返回code为0时认为操作成功返回1时为客户端错误 一般为输入非法值或空指针异常
     * 空指针异常截获方法
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public Object nullPointerExceptionHandler(NullPointerException e){
        return ResultBean.error(1,e.getMessage());
    }

    /**
     * 非法参数异常截获方法
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public Object illegalArgumentExceptionHandler(IllegalArgumentException e){
        return ResultBean.error(1,e.getMessage());
    }

    /**
     * sql语句错误截获方法
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public Object sqlException(SQLException e){
        return ResultBean.error(1,e.getMessage());
    }

    @ExceptionHandler
    @ResponseBody
    public Object ParseException(ParseException e){
        return ResultBean.error(2,"输入时间格式错误");
    }
}
