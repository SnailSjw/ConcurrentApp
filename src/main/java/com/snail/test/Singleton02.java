package com.snail.test;

import java.util.Objects;

/**
 * 单例模式：懒汉式1
 *  对静态方法加synchronized，锁对象是Singleton.class，不存在安全问题；
 *  缺点：synchronized范围较大，且每次调用都需要加锁，影响性能。
 *
 * @author : snail
 * @date : 2021-12-15 08:54
 **/
public final class Singleton02 {
    private Singleton02() {}
    private static Singleton02 INSTANCE;

    public static synchronized Singleton02 getInstance(){
        if (Objects.isNull(INSTANCE)){
            INSTANCE = new Singleton02();
        }
        return INSTANCE;
    }
}
