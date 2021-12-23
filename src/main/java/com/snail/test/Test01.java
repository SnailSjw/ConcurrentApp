package com.snail.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : snail
 * @date : 2021-12-11 22:01
 **/
@Slf4j(topic = "c.Test01")
public class Test01 {
    public static void main(String[] args) {
        test();
        test2();
    }


    /**
     * 第一种创建线程的方式：继承Thread类（下面使用匿名内部类）
     */
    public static void test(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                log.debug("running");
            }
        };
        thread.setName("t1");
        thread.start();
        log.debug("running");
    }

    /**
     * 第二种：实现Runnable接口
     */
    public static void test2(){
        Runnable r = () -> log.debug("running");
        Thread t = new Thread(r,"t1");
        t.start();
    }
}
