package com.snail.test;

import java.io.Serializable;

/**
 * 单例模式-饿汉式
 *
 * @author : snail
 * @date : 2021-12-15 08:13
 **/
public final class Singleton01 implements Serializable {
    private static final Singleton01 INSTANCE = new Singleton01();
//    1.构造方法私有
    private Singleton01() {}

//    提供公共方法获取单例
    public Singleton01 getInstance(){
        return  INSTANCE;
    }

//    提供readResovle方法防止被反射创建实例
    public Object readResolve(){
        return INSTANCE;
    }
}
