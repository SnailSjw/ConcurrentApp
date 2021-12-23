package com.snail.test.deepcopy;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author : snail
 * @date : 2021-12-23 16:26
 **/
@Slf4j(topic = "c.StrategyTwoService")
public class StrategyTwoService implements StrategyService {
    @Override
    public void sendMsg(List<Msg> msgList, List<String> deviceIdList) {
        for (Msg msg : msgList) {
            msg.setDataId("TwoService_"+msg.getDataId());
            log.debug("{} {}",msg.getDataId(), JSON.toJSONString(deviceIdList));
        }
    }
}
