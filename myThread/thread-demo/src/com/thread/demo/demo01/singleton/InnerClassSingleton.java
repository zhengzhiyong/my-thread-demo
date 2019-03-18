package com.thread.demo.demo01.singleton;

/**
 * @author ZhiYong
 * @date 23点47分
 * @desc 静态内部类模式 初始化  懒汉模式- 单例模式，一般推荐使用此方案（简单）
 */
public class InnerClassSingleton {
    private static class SingletonA{
        private static SingletonA singleton = new SingletonA();
    }

    public SingletonA getSingletonA(){
        return SingletonA.singleton;
    }
}
