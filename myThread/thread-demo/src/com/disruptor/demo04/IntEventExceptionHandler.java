package com.disruptor.demo04;

import com.lmax.disruptor.ExceptionHandler;

public class IntEventExceptionHandler implements ExceptionHandler {
    @Override
    public void handleEventException(Throwable ex, long sequence, Object event) {}
    @Override
    public void handleOnStartException(Throwable ex) {}
    @Override
    public void handleOnShutdownException(Throwable ex) {}
}
