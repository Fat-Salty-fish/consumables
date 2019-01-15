package com.sinoyd.artifact.result;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * @Description 用来规范返回数据的结构类 code 表示代码 0为正常 1、2代表用户输入错误 -1、-2代表服务器错误
 * @auther 李忠杰
 * @create 2019-01-15 14:30
 */
@Getter
@Setter
public class ResultBean<T> {
    private Integer code;
    private String message;
    private Collection<T> data;

    public ResultBean(){
    }

    public static ResultBean error(Integer code,String message){
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(code);
        resultBean.setMessage(message);
        return resultBean;
    }

    public static ResultBean success(){
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(0);
        resultBean.setMessage("success");
        return resultBean;
    }

    public static <T> ResultBean<T> success(Collection<T> data){
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(0);
        resultBean.setMessage("success");
        resultBean.setData(data);
        return resultBean;
    }

    public static <T> ResultBean<T> success(T data){
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(0);
        resultBean.setMessage("success");
        if(data==null){
            return resultBean;
        }
        Collection<T> list = new ArrayList<>();
        list.add(data);
        resultBean.setData(list);
        return resultBean;
    }
}
