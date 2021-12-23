package com.snail.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试多线程下的原子性
 *  该测试案例，在32位系统（32位Java虚拟机）中会显示大量的异常打印，其原因是32位的系统中long型数据的读写不是原子性的，多线程之间存在相互干扰。
 *  在64位系统中，测试正常。
 *
 * @author : snail
 * @date : 2021-12-24 07:40
 **/
@Slf4j(topic = "c.AtomicityTest")
public class AtomicityTest {
    public static void main(String[] args) {
        new Thread(new ChangeT(111L)).start();
        new Thread(new ChangeT(-222L)).start();
        new Thread(new ChangeT(333L)).start();
        new Thread(new ChangeT(-444L)).start();
        new Thread(new ReadT()).start();
    }
    public static long t = 0;
    public static class ChangeT implements Runnable{
        private long to;

        public ChangeT(long to) {
            this.to = to;
        }

        @Override
        public void run() {
            while (true){
                AtomicityTest.t = to;
                Thread.yield();
            }
        }
    }

    public static class ReadT implements Runnable{

        @Override
        public void run() {
            while (true){
                long temp = AtomicityTest.t;
                if (temp != 111L && temp != -222L && temp != 333L && temp != -444L ){
                    log.debug("temp= {}",temp);
                }
                Thread.yield();
            }
        }
    }
}
