package com.leyou.page.mq;

import com.leyou.page.service.PageService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PageListener {
    @Autowired
    private PageService pageService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "page.item.update.queue",durable = "true"),
            exchange = @Exchange(value = "ly.item.exchange", type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}
    ))
    public void newPage(Long spuId){
        pageService.creatHTML(spuId);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "page.item.delete.queue",durable = "true"),
            exchange = @Exchange(value = "ly.item.exchange", type = ExchangeTypes.TOPIC),
            key = {"item.delete"}
    ))
    public void deletePage(Long spuId){
        pageService.deleteHTML(spuId);
    }

}
