package com.snail.test;

import java.util.concurrent.locks.LockSupport;

/**
 * 使用park、unpark实现循环打印
 *
 * @author : snail
 * @date : 2021-12-14 20:22
 **/
public class Test08 {
    static Thread t1;
    public static void main(String[] args) throws InterruptedException {
        ParkUnpark pu = new ParkUnpark(5);
        Thread t3 = new Thread(()->{
            pu.print("C",t1);
        },"t3");
        Thread t2 = new Thread(()->{
            pu.print("B",t3);
        },"t2");
        t1 = new Thread(()->{
            pu.print("A",t2);
        },"t1");
        t1.start();
        t2.start();
        t3.start();
        Thread.sleep(1000);
        LockSupport.unpark(t1);
    }
}

class ParkUnpark{
    private int loopNumber;

    /**
     * 打印线程内容
     *
     * @param content 需要打印的内容
     * @param next 下一个需要被唤醒的线程
     */
    public void print(String content,Thread next){
        for (int i = 0; i < loopNumber; i++) {
            LockSupport.park();
            System.out.print(content);
            LockSupport.unpark(next);
        }
    }

    public ParkUnpark(int loopNumber) {
        this.loopNumber = loopNumber;
    }
}
