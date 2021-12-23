package com.snail.test;

/**
 * 单例模式：懒汉式（静态内部类）
 *  线程安全的，在使用时调用getInstance方法，才创建对象，由JVM确保并发安全。
 *
 * @author : snail
 * @date : 2021-12-15 09:37
 **/
public class Singleton04 {

    private static class LazyHolder{
        static final Singleton04 INSTANCE = new Singleton04();
    }
    public static Singleton04 getInstance(){
        return LazyHolder.INSTANCE;
    }
}
