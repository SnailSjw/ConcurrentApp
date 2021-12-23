package com.snail.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试wait方法
 *
 * @author : snail
 * @date : 2021-12-13 20:56
 **/
@Slf4j(topic = "c.Test6")
public class Test6 {
    public static final Object obj = new Object();
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            log.debug("进入wait");
            synchronized (obj){
                try {
                    obj.wait(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("do something...");
        },"t1");
        t1.start();
    }
}
