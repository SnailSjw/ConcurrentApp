package com.snail.test;

import java.util.Objects;

/**
 * 单例模式：懒汉式2（线程安全）
 * 使用：DCL（Double-checked locking）
 *
 * @author : snail
 * @date : 2021-12-15 09:10
 **/
public class Singleton03 {
    private Singleton03(){}
    private volatile static Singleton03 INSTANCE;

    public static Singleton03 getInstance(){
        if (Objects.isNull(INSTANCE)){
            /**
             *      此处两次判断的好处是：第一次创建时会保证线程安全（已经在属性INSTANCE加了volatile关键字），
             *  后续调用时不进入synchronized块，降低阻塞提高效率。
             */
            synchronized (Singleton03.class){
                if (Objects.isNull(INSTANCE)){
                    INSTANCE = new Singleton03();
                }
            }
        }
        return INSTANCE;
    }
}
