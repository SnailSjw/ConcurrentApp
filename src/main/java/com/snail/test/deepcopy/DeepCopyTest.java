package com.snail.test.deepcopy;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.util.*;

/**
 * 测试问题：对需要复用但是会被下游修改的参数要进行深拷贝
 *  常见的有两种情况需要深复制
 *      ·引用类型且需要复用但是被下游修改的参数，需要深复制
 *      ·引用类型作为集合元素时，如果使用这个集合作为另外一个集合的构造函数参数，会导致两个集合中同一个位置的元素指向的是同一个引用，
 *      这导致对引用的修改在两个集合中都可见，所以这时候需要对引用元素进行深复制。
 *
 * @author : snail
 * @date : 2021-12-23 16:21
 **/
@Slf4j(topic = "c.DeepCopyTest")
public class DeepCopyTest {
    static Map<Integer,StrategyService> serviceMap = new HashMap<>();
    static {
        serviceMap.put(111,new StrategyOneService());
        serviceMap.put(222,new StrategyTwoService());
    }





    public static void main(String[] args) {
        Map<Integer, List<String>> appKeyMap = new HashMap<>();
        List<String> oneList = new ArrayList<>();
        oneList.add("device_id1");
        appKeyMap.put(111,oneList);

        List<String> twoList = new ArrayList<>();
        twoList.add("device_id2");
        appKeyMap.put(222,twoList);

        List<Msg> msgList = new ArrayList<>();
        Msg msg = new Msg();
        msg.setDataId("abc");
        msg.setBody("hello");
        msgList.add(msg);

        Iterator<Integer> iterator = appKeyMap.keySet().iterator();
        Map<Integer,List<Msg>> appKeyMsgMap = new HashMap<>();
        while (iterator.hasNext()){
            // 要进行深拷贝
            // appKeyMsgMap.put(iterator.next(),new ArrayList<>(msgList));
            Integer next = iterator.next();
            List<Msg> cp_msgList = new ArrayList<>();
            Iterator<Msg> it = msgList.iterator();
            while (it.hasNext()){
                Msg m= null;
                try {
                    m = (Msg)BeanUtils.cloneBean(it.next());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (null != m){
                    cp_msgList.add(m);
                }
            }
            appKeyMsgMap.put(next,cp_msgList);
        }
        iterator = appKeyMap.keySet().iterator();
        while (iterator.hasNext()){
            Integer next = iterator.next();
            StrategyService strategyService = serviceMap.get(next);
            if (!Objects.isNull(strategyService)){
                // strategyService.sendMsg(msgList,appKeyMap.get(next));
                strategyService.sendMsg(appKeyMsgMap.get(next),appKeyMap.get(next));
            }else {
                log.debug(String.format("appkey:%s, is not registerd service",next));
            }
        }
    }
}
