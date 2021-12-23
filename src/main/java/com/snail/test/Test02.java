package com.snail.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 创建线程第三种方式：使用FutureTask方式创建线程
 *
 * @author : snail
 * @date : 2021-12-11 22:14
 **/
@Slf4j(topic = "c.Test02")
public class Test02 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(() -> {
            log.debug("running");
            Thread.sleep(1000);
            return 100;
        });
        Thread t1 = new Thread(task,"t1");

        t1.start();
        log.debug("running");
        t1.join();
        final Integer result = task.get();
        log.debug("result:{}",result);
    }
}
