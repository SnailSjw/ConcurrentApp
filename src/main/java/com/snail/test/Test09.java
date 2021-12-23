package com.snail.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用await、signal实现循环打印
 *
 * @author : snail
 * @date : 2021-12-14 20:31
 **/
public class Test09 {
    public static void main(String[] args) throws InterruptedException {
        AwaitSignal awaitSignal = new AwaitSignal(5);
        Condition a = awaitSignal.newCondition();
        Condition b = awaitSignal.newCondition();
        Condition c = awaitSignal.newCondition();

        new Thread(()->{
            awaitSignal.print("A",a,b);
        }).start();
        new Thread(()->{
            awaitSignal.print("B",b,c);
        }).start();
        new Thread(()->{
            awaitSignal.print("C",c,a);
        }).start();

        Thread.sleep(1000);
        awaitSignal.lock();
        try {
            a.signalAll();
        }finally {
            awaitSignal.unlock();
        }

    }
}
class AwaitSignal extends ReentrantLock {
    /**
     *
     * @param content 需要打印的内容
     * @param current 当前要进入的Condition
     */
    public void print(String content,Condition current,Condition next){
        for (int i = 0; i < loopNumber; i++) {
            lock();
            try {
                try {
                    current.await();
                    System.out.print(content);
                    next.signalAll(); // 次数signal/signalAll都可以，程序中每个Condition只有一个线程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }finally {
                unlock();
            }
        }
    }

    private int loopNumber;
    public AwaitSignal(int loopNumber) {
        this.loopNumber = loopNumber;
    }
}
