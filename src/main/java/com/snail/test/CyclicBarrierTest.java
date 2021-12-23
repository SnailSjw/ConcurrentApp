package com.snail.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 回环屏障案例
 *
 * @author : snail
 * @date : 2021-12-22 15:56
 **/
@Slf4j(topic = "c.CyclicBarrierTest")
public class CyclicBarrierTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(()->{
            log.debug("running");
            try {
                log.debug("enter in barrier");
                cyclicBarrier.await();
                log.debug("enter out barrier");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        executorService.submit(()->{
            log.debug("running");
            try {
                log.debug("enter in barrier");
                cyclicBarrier.await();
                log.debug("enter out barrier");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2, ()->{
        System.out.println(Thread.currentThread()+" 计算两个线程的结果");
    });
}
