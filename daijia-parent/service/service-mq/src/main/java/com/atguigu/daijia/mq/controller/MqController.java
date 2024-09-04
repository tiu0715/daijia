package com.atguigu.daijia.mq.controller;

import com.atguigu.daijia.common.result.Result;
import com.atguigu.daijia.common.service.RabbitService;
import com.atguigu.daijia.mq.config.DelayedMqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/mq")
public class MqController {


    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private
    RabbitService rabbitService;

/**
 * 消息发送延迟消息：基于延迟插件使用,使用插件后交换机会暂存消息固交换器无法即时路由消息到队列
 */
//@GetMapping("/sendDelayMsg")
//public Result sendDelayMsg() {
//    rabbitTemplate.convertAndSend(DelayedMqConfig.exchange_delay,
//            DelayedMqConfig.routing_delay,
//            "基于延迟插件-我是延迟消息",
//            (message -> {
//                //设置消息ttl
//                message.getMessageProperties().setDelay(10000);
//                return message;
//            })
//    );
//    log.info("基于延迟插件-发送延迟消息成功");
//    return Result.ok();
//}


    /**
     * 消息发送延迟消息：基于延迟插件使用
     */
    @GetMapping("/sendDelayMsg")
    public Result sendDelayMsg() {
        //调用工具方法发送延迟消息
        int delayTime = 10;
        rabbitService.sendDealyMessage(DelayedMqConfig.exchange_delay, DelayedMqConfig.routing_delay, "我是延迟消息", delayTime);
        return Result.ok();
    }


}