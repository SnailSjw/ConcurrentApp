package com.snail.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试调用interrupt方法后，正常线程和阻塞线程是否会继续执行
 *
 * @author : snail
 * @date : 2021-12-12 10:26
 **/
@Slf4j(topic = "c.Test04")
public class Test04 {
    public static void main(String[] args) throws InterruptedException {
//        test();
//        当正常线程被打断时，如果内部未处理，则不会停止执行线程。
//        test2();
//        当正常线程被打断时，调用isInterrupted()方法会返回true，内部可根据该标识进行相应的处理。（推荐这样处理打断）
//        test3();
//        当调用了sleep、join、wait方法的线程被打断时，线程不会再继续执行
    }

    /**
     * 测试正常线程被打断后是否会继续运行（线程内部不处理打断逻辑）
     */
    public static void test(){
        Thread t1 = new Thread(()->{
            while (true){log.debug("run");}
        },"t1");
        t1.start();
//        打断t1线程
        t1.interrupt();
        log.debug("end");
    }

    /**
     * 比较合适的线程被打断后的处理方式
     */
    public static void test2() throws InterruptedException {
        Thread t1 = new Thread(()->{
            while (true){
                log.debug("t1 run");
                log.debug("t1 interrupt state:{}",Thread.currentThread().isInterrupted());
                if (Thread.currentThread().isInterrupted()){
                    break;
                }
            }
        },"t1");
        t1.start();
        Thread.sleep(1000);
        t1.interrupt();
        log.debug("end");
    }

    /**
     * 调用了sleep、wait、join的线程被打断后是线程否会继续运行
     */
    public static void test3() throws InterruptedException {
        Thread t1 = new Thread(()->{
            log.debug("t1 run ");
            try {
                log.debug(" sleep 5s");
                Thread.sleep(5000);
                for (int i = 0; i < 10; i++) {
                    log.debug("print {}",i);
                }
                log.debug(" wake up,thread end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1");
        t1.start();
        log.debug("sleep 1s");
        Thread.sleep(1000);
        log.debug("interrupt t1");
        t1.interrupt();
        t1.join();
        log.debug("t1 interrupt state:{}",t1.isInterrupted());
        log.debug("end");
    }
}
