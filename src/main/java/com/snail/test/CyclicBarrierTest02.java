package com.snail.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 回环屏障案例2
 *
 * @author : snail
 * @date : 2021-12-22 16:31
 **/
@Slf4j(topic = "c.CyclicBarrierTest02")
public class CyclicBarrierTest02 {
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2,()->{
        System.out.println("分布计算");
    });

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(()->{
            try {
                log.debug("step 1");
                cyclicBarrier.await();
                log.debug("step 2");
                cyclicBarrier.await();
                log.debug("step 3");
                cyclicBarrier.await();
                log.debug("out barrier");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        executorService.submit(()->{
            try {
                log.debug("step 1");
                cyclicBarrier.await();
                log.debug("step 2");
                cyclicBarrier.await();
                log.debug("step 3");
                cyclicBarrier.await();
                log.debug("out barrier");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }
}
