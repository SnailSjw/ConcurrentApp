package com.snail.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试List类的remove方法
 *
 * @author : snail
 * @date : 2021-12-12 18:56
 **/
@Slf4j(topic = "c.Test05")
public class Test05 {
    public static void main(String[] args) {
        Demo demo = new Demo();
        Thread t1 = new Thread(()->{
            for (int i = 0; i < 1000; i++) {
                demo.a();
            }
        },"t1");
        Thread t2 = new Thread(()-> {
            for (int i = 0; i < 1000; i++) {
                demo.b();
            }
        },"t2");
        t1.start();
        t2.start();
    }
}

@Slf4j(topic = "c.Demo")
class Demo{
    private int counter =0;

    public synchronized void a(){
        log.debug("counter++");
        counter++;
        c();
    }
    public synchronized void b(){
        log.debug("counter--");
        counter--;
        c();
    }
    public synchronized int c(){
        log.debug("counter= {}",counter);
        return counter;
    }


}