package com.disruptor.demo04;

import com.lmax.disruptor.ExceptionHandler;
/**
 * @author Zhengzy
 * @param
 * @date 2019/3/20 10:06
 * @desc 描述
 * @return 
 * @exception 
 */
public class IntEventExceptionHandler implements ExceptionHandler {
    @Override
    public void handleEventException(Throwable ex, long sequence, Object event) {}
    @Override
    public void handleOnStartException(Throwable ex) {}
    @Override
    public void handleOnShutdownException(Throwable ex) {}
}
