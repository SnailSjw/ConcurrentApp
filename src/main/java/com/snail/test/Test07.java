package com.snail.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 训练：交替输出
 *
 * @author : snail
 * @date : 2021-12-14 17:16
 **/
@Slf4j(topic = "c.Test07")
public class Test07 {
    public static void main(String[] args) {
        WaitNotify wn = new WaitNotify(1,5);
        new Thread(()->{
            wn.print("A",1,2);
        }).start();
        new Thread(()->{
            wn.print("B",2,3);
        }).start();
        new Thread(()->{
            wn.print("C",3,1);
        }).start();
    }

}
class WaitNotify{
    /**
     * 等待标识
     */
    private int waitFlag;
    /**
     * 循环次数
     */
    private int loopNumber;

    public WaitNotify(int waitFlag, int loopNumber) {
        this.waitFlag = waitFlag;
        this.loopNumber = loopNumber;
    }
    public void print(String content,int waitFlag,int nextFlag) {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this){
                while (this.waitFlag != waitFlag){
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(content);
                this.waitFlag = nextFlag;
                this.notifyAll();
            }
        }
    }
}
