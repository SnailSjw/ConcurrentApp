package com.snail.test.deepcopy;

import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import java.util.List;

/**
 * @author : snail
 * @date : 2021-12-23 16:26
 **/
@Slf4j(topic = "c.StrategyOneService")
public class StrategyOneService implements StrategyService {
    @Override
    public void sendMsg(List<Msg> msgList, List<String> deviceIdList) {
        for (Msg msg : msgList) {
            msg.setDataId("OneService_"+msg.getDataId());
            log.debug("{} {}",msg.getDataId(),JSON.toJSONString(deviceIdList));
        }
    }
}
