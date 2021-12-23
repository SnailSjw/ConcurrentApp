package com.snail.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : snail
 * @date : 2021-12-12 09:53
 **/
@Slf4j(topic = "c.Test03")
public class Test03 {
    private static int r =0;
    public static void main(String[] args) throws InterruptedException {
        test();
    }
    public static void test() throws InterruptedException {
        log.debug("start");
        Thread t1= new Thread(()->{
            log.debug("test.run");
            try {
                Thread.sleep(1);
                r = 10;
                log.debug("r= {}",r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1");
        t1.start();
//        t1.join(); //等待线程运行结束
        log.debug("r= {}",r);
        log.debug("end");
    }
}
