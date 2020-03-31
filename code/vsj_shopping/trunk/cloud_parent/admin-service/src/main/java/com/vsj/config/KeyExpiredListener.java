package com.vsj.config;

import com.vsj.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;


/**
 * 监听key过期
 */
public class KeyExpiredListener extends KeyExpirationEventMessageListener {

    private Logger log = LogManager.getLogger(KeyExpiredListener.class);
    @Autowired
    OrderService orderService;

    public KeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        super.onMessage(message, pattern);
        //message.getBody() is key
        String body = new String(message.getBody());
        dealWith(body);
    }

    private void dealWith(String body) {
        String[] r = getType(body);
        switch (r[0]) {
            case "order:order_lose:": {
                orderService.deleteOrder(r[1]);
                System.out.println("删除过期订单" + r[1]);
            }
            break;
            case "order:out_sign:": {
                orderService.updateOutSignOrder(r[1]);
                System.out.println("系统自动签收订单" + r[1]);
            }
            break;
            case "order:settle_accounts:": {
                orderService.updateSettleOrder(r[1]);
                System.out.println("系统自动结算订单" + r[1]);
            }
            break;
        }
    }

    private String[] getType(String body) {
        return body.split("@");
    }
}
