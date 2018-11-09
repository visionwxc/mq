package com.wu.rabbit.mq.service;

import com.wu.rabbit.mq.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MsgProducer implements RabbitTemplate.ConfirmCallback {

    private static final Logger logger = LoggerFactory.getLogger(MsgProducer.class);

    //由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public MsgProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);//设置为原型模式，是因为可能有几种回调机制
    }

    public void  sendMsg(String content){
        //这个是消息的唯一id
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        //消息放入对应的队列A中去
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_A,RabbitConfig.ROUTINGKEY_A,content,correlationData);
    }

    //回调
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        logger.info("回调id: "+ correlationData);
        if(b){
            logger.info("消息成功消费");
        }else {
            logger.error("消息消费失败 "+ s);
        }
    }
}
