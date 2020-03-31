package com.leyou.search.mq;

import com.leyou.search.service.SearchService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemListener {
    @Autowired
    private SearchService searchService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "search.item.insert.queue",durable = "true"),
            exchange = @Exchange(value = "ly.item.exchange",type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}
    ))
    public void listerInsertAndUpdate(Long spuId){
        if(spuId == null){
            return;
        }
        //给索引库修改或者新增商品
        searchService.insertAndFUpdate(spuId);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "search.item.delete.queue",durable = "true"),
            exchange = @Exchange(value = "ly.item.exchange",type = ExchangeTypes.TOPIC),
            key = {"item.delete"}
    ))
    public void listerDelete(Long spuId){
        if(spuId == null){
            return;
        }
        //给索引库修改或者新增商品
        searchService.delete(spuId);
    }

}
