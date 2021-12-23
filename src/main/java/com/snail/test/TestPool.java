package com.snail.test;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义线程池
 *
 * @author : snail
 * @date : 2021-12-16 09:49
 **/
@Slf4j(topic = "c.TestPool")
public class TestPool {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(3,2,1000,TimeUnit.MILLISECONDS);
        for (int i = 0; i < 5; i++) {
            int j = i;
            threadPool.execute(()->{
                log.debug("{}",j);
            });
        }
    }
}

@FunctionalInterface
interface RejectPolicy<T>{
    /**
     * 拒绝策略
     * @param queue
     * @param task
     */
    void reject(BlockingQueue<T> queue, T task);
}

/**
 * 线程池
 */
@Slf4j(topic = "c.TreadPool")
class ThreadPool {
    /**
     * 任务队列
     */
    private BlockingQueue<Runnable> taskQueue ;

    private HashSet<Worker> workers = new HashSet<>();
    /**
     * 核心线程数
     */
    private int coreSize;

    /**
     * 超时时间
     */
    private long timeout;

    /**
     * 超时时间单位
     */
    private TimeUnit timeUnit;

    public void execute(Runnable task){
        synchronized (workers){
            if (workers.size() < coreSize){
                Worker worker = new Worker(task);
                log.debug("创建线程 {},{}",worker,task);
                workers.add(worker);
                worker.start();
            }else {
                taskQueue.put(task);
            }
        }
    }

    /**
     * 构造方法
     *
     * @param capacity 任务队列大小
     * @param coreSize 线程池核心线程大小
     * @param timeout 等待时间
     * @param timeUnit 等待时间单位
     */
    public ThreadPool(int capacity, int coreSize, long timeout, TimeUnit timeUnit) {
        this.taskQueue = new BlockingQueue<>(capacity);
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    class Worker extends Thread{
        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
        //    当task不为null执行任务
        //    当task为null，从任务队列中获取任务执行
        //     while (task != null || (task = taskQueue.take()) != null){
            while (task != null || (task = taskQueue.poll(timeout,timeUnit)) != null){
                try {
                    log.debug("正在执行：{}",task);
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    task = null;
                }
            }
            synchronized (workers){
                log.debug("工作线程被移除{}",this);
                workers.remove(this);
            }
        }
    }

}

/**
 * 任务队列
 *
 * @param <T>
 */
@Slf4j(topic = "c.BlockingQueue")
class BlockingQueue<T> {
    /**
     * 容量
     */
    private int capacity;

    private ReentrantLock lock = new ReentrantLock();

    /**
     * 生产者条件变量
     */
    private Condition fullCondition = lock.newCondition();

    /**
     * 消费者条件变量
     */
    private Condition entryCondition = lock.newCondition();

    private Deque<T> queue = new ArrayDeque<>();

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public T poll(long timeout, TimeUnit unit){
        lock.lock();
        try {
            // 转为纳秒值
            long nanos = unit.toNanos(timeout);
            while (queue.isEmpty() ){
                try {
                    if (nanos <= 0){
                        return null;
                    }
                    // 返回值为剩余时间，防止虚假唤醒后再次等待初始时间。
                    nanos = entryCondition.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullCondition.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 带超时阻塞的添加
     *
     * @param t 任务
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return 是否添加成功
     */
    public boolean offer(T t,long timeout,TimeUnit unit){
        lock.lock();
        try {
            long nanos = unit.toNanos(timeout);
            while (queue.size() == capacity){
                try {
                    log.debug("等待加入任务队列...{}",t);
                    if (nanos <=0){
                        return false;
                    }
                    nanos = fullCondition.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.addLast(t);
            log.debug("加入任务队列{}",t);
            entryCondition.signal();
            return true;
        }finally {
            lock.unlock();
        }
    }

    /**
     * 获取任务
     *
     * @return
     */
    public T take(){
        lock.lock();
        try {
            while (queue.isEmpty() ){
                try {
                    entryCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullCondition.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 提交任务
     *
     * @param t
     */
    public void put(T t){
        lock.lock();
        try {
            while (queue.size() == capacity){
                try {
                    log.debug("等待加入任务队列...{}",t);
                    fullCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.addLast(t);
            log.debug("加入任务队列{}",t);
            entryCondition.signal();
        }finally {
            lock.unlock();
        }
    }

    /**
     * 获取大小
     *
     * @return
     */
    public int size(){
        lock.lock();
        try {
            return queue.size();
        }finally {
            lock.unlock();
        }
    }

}
